package uk.ac.ucl.jsh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.List;
import java.util.Stack;

public class Call implements Command {
    private final List<String> inputs;

    public Call(List<String> inputs) {
        this.inputs = inputs;
    }

    @Override
    public void eval() throws IOException 
    {        
        Jsh jsh = new Jsh();
        String currentDirectory = jsh.getcurrentDirectory();
        Stack <InputStream> stdin = jsh.getStackInputStream();
        Stack <OutputStream> stdout = jsh.getStackOutputStream();

        String appName = inputs.get(0);
        ArrayList<String> appArgs = new ArrayList<String>(inputs.subList(1, inputs.size()));
        
        
        /*   IO-redirection -- works only on files
             > open outputstream for output redirection
             < open inputstream for input redirection
        */
        if (appArgs.contains("<") || appArgs.contains(">"))
        {
            int inputRedirOccurrence = 0;
            int outputRedirOccurrence = 0;
            Integer indexOfInputRedir = null;
            Integer indexOfOutputRedir = null;

            for (String str : appArgs)
            {
                if (str.equals("<"))
                {
                    indexOfInputRedir = appArgs.indexOf(str);
                    inputRedirOccurrence ++; 
                }
                if (str.equals(">"))
                {
                    indexOfOutputRedir = appArgs.indexOf(str);
                    outputRedirOccurrence ++; 
                }
            }
            if (inputRedirOccurrence > 1 || outputRedirOccurrence >1 )  // at most one "<" and one ">" is allowed in single command line
            {
                throw new RuntimeException("IO-redirection: too many redirection operand");
            }
            else
            {
                // containing both inputstream and outputstream redirections
                // eg. cat < test.txt > test2.txt
                if (appArgs.contains("<") && appArgs.contains(">"))  
                {
                    String fileName = appArgs.get(indexOfOutputRedir + 1);
                    FileOutputStream fileWriter = new FileOutputStream(currentDirectory + File.separator + fileName);
                    inputStreamRedirection(appName, appArgs, currentDirectory, fileWriter);
                }
                else if (appArgs.contains(">")) // outputstream redirection only
                {
                    if (indexOfOutputRedir + 1 >= appArgs.size())
                    {
                        throw new RuntimeException("Outputstream redirection: too many files given as outputstream");
                    }
                    String fileName = appArgs.get(indexOfOutputRedir + 1);
                    outputstreamRedirection(appName, appArgs, fileName, currentDirectory);            
                }
                else if (appArgs.contains("<")) // inputstream redirection only
                {
                    inputStreamRedirection(appName, appArgs, currentDirectory, System.out);
                }
            }

        }
        else
        //normal execution of commands without IO-redirection
        {
            for(int i = 0; i < appArgs.size(); i++)
            {
                String arg = appArgs.get(i);

                // Check for globbing
                if(arg.contains("*") && !appName.equals("find"))
                {
                    Globbing glob = new Globbing();
                    ArrayList<String> expandedFiles = glob.expand(currentDirectory, arg);
                    appArgs.addAll(i, expandedFiles);
                    appArgs.remove(arg);
                    // Skip past the expanded files
                    i += expandedFiles.size();
                }

                // Check for double and single quotes
                else if (arg.startsWith("\"") || arg.startsWith("\'")) 
                {
                    appArgs.set(i, arg.substring(1, arg.length() - 1));
                }
            }
            executeCmd(appName, appArgs, currentDirectory, stdin.pop(), stdout.pop());
        }
        
    }

    public void executeCmd(String appName, ArrayList<String> appArgs, String currentDirectory, InputStream stdin, OutputStream stdout) throws IOException
    {
        if (appName.charAt(0) == '_') 
        {
            appName = appName.substring(1, appName.length());
            AppCase app = new UnsafeCommand(AppFactory.createApp(appName));
            app.runCommand(appArgs, currentDirectory, stdin, stdout);
        }
        else 
        {
            AppCase app = AppFactory.createApp(appName);
            app.runCommand(appArgs, currentDirectory, stdin, stdout);
        }
    }



    public void outputstreamRedirection(String appName, ArrayList<String> appArgs, String fileName, String currentDirectory) throws IOException
    {
        // only one file is permitted to be redirected as outputstream 
        int outRedirIndex = appArgs.indexOf(">");        
        FileOutputStream fileWriter = new FileOutputStream(currentDirectory + File.separator + fileName);
        ArrayList<String> cmdArgs = new ArrayList<String>(appArgs.subList(0, outRedirIndex));
        executeCmd(appName, cmdArgs, currentDirectory, null, fileWriter);
    }

    public void inputStreamRedirection(String appName, ArrayList<String> appArgs, String currentDirectory, OutputStream stdout) throws IOException
    {   
        // multiple files may be used as standard inputstream
        int inRedirIndex = appArgs.indexOf("<");
        ArrayList<String> cmdArgs = new ArrayList<String>(appArgs.subList(0, inRedirIndex));

        ArrayList<String> fileNames = new ArrayList<String>(appArgs.subList(inRedirIndex + 1, appArgs.size()));

        for (int i = 0; i < fileNames.size(); i++)
        {
            String fileName = fileNames.get(i);
            if(fileName.contains("*"))
            {
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
}