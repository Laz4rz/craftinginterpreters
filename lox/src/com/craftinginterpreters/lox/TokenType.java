package com.craftinginterpreters.lox;

// Tokens are enums for groups of characters the
// parser/lexer groups together, the groups them-
// selves are called lexemes

enum TokenType {
    // SINGLE CHARACTER TOKEN TYPES
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    // COMPARISON TOKENS, SINGLE OR TWO CHARACTER
    // BANG is just !
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // LITERALS
    // (q) not sure what identifier is yet
    IDENTIFIER, STRING, NUMBER,

    // Keywords
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}


