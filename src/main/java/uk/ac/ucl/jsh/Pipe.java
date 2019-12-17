package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;


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
        String currentDirectory = jsh.getcurrentDirectory();
        PipedInputStream piped_input = new PipedInputStream();
        PipedOutputStream piped_output = new PipedOutputStream();
        piped_input.connect(piped_output);

        // ArrayList<String> argsLeft = new ArrayList<>();
        // argsLeft.add(inputs.get(0));
        // argsLeft.add(inputs.get(1));

        // Call left = new Call(argsLeft);
        l.eval();

        // ArrayList<String> argsRight = new ArrayList<>();
        // argsRight.add(inputs.get(2));
        // argsRight.add(inputs.get(3));
        // argsRight.add(inputs.get(1));
        // Call right = new Call(argsRight);
        r.eval();

    }

}