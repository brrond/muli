# Muli-Lang: Muli Compiler

Muli's compiler extends Java with constraint-logic programming features. Built on [ExtendJ](https://extendj.org/), an extensible Java compiler framework.

## Overview

**muli-lang** translates `.muli` files to standard Java bytecode, adding support for:
- Free variables (unbound symbolic values)
- Constraint expressions
- Encapsulated search constructs
- Backtracking primitives

## Architecture

Built on ExtendJ using JastAdd:
- `.jrag`/`.jadd` files: Attribute grammar specifications
- `.ast` files: Abstract syntax tree definitions
- ExtendJ frontend: Java 8 parsing and semantic analysis
- Custom aspects: Muli-specific language features

## Building

### Standalone JAR
```bash
./gradlew jar
# Output: muli-lang-*.jar
```

### Use from Root
```bash
make muli-lang.jar
```

## Usage

```bash
java -jar muli-lang-*.jar path/to/File.muli
```

Compiles `.muli` to `.class` bytecode compatible with standard JVM.

## Development

**Project structure**:
- `src/jastadd/`: Muli language specifications (.jrag, .ast)
- `src/java/`: Compiler entry point
- `extendj/`: ExtendJ submodule (Java compiler base)
- `build.gradle`: JastAdd plugin configuration

**Making changes**:
1. Edit `.jrag` or `.ast` files in `src/jastadd/`
2. Run `./gradlew jar` to rebuild
3. Test with sample `.muli` files

**Force rebuild** (if needed):
```bash
./gradlew --rerun-tasks jar
```

## ExtendJ Integration

Muli imports `java8 frontend` module from ExtendJ for:
- Lexing/parsing Java syntax
- Type checking
- Name resolution

Muli aspects extend these with constraint-logic features.

## References

- [ExtendJ](https://extendj.org/)
- [JastAdd](http://jastadd.org/)
- [JastAdd Gradle Plugin](https://github.com/jastadd/jastaddgradle)