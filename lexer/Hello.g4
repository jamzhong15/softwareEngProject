// Define a grammar called Hello
grammar Hello;

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

quoted : singlequoted
       | doublequoted
       | backquoted
       ;

commandtoken : COMMANDT;

unquoted : UNQUOTED;

singlequoted : SINGLEQUOTED;

backquoted :BACKQUOTED;

doublequoted: DOUBLEQUOTED;


//lexer
BAR : '|';

SEMICOL : ';';

LT : '<';

GT : '>';

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

SINGLEQUOTED : '\'' ~('\n'|'\'')* '\'';

BACKQUOTED: '`' ~('\n' | '`')* '`';

DOUBLEQUOTED : '"' (BACKQUOTED | ~('\n' | '`' | '"'))* '"';

UNQUOTED : ~('\t' | '\'' | '"' | '`' | '\n' | ';' | '|' | '<' | '>' | ' ')*;

WS : [ \t\r\n]+ -> skip; // skip spaces, tabs, newlines
