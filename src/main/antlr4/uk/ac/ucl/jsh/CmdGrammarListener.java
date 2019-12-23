// Generated from CmdGrammar.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CmdGrammarParser}.
 */
public interface CmdGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(CmdGrammarParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(CmdGrammarParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(CmdGrammarParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(CmdGrammarParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#pipe}.
	 * @param ctx the parse tree
	 */
	void enterPipe(CmdGrammarParser.PipeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#pipe}.
	 * @param ctx the parse tree
	 */
	void exitPipe(CmdGrammarParser.PipeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(CmdGrammarParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(CmdGrammarParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(CmdGrammarParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(CmdGrammarParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void enterRedirection(CmdGrammarParser.RedirectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#redirection}.
	 * @param ctx the parse tree
	 */
	void exitRedirection(CmdGrammarParser.RedirectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(CmdGrammarParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(CmdGrammarParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void enterQuoted(CmdGrammarParser.QuotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#quoted}.
	 * @param ctx the parse tree
	 */
	void exitQuoted(CmdGrammarParser.QuotedContext ctx);
	/**
	 * Enter a parse tree produced by {@link CmdGrammarParser#unquoted}.
	 * @param ctx the parse tree
	 */
	void enterUnquoted(CmdGrammarParser.UnquotedContext ctx);
	/**
	 * Exit a parse tree produced by {@link CmdGrammarParser#unquoted}.
	 * @param ctx the parse tree
	 */
	void exitUnquoted(CmdGrammarParser.UnquotedContext ctx);
}