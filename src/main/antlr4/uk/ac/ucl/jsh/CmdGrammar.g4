// Define a grammar called Hello
grammar CmdGrammar;

start : command;

command : pipe
        | command SEMICOL command
        | call
        | EOF
        ;

pipe : call BAR call
     | pipe BAR call
     ;

// call : redirection* commandtoken atom*
//      ;

call : redirection* argument atom*;

atom : redirection
     | argument
     ;

redirection : LT argument
            | GT argument
            ;



argument : (quoted | unquoted)+;

quoted : SINGLEQUOTED
       | DOUBLEQUOTED
       | BACKQUOTED
       ;

// commandtoken : COMMANDT;

unquoted : UNQUOTED;


//lexer
BAR : '|';

SEMICOL : ';';

LT : '<';

GT : '>';

SINGLEQUOTED : '\'' ~('\n'|'\'')* '\'';

BACKQUOTED: '`' ~('\n' | '`')* '`';

DOUBLEQUOTED : '"' (BACKQUOTED | ~('\n' | '`' | '"'))* '"';

UNQUOTED : ~('\t' | '\'' | '"' | '`' | '\n' | ';' | '|' | '<' | '>' | ' ')+;

//UNQUOTED : ~('\t' | '\'' | '"' | '`' | '\n' | ';' | '|' | '<' | '>' | ' ')*;


WS : [ \t\r\n]+ -> skip; // skip spaces, tabs, newlines
