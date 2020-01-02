package uk.ac.ucl.jsh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Call implements Command {
    private final List<String> inputs;

    public Call(List<String> inputs) {
        this.inputs = inputs;
    }

    @Override
    public void eval() throws IOException {
        Jsh jsh = new Jsh();
        String currentDirectory = jsh.getcurrentDirectory();
        Stack<InputStream> stdin = jsh.getStackInputStream();
        Stack<OutputStream> stdout = jsh.getStackOutputStream();

        String appName = inputs.get(0);
        ArrayList<String> appArgs = new ArrayList<String>(inputs.subList(1, inputs.size()));

        /* checking for backquotes
         * command substitution  
         */
        for (int i = 0; i < appArgs.size(); i++) {
            String arg = appArgs.get(i);
            if (arg.startsWith("`")) {
                appArgs.addAll(i, command_substitution(arg));
                appArgs.remove(arg);
            }
        }

        /*
         * IO-redirection -- works only on files > open outputstream for output
         * redirection < open inputstream for input redirection
         */
        if (appArgs.contains("<") || appArgs.contains(">")) {
            io_redirection(appName, appArgs, currentDirectory, stdout.pop());
        } else
        // normal execution of commands without IO-redirection
        {
            for (int i = 0; i < appArgs.size(); i++) {
                String arg = appArgs.get(i);

                // Check for globbing
                if (arg.contains("*") && !appName.equals("find")) {
                    Globbing glob = new Globbing();
                    ArrayList<String> expandedFiles = glob.expand(currentDirectory, arg);
                    appArgs.addAll(i, expandedFiles);
                    appArgs.remove(arg);
                    // Skip past the expanded files
                    i += expandedFiles.size();
                }

                // Check for double and single quotes
                else if (arg.startsWith("\"") || arg.startsWith("\'")) {
                    appArgs.set(i, arg.substring(1, arg.length() - 1));
                }
            }
            executeCmd(appName, appArgs, currentDirectory, stdin.pop(), stdout.pop());
        }

    }

    public ArrayList<String> command_substitution(String backquoted) throws IOException {
        Jsh jsh = new Jsh();
        ArrayList<String> result = new ArrayList<>();
        String subcommand = backquoted.substring(1, backquoted.length() - 1);
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out = new PipedOutputStream(in);
        jsh.start(subcommand, out);

        out.close();

        BufferedReader subcommand_result_reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String string = null;

        while ((string = subcommand_result_reader.readLine()) != null) {
            result.add(string);
        }

        return result;

    }

    public void io_redirection(String appName, ArrayList<String> appArgs, String currentDirectory, OutputStream stdout) throws IOException {
        int inputRedirOccurrence = 0;
        int outputRedirOccurrence = 0;
        Integer indexOfInputRedir = null;
        Integer indexOfOutputRedir = null;


        for (String str : appArgs) {
            if (str.equals("<")) {
                indexOfInputRedir = appArgs.indexOf(str);
                inputRedirOccurrence++;
            }
            if (str.equals(">")) {
                indexOfOutputRedir = appArgs.indexOf(str);
                outputRedirOccurrence++;
            }
        }
        if (inputRedirOccurrence > 1 || outputRedirOccurrence > 1) // at most one "<" and one ">" is allowed in single
                                                                   // command line
        {
            throw new RuntimeException("IO-redirection: too many redirection operand");
        } else {
            // containing both inputstream and outputstream redirections
            // eg. cat < test.txt > test2.txt
            if (appArgs.contains("<") && appArgs.contains(">")) 
            {
                if (appArgs.size() - indexOfOutputRedir > 2) {
                    throw new RuntimeException("Outputstream redirection: too many files given as outputstream");
                }
                if (indexOfOutputRedir + 1 == appArgs.size()) {
                    throw new RuntimeException("Outpustream redirection: parse error near '\\n'");
                }
                if (appArgs.get(indexOfInputRedir + 1).equals(">")) // situation: cat < > file.txt
                {
                    appArgs.add(indexOfInputRedir+1, null);
                    inputStreamRedirection(appName, appArgs, currentDirectory, null);
                }
                else
                {
                    String fileName = appArgs.get(indexOfOutputRedir + 1);
                    FileOutputStream fileWriter = new FileOutputStream(currentDirectory + File.separator + fileName);
                    inputStreamRedirection(appName, appArgs, currentDirectory, fileWriter);
                }
            } 
            else if (appArgs.contains(">")) // outputstream redirection only
            {
                if (appArgs.size() - indexOfOutputRedir > 2)
                {
                    throw new RuntimeException("Outputstream redirection: too many files given as outputstream");
                }
                if (indexOfOutputRedir + 1 == appArgs.size())  // no files are given
                {
                    throw new RuntimeException("Outpustream redirection: parse error near '\\n'");
                }
                String fileName = appArgs.get(indexOfOutputRedir + 1);
                outputstreamRedirection(appName, appArgs, currentDirectory, fileName);
            } else if (appArgs.contains("<")) // inputstream redirection only
            {
                if (indexOfInputRedir + 1 == appArgs.size()) {
                    appArgs.add(null);
                }
                inputStreamRedirection(appName, appArgs, currentDirectory, stdout);
            }
        }
    }

    public void outputstreamRedirection(String appName, ArrayList<String> appArgs, String currentDirectory,
            String fileName) throws IOException {
        // only one file is permitted to be redirected as outputstream
        int outRedirIndex = appArgs.indexOf(">");
        FileOutputStream fileWriter = new FileOutputStream(currentDirectory + File.separator + fileName);
        ArrayList<String> cmdArgs = new ArrayList<String>(appArgs.subList(0, outRedirIndex));
        executeCmd(appName, cmdArgs, currentDirectory, null, fileWriter);
    }

    public void inputStreamRedirection(String appName, ArrayList<String> appArgs, String currentDirectory,
            OutputStream stdout) throws IOException {
        // multiple files may be used as standard inputstream
        int inRedirIndex = appArgs.indexOf("<");
        ArrayList<String> cmdArgs = new ArrayList<String>(appArgs.subList(0, inRedirIndex));
        ArrayList<String> fileNames = new ArrayList<String>(appArgs.subList(inRedirIndex + 1, appArgs.size()));
        if (fileNames.contains(">")) {
            fileNames = new ArrayList<String>(fileNames.subList(0, fileNames.indexOf(">")));
        }

        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            if (fileName.contains("*")) {
                Globbing glob = new Globbing();
                ArrayList<String> expandedFiles = glob.expand(currentDirectory, fileName);
                fileNames.addAll(i, expandedFiles);
                fileNames.remove(fileName);
                // Skip past the expanded files
                i += expandedFiles.size();
            }
        }
        
        for (String fileName : fileNames) {
            FileInputStream fileReader = new FileInputStream(currentDirectory + File.separator + fileName);
            executeCmd(appName, cmdArgs, currentDirectory, fileReader, stdout);
        }
    }

    public void executeCmd(String appName, ArrayList<String> appArgs, String currentDirectory, InputStream stdin,
            OutputStream stdout) throws IOException {
        if (appName.charAt(0) == '_') {
            appName = appName.substring(1, appName.length());
            AppCase app = new UnsafeCommand(AppFactory.createApp(appName));
            app.runCommand(appArgs, currentDirectory, stdin, stdout);
        } else {
            AppCase app = AppFactory.createApp(appName);
            app.runCommand(appArgs, currentDirectory, stdin, stdout);
        }
    }

}