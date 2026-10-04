grammar Constraints;

@header { package io.github.vodobryshkin.decimalregions.parser; }

formula
    : constraint (booleanSymbol constraint)* EOF
    ;

constraint
    : expr parSymbol expr
    ;


parSymbol
    : GT
    | GE
    | LT
    | LE
    | EQ
    | NEQ
    ;

booleanSymbol
    : AND
    | OR
    | XOR
    ;

expr
    : sum
    ;

sum
    : prod ((PLUS | MINUS) prod)*
    ;

prod
    : pow ((MUL | DIV) pow)*
    ;

pow
    : unary (POW unary)*
    ;

unary
    : (PLUS | MINUS)? atom
    ;

atom
    : X
    | Y
    | R
    | number
    | LPAREN expr RPAREN
    | func LPAREN expr RPAREN
    ;

func
    : SQRT
    | SIN
    | COS
    | TAN
    | LN
    | LOG
    | EXP
    | ABS
    ;

number
    : INT
    | FLOAT
    ;

AND : '&&';
OR  : '||';
XOR : 'xor';

GE  : '>=';
GT  : '>';
LE  : '<=';
LT  : '<';
NEQ : '!=';
EQ  : '=';

PLUS : '+';
MINUS: '-';
MUL  : '*';
DIV  : '/';
POW  : '^';

X : 'x';
Y : 'y';
R : 'r';

SQRT : 'sqrt';
SIN  : 'sin';
COS  : 'cos';
TAN  : 'tan';
LN   : 'ln';
LOG  : 'log';
EXP  : 'exp';
ABS  : 'abs';

LPAREN : '(';
RPAREN : ')';

FLOAT : DIGIT+ '.' DIGIT+;
INT   : DIGIT+;

fragment DIGIT : [0-9];

WS : [ \t\r\n]+ -> skip;
