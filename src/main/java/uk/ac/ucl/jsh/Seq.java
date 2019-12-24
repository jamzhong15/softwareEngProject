package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;

public class Seq implements Command {
    private final Command l, r;

    public Seq(Command l, Command r) {
        this.l = l;
        this.r = r;
    }

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