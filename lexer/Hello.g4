// Define a grammar called Hello
grammar Hello;

start: command;

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

unquoted : UNQUOTED;

singlequoted : SINGLEQUOTED;

backquoted :BACKQUOTED;

doublequoted: DOUBLEQUOTED;

commandtoken : COMMANDT;



//lexer
BAR : '|';

SEMICOL : ';';

LT : '<';

GT : '>';

COMMANDT : 'grep'
         | 'cd'
         | 'ls'
         | 'cat'
         | 'head'
         | 'tail'
         | 'echo'
         ;

SINGLEQUOTED : '\'' ~('\n'|'\'')* '\'';

BACKQUOTED: '`' ~('\n' | '`')* '`';

DOUBLEQUOTED : '"' (BACKQUOTED | ~('\n' | '`' | '"'))* '"';

UNQUOTED : ~('\t' | '\'' | '"' | '`' | '\n' | ';' | '|' | '<' | '>' | ' ')*;

//ID : [a-z]+ ;             // match lower-case identifiers
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
