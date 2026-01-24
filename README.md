Münster Logic-Imperative Language
=================================

**Constraint-logic object-oriented programming:** Combine the flexibility of OO/imperative languages with the expressiveness of constraint-logic programming! Muli builds upon Java and extends it with features adapted from constraint-logic programming. Muli offers free variables, constraints, encapsulated search, and a JVM-based runtime environment that executes your Muli programs.

Fork from: [muli](https://github.com/wwu-pi/muli).

All the muli-subproject (git submodules) were merged into this one monorepo.

# Compiler and runtime
## Requirements

* JDK Version 8 (e.g. OpenJDK 1.8.0)
* Git (e.g. git 2.14.1)

## Set up a development environment

1. Clone the repository and `cd` into it.
2. Install dependencies: `make`.
3. Develop the runtime inside the `muli-env` directory. There, use `./gradlew run` to execute Muli; arguments (e. g., class to execute) can be added via ` -Dexec.args="..."`.
4. Develop the compiler inside `muli-lang`. There, run `./gradlew jar` to package the compiler and run the created jar.

## Alternatively: Create a distribution

1. Clone the repository and `cd` into it.
2. Run `make muli-env.zip`. The created file `muli-env.zip` is a full archive containing the Muli runtime and its dependencies.
3. Deploy that file where you need it by extracting its contents.
4. Use a starter from `muli-env-*/bin/` according to your OS.
5. Run `make muli-lang.jar`. The jar archive contains the Muli compiler, ready for use.

# Examples

To create and test new examples I would recommend following:

## Create new example

- Create new subfolder (java package) under `examples/` with your name or purpose of the examples;
  - This dir is also linked under (`muli-env/muli-runtime/examples`)
- Create your examples in `*.muli` (and/or `*.java`) files;
- Create new test class for your example in `muli-env/muli-runtime/src/test/java`, e.g. `muli-env/muli-runtime/src/test/java/brrond/HelloWorldTest.java`)

## Compile and run

- Build the project (compiler and ~~runtime~~)
- ```shell
  cd examples/ 
  java -jar ../muli-lang.jar YOUR_DIR_UNDER_EXAMPLES/Class.muli  # (or multiple classes)
  ```
- In Intellij go into your test class and execute it (via gradle configuration).

## Note

You also need z3 library: (on my machine it's, I built z3 from source)

LD_LIBRARY_PATH=~/bin/z3/build
