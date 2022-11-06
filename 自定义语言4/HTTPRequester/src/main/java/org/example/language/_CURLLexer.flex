package org.example.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.example.language.CURLTypes.*;

%%

%{
  public _CURLLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _CURLLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

WHITE_SPACE=[ \t\n\x0B\f\r]+
CURL=CURL|curl
URL=(https?|ftp|file):"//"[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]
QUOTED_STRING=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
BASIC_STRING=[0-9a-zA_Z]*
COMMENT="//".*
OPTION=--append|-a|-A|--user-agent|-anyauth|-u|--user|-G|--get|-X|--request|--header|-H|--output|-o
METHOD=GET|HEAD|POST|PUT|DELETE|CONNECT|OPTIONS|TRACE

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return WHITE_SPACE; }


  {WHITE_SPACE}        { return WHITE_SPACE; }
  {CURL}               { return CURL; }
  {URL}                { return URL; }
  {QUOTED_STRING}      { return QUOTED_STRING; }
  {BASIC_STRING}       { return BASIC_STRING; }
  {COMMENT}            { return COMMENT; }
  {OPTION}             { return OPTION; }
  {METHOD}             { return METHOD; }

}

[^] { return BAD_CHARACTER; }
