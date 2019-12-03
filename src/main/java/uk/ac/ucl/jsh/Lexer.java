package uk.ac.ucl.jsh;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Lexer {

    public ArrayList<String> rawCommands;
    public String currentDirectory;

    public Lexer(ArrayList<String> rawCommands, String currentDirectory) {
        this.rawCommands = rawCommands;
        this.currentDirectory = currentDirectory;
    }

    public ArrayList<String> getTokens() throws IOException {
        ArrayList<String> tokens = new ArrayList<String>();

        for (String rawCommand : rawCommands) {
            String spaceRegex = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
            Pattern regex = Pattern.compile(spaceRegex); // 正则表达式为spaceRegex
            Matcher regexMatcher = regex.matcher(rawCommand); 
            String nonQuote;
            while (regexMatcher.find()) { // 只要regrexMatcher里还有spaceREgex就返回true
                if (regexMatcher.group(1) != null || regexMatcher.group(2) != null) { // group(n) return 第n组匹配到的string spaceRegex 和rawCommand 比较
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