/*-***
 *
 * This file defines a stand-alone lexical analyzer for a subset of the Pascal
 * programming language.  This is the same lexer that will later be integrated
 * with a CUP-based parser.  Here the lexer is driven by the simple Java test
 * program in ./PascalLexerTest.java, q.v.  See 330 Lecture Notes 2 and the
 * Assignment 2 writeup for further discussion.
 *
 */


import java_cup.runtime.*;


%%
/*-*
 * LEXICAL FUNCTIONS:
 */

%cup
%line
%column
%unicode
%class Lexer

%{

/**
 * Return a new Symbol with the given token id, and with the current line and
 * column numbers.
 */
Symbol newSym(int tokenId) {
    return new Symbol(tokenId, yyline, yycolumn);
}

/**
 * Return a new Symbol with the given token id, the current line and column
 * numbers, and the given token value.  The value is used for tokens such as
 * identifiers and numbers.
 */
Symbol newSym(int tokenId, Object value) {
    return new Symbol(tokenId, yyline, yycolumn, value);
}

%}


/*-*
 * PATTERN DEFINITIONS:
 */
number = [0-9]
int_lit = {number}*
id = [a-zA-Z][a-zA-Z0-9]*
letter =[[[^\n]&&[^\t]]&&[[^\\][^\']]]|\\\\|\\\'|\\t|\\n 
char_lit = \'{letter}\'
float_lit = {number}+.{number}+
str_letter =[[[^\n]&&[^\t]]&&[[^\\][^\"]]]|\\\\|\\\"|\\t|\\n 
str_lit = \"{str_letter}*\"
whitespace = [ \n\t\r]

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
comment = {TraditionalComment} | {EndOfLineComment}  

TraditionalComment   = "\\*" [^*] ~"*\\"
// Comment can be the last line of the file, without line terminator.
EndOfLineComment     = \\\\{InputCharacter}*{LineTerminator}?
/**
 * Implement patterns as regex here
 */




%%
/**
 * LEXICAL RULES:
 */

/**
 * Implement terminals here, ORDER MATTERS!
 */
class		{return newSym(sym.CLASS,"class");} 
final		{return newSym(sym.FINAL,"final");} 
void		{return newSym(sym.VOID,"void");} 
int			{return newSym(sym.INT,"int");} 
char		{return newSym(sym.CHAR,"char");} 
bool		{return newSym(sym.BOOL,"bool");} 
float		{return newSym(sym.FLOAT,"float");} 
if			{return newSym(sym.IF,"if");} 
else		{return newSym(sym.ELSE,"else");} 
while		{return newSym(sym.WHILE,"while");} 
read		{return newSym(sym.READ,"read");} 
print		{return newSym(sym.PRINT,"print");} 
printline	{return newSym(sym.PRINTLINE,"printline");} 
return		{return newSym(sym.RETURN,"return");} 
"++"	{return newSym(sym.INCREMENT,"++");} 
"--"	{return newSym(sym.DECREMENT,"--");} 
true		{return newSym(sym.TRUE,"true");} 
false		{return newSym(sym.FALSE,"false");} 
";"	{return newSym(sym.SEMI_COLON,";");} 
"("	{return newSym(sym.L_PAREN,"(");} 
")"	{return newSym(sym.R_PAREN,")");} 
"["	{return newSym(sym.L_BRAC,"[");} 
"]"	{return newSym(sym.R_BRAC,"]");} 
"{"	{return newSym(sym.L_CURLY,"{");} 
"}"	{return newSym(sym.R_CURLY,"}");} 
"~" 	{return newSym(sym.UNI_NOT,"~");} 
"?"	{return newSym(sym.QUESTION_MARK,"?");} 
":"	{return newSym(sym.COLON,":");} 
"*"	{return newSym(sym.MULTIPLY,"*");} 
"/"	{return newSym(sym.DIVIDE,"/");} 
"+"	{return newSym(sym.PLUS,"+");} 
"-"	{return newSym(sym.MINUS,"-");} 
"<"	{return newSym(sym.LESS,"<");} 
">"	{return newSym(sym.GREATER,">");} 
"<="	{return newSym(sym.LESS_EQ,"<=");} 
">="	{return newSym(sym.GREATER_EQ,">=");} 
"=="	{return newSym(sym.EQ,"==");} 
"!="	{return newSym(sym.NOT_EQ,"!=");} 
"&&"	{return newSym(sym.AND,"&&");} 
"||"	{return newSym(sym.OR,"||");} 
","	{return newSym(sym.COMMA,",");} 
"="	{return newSym(sym.ASSIGN,"=");} 
{id}	{return newSym(sym.ID, yytext());} 
{int_lit}	{return newSym(sym.PRINTLINE,new Integer(yytext()));} 
{char_lit}	{return newSym(sym.PRINTLINE,yytext());} 
{str_lit}	{return newSym(sym.PRINTLINE,yytext());} 
{float_lit}	{return newSym(sym.PRINTLINE,yytext());} 
{comment}	{return newSym(sym.PRINTLINE,yytext());} 

{whitespace}    { /* Ignore whitespace. */ }
.               { System.out.println("Illegal char, '" + yytext() +
                    "' line: " + yyline + ", column: " + yychar); } 
