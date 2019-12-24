// Generated from /workspaces/jsh-team-6/src/main/antlr4/uk/ac/ucl/jsh/CmdGrammar.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CmdGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BAR=1, SEMICOL=2, LT=3, GT=4, SINGLEQUOTED=5, BACKQUOTED=6, DOUBLEQUOTED=7, 
		UNQUOTED=8, WS=9;
	public static final int
		RULE_start = 0, RULE_command = 1, RULE_pipe = 2, RULE_call = 3, RULE_atom = 4, 
		RULE_redirection = 5, RULE_argument = 6, RULE_quoted = 7, RULE_unquoted = 8;
	public static final String[] ruleNames = {
		"start", "command", "pipe", "call", "atom", "redirection", "argument", 
		"quoted", "unquoted"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'|'", "';'", "'<'", "'>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "BAR", "SEMICOL", "LT", "GT", "SINGLEQUOTED", "BACKQUOTED", "DOUBLEQUOTED", 
		"UNQUOTED", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CmdGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CmdGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class StartContext extends ParserRuleContext {
		public CommandContext command() {
			return getRuleContext(CommandContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			command(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public PipeContext pipe() {
			return getRuleContext(PipeContext.class,0);
		}
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public TerminalNode EOF() { return getToken(CmdGrammarParser.EOF, 0); }
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public TerminalNode SEMICOL() { return getToken(CmdGrammarParser.SEMICOL, 0); }
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
	}

	public final CommandContext command() throws RecognitionException {
		return command(0);
	}

	private CommandContext command(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CommandContext _localctx = new CommandContext(_ctx, _parentState);
		CommandContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_command, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(21);
				pipe(0);
				}
				break;
			case 2:
				{
				setState(22);
				call();
				}
				break;
			case 3:
				{
				setState(23);
				match(EOF);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(31);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CommandContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_command);
					setState(26);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(27);
					match(SEMICOL);
					setState(28);
					command(4);
					}
					} 
				}
				setState(33);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PipeContext extends ParserRuleContext {
		public List<CallContext> call() {
			return getRuleContexts(CallContext.class);
		}
		public CallContext call(int i) {
			return getRuleContext(CallContext.class,i);
		}
		public TerminalNode BAR() { return getToken(CmdGrammarParser.BAR, 0); }
		public PipeContext pipe() {
			return getRuleContext(PipeContext.class,0);
		}
		public PipeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pipe; }
	}

	public final PipeContext pipe() throws RecognitionException {
		return pipe(0);
	}

	private PipeContext pipe(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PipeContext _localctx = new PipeContext(_ctx, _parentState);
		PipeContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_pipe, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(35);
			call();
			setState(36);
			match(BAR);
			setState(37);
			call();
			}
			_ctx.stop = _input.LT(-1);
			setState(44);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new PipeContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_pipe);
					setState(39);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(40);
					match(BAR);
					setState(41);
					call();
					}
					} 
				}
				setState(46);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class CallContext extends ParserRuleContext {
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public List<RedirectionContext> redirection() {
			return getRuleContexts(RedirectionContext.class);
		}
		public RedirectionContext redirection(int i) {
			return getRuleContext(RedirectionContext.class,i);
		}
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_call);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LT || _la==GT) {
				{
				{
				setState(47);
				redirection();
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(53);
			argument();
			setState(57);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(54);
					atom();
					}
					} 
				}
				setState(59);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public RedirectionContext redirection() {
			return getRuleContext(RedirectionContext.class,0);
		}
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_atom);
		try {
			setState(62);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
			case GT:
				enterOuterAlt(_localctx, 1);
				{
				setState(60);
				redirection();
				}
				break;
			case SINGLEQUOTED:
			case BACKQUOTED:
			case DOUBLEQUOTED:
			case UNQUOTED:
				enterOuterAlt(_localctx, 2);
				{
				setState(61);
				argument();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RedirectionContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(CmdGrammarParser.LT, 0); }
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public TerminalNode GT() { return getToken(CmdGrammarParser.GT, 0); }
		public RedirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_redirection; }
	}

	public final RedirectionContext redirection() throws RecognitionException {
		RedirectionContext _localctx = new RedirectionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_redirection);
		try {
			setState(68);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				match(LT);
				setState(65);
				argument();
				}
				break;
			case GT:
				enterOuterAlt(_localctx, 2);
				{
				setState(66);
				match(GT);
				setState(67);
				argument();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentContext extends ParserRuleContext {
		public List<QuotedContext> quoted() {
			return getRuleContexts(QuotedContext.class);
		}
		public QuotedContext quoted(int i) {
			return getRuleContext(QuotedContext.class,i);
		}
		public List<UnquotedContext> unquoted() {
			return getRuleContexts(UnquotedContext.class);
		}
		public UnquotedContext unquoted(int i) {
			return getRuleContext(UnquotedContext.class,i);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argument);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(72); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(72);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case SINGLEQUOTED:
					case BACKQUOTED:
					case DOUBLEQUOTED:
						{
						setState(70);
						quoted();
						}
						break;
					case UNQUOTED:
						{
						setState(71);
						unquoted();
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(74); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuotedContext extends ParserRuleContext {
		public TerminalNode SINGLEQUOTED() { return getToken(CmdGrammarParser.SINGLEQUOTED, 0); }
		public TerminalNode DOUBLEQUOTED() { return getToken(CmdGrammarParser.DOUBLEQUOTED, 0); }
		public TerminalNode BACKQUOTED() { return getToken(CmdGrammarParser.BACKQUOTED, 0); }
		public QuotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quoted; }
	}

	public final QuotedContext quoted() throws RecognitionException {
		QuotedContext _localctx = new QuotedContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_quoted);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SINGLEQUOTED) | (1L << BACKQUOTED) | (1L << DOUBLEQUOTED))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnquotedContext extends ParserRuleContext {
		public TerminalNode UNQUOTED() { return getToken(CmdGrammarParser.UNQUOTED, 0); }
		public UnquotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unquoted; }
	}

	public final UnquotedContext unquoted() throws RecognitionException {
		UnquotedContext _localctx = new UnquotedContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_unquoted);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(UNQUOTED);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return command_sempred((CommandContext)_localctx, predIndex);
		case 2:
			return pipe_sempred((PipeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean command_sempred(CommandContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean pipe_sempred(PipeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13S\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\5\3\33\n\3\3\3\3\3\3\3\7\3 \n\3\f\3\16\3#\13\3\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\7\4-\n\4\f\4\16\4\60\13\4\3\5\7\5\63\n\5\f\5\16"+
		"\5\66\13\5\3\5\3\5\7\5:\n\5\f\5\16\5=\13\5\3\6\3\6\5\6A\n\6\3\7\3\7\3"+
		"\7\3\7\5\7G\n\7\3\b\3\b\6\bK\n\b\r\b\16\bL\3\t\3\t\3\n\3\n\3\n\2\4\4\6"+
		"\13\2\4\6\b\n\f\16\20\22\2\3\3\2\7\t\2S\2\24\3\2\2\2\4\32\3\2\2\2\6$\3"+
		"\2\2\2\b\64\3\2\2\2\n@\3\2\2\2\fF\3\2\2\2\16J\3\2\2\2\20N\3\2\2\2\22P"+
		"\3\2\2\2\24\25\5\4\3\2\25\3\3\2\2\2\26\27\b\3\1\2\27\33\5\6\4\2\30\33"+
		"\5\b\5\2\31\33\7\2\2\3\32\26\3\2\2\2\32\30\3\2\2\2\32\31\3\2\2\2\33!\3"+
		"\2\2\2\34\35\f\5\2\2\35\36\7\4\2\2\36 \5\4\3\6\37\34\3\2\2\2 #\3\2\2\2"+
		"!\37\3\2\2\2!\"\3\2\2\2\"\5\3\2\2\2#!\3\2\2\2$%\b\4\1\2%&\5\b\5\2&\'\7"+
		"\3\2\2\'(\5\b\5\2(.\3\2\2\2)*\f\3\2\2*+\7\3\2\2+-\5\b\5\2,)\3\2\2\2-\60"+
		"\3\2\2\2.,\3\2\2\2./\3\2\2\2/\7\3\2\2\2\60.\3\2\2\2\61\63\5\f\7\2\62\61"+
		"\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\67\3\2\2\2\66\64"+
		"\3\2\2\2\67;\5\16\b\28:\5\n\6\298\3\2\2\2:=\3\2\2\2;9\3\2\2\2;<\3\2\2"+
		"\2<\t\3\2\2\2=;\3\2\2\2>A\5\f\7\2?A\5\16\b\2@>\3\2\2\2@?\3\2\2\2A\13\3"+
		"\2\2\2BC\7\5\2\2CG\5\16\b\2DE\7\6\2\2EG\5\16\b\2FB\3\2\2\2FD\3\2\2\2G"+
		"\r\3\2\2\2HK\5\20\t\2IK\5\22\n\2JH\3\2\2\2JI\3\2\2\2KL\3\2\2\2LJ\3\2\2"+
		"\2LM\3\2\2\2M\17\3\2\2\2NO\t\2\2\2O\21\3\2\2\2PQ\7\n\2\2Q\23\3\2\2\2\13"+
		"\32!.\64;@FJL";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}