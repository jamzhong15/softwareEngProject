package uk.ac.ucl.jsh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class Tail implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {

        if (appArgs.isEmpty()) {
            
            if (input == null) {
                throw new RuntimeException("tail: missing arguments");
            } else {
                int tailLines = 10;
                
                BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                writeFromStdOutput(tailLines, stdinReader, stdoutWriter);

            }
        }

        else if (appArgs.size() == 2 && appArgs.get(0).equals("-n")) {
            int tailLines = 10;
            if (input == null) {
                throw new RuntimeException("tail: wrong arguments");
            } else {
                try {
                    tailLines = Integer.parseInt(appArgs.get(1));
                } catch (Exception e) {
                    throw new RuntimeException("tail: wrong argument " + appArgs.get(1));
                }

                BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input));
                writeFromStdOutput(tailLines, stdinReader, stdoutWriter);
                
            }
        }

        else {
            OutputStreamWriter writer = new OutputStreamWriter(output);
            int tailLines = 10;
            int fileIndex = 0;
            if (appArgs.get(0).equals("-n")) {
                try {
                    tailLines = Integer.parseInt(appArgs.get(1));
                } catch (Exception e) {
                    throw new RuntimeException("head: wrong argument " + appArgs.get(1));
                }
                fileIndex = 2;
            }

            for (; fileIndex < appArgs.size(); fileIndex++) {
                String tailArg = appArgs.get(fileIndex);
                File tailFile = new File(currentDirectory + File.separator + tailArg);
                if (tailFile.exists()) {
                    Charset encoding = StandardCharsets.UTF_8;
                    Path filePath = Paths.get((String) currentDirectory + File.separator + tailArg);
                    ArrayList<String> storage = new ArrayList<>();
                    BufferedReader reader = Files.newBufferedReader(filePath, encoding);
                    try {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            storage.add(line);
                        }
                        int index = 0;
                        if (tailLines > storage.size()) {
                            index = 0;
                        } else {
                            index = storage.size() - tailLines;
                        }
                        for (int i = index; i < storage.size(); i++) {
                            writer.write(storage.get(i) + System.getProperty("line.separator"));
                            writer.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("tail: cannot open " + tailArg);
                    }
                } else {
                    throw new RuntimeException("tail: " + tailArg + " does not exist");
                }

            }

        }

    }

    private void writeFromStdOutput(int tailLines, BufferedReader reader, BufferedWriter writer) throws IOException {
        ArrayList<String> storage = new ArrayList<>();

        String line = null;
        while ((line = reader.readLine()) != null) {
            storage.add(line);
        }
        int index = 0;
        if (tailLines > storage.size()) {
            index = 0;
        } else {
            index = storage.size() - tailLines;
        }
        for (int i = index; i < storage.size(); i++) {
            writer.write(storage.get(i) + System.getProperty("line.separator"));
            writer.flush();
        }
    }

}
