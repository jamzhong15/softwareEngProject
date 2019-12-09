package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.OutputStream;

public interface Command
{
    static public void eval(String cmdline, OutputStream output) throws IOException;
}