package uk.ac.ucl.jsh;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

class UnsafeCommand extends AppDecorator {

    public UnsafeCommand(AppCase appCase) {
        super(appCase);
    }
    
    /**
     * unsafe version of command: print out the error message, rather than throw the exception
     */
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output) {
        try {
            super.runCommand(appArgs, currentDirectory, input, output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}