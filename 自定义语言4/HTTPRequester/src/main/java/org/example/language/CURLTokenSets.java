package org.example.language;

import com.intellij.psi.tree.TokenSet;

public interface CURLTokenSets {

    TokenSet COMMAND = TokenSet.create(CURLTypes.COMMAND);
    TokenSet KV = TokenSet.create(CURLTypes.KV);
    TokenSet METHOD = TokenSet.create(CURLTypes.METHOD);
    TokenSet OPTION = TokenSet.create(CURLTypes.OPTION);
    TokenSet OPTION_1 = TokenSet.create(CURLTypes.OPTION_1);
    TokenSet OPTION_1_STATEMENT = TokenSet.create(CURLTypes.OPTION_1_STATEMENT);
    TokenSet OPTION_2 = TokenSet.create(CURLTypes.OPTION_2);
    TokenSet OPTION_2_STATEMENT = TokenSet.create(CURLTypes.OPTION_2_STATEMENT);
    TokenSet OPTION_3 = TokenSet.create(CURLTypes.OPTION_3);
    TokenSet OPTION_3_STATEMENT = TokenSet.create(CURLTypes.OPTION_3_STATEMENT);
    TokenSet OPTION_4 = TokenSet.create(CURLTypes.OPTION_4);
    TokenSet OPTION_4_STATEMENT = TokenSet.create(CURLTypes.OPTION_4_STATEMENT);
    TokenSet OPTION_5 = TokenSet.create(CURLTypes.OPTION_5);
    TokenSet OPTION_5_STATEMENT = TokenSet.create(CURLTypes.OPTION_5_STATEMENT);
    TokenSet OPTION_6 = TokenSet.create(CURLTypes.OPTION_6);
    TokenSet OPTION_6_STATEMENT = TokenSet.create(CURLTypes.OPTION_6_STATEMENT);
    TokenSet OPTION_7 = TokenSet.create(CURLTypes.OPTION_7);
    TokenSet OPTION_7_STATEMENT = TokenSet.create(CURLTypes.OPTION_7_STATEMENT);
    TokenSet BASIC_STRING = TokenSet.create(CURLTypes.BASIC_STRING);
    TokenSet COMMENT = TokenSet.create(CURLTypes.COMMENT);
    TokenSet CURL = TokenSet.create(CURLTypes.CURL);
    TokenSet QUOTED_STRING = TokenSet.create(CURLTypes.QUOTED_STRING);
    TokenSet URL = TokenSet.create(CURLTypes.URL);
}
