package uk.ac.ucl.jsh;

import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTree;

public class CmdVisitor extends CmdGrammarBaseVisitor<Command> 
{
    @Override
    public Command visitCall(final CmdGrammarParser.CallContext ctx)
    {
        ArrayList<String> tokens = new ArrayList<>();
        ParseTree argumentTree = ctx.getChild(0);
        System.out.println("children = " + argumentTree.getChildCount());
        
        for (int i = 0; i < argumentTree.getChildCount(); i++) {
            tokens.add(argumentTree.getChild(i).getText());
        }
        Call call = new Call(tokens);
        return call;
    }

    @Override
    public Command visitCommand(final CmdGrammarParser.CommandContext ctx)
    {
        int children = ctx.getChildCount();
        if(children == 1)
        {
            return visit(ctx.getChild(0));
        }
        else
        {
            Command left = visit(ctx.command(0));
            Command right = visit(ctx.command(1));
            Seq s = new Seq(left, right);
            return s;

        }
    }

    @Override
    public Command visitPipe(final CmdGrammarParser.PipeContext ctx)
    {
        Command l = visit(ctx.getChild(0));
        Command r = visit(ctx.getChild(2));

        Pipe pipe = new Pipe(l, r);
        return pipe;
    }
}