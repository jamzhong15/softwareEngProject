package uk.ac.ucl.jsh;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Sed implements AppCase {

    // sed REPLACEMENT [FILE]
    // REPLACEMENT :: s/regexp/replacementString/
    // s/regexp/replacementString/g

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        if (appArgs.isEmpty()) {
            throw new RuntimeException("sed: missing arguments");
        }

        // first SYMBOL after s is used as delimiter for extracting regexp and
        // replacement
        String replacement = appArgs.get(0);
        String delimiter = Character.toString(replacement.charAt(1));
        if(delimiter.equals("\\"))
        {
            delimiter = "\\\\";
        }
        else if (delimiter.equals("|"))
        {
            delimiter = "\\|";
        }
        String[] replacementArgs = replacement.split(delimiter, 0);
        Pattern regexPattern = Pattern.compile(replacementArgs[1]);
        String regexp = replacementArgs[1];
        String replacementString = replacementArgs[2];
        Boolean global = false;
        if (!replacementArgs[0].equals("s")) {
            throw new RuntimeException(
                    "sed: wrong replacement format, replace " + replacementArgs[0] + delimiter + " with s" + delimiter);
        }
        if (replacementArgs.length == 4) {
            if (replacementArgs[3].equals("g")) {
                global = true;
            } else {
                throw new ArrayIndexOutOfBoundsException("sed: wrong global specifier, replace " + delimiter
                        + replacementArgs[3] + " with " + delimiter + "g");
            }
        }
        if (replacementArgs.length > 4) {
            throw new RuntimeException("sed: too many arguments. Try s" + delimiter + replacementArgs[1] + delimiter
                    + replacementArgs[2] + delimiter + "g");
        }

        if (appArgs.size() == 1) // read strings from standard input, and print to stdard output
        {
            try (BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input))) {
                StringBuffer replacedString = new StringBuffer();
                String stringInStdin = null;
                while ((stringInStdin = stdinReader.readLine()) != null) {
                    Matcher matcher = regexPattern.matcher(stringInStdin);
                    if (matcher.find()) {
                        String newLine;
                        if (global == true) {
                            newLine = stringInStdin.replaceAll(regexp, replacementString);
                        } else {
                            newLine = stringInStdin.replaceFirst(regexp, replacementString);
                        }
                        replacedString.append(newLine);
                        replacedString.append('\n');
                    } else {
                        replacedString.append(stringInStdin);
                        replacedString.append('\n');
                    }
                }
                String inputStr = replacedString.toString();
                writer.write(inputStr);
                writer.flush();
            } catch (NullPointerException e) {
                throw new RuntimeException("sed: cannot read from stdin, please provide filename");
            }
        } else // read from files, and operate on file contents
        {
            int i;
            for (i = 1; i < appArgs.size(); i++) {
                String arg = appArgs.get(i);
                editFile(currentDirectory, arg, replacement, regexPattern, regexp, replacementString, global);
            }
        }
    }

    public void editFile(String currentDirectory, String arg, String replacement, Pattern regexPattern, String regexp,
            String replacementString, Boolean global) {
        arg.trim();
        Charset encoding = StandardCharsets.UTF_8;
        File currFile = new File(currentDirectory + File.separator + arg);
        if (currFile.exists()) {
            StringBuffer replacedString = new StringBuffer();

            Path filePath = Paths.get(currentDirectory + File.separator + arg);
            try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = regexPattern.matcher(line);
                    if (matcher.find()) {
                        String newLine;
                        if (global == true) {
                            newLine = line.replaceAll(regexp, replacementString);
                        } else {
                            newLine = line.replaceFirst(regexp, replacementString);
                        }
                        replacedString.append(newLine);
                        replacedString.append('\n');
                    } else {
                        replacedString.append(line);
                        replacedString.append('\n');
                    }
                }
                reader.close();

                String inputStr = replacedString.toString();
                FileOutputStream fileOut = new FileOutputStream(currFile);
                fileOut.write(inputStr.getBytes());
                fileOut.close();

            } catch (IOException e) {
                throw new RuntimeException("sed: cannot open " + arg);
            }
        } else {
            throw new RuntimeException("sed: file " + arg + " does not exist");
        }
    }
}
