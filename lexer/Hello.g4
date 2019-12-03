// Define a grammar called Hello
grammar Hello;

command : pipe 
        | command SEMICOL command
        | call
        ;

pipe : call BAR call
     | pipe BAR call
     ;

call : redirection* commandtoken  atom*
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

COMMANDT : 'echo' 
         | 'ls'
         ;

SINGLEQUOTED : '\'' ~('\n'|'\'')* '\'';

BACKQUOTED: '`' ~('\n' | '`')* '`';

DOUBLEQUOTED : '"' BACKQUOTED | ~('\n' | '`' | '"')* '"';

UNQUOTED : ~('\t' | '\'' | '"' | '`' | '\n' | ';' | '|' | '<' | '>')*;

//ID : [a-z]+ ;             // match lower-case identifiers
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
