package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Stack;

/**
 * pipe | will be analysed if there is no seq in the command line
 */

public class Pipe implements Command 
{
    private final Command l, r;

    public Pipe(Command l, Command r) {
        this.l = l;
        this.r = r;
    }

/**
 * left part of the command: 
 * get input from commandline input, 
 * if no commandline input, get input from stdin.
 * push  output to the input stack
 * call eval to the left part of the command
 * 
 * right part of the command: 
 * get input from inputStack and 
 * call eval to the right part of the command
 */
    @Override
    public void eval() throws IOException 
    {
        Jsh jsh = new Jsh();

        // Connect streams and add to the stack
        
        PipedInputStream piped_input = new PipedInputStream(100000);
        PipedOutputStream piped_output = new PipedOutputStream(piped_input);
        Stack <InputStream> stdin = jsh.getStackInputStream();
        Stack <OutputStream> stdout = jsh.getStackOutputStream();

        stdin.push(piped_input);
        stdout.push(piped_output);
        
        l.eval();
        piped_output.close();

        stdin.push(piped_input);
        r.eval();
    }

}