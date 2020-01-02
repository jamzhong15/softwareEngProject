package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

class Pwd implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(currentDirectory);
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}