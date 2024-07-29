// package ... usually mirrors the directory structure of a project
// so this should be in rootfolder/com/craftinginterpreters/lox
// it works like manually defining module names, after writing this
// is some package, you can import it in other, files like so:
// import com.craftinginterpreters.someClass
// or import com.craftinginterpreters.* to import all
package com.craftinginterpreters.lox;

// io contains functions for i/o operations
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
// nio extends the i/o, stands for: new i/o
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {
    static boolean hadError = false;

    // public - makes it accessible from any other class
    // static - associated with class, doesn't need class instance
    // void   - return type, Lox returns nothing
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            // 64 signifies incorrect usage exit
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }
    
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        // we create a new String object, using a stream of bytes
        // and defaultCharset to decode the bytes into characters
        // defualtCharset retrieves charset based on the config
        // of machine running the JVM
        run(new String(bytes, Charset.defaultCharset()));

        // if run had error in syntax - exit with 65
        // 65 signifies data format error
        if (hadError) System.exit(65);
    }

    private static void runPrompt() throws IOException {
        // InputStreamReader - automatic conversion from bytes to chars
        // System.in - a standard input stream that listens to input from
        // keyboard 
        // BufferedReader - reading methods and buffering for InputStreamReader
        // without it, InputStreamReader would have to directly call system for
        // each read, and we'd have to manually read lines
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);
        
        // using infinite for instead of while is stylistic
        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }
    
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    static void error(int line, String message) {
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        // err - a different printing level, allows granular control on
        // where to redirect them (ie. logs)
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        
        hadError = true;
    }
}

