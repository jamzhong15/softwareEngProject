// Generated from Hello.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link HelloParser}.
 */
public interface HelloListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link HelloParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(HelloParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(HelloParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(HelloParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(HelloParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#pipe}.
	 * @param ctx the parse tree
	 */
	void enterPipe(HelloParser.PipeContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#pipe}.
	 * @param ctx the parse tree
	 */
	void exitPipe(HelloParser.PipeContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(HelloParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(HelloParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(HelloParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(HelloParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#redirection}.
	 * @param ctx the parse tree
	 */
	void enterRedirection(HelloParser.RedirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#redirection}.
	 * @param ctx the parse tree
	 */
	void exitRedirection(HelloParser.RedirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(HelloParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(HelloParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#quoted}.
	 * @param ctx the parse tree
	 */
	void enterQuoted(HelloParser.QuotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#quoted}.
	 * @param ctx the parse tree
	 */
	void exitQuoted(HelloParser.QuotedContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#unquoted}.
	 * @param ctx the parse tree
	 */
	void enterUnquoted(HelloParser.UnquotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#unquoted}.
	 * @param ctx the parse tree
	 */
	void exitUnquoted(HelloParser.UnquotedContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#singlequoted}.
	 * @param ctx the parse tree
	 */
	void enterSinglequoted(HelloParser.SinglequotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#singlequoted}.
	 * @param ctx the parse tree
	 */
	void exitSinglequoted(HelloParser.SinglequotedContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#backquoted}.
	 * @param ctx the parse tree
	 */
	void enterBackquoted(HelloParser.BackquotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#backquoted}.
	 * @param ctx the parse tree
	 */
	void exitBackquoted(HelloParser.BackquotedContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#doublequoted}.
	 * @param ctx the parse tree
	 */
	void enterDoublequoted(HelloParser.DoublequotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#doublequoted}.
	 * @param ctx the parse tree
	 */
	void exitDoublequoted(HelloParser.DoublequotedContext ctx);
	/**
	 * Enter a parse tree produced by {@link HelloParser#commandtoken}.
	 * @param ctx the parse tree
	 */
	void enterCommandtoken(HelloParser.CommandtokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link HelloParser#commandtoken}.
	 * @param ctx the parse tree
	 */
	void exitCommandtoken(HelloParser.CommandtokenContext ctx);
}