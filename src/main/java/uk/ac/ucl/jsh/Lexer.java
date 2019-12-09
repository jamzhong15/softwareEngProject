package uk.ac.ucl.jsh;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lexer
{
    ArrayList<String> rawCommands;
    String currentDirectory;

    public Lexer(ArrayList<String> rawCommands, String currentDirectory)
    {
        this.rawCommands = rawCommands;
        this.currentDirectory = currentDirectory;
    }

    public ArrayList<String> getTokenList() throws IOException
    {
        ArrayList<String> tokens = new ArrayList<String>();

        for (String rawCommand : rawCommands) 
        {
            String spaceRegex = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
            Pattern regex = Pattern.compile(spaceRegex);
            Matcher regexMatcher = regex.matcher(rawCommand);
            String nonQuote;
            while (regexMatcher.find()) {
                if (regexMatcher.group(1) != null || regexMatcher.group(2) != null) {
                    String quoted = regexMatcher.group(0).trim();
                    tokens.add(quoted.substring(1,quoted.length()-1));
                } 
                else 
                {
                    nonQuote = regexMatcher.group().trim();
                    ArrayList<String> globbingResult = new ArrayList<String>();
                    Path dir = Paths.get(currentDirectory);
                    DirectoryStream<Path> stream = Files.newDirectoryStream(dir, nonQuote);
                    for (Path entry : stream) {
                        globbingResult.add(entry.getFileName().toString());
                    }
                    if (globbingResult.isEmpty()) {
                        globbingResult.add(nonQuote);
                    }
                    tokens.addAll(globbingResult);
                }
            }
        }
        return tokens;
    }
}