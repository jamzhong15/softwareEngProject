package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

/**
 * seq ";", will be recognised firstly while analysing the antlr concrete syntax tree
 */

public class Seq implements Command {
    private final Command l, r;

    public Seq(Command l, Command r) {
        this.l = l;
        this.r = r;
    }

    /**
     * push null to stdin stack
     * push system.out to stdout stack
     * call eval to the left part of seq
     * call eval to the right part of seq
     */

    @Override
    public void eval() throws IOException {

        Jsh jsh = new Jsh();

        Stack <InputStream> stdin = jsh.getStackInputStream();
        Stack <OutputStream> stdout = jsh.getStackOutputStream();

        stdin.push(null);
        stdout.push(System.out);

        l.eval();
        r.eval();
    }
}