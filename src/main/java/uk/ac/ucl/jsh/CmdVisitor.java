package uk.ac.ucl.jsh;

import java.io.IOException;
import java.util.ArrayList;

public class CmdVisitor extends CmdGrammarBaseVisitor<ArrayList<String>>
{
    @Override
    public ArrayList<String> visitCall(final CmdGrammarParser.CallContext ctx) throws IOException
    {
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add(ctx.getChild(0).getText());

        if (ctx.getChildCount() == 2)
        {
            int i;
            for (i = 0; i<ctx.getChild(1).getChild(0).getChildCount(); i++ )
            {
                String token = ctx.getChild(1).getChild(0).getChild(i).getText();
                tokens.add(token);
            }
        }
        
        Call call = new Call();

        call.eval(tokens, System.out);
        return tokens;
    }

    @Override
    public ArrayList<String> visitCommand(final CmdGrammarParser.CommandContext ctx)
    {
        ArrayList<String> command = new ArrayList<>();
        int children = ctx.getChildCount();
        if(children == 1)
        {
            command.addAll(visit(ctx.getChild(0)));
        }
        else
        {
            visit(ctx.getChild(0));
            visit(ctx.getChild(ctx.getChildCount() -1));

        }
        return command;
    }

    @Override
    public ArrayList<String> visitPipe(final CmdGrammarParser.PipeContext ctx)
    {
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add(ctx.getChild(0).getText());
        tokens.add(ctx.getChild(2).getText());
        for (String token : tokens)
        {
            System.out.println("token = "+ token);
        }


        Pipe pipe = new Pipe();
        try {
            pipe.eval(tokens, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokens;
    }
}