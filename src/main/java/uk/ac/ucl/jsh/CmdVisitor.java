package uk.ac.ucl.jsh;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

public class CmdVisitor extends CmdGrammarBaseVisitor<Command> 
{
    @Override
    public Command visitCall(final CmdGrammarParser.CallContext ctx)
    {
        ArrayList<String> tokens = new ArrayList<>();

        List<ParseTree> allChildrenTree = ctx.children;

        for (ParseTree childTree : allChildrenTree)
        {
            for (int i = 0 ; i < childTree.getChildCount(); i++)
            {
                ParseTree deeperChildTree = childTree.getChild(i);
                if (deeperChildTree.getChildCount() > 1)
                {
                    for (int n = 0 ; n < deeperChildTree.getChildCount() ; n++)
                    {
                        ParseTree evenDeeperChildTree = deeperChildTree.getChild(n);
                        tokens.add(evenDeeperChildTree.getText());
                    }
                }
                else
                {
                    tokens.add(deeperChildTree.getText());
                }
            }
        }

        // ParseTree argumentTree = ctx.getChild(0);
        // for (int i = 0; i < argumentTree.getChildCount(); i++) 
        // {
        //     tokens.add(argumentTree.getChild(i).getText());
        // }

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