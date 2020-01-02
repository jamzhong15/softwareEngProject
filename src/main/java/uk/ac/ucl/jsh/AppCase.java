package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public interface AppCase {
    void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException;
}