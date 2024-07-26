## Lox

That's gonna be a long journey. Yeah, I have no Java experience. At all. Python? Fluent. C? Not bad. Scala? A little. Zig? Ziglings. Java? None, nada, null. 

#### Hello world and how java runs things

To run a hello world program in java we got to create a `HelloWorld.java` file (install JDK btw). Inside the file put this esoteric thing:

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

Now you can call `javac` (`javac HelloWorld.java`) on it, which I believe stand for Java Compiler. You may be a little surprised that it didn't create an executable, but a `HelloWorld.class` file. 

This is because (Claude 3.5 speaking):

```
When you run java HelloWorld, you're not directly running a file. Instead:

Source file (.java):

This is the human-readable code you write (HelloWorld.java).


Compiled file (.class):

When you run javac HelloWorld.java, it creates HelloWorld.class.
This .class file contains Java bytecode, not machine code.


Java Virtual Machine (JVM):

When you run java HelloWorld, you're actually starting the JVM.
The JVM looks for a class named HelloWorld and loads its .class file.
It then finds the main method in that class and begins execution.
```

And some more intuition:

```
Key points:

The java command doesn't run a file directly; it starts the JVM.
The JVM loads and executes the bytecode from the .class file.
You don't specify the .class extension when running Java programs.
The name you provide to the java command is the name of the class containing the main method, not a filename.

This model allows Java to be "write once, run anywhere":

The same .class file can run on any system with a compatible JVM.
The JVM acts as an intermediary between your code and the actual hardware.

So, when you type java HelloWorld, you're telling the JVM to:

Find a class named HelloWorld
Load its bytecode (from HelloWorld.class)
Execute its main method 
```

Got it? Noice. It works for me btw.
