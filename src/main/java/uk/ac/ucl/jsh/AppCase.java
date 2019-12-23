package uk.ac.ucl.jsh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public interface AppCase {
    void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException;
}

class cd implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        if (appArgs.isEmpty()) {
            throw new RuntimeException("cd: missing argument");
        } else if (appArgs.size() > 1) {
            throw new RuntimeException("cd: too many arguments");
        }
        String dirString = appArgs.get(0);
        File dir = new File(currentDirectory, dirString);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("cd: " + dirString + " is not an existing directory");
        }
        try {
            currentDirectory = dir.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Jsh jsh = new Jsh();
        jsh.setcurrentDirectory(currentDirectory);
    }
}

class pwd implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(currentDirectory);
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}

class ls implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {

        OutputStreamWriter writer = new OutputStreamWriter(output);

        File currDir;
        if (appArgs.isEmpty()) {
            currDir = new File(currentDirectory);
        } else if (appArgs.size() == 1) {
            currDir = new File(appArgs.get(0));
        } else {
            throw new RuntimeException("ls: too many arguments");
        }
        try {
            File[] listOfFiles = currDir.listFiles();
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
        } catch (NullPointerException e) {
            throw new RuntimeException("ls: no such directory");
        }
    }

}

class cat implements AppCase {

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
            }
            else {
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input));
                String stringInStdin = null;
                while ((stringInStdin = stdinReader.readLine()) != null) {
                    stdoutWriter.write(String.valueOf(stringInStdin));   
                    stdoutWriter.write(System.getProperty("line.separator"));
                    stdoutWriter.flush();
                }
            }
        } 
        else { // get from user input
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

class echo implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        if (appArgs.isEmpty()) {
            writer.flush();
        }
        else {
            for (int i = 0; i < appArgs.size()-1; i++) {
                String arg = appArgs.get(i);
                writer.write(arg);
                writer.write(" ");
                writer.flush();
            }
            String lastArg = appArgs.get(appArgs.size()-1);
            writer.write(lastArg);
            writer.flush();
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}

class head implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        if (appArgs.isEmpty()) {
            BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
            if (input == null) {
                throw new RuntimeException("head: missing arguments");
            }
            else {
                int headLines = 10;
                BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input));
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

        else if (appArgs.size() != 1 && appArgs.size() != 3) {
            BufferedWriter stdoutWriter = new BufferedWriter(new OutputStreamWriter(output));
            int headLines = 10;
            if (appArgs.size() == 2) {
                if (input == null) {
                    throw new RuntimeException("head: wrong arguments");
                }
                else {
                    if (!appArgs.get(0).equals("-n")) {
                        throw new RuntimeException("head: wrong argument " + appArgs.get(0));
                    }
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
            else {
                throw new RuntimeException("head: wrong arguments");
            }
        }

        else {
            if (appArgs.size() == 3 && !appArgs.get(0).equals("-n")) {
                throw new RuntimeException("head: wrong argument " + appArgs.get(0));
            }
            int headLines = 10;
            String headArg;
            if (appArgs.size() == 3) {
                try {
                    headLines = Integer.parseInt(appArgs.get(1));
                } catch (Exception e) {
                    throw new RuntimeException("head: wrong argument " + appArgs.get(1));
                }
                headArg = appArgs.get(2);
            } else {
                headArg = appArgs.get(0);
            }
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

class tail implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        if (appArgs.isEmpty()) {
            throw new RuntimeException("tail: missing arguments");
        }
        if (appArgs.size() != 1 && appArgs.size() != 3) {
            throw new RuntimeException("tail: wrong arguments");
        }
        if (appArgs.size() == 3 && !appArgs.get(0).equals("-n")) {
            throw new RuntimeException("tail: wrong argument " + appArgs.get(0));
        }
        int tailLines = 10;
        String tailArg;
        if (appArgs.size() == 3) {
            try {
                tailLines = Integer.parseInt(appArgs.get(1));
            } catch (Exception e) {
                throw new RuntimeException("tail: wrong argument " + appArgs.get(1));
            }
            tailArg = appArgs.get(2);
        } else {
            tailArg = appArgs.get(0);
        }
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

class grep implements AppCase {

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        if (appArgs.isEmpty()) {
            throw new RuntimeException("grep: missing arguments");
        }
        if (appArgs.size() == 1) {
            Pattern grepPattern = Pattern.compile(appArgs.get(0));
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = grepPattern.matcher(line);
                    if (matcher.find()) {
                        writer.write(line);
                        writer.write(System.getProperty("line.separator"));
                        writer.flush();
                    }
                }
            }
        } else {
            Pattern grepPattern = Pattern.compile(appArgs.get(0));
            int numOfFiles = appArgs.size() - 1;
            Path filePath;
            Path[] filePathArray = new Path[numOfFiles];
            Path currentDir = Paths.get(currentDirectory);
            for (int i = 0; i < numOfFiles; i++) {
                filePath = currentDir.resolve(appArgs.get(i + 1));
                if (Files.notExists(filePath) || Files.isDirectory(filePath) || !Files.exists(filePath)
                        || !Files.isReadable(filePath)) {
                    throw new RuntimeException("grep: wrong file argument");
                }
                filePathArray[i] = filePath;
            }
            for (int j = 0; j < filePathArray.length; j++) {
                Charset encoding = StandardCharsets.UTF_8;
                try (BufferedReader reader = Files.newBufferedReader(filePathArray[j], encoding)) {
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        Matcher matcher = grepPattern.matcher(line);
                        if (matcher.find()) {
                            writer.write(line);
                            writer.write(System.getProperty("line.separator"));
                            writer.flush();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("grep: cannot open " + appArgs.get(j + 1));
                }
            }
        }
    }
}

class sed implements AppCase {
    
    // sed REPLACEMENT [FILE]
    // REPLACEMENT :: s/regexp/replacementString/
    //                s/regexp/replacementString/g

    @Override
    public void runCommand(ArrayList<String> appArgs, String currentDirectory, InputStream input, OutputStream output)
            throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);

        if (appArgs.isEmpty()){throw new RuntimeException("sed: missing arguments");}
        
        // first SYMBOL after s is used as delimiter for extracting regexp and replacement
        String replacement = appArgs.get(0);
        String delimiter = Character.toString(replacement.charAt(1));
        String[] replacementArgs = replacement.split(delimiter, 0);
        Pattern regexPattern = Pattern.compile(replacementArgs[1]);
        String regexp = replacementArgs[1];
        String replacementString = replacementArgs[2];
        Boolean global = false;
        if (!replacementArgs[0].equals("s")){throw new RuntimeException("sed: wrong replacement format, replace "+replacementArgs[0]+delimiter+ " with s"+delimiter);}

        if (replacementArgs.length == 4)
        {
            if (replacementArgs[3].equals("g")){global = true;}
            else {throw new RuntimeException("sed: wrong global spedifier, replace "+delimiter+replacementArgs[4]+ " with "+delimiter+"g");}
        }
        
        if (appArgs.size() == 1) // read strings from standard input, and print to stdard output
        {
            try (BufferedReader stdinReader = new BufferedReader(new InputStreamReader(input)))
            {
                StringBuffer replacedString = new StringBuffer();
                String stringInStdin = null;
                while ((stringInStdin = stdinReader.readLine()) != null) 
                {
                    Matcher matcher = regexPattern.matcher(stringInStdin);
                    if (matcher.find())
                    {
                        String newLine;
                        if (global == true){newLine = stringInStdin.replaceAll(regexp, replacementString);}
                        else {newLine = stringInStdin.replaceFirst(regexp, replacementString);}
                        replacedString.append(newLine);
                        replacedString.append('\n');
                    }
                    else
                    {
                        replacedString.append(stringInStdin);
                        replacedString.append('\n');
                    }
                }
                String inputStr = replacedString.toString();
                writer.write(inputStr);
                writer.flush();
            }
            catch (NullPointerException e)
            {
                throw new RuntimeException("sed: cannot read from stdin, please provide filename");
            }
        }
        else // read from files, and operate on file contents
        {
            int i;
            for (i = 1; i<appArgs.size() ;i++)
            {
                String arg = appArgs.get(i);
                editFile(currentDirectory, arg, replacement, regexPattern, regexp, replacementString, global);
            }
        }
    }

    public void editFile(String currentDirectory, String arg, String replacement, Pattern regexPattern, String regexp, String replacementString, Boolean global)
    {
        arg.trim();
        Charset encoding = StandardCharsets.UTF_8;
        File currFile = new File(currentDirectory + File.separator + arg);
        if (currFile.exists()) {
            StringBuffer replacedString = new StringBuffer();

            Path filePath = Paths.get(currentDirectory + File.separator + arg);
            try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                String line = null;
                while ((line = reader.readLine()) != null) 
                {
                    Matcher matcher = regexPattern.matcher(line);
                    if (matcher.find())
                    {
                        String newLine;
                        if (global == true){newLine = line.replaceAll(regexp, replacementString);}
                        else {newLine = line.replaceFirst(regexp, replacementString);}
                        replacedString.append(newLine);
                        replacedString.append('\n');
                    }
                    else
                    {
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
            throw new RuntimeException("sed: file "+arg+ " does not exist");
        }
    }
}
