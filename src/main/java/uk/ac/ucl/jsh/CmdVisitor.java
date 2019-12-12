package uk.ac.ucl.jsh;

import java.util.ArrayList;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class CmdVisitor extends CmdGrammarBaseVisitor<ArrayList<String>>
{
    @Override
    public ArrayList<String> visitCall(final CmdGrammarParser.CallContext ctx)
    {
        ArrayList<String> tokens = new ArrayList<>();
        tokens.add(ctx.getChild(0).getText());
        if(ctx.getChildCount() == 2)
        {
            ParseTree arguments = ctx.getChild(1).getChild(0);
            for (int i = 0; i<arguments.getChildCount(); i++ )
            {
                String token = arguments.getChild(i).getText();     // Add all the arguments
                tokens.add(token);
            }

        }
        
        
        Call call = new Call();
        try {
            call.eval(tokens, System.out);
        } catch (Exception e) {
            e.printStackTrace();
        }

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