package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

public interface Command
{
    public void eval(ArrayList<String> input, OutputStream output) throws IOException;
}