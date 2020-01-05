package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public abstract class AppDecorator implements AppCase {

    private AppCase appCase;

    public AppDecorator(AppCase appCase) {
        this.appCase = appCase;
    }

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        appCase.runCommand(appArgs, currentDirectory, input, output);
    }
}