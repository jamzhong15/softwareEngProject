package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public class Pipe implements Command 
{

    @Override
    public void eval(ArrayList<String> input, OutputStream output) throws IOException 
    {
        ArrayList<String> argsLeft = new ArrayList<>();
        argsLeft.add(input.get(0));
        argsLeft.add(input.get(1));
        Call left = new Call();
        left.eval(argsLeft, output);

        ArrayList<String> argsRight = new ArrayList<>();
        argsRight.add(input.get(2));
        argsRight.add(input.get(3));
        argsRight.add(input.get(1));
        Call right = new Call();
        right.eval(argsRight, output);

    }

}