package uk.ac.ucl.jsh;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jsh {
    //get current directory
    private static String currentDirectory = System.getProperty("user.dir");

    public static void eval(String cmdline, OutputStream output) throws IOException {
        // obtain rawCommand from CmdExtractor class
        CmdExtractor cmdExtratcor = new CmdExtractor(cmdline);
        ArrayList<String> rawCommands = cmdExtratcor.readInput();

        //obtain tokens from Lexer class
        Lexer lexer = new Lexer(rawCommands, currentDirectory);
        ArrayList<String> tokens = lexer.getTokens();

        // get appName and appArgs using tokens
        String appName = tokens.get(0);
        ArrayList<String> appArgs = new ArrayList<String>(tokens.subList(1, tokens.size()));
       
        AppCase appCase = new AppCase(appName, appArgs, currentDirectory, output);
        appCase.eval1();

}

public static void main(String[] args) {
    if (args.length > 0) {
        if (args.length != 2) {
            System.out.println("jsh: wrong number of arguments");
            return;
        }
        if (!args[0].equals("-c")) {
            System.out.println("jsh: " + args[0] + ": unexpected argument");
        }
        try {
            eval(args[1], System.out);
        } catch (Exception e) {
            System.out.println("jsh: " + e.getMessage());
        }
    } else {
        System.out.println("Welcome to JSH!");
        Scanner input = new Scanner(System.in);
        try {
            while (true) {
                String prompt = currentDirectory + "> ";
                System.out.print(prompt);
                try {
                    String cmdline = input.nextLine();
                    eval(cmdline, System.out);
                } catch (Exception e) {
                    System.out.println("jsh: " + e.getMessage());
                }
            }
        } finally {
            input.close();
        }
        }
    }
}
