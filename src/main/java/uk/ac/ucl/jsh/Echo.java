package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.util.ArrayList;
class echo implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        if (appArgs.isEmpty()) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        } 
        else
        {
            for (int i = 0; i < appArgs.size() - 1; i++) {
                String arg = appArgs.get(i);
                writer.write(arg);
                writer.write(" ");
                writer.flush();
            }

            String lastArg = appArgs.get(appArgs.size() - 1);
            writer.write(lastArg);
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}