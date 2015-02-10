package ejerciociocup;
import java_cup.runtime.Symbol;
import java_cup.runtime.*;
import java.io.Reader;

%%
%class AnalizadorLexico
%cup
%full
%line
%char
%ignorecase
%eofval{
	return new Symbol(sym.EOF,new String("Fin del archivo"));
%eofval}

digito = [0-9]
letra = [a-zA-Z]
id = ({letra}|{digito})*({letra}|{digito})*
espacio = \t|\f|" "|\r|\n

%%

","			{return new Symbol(sym.COMA, yychar, yyline, yytext());}
":"			{return new Symbol(sym.DOS_PUNTOS, yychar, yyline, yytext());}
"{"			{return new Symbol(sym.LLAVE_IZQUIERDA, yychar, yyline, yytext());}
"}"			{return new Symbol(sym.LLAVE_DERECHA, yychar, yyline, yytext());}
{id}                    {return new Symbol(sym.ID, yychar, yyline, yytext());}
{espacio}               {}		
.                       { System.out.println("Caracter ilegal: " + yytext()); }