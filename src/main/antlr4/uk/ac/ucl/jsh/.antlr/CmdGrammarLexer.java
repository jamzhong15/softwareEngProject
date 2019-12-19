// Generated from /workspaces/jsh-team-6/src/main/antlr4/uk/ac/ucl/jsh/CmdGrammar.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CmdGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BAR=1, SEMICOL=2, LT=3, GT=4, COMMANDT=5, SINGLEQUOTED=6, BACKQUOTED=7, 
		DOUBLEQUOTED=8, UNQUOTED=9, WS=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"BAR", "SEMICOL", "LT", "GT", "COMMANDT", "SINGLEQUOTED", "BACKQUOTED", 
		"DOUBLEQUOTED", "UNQUOTED", "WS"
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


	public CmdGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CmdGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\f\u009b\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\5\6q\n\6\3\7\3\7\7\7u\n\7\f\7\16\7x\13\7\3\7\3\7\3\b\3\b"+
		"\7\b~\n\b\f\b\16\b\u0081\13\b\3\b\3\b\3\t\3\t\3\t\7\t\u0088\n\t\f\t\16"+
		"\t\u008b\13\t\3\t\3\t\3\n\7\n\u0090\n\n\f\n\16\n\u0093\13\n\3\13\6\13"+
		"\u0096\n\13\r\13\16\13\u0097\3\13\3\13\2\2\f\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\3\2\7\4\2\f\f))\4\2\f\fbb\5\2\f\f$$bb\n\2\13\f\""+
		"\"$$))=>@@bb~~\5\2\13\f\17\17\"\"\2\u00b5\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\3\27\3\2\2\2\5\31\3\2\2\2\7\33\3\2\2\2\t"+
		"\35\3\2\2\2\13p\3\2\2\2\rr\3\2\2\2\17{\3\2\2\2\21\u0084\3\2\2\2\23\u0091"+
		"\3\2\2\2\25\u0095\3\2\2\2\27\30\7~\2\2\30\4\3\2\2\2\31\32\7=\2\2\32\6"+
		"\3\2\2\2\33\34\7>\2\2\34\b\3\2\2\2\35\36\7@\2\2\36\n\3\2\2\2\37 \7g\2"+
		"\2 !\7e\2\2!\"\7j\2\2\"q\7q\2\2#$\7n\2\2$q\7u\2\2%&\7i\2\2&\'\7t\2\2\'"+
		"(\7g\2\2(q\7r\2\2)*\7e\2\2*q\7f\2\2+,\7r\2\2,-\7y\2\2-q\7f\2\2./\7e\2"+
		"\2/\60\7c\2\2\60q\7v\2\2\61\62\7j\2\2\62\63\7g\2\2\63\64\7c\2\2\64q\7"+
		"f\2\2\65\66\7v\2\2\66\67\7c\2\2\678\7k\2\28q\7n\2\29:\7y\2\2:q\7e\2\2"+
		";<\7u\2\2<=\7g\2\2=q\7f\2\2>?\7h\2\2?@\7k\2\2@A\7p\2\2Aq\7f\2\2BC\7a\2"+
		"\2CD\7g\2\2DE\7e\2\2EF\7j\2\2Fq\7q\2\2GH\7a\2\2HI\7n\2\2Iq\7u\2\2JK\7"+
		"a\2\2KL\7i\2\2LM\7t\2\2MN\7g\2\2Nq\7r\2\2OP\7a\2\2PQ\7e\2\2Qq\7f\2\2R"+
		"S\7a\2\2ST\7r\2\2TU\7y\2\2Uq\7f\2\2VW\7a\2\2WX\7e\2\2XY\7c\2\2Yq\7v\2"+
		"\2Z[\7a\2\2[\\\7j\2\2\\]\7g\2\2]^\7c\2\2^q\7f\2\2_`\7a\2\2`a\7v\2\2ab"+
		"\7c\2\2bc\7k\2\2cq\7n\2\2de\7a\2\2ef\7y\2\2fq\7e\2\2gh\7a\2\2hi\7u\2\2"+
		"ij\7g\2\2jq\7f\2\2kl\7a\2\2lm\7h\2\2mn\7k\2\2no\7p\2\2oq\7f\2\2p\37\3"+
		"\2\2\2p#\3\2\2\2p%\3\2\2\2p)\3\2\2\2p+\3\2\2\2p.\3\2\2\2p\61\3\2\2\2p"+
		"\65\3\2\2\2p9\3\2\2\2p;\3\2\2\2p>\3\2\2\2pB\3\2\2\2pG\3\2\2\2pJ\3\2\2"+
		"\2pO\3\2\2\2pR\3\2\2\2pV\3\2\2\2pZ\3\2\2\2p_\3\2\2\2pd\3\2\2\2pg\3\2\2"+
		"\2pk\3\2\2\2q\f\3\2\2\2rv\7)\2\2su\n\2\2\2ts\3\2\2\2ux\3\2\2\2vt\3\2\2"+
		"\2vw\3\2\2\2wy\3\2\2\2xv\3\2\2\2yz\7)\2\2z\16\3\2\2\2{\177\7b\2\2|~\n"+
		"\3\2\2}|\3\2\2\2~\u0081\3\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0082"+
		"\3\2\2\2\u0081\177\3\2\2\2\u0082\u0083\7b\2\2\u0083\20\3\2\2\2\u0084\u0089"+
		"\7$\2\2\u0085\u0088\5\17\b\2\u0086\u0088\n\4\2\2\u0087\u0085\3\2\2\2\u0087"+
		"\u0086\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2"+
		"\2\2\u008a\u008c\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u008d\7$\2\2\u008d"+
		"\22\3\2\2\2\u008e\u0090\n\5\2\2\u008f\u008e\3\2\2\2\u0090\u0093\3\2\2"+
		"\2\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\24\3\2\2\2\u0093\u0091"+
		"\3\2\2\2\u0094\u0096\t\6\2\2\u0095\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097"+
		"\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u009a\b\13"+
		"\2\2\u009a\26\3\2\2\2\n\2pv\177\u0087\u0089\u0091\u0097\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}