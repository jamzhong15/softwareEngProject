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

    @Override
    public ArrayList<String> visitCommand(final CmdGrammarParser.CommandContext ctx)
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
}