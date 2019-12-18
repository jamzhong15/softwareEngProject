package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Stack;


public class Pipe implements Command 
{
    private final Command l, r;

    public Pipe(Command l, Command r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public void eval() throws IOException 
    {
        Jsh jsh = new Jsh();
        
        PipedInputStream piped_input = new PipedInputStream(100000);
        PipedOutputStream piped_output = new PipedOutputStream((PipedInputStream) piped_input);
        Stack <InputStream> stdin = jsh.getStackInputStream();
        Stack <OutputStream> stdout = jsh.getStackOutputStream();

        stdin.push(piped_input);
        stdout.push(piped_output);
        jsh.setisPipedBool(true);
        l.eval();
        piped_output.close();

        stdin.push(piped_input);
        r.eval();
        jsh.setisPipedBool(false);
    }

}