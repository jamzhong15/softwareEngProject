package uk.ac.ucl.jsh;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

public class CmdVisitor extends CmdGrammarBaseVisitor<Command> 
{

    /** 
     * example tokens tree from Call branch:
     * 
     *                               ____ [unquoted] ____ {token}
     *                               |
     *            ____ [argument] ___|
     *            |                  |____ [quoted] ____ {token}
     *   call ____|
     *            |                             
     *            |                                  |--- {token}
     *            |____ [atom] ____ [redirection] ___|                 
     *                                               |___ [argument] ____ [unquoted] ____ {token}
     *  
     *  starting from call branch, its child trees (in square brackets) are extracted recursively,
     *  until when the child trees containing only text tokens. For example [unquoted], [quoted] have 
     *  no more children trees follwing, but only tokens.
     * 
     *  These tokens become arguments that will be executed in a the call class. 
     */
    

    @Override
    public Command visitCall(final CmdGrammarParser.CallContext ctx)
    {
        ArrayList<String> tokens = new ArrayList<>();

        List<ParseTree> allChildrenTree = ctx.children;

        for (ParseTree argument_atom_tree : allChildrenTree)
        {
            extractTokensFromChildTree(argument_atom_tree, tokens);
        }

        Call call = new Call(tokens);
        return call;
    }

    public void extractTokensFromChildTree(ParseTree childTree, ArrayList<String> tokens)
    {
        if (childTree.getChildCount() == 0)
        {
            // System.out.println("childcount == 0, and getText == "+childTree.getText());
            tokens.add(childTree.getText());
        }
        else
        {
            for (int i = 0 ; i < childTree.getChildCount(); i++)
            {
                ParseTree subTrees = childTree.getChild(i);
                extractTokensFromChildTree(subTrees, tokens);
            }
        }
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

    /**
     * visit the pipe of concrete syntax tree
     * return left command and right command
     */
    @Override
    public Command visitPipe(final CmdGrammarParser.PipeContext ctx)
    {
        Command l = visit(ctx.getChild(0));
        Command r = visit(ctx.getChild(2));

        Pipe pipe = new Pipe(l, r);
        return pipe;
    }


}