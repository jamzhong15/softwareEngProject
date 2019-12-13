package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public abstract class UnsafeDecorator implements AppCase {

    private AppCase appCase;

    public UnsafeDecorator(AppCase appCase) {
        this.appCase = appCase;
    }

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream stdin, OutputStream output)
            throws IOException {
        appCase.runCommand(appArgs, currentDirectory, output);
    }
}