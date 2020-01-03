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

class Head implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {

        OutputStreamWriter writer = new OutputStreamWriter(output);
        
        // read first 10 lines in stdin
        if (appArgs.isEmpty()) {
            BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
            if (input == null) {
                throw new RuntimeException("head: missing arguments");
            } else {
                int headLines = 10;
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                String stringInStdin = null;
                for (int i = 0; i < headLines; i++) {
                    if ((stringInStdin = stdinReader.readLine()) != null) {
                        stdoutWriter.write(String.valueOf(stringInStdin));
                        stdoutWriter.write(System.getProperty("line.separator"));
                        stdoutWriter.flush();
                    }
                }
            }
        }
        // read chosen number of lines in stdin
        else if (appArgs.size() == 2 && appArgs.get(0).equals("-n"))
        {
            int headLines = 10;
            BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));

            if (input == null) {
                throw new RuntimeException("head: wrong arguments");
            } else {
                try {
                    headLines = Integer.parseInt(appArgs.get(1));
                } catch (Exception e) {
                    throw new RuntimeException("head: wrong argument " + appArgs.get(1));
                }
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input));
                for (int i = 0; i < headLines; i++) {
                    String stringInStdin = null;
                    if ((stringInStdin = stdinReader.readLine()) != null) {
                        stdoutWriter.write(stringInStdin);
                        stdoutWriter.write(System.getProperty("line.separator"));
                        stdoutWriter.flush();
                    }
                }
            }
        }
        // read from files
        else {
            int headLines = 10;
            int fileIndex = 0;
            if (appArgs.get(0).equals("-n")) {
                try {
                    headLines = Integer.parseInt(appArgs.get(1));
                } catch (Exception e) {
                    throw new RuntimeException("head: wrong argument " + appArgs.get(1));
                }
                fileIndex = 2;
            }

            for (; fileIndex < appArgs.size(); fileIndex++) {
                String headArg = appArgs.get(fileIndex);
                File headFile = new File(currentDirectory + File.separator + headArg);
                if (headFile.exists()) {
                    Charset encoding = StandardCharsets.UTF_8;
                    Path filePath = Paths.get((String) currentDirectory + File.separator + headArg);
                    try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                        for (int i = 0; i < headLines; i++) {
                            String line = null;
                            if ((line = reader.readLine()) != null) {
                                writer.write(line);
                                writer.write(System.getProperty("line.separator"));
                                writer.flush();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("head: cannot open " + headArg);
                    }
                } else {
                    throw new RuntimeException("head: " + headArg + " does not exist");
                }

            }
        }

    }
}
