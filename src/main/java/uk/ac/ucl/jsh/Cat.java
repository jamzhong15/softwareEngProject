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

class Cat implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        /*
         * if appArgs is empty (meaning no files are specified in the argument), check
         * if stack inputstream is null. if stack inputstream is not null, pop from
         * stack and read this inputstream as input. if stack inputstream is null, then
         * throw exception.
         */
        if (appArgs.isEmpty()) { // try to read from stdin
            BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
            if (input == null) {
                throw new RuntimeException("cat: missing arguments");
            } else {
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input));
                String stringInStdin = null;
                while ((stringInStdin = stdinReader.readLine()) != null) {
                    stdoutWriter.write(String.valueOf(stringInStdin));
                    stdoutWriter.write(System.getProperty("line.separator"));
                    stdoutWriter.flush();
                }
            }
        } else { // get from user input
            for (String arg : appArgs) {
                Charset encoding = StandardCharsets.UTF_8;
                File currFile = new File(currentDirectory + File.separator + arg);
                if (currFile.exists()) {
                    Path filePath = Paths.get(currentDirectory + File.separator + arg);

                    try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            writer.write(String.valueOf(line));
                            writer.write(System.getProperty("line.separator"));
                            writer.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("cat: cannot open " + arg);
                    }
                } else {
                    throw new RuntimeException("cat: file does not exist");
                }
            }

        }

    }
}