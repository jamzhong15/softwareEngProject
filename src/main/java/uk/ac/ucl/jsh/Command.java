package uk.ac.ucl.jsh;

import java.io.IOException;
/**
 * interface for seq, pipe and call class
 * used when analysing entire command line input
 */
public interface Command
{
    void eval() throws IOException;
}