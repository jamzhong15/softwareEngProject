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

call : redirection* commandtoken atom*
     ;

atom : redirection
     | argument
     ;

redirection : LT argument
            | GT argument
            ;



argument : (quoted | unquoted)+;

quoted : SINGLEQUOTE unquoted SINGLEQUOTE
       | DOUBLEQUOTE unquoted DOUBLEQUOTE
       | BACKQUOTE unquoted BACKQUOTE
       ;

commandtoken : COMMANDT;

unquoted : UNQUOTED;

// singlequoted : SINGLEQUOTED;

// backquoted :BACKQUOTED;

// doublequoted: DOUBLEQUOTED;


//lexer
BAR : '|';

SEMICOL : ';';

LT : '<';

GT : '>';

SINGLEQUOTE : '\'';

DOUBLEQUOTE : '"';

BACKQUOTE : '`';

COMMANDT : 'echo' 
         | 'ls'
         | 'grep'
         | 'cd'
         | 'pwd'
         | 'cat'
         | 'head'
         | 'tail'
         | 'wc'
         | 'sed'
         | 'find'
         ;

// SINGLEQUOTED : '\'' ~('\n'|'\'')* '\'';

// BACKQUOTED: '`' ~('\n' | '`')* '`';

// DOUBLEQUOTED : '"' (BACKQUOTED | ~('\n' | '`' | '"'))* '"';

UNQUOTED : ~('\t' | '\'' | '"' | '`' | '\n' | ';' | '|' | '<' | '>' | ' ')*;

WS : [ \t\r\n]+ -> skip; // skip spaces, tabs, newlines
