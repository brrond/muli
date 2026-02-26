Münster Logic-Imperative Language
=================================

**Constraint-logic object-oriented programming:** Combine the flexibility of OO/imperative languages with the expressiveness of constraint-logic programming! Muli builds upon Java and extends it with features adapted from constraint-logic programming. Muli offers free variables, constraints, encapsulated search, and a JVM-based runtime environment that executes your Muli programs.

Fork from: [muli](https://github.com/wwu-pi/muli).

All the muli-subproject (git submodules) were merged into this one monorepo.

## Quick Start

```bash
# Clone and setup
git clone https://github.com/brrond/muli.git
cd muli
make install-dev

# Run an example
cd muli-env
./gradlew run -Dexec.args="brrond.HelloWorld"
```

## Documentation

- **[ARCHITECTURE.md](ARCHITECTURE.md)**: Deep dive into system architecture and components
- **[EXAMPLES.md](EXAMPLES.md)**: How to write, run, and debug Muli programs
- **[muli-lang/README.md](muli-lang/README.md)**: Compiler documentation
- **[muggl-for-muli/README.md](muggl-for-muli/README.md)**: Symbolic execution engine

## How Muli Works

Muli extends Java with constraint-logic programming features:
1. Write programs in `.muli` files (extended Java syntax with free variables and constraints)
2. Compile with **muli-lang** (compiler) to standard Java bytecode
3. Execute with **muli-env** (runtime) which uses **Muggl** for symbolic execution
4. Solvers (JaCoP, muconst, Z3) handle constraint satisfaction

## Architecture

```
┌─────────────┐     Java        ┌──────────────┐
│ .muli files │ ──────────────> │  .class files │
└─────────────┘   (muli-lang)   └──────────────┘
                                        │
                                        │ execution
                                        ▼
                              ┌──────────────────┐
                              │   muli-env       │
                              │   (MuliRunner)   │
                              └────────┬─────────┘
                                       │
                         ┌─────────────┼─────────────┐
                         ▼             ▼             ▼
                    ┌────────┐   ┌─────────┐  ┌─────────┐
                    │ Muggl  │   │ Solvers │  │ Search  │
                    │ Core   │   │ (J/M/Z3)│  │ Algos   │
                    └────────┘   └─────────┘  └─────────┘
```

### Components

- **muli-lang**: ExtendJ-based compiler that translates `.muli` to `.class`
- **muli-env**: Runtime environment containing:
  - **muli-runtime**: Main execution engine
  - **muggl-core**: Symbolic execution engine
  - **muggl-solvers**: Constraint solver integrations
- **muli-classpath**: Classpath and library support
- **muggl-for-muli**: Forked Muggl adapted for Muli

### Lang vs Env

- **lang**: Compile-time - parses Muli syntax, generates bytecode
- **env**: Runtime - executes bytecode symbolically with backtracking

### What is Muggl?

**Muggl** (Muenster Generator of Glass-box Test Cases) is a symbolic execution engine for Java bytecode. Muli uses Muggl to:
- Execute bytecode symbolically (tracking constraints)
- Manage search trees and backtracking
- Interface with constraint solvers

### Solvers

Three constraint solvers are integrated:
- **JaCoP**: Java Constraint Programming solver (finite domains)
- **muconst**: Custom constraint solver for Muli
- **Z3**: SMT solver from Microsoft Research

## Requirements

- **JDK 8** (OpenJDK 1.8.0 or equivalent) - **Required**, newer versions may cause issues
- Git
- Make (for build automation)

## Compilation

### Quick Start
```bash
git clone https://github.com/brrond/muli.git
cd muli
make install-dev  # Install dependencies
```

### Build Components

1. **Runtime distribution** (muli-env.zip):
   ```bash
   make muli-env.zip
   ```
   Creates `muli-env.zip` with runtime, dependencies, and launch scripts in `muli-env-*/bin/`

2. **Compiler JAR** (muli-lang.jar):
   ```bash
   make muli-lang.jar
   ```
   Creates `muli-lang.jar` - standalone compiler

3. **Both**:
   ```bash
   make dist
   ```

## Running Examples

### Using Runtime Directly
```bash
cd muli-env
./gradlew run -Dexec.args="brrond.HelloWorld"
```

## Development Workflow

### IntelliJ IDEA

1. **Import Project**: File → Open → Select `muli` root directory
2. **Gradle Sync**: IntelliJ auto-imports Gradle projects
3. **Run Configurations**:
   - **Runtime**: Create Gradle run config for `muli-env:run` with arguments in VM options: `-Dexec.args="your.ClassName"`
4. **Compiler Development**: Work in `muli-lang` module, run `jar` task to rebuild
5. **Runtime Development**: Work in `muli-env/muli-runtime`

### Console

**Development cycle**:
```bash
# 1. Write .muli file in examples/
# 2. Compile to .class (if using compiler changes):
cd muli-lang && ./gradlew jar
java -jar muli-lang-*.jar ../examples/yourfile.muli

# 3. Run with runtime:
cd muli-env
./gradlew run -Dexec.args="your.ClassName"
```

**Rebuild everything**:
```bash
make clean  # if defined
make install-dev
```

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
