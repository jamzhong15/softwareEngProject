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
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * used to analyse single appCase
 */

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

        // handling double quoted arguments
        // single quotes are final, inner contents counted as single text argument
        appArgs = DoubleQuotedArguments(appArgs);

        // /**
        //  * BACK SLASH
        //  * remove backslash 
        //  */

        // for (int i = 0; i < appArgs.size(); i++) {
        //     String arg = appArgs.get(i);
        //     if (arg.contains("\\")) {
        //         appArgs.add(backSlash(arg));
        //         appArgs.remove(arg);
        //     }
        // }

        /**
         * COMMAND SUBSTITUTION
         * checking for backquotes
         */

        if(appName.startsWith("`"))
        {
            appName = command_substitution(appName).get(0);
        }
        for (int i = 0; i < appArgs.size(); i++) {
            String arg = appArgs.get(i);
            if (arg.startsWith("`")) {
                appArgs.addAll(i, command_substitution(arg));
                appArgs.remove(arg);
            }
        }

        /**
         * IO-REDIRECTION --> works only on files 
         * ">" outputstream redirection
         * "<" inputstream redirection
         */
        if (appArgs.contains("<") || appArgs.contains(">")) 
        {
            io_redirection(appName, appArgs, currentDirectory, stdin.pop(), stdout.pop());
        }
        else
        // normal execution of commands without IO-redirection
        {
            for (int i = 0; i < appArgs.size(); i++) {
                String arg = appArgs.get(i);
                if(arg.startsWith("\'")) // ignore any backquotes o
                {
                    appArgs.set(i, arg.substring(1, arg.length() - 1));
                }
                // Check for globbing
                if (arg.contains("*") && !appName.equals("find")) {
                    Globbing glob = new Globbing();
                    ArrayList<String> expandedFiles = glob.expand(currentDirectory, arg);
                    appArgs.addAll(i, expandedFiles);
                    appArgs.remove(arg);
                    // Skip past the expanded files
                    i += expandedFiles.size();
                }
            }
            executeCmd(appName, appArgs, currentDirectory, stdin.pop(), stdout.pop());
        }
    }

    public ArrayList<String> DoubleQuotedArguments(ArrayList<String> appArgs)
    {
        // Check for double quotes and REVEAL BACKQUOTED contents if any
        for (int i = 0; i < appArgs.size(); i++)
        {
            String arg = appArgs.get(i);
            if (arg.startsWith("\""))  // check for embedded backquotes inside double quotes
            {
                appArgs.set(i, arg.substring(1, arg.length() - 1));

                if (arg.contains("`"))
                {
                    String[] backquoted_contents = appArgs.get(i).split("`", 0);
                    for (int index = 1 ; index < backquoted_contents.length; index +=2)
                    {
                        String backquoted_element = "`" + backquoted_contents[index] + "`";
                        backquoted_element = backquoted_element.trim();
                        Array.set(backquoted_contents, index, backquoted_element);
                    }
                    appArgs.remove(i);
                    ArrayList<String> newlist = new ArrayList<>(Arrays.asList(backquoted_contents));
                    // newlist.remove(0); // by split method, first "`" in arg will be transformed into unwanted " " element.
                    // appArgs.add(backquoted_contents[0].trim());

                    appArgs.addAll(i, newlist);
                    i += backquoted_contents.length;
                }
            }
        }
        return appArgs;
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
            result.add(string.trim());
        }
        return result;
    }

    public void io_redirection(String appName, ArrayList<String> appArgs, String currentDirectory, InputStream stdin, OutputStream stdout) throws IOException {
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
        } 
        else 
        {
            //////////////////////////////////////////////
            // containing both "<" and ">" redirections //
            //    eg. cat < test.txt > test2.txt        //
            //////////////////////////////////////////////
            if (appArgs.contains("<") && appArgs.contains(">")) 
            {
                // no filename given for ">" redirection: throw exception
                // eg. cat < test.txt >  
                if (indexOfOutputRedir + 1 == appArgs.size()) {throw new RuntimeException("Outpustream redirection: parse error near '\\n'");}

                // no filename given for "<" redirection: pass in null, which will throw null exception
                // eg. cat < > test.txt
                else if (appArgs.get(indexOfInputRedir + 1).equals(">"))
                {
                    appArgs.add(indexOfInputRedir+1, null);
                    inputStreamRedirection(appName, appArgs, currentDirectory, null);
                }

                /** 
                 * more than one file args given for ">" redirection:  move extra files to left, as fileName arguments for the command
                 * remove first fileName after "<" symbole, since arguments have higher priority that stdin, remove "<" symbol
                 * eg (appArgs.size() - indexOfOutputRedir > 2):: cat < test.txt > file1.txt file2.txt file3.txt  -->  cat file2.txt file3.txt > file1.txt
                 */

                /** 
                 * more than one file args given for "<" redirection: 
                 * eg (!appArgs.get(indexOfOutputRedir + 2).equals(">")):: cat < test.txt test1.txt test2.txt > file1.txt  -->  cat test1.txt test2.txt > file1.txt
                 * changes into normal outputstream redirection
                 */
                else if (appArgs.size() - indexOfOutputRedir > 2 || !appArgs.get(indexOfInputRedir + 2).equals(">")) 
                {
                    ArrayList<String> modified_appArgs = new ArrayList<>();
                    for (int i = indexOfOutputRedir + 2 ; i < appArgs.size() ; i++)
                    {
                        modified_appArgs.add(appArgs.get(i));
                    }
                    final int outputDirSymbol_index = indexOfOutputRedir; // variable must be final when used in lambda function
                    appArgs.removeIf(argument-> appArgs.indexOf(argument) > outputDirSymbol_index + 1);
                    appArgs.addAll(indexOfOutputRedir, modified_appArgs);

                    appArgs.remove(indexOfInputRedir+1);
                    appArgs.remove((int)indexOfInputRedir);
                    outputstreamRedirection(appName, appArgs, currentDirectory);
                }

                /** 
                 * normal running of combined io-redirection
                 * eg :: cat < *.txt > file.txt
                 */
                else if (appArgs.get(indexOfInputRedir+2).equals(">"))
                {
                    String fileName = appArgs.get(indexOfOutputRedir + 1);
                    FileOutputStream fileWriter = new FileOutputStream(currentDirectory + File.separator + fileName);
                    inputStreamRedirection(appName, appArgs, currentDirectory, fileWriter);
                }
            }
            ///////////////////////////////////////
            //      ">" redirection only         //
            //  eg. echo Hello World > file.txt  //
            ///////////////////////////////////////
            else if (appArgs.contains(">"))
            {
                // no files are given throw exception
                if (indexOfOutputRedir + 1 == appArgs.size()){throw new RuntimeException("Outpustream redirection: parse error near '\\n'");}

                /**
                 * more than one file args given : 
                 * eg. echo Hello World > file1.txt file2.txt file3.txt  -->  echo Hello World file2.txt file3.txt > file1.txt
                 * [Hello World file2.txt file3.txt] is written into file1.txt
                 */
                else if (appArgs.size() - indexOfOutputRedir > 2)
                
                {
                    ArrayList<String> modified_appArgs = new ArrayList<>();
                    for (int i = indexOfOutputRedir + 2 ; i < appArgs.size() ; i++)
                    {
                        modified_appArgs.add(appArgs.get(i));
                    }
                    final int outputDirSymbol_index = indexOfOutputRedir; // variable must be final when used in lambda function
                    appArgs.removeIf(argument->appArgs.indexOf(argument) > outputDirSymbol_index + 1);
                    appArgs.addAll(indexOfOutputRedir, modified_appArgs);
                }

                outputstreamRedirection(appName, appArgs, currentDirectory);
            }
            //////////////////////////////
            //   "<" redirection only   //
            //   eg. cat < file.txt     //
            ////////////////////////////// 
            else if (appArgs.contains("<"))
            {
                // no file arg is given, pass in null file, result in null exception thrown
                if (indexOfInputRedir + 1 == appArgs.size())
                {
                    appArgs.add(null);
                    inputStreamRedirection(appName, appArgs, currentDirectory, stdout);
                }

                /** more than one file arg given for stdin: remove > symbol and first file arg after it, run normal command execution
                 * eg. cat < file1.txt file2.txt  -->  cat file2.txt
                 * note: [file1.txt] can be globbed file name, but is removed regardlessly
                 */
                else if (appArgs.size() - indexOfInputRedir > 2)
                
                {
                    appArgs.remove(indexOfInputRedir+1);
                    appArgs.remove((int)indexOfInputRedir);
                    executeCmd(appName, appArgs, currentDirectory, stdin, stdout);
                }
                else
                // normal inputstream redirection operation on one file arg
                {
                    inputStreamRedirection(appName, appArgs, currentDirectory, stdout);
                }
            }
        }
    }

    public void outputstreamRedirection(String appName, ArrayList<String> appArgs, String currentDirectory) throws IOException {
        // only one file is permitted to be redirected as outputstream
        int outRedirIndex = appArgs.indexOf(">");
        ArrayList<String> cmdArgs = new ArrayList<String>(appArgs.subList(0, outRedirIndex));
        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add(appArgs.get(outRedirIndex + 1));

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

        for (String fileName : fileNames)
        {
            FileOutputStream fileWriter = new FileOutputStream(currentDirectory + File.separator + fileName);
            executeCmd(appName, cmdArgs, currentDirectory, null, fileWriter);   
        }
    }

    public void inputStreamRedirection(String appName, ArrayList<String> appArgs, String currentDirectory,
            OutputStream stdout) throws IOException {
        // globbed filename is allowed
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

        for (String fileName : fileNames) 
        {
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

//     public String backSlash (String arg) throws IOException {
//         String resultString = arg;
//         for (int i = 0; i < arg.length(); i++) {
//             if (arg.charAt(i) == '\\') {
//                 StringBuilder sb = new StringBuilder(arg);
//                 sb.deleteCharAt(i);
//                 resultString = sb.toString();
//             }
//         }
//         return resultString;
//     }
}