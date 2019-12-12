package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import java.util.List;

public class Call implements Command {
    private final List<String> inputs;

    public Call(List<String> inputs) {
        this.inputs = inputs;
    }

    @Override
    public void eval(OutputStream output) throws IOException 
    {        
        Jsh jsh = new Jsh();
        String currentDirectory = jsh.getcurrentDirectory();

        
        String appName = inputs.get(0);
        ArrayList<String> appArgs = new ArrayList<String>(inputs.subList(1, inputs.size()));
        if (appName.charAt(0) == '_') 
        {
            appName = appName.substring(1, appName.length());
            AppCase app = new UnsafeCommand(AppFactory.createApp(appName));
            app.runCommand(appArgs, currentDirectory, stdin, output);
        }
        else 
        {
            AppCase app = AppFactory.createApp(appName);
            app.runCommand(appArgs, currentDirectory, output);
        }
    }
}