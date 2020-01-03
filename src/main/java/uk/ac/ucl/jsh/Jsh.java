package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Stack;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;


public class Jsh 
{
    //get current directory
    private static String currentDirectory = System.getProperty("user.dir");
    private static Stack<InputStream> stdin = new Stack<>();
    private static Stack<OutputStream> stdout = new Stack<>();
    
    public void setcurrentDirectory(String newDirectory) 
    {
        currentDirectory = newDirectory;
    }

    public String getcurrentDirectory()
    {
        return currentDirectory;
    }

    public Stack<InputStream> getStackInputStream()
    {
        return stdin;
    }

    public Stack<OutputStream> getStackOutputStream()
    {
        return stdout;
    }

    public void start(String cmdline, OutputStream output) throws IOException 
    {
        CharStream charstream = CharStreams.fromString(cmdline);
        CmdGrammarLexer lexer = new CmdGrammarLexer(charstream);
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        CmdGrammarParser parser = new CmdGrammarParser(commonTokenStream);
        ParseTree tree = parser.command();

        stdin.push(null);
        stdout.push(output);

        CmdVisitor visitor = new CmdVisitor();
        Command c = visitor.visit(tree);
        c.eval();
        
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
                Jsh jsh = new Jsh();
                jsh.start(args[1], System.out);
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
                        Jsh jsh = new Jsh();
                        String cmdline = input.nextLine();
                        if(!cmdline.equals(""))
                        {
                            jsh.start(cmdline, System.out);
                        }
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
