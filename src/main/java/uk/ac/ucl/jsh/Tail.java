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

class tail implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        if (appArgs.isEmpty()) {
            BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
            if (input == null) {
                throw new RuntimeException("tail: missing arguments");
            } else {
                int tailLines = 10;
                ArrayList<String> storage = new ArrayList<>();
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                for (int i = 0; i < tailLines; i++) {
                    String stringInStdin = null;
                    while ((stringInStdin = stdinReader.readLine()) != null) {
                        storage.add(stringInStdin);
                    }
                    int index = 0;
                    if (tailLines > storage.size()) {
                        index = 0;
                    } else {
                        index = storage.size() - tailLines;
                    }
                    for (int p = index; p < storage.size(); p++) {
                        stdoutWriter.write(storage.get(p) + System.getProperty("line.separator"));
                        stdoutWriter.flush();
                    }
                }
            }
        }

        else if (appArgs.size() == 2 && appArgs.get(0).equals("-n")) {
            int tailLines = 10;
            BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
            if (input == null) {
                throw new RuntimeException("tail: wrong arguments");
            } else {
                try {
                    tailLines = Integer.parseInt(appArgs.get(1));
                } catch (Exception e) {
                    throw new RuntimeException("tail: wrong argument " + appArgs.get(1));
                }

                ArrayList<String> storage = new ArrayList<>();
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input));
                String stringInStdin = null;
                while ((stringInStdin = stdinReader.readLine()) != null) {
                    storage.add(stringInStdin);
                }
                int index = 0;
                if (tailLines > storage.size()) {
                    index = 0;
                } else {
                    index = storage.size() - tailLines;
                }
                for (int p = index; p < storage.size(); p++) {
                    stdoutWriter.write(storage.get(p) + System.getProperty("line.separator"));
                    stdoutWriter.flush();
                }
            }
        }

        else {
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
                    try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
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

}
