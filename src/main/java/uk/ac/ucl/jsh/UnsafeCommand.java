package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Stack;

class UnsafeCommand extends UnsafeDecorator {

    public UnsafeCommand(AppCase appCase) {
        super(appCase);
    }

    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output) {
        try {
            super.runCommand(appArgs, currentDirectory, input, output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}