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

call : redirection* argument atom*;

atom : redirection
     | argument
     ;

redirection : LT argument*
            | GT argument*
            ;



argument : (quoted | unquoted)+;

quoted : SINGLEQUOTED
       | DOUBLEQUOTED
       | BACKQUOTED unquoted BACKQUOTED
       ;

// commandtoken : COMMANDT;

unquoted : UNQUOTED;


//lexer
BAR : '|';

SEMICOL : ';';

LT : '<';

GT : '>';

BACKQUOTED: '`';

SINGLEQUOTED : '\'' ~('\n'|'\'')* '\'';

//BACKQUOTED: '`' ~('\n' | '`')* '`';

DOUBLEQUOTED : '"' (BACKQUOTED | ~('\n' | '`' | '"'))* '"';

UNQUOTED : ~('\t' | '\'' | '"' | '`' | '\n' | ';' | '|' | '<' | '>' | ' ')+;

WS : [ \t\r\n]+ -> skip; // skip spaces, tabs, newlines
