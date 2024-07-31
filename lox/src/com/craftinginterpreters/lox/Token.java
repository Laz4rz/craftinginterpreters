package com.craftinginterpreters.lox;

class Token {
    // final keyword means that I can only assign
    // this variable once per object creation
    // 
    // Object type is inherited by every class in 
    // java, meaning that if I assign type Object
    // to some variable, then it can be an object 
    // of any class
    final TokenType type; 
    final String lexeme;
    final Object literal;
    final int line;

    // this acts as an init of this class, meaning
    // whenever the Token class is created, this 
    // method will initialize the object with passed
    // values
    // (q) not sure what literal is here
    Token(TokenType type, String lexeme, Object literal, int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
    
    // (q) why can the method be public? 
    // (a) public - method can be used on class in 
    // or outside of package
    //
    // private - method can be used only inside 
    // the class definition
    //
    // protected - method can be used only inside
    // the package
    public String toString() {
        return type + " " + lexeme + " " + literal; 
    }
}

