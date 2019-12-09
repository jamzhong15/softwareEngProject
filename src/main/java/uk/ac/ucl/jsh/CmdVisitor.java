package uk.ac.ucl.jsh;

import java.util.ArrayList;

public class CmdVisitor extends CmdGrammarBaseVisitor<ArrayList<String>>
{
    @Override
    public ArrayList<String> visitCall(final CmdGrammarParser.CallContext ctx)
    {
        ArrayList<String> tokens = new ArrayList<>();
        System.out.println("child count = "+ ctx.getChildCount());
        int i;
        for (i = 0; i<ctx.getChildCount(); i++ )
        {
            String token = ctx.getChild(i).getText();
            System.out.println("token = " + token);
            tokens.add(token);
        }
        return tokens;
    }

    // @Override
    // public ArrayList<String> visitCommandtoken(final CmdGrammarParser.CallContext ctx)
    // {
    //     ArrayList<String> tokens = new ArrayList<>();
    //     return tokens;
    // }

    @Override
    public ArrayList<String> visitCommand(final CmdGrammarParser.CommandContext ctx)
    {
        ArrayList<String> command = new ArrayList<>();
        System.out.println("child count = "+ ctx.getChildCount());
        int i;
        int children = ctx.getChildCount();
        // for (i = 0; i<children; i++ )
        // {
        //     //String token = ctx.getChild(i).getText();
        //     //System.out.println("token = " + token);
        //     //command.add(token);
        // }
        if(children == 1)
        {
            command.addAll(visit(ctx.getChild(0)));
            //command.add(ctx.getChild(0).getText());
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
        ArrayList<String> pipe = new ArrayList<>();
        System.out.println("child count = "+ ctx.getChildCount());
        int i;
        int children = ctx.getChildCount();
        // for (i = 0; i<children; i++ )
        // {
        //     String token = ctx.getChild(i).getText();
        //     //System.out.println("token = " + token);
        //     pipe.add(token);
        // }
        pipe.addAll(visit(ctx.getChild(0)));
        pipe.add(ctx.getChild(1).getText());
        pipe.addAll(visit(ctx.getChild(2)));

        
        return pipe;
    }
}