package uk.ac.ucl.jsh;

import java.io.OutputStream;
import java.util.ArrayList;

class UnsafeCommand extends UnsafeDecorator {

    public UnsafeCommand(AppCase appCase) {
        super(appCase);
    }

    public void runCommand(String appName, ArrayList<String> appArgs, String currentDirectory, OutputStream output) {
        try {
            super.runCommand(appName, appArgs, currentDirectory, output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}