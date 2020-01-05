package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * interface for run different types of command
 */

public interface AppCase {
    /**
     * @param appArgs
     * @param currentDirectory
     * @param input
     * @param output
     * @throws IOException
     */
    void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException;
}