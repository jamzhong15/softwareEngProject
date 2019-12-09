// Generated from /Users/jameszhong/Desktop/University/Year2/SoftwareEng/jsh-team-6/lexer/Hello.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HelloParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BAR=1, SEMICOL=2, LT=3, GT=4, COMMANDT=5, SINGLEQUOTED=6, BACKQUOTED=7, 
		DOUBLEQUOTED=8, UNQUOTED=9, WS=10;
	public static final int
		RULE_start = 0, RULE_command = 1, RULE_pipe = 2, RULE_call = 3, RULE_atom = 4, 
		RULE_redirection = 5, RULE_argument = 6, RULE_quoted = 7, RULE_unquoted = 8, 
		RULE_singlequoted = 9, RULE_backquoted = 10, RULE_doublequoted = 11, RULE_commandtoken = 12;
	public static final String[] ruleNames = {
		"start", "command", "pipe", "call", "atom", "redirection", "argument", 
		"quoted", "unquoted", "singlequoted", "backquoted", "doublequoted", "commandtoken"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'|'", "';'", "'<'", "'>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "BAR", "SEMICOL", "LT", "GT", "COMMANDT", "SINGLEQUOTED", "BACKQUOTED", 
		"DOUBLEQUOTED", "UNQUOTED", "WS"
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
	public String getGrammarFileName() { return "Hello.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public HelloParser(TokenStream input) {
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
			setState(26);
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
		public TerminalNode EOF() { return getToken(HelloParser.EOF, 0); }
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public TerminalNode SEMICOL() { return getToken(HelloParser.SEMICOL, 0); }
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
			setState(32);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(29);
				pipe(0);
				}
				break;
			case 2:
				{
				setState(30);
				call();
				}
				break;
			case 3:
				{
				setState(31);
				match(EOF);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(39);
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
					setState(34);
					if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
					setState(35);
					match(SEMICOL);
					setState(36);
					command(4);
					}
					} 
				}
				setState(41);
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
		public TerminalNode BAR() { return getToken(HelloParser.BAR, 0); }
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
			setState(43);
			call();
			setState(44);
			match(BAR);
			setState(45);
			call();
			}
			_ctx.stop = _input.LT(-1);
			setState(52);
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
					setState(47);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(48);
					match(BAR);
					setState(49);
					call();
					}
					} 
				}
				setState(54);
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
		public CommandtokenContext commandtoken() {
			return getRuleContext(CommandtokenContext.class,0);
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
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LT || _la==GT) {
				{
				{
				setState(55);
				redirection();
				}
				}
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(61);
			commandtoken();
			setState(65);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(62);
					atom();
					}
					} 
				}
				setState(67);
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
			setState(70);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
			case GT:
				enterOuterAlt(_localctx, 1);
				{
				setState(68);
				redirection();
				}
				break;
			case SINGLEQUOTED:
			case BACKQUOTED:
			case DOUBLEQUOTED:
			case UNQUOTED:
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
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
		public TerminalNode LT() { return getToken(HelloParser.LT, 0); }
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public TerminalNode GT() { return getToken(HelloParser.GT, 0); }
		public RedirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_redirection; }
	}

	public final RedirectionContext redirection() throws RecognitionException {
		RedirectionContext _localctx = new RedirectionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_redirection);
		try {
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LT:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				match(LT);
				setState(73);
				argument();
				}
				break;
			case GT:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				match(GT);
				setState(75);
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
			setState(80); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(80);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case SINGLEQUOTED:
					case BACKQUOTED:
					case DOUBLEQUOTED:
						{
						setState(78);
						quoted();
						}
						break;
					case UNQUOTED:
						{
						setState(79);
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
				setState(82); 
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
		public SinglequotedContext singlequoted() {
			return getRuleContext(SinglequotedContext.class,0);
		}
		public DoublequotedContext doublequoted() {
			return getRuleContext(DoublequotedContext.class,0);
		}
		public BackquotedContext backquoted() {
			return getRuleContext(BackquotedContext.class,0);
		}
		public QuotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quoted; }
	}

	public final QuotedContext quoted() throws RecognitionException {
		QuotedContext _localctx = new QuotedContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_quoted);
		try {
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SINGLEQUOTED:
				enterOuterAlt(_localctx, 1);
				{
				setState(84);
				singlequoted();
				}
				break;
			case DOUBLEQUOTED:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				doublequoted();
				}
				break;
			case BACKQUOTED:
				enterOuterAlt(_localctx, 3);
				{
				setState(86);
				backquoted();
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

	public static class UnquotedContext extends ParserRuleContext {
		public TerminalNode UNQUOTED() { return getToken(HelloParser.UNQUOTED, 0); }
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
			setState(89);
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

	public static class SinglequotedContext extends ParserRuleContext {
		public TerminalNode SINGLEQUOTED() { return getToken(HelloParser.SINGLEQUOTED, 0); }
		public SinglequotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_singlequoted; }
	}

	public final SinglequotedContext singlequoted() throws RecognitionException {
		SinglequotedContext _localctx = new SinglequotedContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_singlequoted);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(SINGLEQUOTED);
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

	public static class BackquotedContext extends ParserRuleContext {
		public TerminalNode BACKQUOTED() { return getToken(HelloParser.BACKQUOTED, 0); }
		public BackquotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_backquoted; }
	}

	public final BackquotedContext backquoted() throws RecognitionException {
		BackquotedContext _localctx = new BackquotedContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_backquoted);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(BACKQUOTED);
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

	public static class DoublequotedContext extends ParserRuleContext {
		public TerminalNode DOUBLEQUOTED() { return getToken(HelloParser.DOUBLEQUOTED, 0); }
		public DoublequotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_doublequoted; }
	}

	public final DoublequotedContext doublequoted() throws RecognitionException {
		DoublequotedContext _localctx = new DoublequotedContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_doublequoted);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(DOUBLEQUOTED);
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

	public static class CommandtokenContext extends ParserRuleContext {
		public TerminalNode COMMANDT() { return getToken(HelloParser.COMMANDT, 0); }
		public CommandtokenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commandtoken; }
	}

	public final CommandtokenContext commandtoken() throws RecognitionException {
		CommandtokenContext _localctx = new CommandtokenContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_commandtoken);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(COMMANDT);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\ff\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\3\3\3\3\3\3\3\5\3#\n\3\3\3\3\3\3\3"+
		"\7\3(\n\3\f\3\16\3+\13\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4\65\n\4\f"+
		"\4\16\48\13\4\3\5\7\5;\n\5\f\5\16\5>\13\5\3\5\3\5\7\5B\n\5\f\5\16\5E\13"+
		"\5\3\6\3\6\5\6I\n\6\3\7\3\7\3\7\3\7\5\7O\n\7\3\b\3\b\6\bS\n\b\r\b\16\b"+
		"T\3\t\3\t\3\t\5\tZ\n\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16"+
		"\2\4\4\6\17\2\4\6\b\n\f\16\20\22\24\26\30\32\2\2\2d\2\34\3\2\2\2\4\"\3"+
		"\2\2\2\6,\3\2\2\2\b<\3\2\2\2\nH\3\2\2\2\fN\3\2\2\2\16R\3\2\2\2\20Y\3\2"+
		"\2\2\22[\3\2\2\2\24]\3\2\2\2\26_\3\2\2\2\30a\3\2\2\2\32c\3\2\2\2\34\35"+
		"\5\4\3\2\35\3\3\2\2\2\36\37\b\3\1\2\37#\5\6\4\2 #\5\b\5\2!#\7\2\2\3\""+
		"\36\3\2\2\2\" \3\2\2\2\"!\3\2\2\2#)\3\2\2\2$%\f\5\2\2%&\7\4\2\2&(\5\4"+
		"\3\6\'$\3\2\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\5\3\2\2\2+)\3\2\2\2,-"+
		"\b\4\1\2-.\5\b\5\2./\7\3\2\2/\60\5\b\5\2\60\66\3\2\2\2\61\62\f\3\2\2\62"+
		"\63\7\3\2\2\63\65\5\b\5\2\64\61\3\2\2\2\658\3\2\2\2\66\64\3\2\2\2\66\67"+
		"\3\2\2\2\67\7\3\2\2\28\66\3\2\2\29;\5\f\7\2:9\3\2\2\2;>\3\2\2\2<:\3\2"+
		"\2\2<=\3\2\2\2=?\3\2\2\2><\3\2\2\2?C\5\32\16\2@B\5\n\6\2A@\3\2\2\2BE\3"+
		"\2\2\2CA\3\2\2\2CD\3\2\2\2D\t\3\2\2\2EC\3\2\2\2FI\5\f\7\2GI\5\16\b\2H"+
		"F\3\2\2\2HG\3\2\2\2I\13\3\2\2\2JK\7\5\2\2KO\5\16\b\2LM\7\6\2\2MO\5\16"+
		"\b\2NJ\3\2\2\2NL\3\2\2\2O\r\3\2\2\2PS\5\20\t\2QS\5\22\n\2RP\3\2\2\2RQ"+
		"\3\2\2\2ST\3\2\2\2TR\3\2\2\2TU\3\2\2\2U\17\3\2\2\2VZ\5\24\13\2WZ\5\30"+
		"\r\2XZ\5\26\f\2YV\3\2\2\2YW\3\2\2\2YX\3\2\2\2Z\21\3\2\2\2[\\\7\13\2\2"+
		"\\\23\3\2\2\2]^\7\b\2\2^\25\3\2\2\2_`\7\t\2\2`\27\3\2\2\2ab\7\n\2\2b\31"+
		"\3\2\2\2cd\7\7\2\2d\33\3\2\2\2\f\")\66<CHNRTY";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}