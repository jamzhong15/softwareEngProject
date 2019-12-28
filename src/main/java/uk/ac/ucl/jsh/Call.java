package uk.ac.ucl.jsh;

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
                System.out.println(i);

                // Skip past the expanded files
                i += expandedFiles.size();
                System.out.println(i);
            }

            // Check for double and single quotes
            else if (arg.startsWith("\"") || arg.startsWith("\'")) 
            {
                appArgs.set(i, arg.substring(1, arg.length() - 1));
            }
        }

        for(int i = 0; i < appArgs.size(); i++)
        {
            String arg = appArgs.get(i);
            if(arg.contains("*") && !appName.equals("find"))
            {
                Globbing glob = new Globbing();
                appArgs.addAll(i, glob.expand(currentDirectory, arg));
                appArgs.remove(arg);
            }
        }

        

        if (appName.charAt(0) == '_') 
        {
            appName = appName.substring(1, appName.length());
            AppCase app = new UnsafeCommand(AppFactory.createApp(appName));
            app.runCommand(appArgs, currentDirectory, stdin.pop(), stdout.pop());
        }
        else 
        {
            AppCase app = AppFactory.createApp(appName);
            app.runCommand(appArgs, currentDirectory, stdin.pop(), stdout.pop());
        }
    }
}