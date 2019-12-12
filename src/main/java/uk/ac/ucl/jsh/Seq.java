package uk.ac.ucl.jsh;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Seq implements Command {
    private final Command l, r;

    public Seq(Command l, Command r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public void eval(OutputStream output) throws IOException {
       l.eval(output);
       r.eval(output);
    }
}