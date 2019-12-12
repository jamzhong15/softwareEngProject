package uk.ac.ucl.jsh;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;


public class Jsh 
{
    //get current directory
    private static String currentDirectory = System.getProperty("user.dir");

    public void setcurrentDirectory(String newDirectory) 
    {
        currentDirectory = newDirectory;
    }

    public String getcurrentDirectory()
    {
        return currentDirectory;
    }

    public static void start(String cmdline, OutputStream output) throws IOException 
    {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

        CharStream charstream = CharStreams.fromString(cmdline);
        CmdGrammarLexer lexer = new CmdGrammarLexer(charstream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        CmdGrammarParser parser = new CmdGrammarParser(commonTokenStream);
        ParseTree tree = parser.command();

        CmdVisitor visitor = new CmdVisitor();
        Command c = visitor.visit(tree);
        c.eval(output);
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
                start(args[1], System.out);
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
                        start(cmdline, System.out);
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
