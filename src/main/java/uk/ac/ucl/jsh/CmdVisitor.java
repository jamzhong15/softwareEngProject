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

        for (ParseTree argument_atom_tree : allChildrenTree)
        {
            for (int i = 0 ; i < argument_atom_tree.getChildCount(); i++)
            {
                ParseTree unquoted_redirection_tree = argument_atom_tree.getChild(i);
                if (unquoted_redirection_tree.getChildCount() > 1)
                {
                    for (int n = 0 ; n < unquoted_redirection_tree.getChildCount() ; n++)
                    {
                        ParseTree redirection_argument_tree = unquoted_redirection_tree.getChild(n);
                        if (redirection_argument_tree.getChildCount() > 1)
                        {
                            for (int q = 0 ; q < redirection_argument_tree.getChildCount() ; q++)
                            {
                                ParseTree quoted_unquoted_argument_tree = redirection_argument_tree.getChild(q);
                                tokens.add(quoted_unquoted_argument_tree.getText());
                            }
                        }
                        else
                        {
                            tokens.add(redirection_argument_tree.getText());
                        }
                    }
                }
                else
                {
                    tokens.add(unquoted_redirection_tree.getText());
                }
            }
        }

        if (tokens.contains("`"))
        {
            System.out.println("contain `");
            System.out.println("index = " + tokens.indexOf("`"));

            boolean inBackquote = false;
            int openBackquote = 0;
            int closeBackquote = 0;
            for (int i = 0; i<tokens.size(); i++)
            {
                if (tokens.get(i).equals("`") && inBackquote == false)
                {
                    openBackquote = i;
                    inBackquote = true;
                }
                if (tokens.get(i).equals("`") && inBackquote == true)
                {
                    closeBackquote = i;
                    inBackquote = false;

                    ArrayList<String> subCommand = new ArrayList<String>(tokens.subList(openBackquote+1, closeBackquote));
                    System.out.println(subCommand.toString());
                    // jsh.start(subCommand.toString())
                }

            }
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