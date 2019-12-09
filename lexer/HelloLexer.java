// Generated from Hello.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class HelloLexer extends Lexer {
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


	public HelloLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Hello.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\fa\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\67\n\6"+
		"\3\7\3\7\7\7;\n\7\f\7\16\7>\13\7\3\7\3\7\3\b\3\b\7\bD\n\b\f\b\16\bG\13"+
		"\b\3\b\3\b\3\t\3\t\3\t\7\tN\n\t\f\t\16\tQ\13\t\3\t\3\t\3\n\7\nV\n\n\f"+
		"\n\16\nY\13\n\3\13\6\13\\\n\13\r\13\16\13]\3\13\3\13\2\2\f\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\3\2\7\4\2\f\f))\4\2\f\fbb\5\2\f\f$"+
		"$bb\n\2\13\f\"\"$$))=>@@bb~~\5\2\13\f\17\17\"\"\2l\2\3\3\2\2\2\2\5\3\2"+
		"\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21"+
		"\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3\27\3\2\2\2\5\31\3\2\2\2\7\33\3\2"+
		"\2\2\t\35\3\2\2\2\13\66\3\2\2\2\r8\3\2\2\2\17A\3\2\2\2\21J\3\2\2\2\23"+
		"W\3\2\2\2\25[\3\2\2\2\27\30\7~\2\2\30\4\3\2\2\2\31\32\7=\2\2\32\6\3\2"+
		"\2\2\33\34\7>\2\2\34\b\3\2\2\2\35\36\7@\2\2\36\n\3\2\2\2\37 \7i\2\2 !"+
		"\7t\2\2!\"\7g\2\2\"\67\7r\2\2#$\7e\2\2$\67\7f\2\2%&\7n\2\2&\67\7u\2\2"+
		"\'(\7e\2\2()\7c\2\2)\67\7v\2\2*+\7j\2\2+,\7g\2\2,-\7c\2\2-\67\7f\2\2."+
		"/\7v\2\2/\60\7c\2\2\60\61\7k\2\2\61\67\7n\2\2\62\63\7g\2\2\63\64\7e\2"+
		"\2\64\65\7j\2\2\65\67\7q\2\2\66\37\3\2\2\2\66#\3\2\2\2\66%\3\2\2\2\66"+
		"\'\3\2\2\2\66*\3\2\2\2\66.\3\2\2\2\66\62\3\2\2\2\67\f\3\2\2\28<\7)\2\2"+
		"9;\n\2\2\2:9\3\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3\2\2\2=?\3\2\2\2><\3\2\2\2"+
		"?@\7)\2\2@\16\3\2\2\2AE\7b\2\2BD\n\3\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2"+
		"EF\3\2\2\2FH\3\2\2\2GE\3\2\2\2HI\7b\2\2I\20\3\2\2\2JO\7$\2\2KN\5\17\b"+
		"\2LN\n\4\2\2MK\3\2\2\2ML\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2PR\3\2\2"+
		"\2QO\3\2\2\2RS\7$\2\2S\22\3\2\2\2TV\n\5\2\2UT\3\2\2\2VY\3\2\2\2WU\3\2"+
		"\2\2WX\3\2\2\2X\24\3\2\2\2YW\3\2\2\2Z\\\t\6\2\2[Z\3\2\2\2\\]\3\2\2\2]"+
		"[\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\b\13\2\2`\26\3\2\2\2\n\2\66<EMOW]\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}