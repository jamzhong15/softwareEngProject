package uk.ac.ucl.jsh;

import java.io.BufferedReader;
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

/**
 * prints the no. of bytes, words and line in given file
 * e.g. wc -m fileName, print the no. of character in fileName
 *      wc -w fileName, print the no. of words in fileName
 *      wc -l fileName, print the no. of newLine in fileName
 */
class Wc implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {

        OutputStreamWriter writer = new OutputStreamWriter(output);
        int charCount = 0;
        int wordCount = 0;
        int lineCount = 0;

        // Check if it should using standard input
        if (appArgs.isEmpty() || appArgs.size() == 1
                && (appArgs.get(0).equals("-m") || appArgs.get(0).equals("-w") || appArgs.get(0).equals("-l"))) {

            String command = "";

            if (!appArgs.isEmpty()) {
                command = appArgs.get(0);
            }
            // get argument from stdin
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    charCount += line.length() + 1;

                    if (!line.trim().isEmpty()) {
                        String[] words = line.trim().split("\\s+");
                        wordCount += words.length;
                    }
                }

                writeCount(command, charCount, wordCount, lineCount, writer);

            } catch (NullPointerException e) {
                throw new RuntimeException("wc: missing arguments");
            }
        } else {

            // Otherwise use the arguments
            String command = appArgs.get(0);
            if (command.equals("-m") || command.equals("-w") || command.equals("-l")) {
                // Remove the command type from the list so that only file-names are left
                appArgs.remove(0);
            }

            for (String arg : appArgs) {
                Charset encoding = StandardCharsets.UTF_8;
                File currFile = new File(currentDirectory + File.separator + arg);
                if (currFile.exists()) {
                    Path filePath = Paths.get(currFile.getAbsolutePath());

                    try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            lineCount++;
                            charCount += line.length() + 1;

                            if (!line.trim().isEmpty()) {
                                String[] words = line.trim().split("\\s+");
                                wordCount += words.length;
                            }
                        }
                    } catch (IOException e) 
                    {
                        writer.close();
                        throw new RuntimeException("wc: cannot open " + arg);
                    }
                } else 
                {
                    writer.close();
                    throw new RuntimeException("wc: " + arg + " does not exist");
                }
               
            }
            writeCount(command, charCount, wordCount, lineCount, writer);
        }
        
    }

    // used to output the result
    private void writeCount(String command, int charCount, int wordCount, int lineCount, OutputStreamWriter writer) throws IOException {
        switch (command) {
        case "-m":
            writer.write(Integer.toString(charCount));
            break;

        case "-w":
            writer.write(Integer.toString(wordCount));
            break;

        case "-l":
            writer.write(Integer.toString(lineCount));
            break;

        default:
            writer.write(Integer.toString(lineCount) + "\t");
            writer.write(Integer.toString(wordCount) + "\t");
            writer.write(Integer.toString(charCount));
            break;
        }
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}