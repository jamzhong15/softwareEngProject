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