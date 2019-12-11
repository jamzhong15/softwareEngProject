package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Call implements Command {

    @Override
    public void eval(ArrayList<String> input, OutputStream output) throws IOException 
    {        
        Jsh jsh = new Jsh();
        String currentDirectory = jsh.getcurrentDirectory();

        String appName = input.get(0);
        ArrayList<String> appArgs = new ArrayList<String>(input.subList(1, input.size()));
        if (appName.charAt(0) == '_') 
        {
            appName = appName.substring(1, appName.length());
            AppCase app = new UnsafeCommand(AppFactory.createApp(appName));
            app.runCommand(appName, appArgs, currentDirectory, output);
        }
        else 
        {
            AppCase app = AppFactory.createApp(appName);
            app.runCommand(appName, appArgs, currentDirectory, output);
        }

    }
}