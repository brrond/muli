# Muli Examples

This document explains how to write, compile, and run Muli programs.

## Example Structure

Examples are located in `examples/` and tests in `muli-env/muli-runtime/src/test/java/`.

```
examples/
├── brrond/           # User examples
│   ├── HelloWorld.muli
│   ├── Simple.muli
│   └── sudoku/
├── applications/     # Application examples
├── mulist/           # Muli list examples
└── ...
```

## Writing Muli Programs

Muli files (`.muli`) are Java files with constraint-logic extensions:

```java
package brrond;

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
```

Muli adds features like:
- Free variables (symbolic/unbound values)
- Constraints on variables
- Search and backtracking
- Solution enumeration

## Running Examples

### Method 1: Using Gradle (Recommended)

```bash
cd muli-env
./gradlew run -Dexec.args="brrond.HelloWorld"
```

Replace `brrond.HelloWorld` with your class's fully-qualified name.

### Method 2: Using Distribution

```bash
# Build distribution first
make muli-env.zip
unzip muli-env.zip

# Run
muli-env-*/bin/muli-runtime brrond.HelloWorld
```

### Method 3: Run Tests

Many examples have corresponding test classes:

```bash
cd muli-env
./gradlew test --tests "brrond.HelloWorldTest"
./gradlew test --tests "brrond.sudoku.SudokuTest"
```

View all tests:
```bash
find muli-env/muli-runtime/src/test/java -name "*Test.java"
```

## Creating New Examples

### 1. Create .muli File

```bash
# Create in examples directory
nano examples/mypackage/MyExample.muli
```

```java
package mypackage;

public class MyExample {
    public static void main(String[] args) {
        // Your Muli code
    }
}
```

### 2. Compile (if using compiler changes)

```bash
cd muli-lang
./gradlew jar
java -jar muli-lang-*.jar ../examples/mypackage/MyExample.muli
```

**Note**: Most examples don't need recompilation - they're already .muli/.java files.

### 3. Run

```bash
cd muli-env
./gradlew run -Dexec.args="mypackage.MyExample"
```

### 4. Add Test (Optional)

Create test in `muli-env/muli-runtime/src/test/java/`:

```java
package mypackage;

import org.junit.Test;

public class MyExampleTest {
    @Test
    public void testExample() {
        MyExample.main(new String[]{});
    }
}
```

Run:
```bash
./gradlew test --tests "mypackage.MyExampleTest"
```

## Example Walkthrough: Sudoku

**Location**: `examples/brrond/sudoku/`

**Test**: `muli-env/muli-runtime/src/test/java/brrond/sudoku/SudokuTest.java`

**Run**:
```bash
cd muli-env
./gradlew test --tests "brrond.sudoku.SudokuTest"
```

The test demonstrates:
- Constraint-based puzzle solving
- Backtracking search
- Solution finding

## Debugging Examples

### Enable Verbose Output

Edit `muli-env/muli-runtime/src/main/resources/log4j.properties` or pass JVM arguments:

```bash
./gradlew run -Dexec.args="brrond.HelloWorld" -Dlog4j.configuration=file:path/to/log4j.properties
```

### Run in IDE

1. Import project in IntelliJ IDEA
2. Navigate to test class
3. Right-click → Run/Debug Test
4. Set breakpoints as needed

## Common Issues

**Class not found**: Ensure package name matches directory structure
**No main method**: Muli looks for standard Java `main(String[])` method
**Classpath issues**: Run `make install-dev` to ensure dependencies are installed

## Additional Examples

Explore more in:
- `examples/new_examples/`: Recent examples (MagicSquare, ZebraPuzzle, etc.)
- `examples/sac19/`, `examples/sac22_mulib_benchmark/`: Research benchmarks
- `muli-env/muli-runtime/src/test/java/de/wwu/muli/`: Core test suite
