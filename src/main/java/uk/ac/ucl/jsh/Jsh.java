package uk.ac.ucl.jsh;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Jsh 
{
    //get current directory
    private static String currentDirectory = System.getProperty("user.dir");

    public void setcurrentDirectory(String newDirectory) 
    {
        currentDirectory = newDirectory;
    }

    public static void eval(String cmdline, OutputStream output) throws IOException 
    {

        // obtain rawCommand from CmdExtractor class
        CmdExtractor cmdExtratcor = new CmdExtractor(cmdline);
        ArrayList<String> rawCommands = cmdExtratcor.readInput();

        //obtain tokens from Lexer class
        Lexer lexer = new Lexer(rawCommands, currentDirectory);
        ArrayList<String> tokens = lexer.getTokens();

        // get appName and appArgs using tokens
        String appName = tokens.get(0);
        ArrayList<String> appArgs = new ArrayList<String>(tokens.subList(1, tokens.size()));
        if (appName.charAt(0) == '_') 
        {
            appName = appName.substring(1, appName.length());
            AppCase app = new UnsafeCommand(AppFactory.createApp(appName));
            app.runCommand(appName, appArgs, currentDirectory, output);
        }
        else 
        {
            AppCase app = AppFactory.createApp(appName);
            app.runCommand(appName, appArgs, currentDirectory, output);
        }
    }


public static void main(String[] args) 
{
    if (args.length > 0) 
    {
        if (args.length != 2) 
        {
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
    } 
    else 
    {
        System.out.println("Welcome to JSH!");
        Scanner input = new Scanner(System.in);
        try 
        {
            while (true) 
            {
                String prompt = currentDirectory + "> ";
                System.out.print(prompt);
                try 
                {
                    String cmdline = input.nextLine();
                    eval(cmdline, System.out);
                } 
                catch (Exception e) 
                {
                    System.out.println("jsh: " + e.getMessage());
                }
            }
        } 
        finally 
        {
            input.close();
        }
    }
}
}
