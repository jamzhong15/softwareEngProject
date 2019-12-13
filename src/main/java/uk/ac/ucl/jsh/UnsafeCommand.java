package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

class UnsafeCommand extends UnsafeDecorator {

    public UnsafeCommand(AppCase appCase) {
        super(appCase);
    }

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream stdin, OutputStream output) throws IOException 
    {
        try 
        {
            super.runCommand(appName, appArgs, currentDirectory, output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}