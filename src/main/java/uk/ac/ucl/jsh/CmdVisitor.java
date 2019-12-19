package uk.ac.ucl.jsh;

import java.io.IOException;
import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTree;

<<<<<<< HEAD
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class CmdVisitor extends CmdGrammarBaseVisitor<ArrayList<String>> {
    @Override
    public ArrayList<String> visitCall(final CmdGrammarParser.CallContext ctx) {
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add(ctx.getChild(0).getText());
        if (ctx.getChildCount() == 2) {
            ParseTree arguments = ctx.getChild(1).getChild(0);
            for (int i = 0; i < arguments.getChildCount(); i++) {
                String token = arguments.getChild(i).getText(); // Add all the arguments
                tokens.add(token);
            }

        }

        Call call = new Call();
        try {
            call.eval(tokens, System.out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
=======
public class CmdVisitor extends CmdGrammarBaseVisitor<Command> 
{
    @Override
    public Command visitCall(final CmdGrammarParser.CallContext ctx)
    {
        ArrayList<String> tokens = new ArrayList<>();
        ParseTree argumentTree = ctx.getChild(0);
        
        for (int i = 0; i < argumentTree.getChildCount(); i++) {
            tokens.add(argumentTree.getChild(i).getText());
>>>>>>> master
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