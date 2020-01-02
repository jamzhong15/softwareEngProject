package uk.ac.ucl.jsh;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

class ls implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {

        File currDir;

        if (appArgs.isEmpty()) {
            currDir = new File(currentDirectory);
            listFiles(currDir, output);
        } else if (appArgs.size() == 1) {
            currDir = new File(currentDirectory + "/" + appArgs.get(0));
            if (!currDir.isDirectory()) {
                throw new RuntimeException("ls: cannot access '" + appArgs.get(0) + "': No such file or directory");
            }
            listFiles(currDir, output);
        } else {
            for (String arg : appArgs) {
                currDir = new File(currentDirectory + "/" + arg);
                if (!currDir.isDirectory()) {
                    throw new RuntimeException("ls: cannot access '" + arg + "': No such file or directory");
                } else {
                    OutputStreamWriter writer = new OutputStreamWriter(output);
                    writer.write(arg + ":\n");
                    writer.flush();
                    listFiles(currDir, output);
                }
            }
        }

    }

    private void listFiles(File dir, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        File[] listOfFiles = dir.listFiles();
        boolean atLeastOnePrinted = false;
        for (File file : listOfFiles) {
            if (!file.getName().startsWith(".")) {
                writer.write(file.getName());
                writer.write("\t"); 
                writer.flush();
                atLeastOnePrinted = true;
            }
        }
        if (atLeastOnePrinted) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}