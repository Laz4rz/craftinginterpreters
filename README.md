## Chapter 1

#### Tasks:
Are done inside lox/clox directories!

## Chapter 2

#### Tasks:
1. Pick an open source implementation of a language you like. Download the source code and poke around in it. Try to find the code that implements the scanner and parser. Are they hand-written, or generated using tools like Lex and Yacc? ( .l or .y files usually imply the latter.)

Of course I picked Zig. 

What I found is that Zig bootstraps itself in the bootstrap.c file. While running it, the following things happen:

```
1. **Compiling `zig-wasm2c`**:
   ```c
   cc, "-o", "zig-wasm2c", "stage1/wasm2c.c", "-O2", "-std=c99"
   ```
   - **Purpose**: This command compiles a C file, `wasm2c.c`, into an executable named `zig-wasm2c`.
   - **Functionality**: This executable likely converts WebAssembly (WASM) modules into C code, as suggested by its name and the source file directory (`stage1` suggests an early step in the build process).

2. **Running `zig-wasm2c`**:
   ```c
   "./zig-wasm2c", "stage1/zig1.wasm", "zig1.c"
   ```
   - **Purpose**: This uses the previously compiled `zig-wasm2c` to process a WASM file (`zig1.wasm`).
   - **Functionality**: The output is `zig1.c`, which implies that the WASM file is being translated to a C source file, possibly part of bootstrapping or setting up a larger compilation process.

3. **Compiling `zig1`**:
   ```c
   cc, "-o", "zig1", "zig1.c", "stage1/wasi.c", "-std=c99", "-Os", "-lm"
   ```
   - **Purpose**: Compiles the `zig1.c` (generated from `zig-wasm2c`) along with `wasi.c` into an executable named `zig1`.
   - **Functionality**: This executable likely represents a preliminary version of the main software being built, possibly including some WASI (WebAssembly System Interface) support, indicated by the inclusion of `wasi.c`.

4. **Running `zig1` for various build tasks**:
   - Building a C file:
     ```c
     "./zig1", "lib", "build-exe", "-ofmt=c", "-lc", "-OReleaseSmall", "--name", "zig2", "-femit-bin=zig2.c", "-target", host_triple
     ```
   - Building an object file:
     ```c
     "./zig1", "lib", "build-obj", "-ofmt=c", "-OReleaseSmall", "--name", "compiler_rt", "-femit-bin=compiler_rt.c", "-target", host_triple
     ```
   - **Purpose**: These commands utilize `zig1` to orchestrate further build steps, generating intermediate C source or object files (`zig2.c`, `compiler_rt.c`).
   - **Functionality**: It demonstrates the capability of `zig1` to manage dependency tracking and target-specific builds, as indicated by the parameters like `-target` and `-ofmt=c`.

5. **Compiling `zig2`**:
   ```c
   cc, "-o", "zig2", "zig2.c", "compiler_rt.c", "-std=c99", "-O2", "-fno-stack-protector", "-Istage1"
   ```
   - **Purpose**: Compiles `zig2.c` and `compiler_rt.c` into the final executable `zig2`.
   - **Functionality**: This is likely the final or near-final version of the software, incorporating all previous build steps and optimizations. The inclusion of `compiler_rt.c` suggests it links with runtime support, necessary for comprehensive functionality in the environment it's targeted for.
```

A good source on why Zig went with WASM (and left C++) is: https://ziglang.org/news/goodbye-cpp/

Initially the Zig bootstrap was using C++ and did not free memory, until the compilation was over. Now Andrew chose to use a WASM virtual machine, thats written in C. WASM is architecture agnostic, so using it to build needed C files — that could be architecture dependent — is a big strategic win, cause now these files are going to be transpiled from WASM to correct C code, matching the architecture you compile it on.

The entire bootstrapping structure looks like this:

```
+----------------------+           +-------------------+
|  stage1/wasm2c.c     |           |  stage1/zig1.wasm |
|  Compile with $CC    |           |  WASM file        |
+----------------------+           +-------------------+
         |                                    |
         |                                    |
         V                                    V
+----------------------+   +---------------------------------+
|  Executable:         |   |  Generate zig1.c using           |
|  zig-wasm2c          +--->  ./zig-wasm2c                    |
+----------------------+   +---------------------------------+
                                         |
                                         |
                                         V
                              +-------------------------+
                              |  zig1.c                 |
                              |  Compile with $CC       |
                              |  +                      |
                              |  stage1/wasi.c          |
                              |                         |
                              +-------------------------+
                                         |
                                         V
                              +-------------------------+
                              |  Executable:            |
                              |  zig1                   |
                              +-------------------------+
                                         |
             +---------------------------+---------------------------+
             |                           |                           |
             V                           V                           V
+-------------------------+   +-------------------------+   +-------------------------+
|  Build zig2.c using     |   |  Build compiler_rt.c    |   |  Other builds using     |
|  ./zig1 as a tool       |   |  using ./zig1 as a tool |   |  ./zig1 as a tool       |
|  (e.g., compiler passes)|   |  (e.g., runtime support)|   |  (custom commands)      |
+-------------------------+   +-------------------------+   +-------------------------+
             |                           |                           
             |                           |                           
             V                           V                           
+-------------------------+   +-------------------------+            
|  Compile zig2.c and     |   |  Compile compiler_rt.c  |           
|  compiler_rt.c with $CC |   |  with $CC separately    |           
|  to create zig2         |   |  (if needed)            |           
+-------------------------+   +-------------------------+            

```

The essence is in the 2 first rows, where the WASM transpiler is compiled, and used to transpile zig1.wasm to zig1.c.

Then I guess that build.zig is the compiler file. 

2. Just-in-time compilation tends to be the fastest way to implement a dynamically-
typed language, but not all of them use it. What reasons are there to not JIT?

Just in time is the second option after Ahead of time (for compiled languages, remember other use interpreters, and some like Python can be both interpreted and JITed).

Haven't really found a single best answer, that I would really like. In general JIT needs a longer startup time, consumes more memory and is more CPU hungry.

3. Most Lisp implementations that compile to C also contain an interpreter that lets them execute Lisp code on the fly as well. Why?

Probably because it allows the best of both worlds? You can get a fast compiled version of the program, or use an interpreter to iterate over ideas fast, with dynamic typing.


