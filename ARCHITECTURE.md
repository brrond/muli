# Muli Architecture

This document provides technical details about Muli's architecture and components.

## High-Level Overview

```
┌───────────────────────────────────────────────────────┐
│                    Muli System                        │
├───────────────────────────────────────────────────────┤
│                                                       │
│  Compile Time              Runtime                   │
│  ┌─────────────┐          ┌──────────────────┐      │
│  │  muli-lang  │          │    muli-env      │      │
│  │  (Compiler) │────.class│   (Runtime)      │      │
│  └─────────────┘──────────>└─────┬────────────┘      │
│        │                          │                   │
│        │ ExtendJ                  │ Muggl             │
│        ▼                          ▼                   │
│   Java Parser           Symbolic Executor            │
│   + Muli Extensions     + Constraint Solving         │
│                         + Search Strategies          │
└───────────────────────────────────────────────────────┘
```

## Component Breakdown

### 1. muli-lang (Compiler)

**Purpose**: Extend Java syntax with constraint-logic features and compile to bytecode

**Technology**: ExtendJ + JastAdd

**Key Files**:
- `src/jastadd/*.jrag`: Attribute grammars for Muli extensions
- `src/jastadd/*.ast`: AST node definitions
- `extendj/`: Java compiler base

**Pipeline**:
```
.muli source → Parse → Type Check → Generate Bytecode → .class
```

**Extensions Added**:
- Free variable declarations
- Constraint expressions
- Search constructs
- Backtracking annotations

### 2. muli-env (Runtime Environment)

**Purpose**: Execute Muli bytecode symbolically with constraint solving

**Subprojects**:

#### a. muli-runtime

**Main execution engine**

**Entry Point**: `de.wwu.muli.env.MuliRunner`

**Key Packages**:
- `de.wwu.muli.env`: Main runner and VM control
- `de.wwu.muli.iteratorsearch`: Search algorithms (DFS, BFS, IDDFS)
- `de.wwu.muli.listener`: Execution listeners and coverage tracking

**Search Algorithms** (`iteratorsearch/`):
- `DepthFirstSearchAlgorithmWithLocalBacktracking`
- `DepthFirstSearchAlgorithmWithGlobalBacktracking`
- `BreadthFirstSearch`
- `IterativeDeepeningDFS`

Each algorithm explores the search space differently for finding solutions.

#### b. muggl-core

**Symbolic execution engine**

**Responsibilities**:
- Interpret Java bytecode
- Track symbolic values and constraints
- Manage execution frames and stacks
- Handle choice points for backtracking

**Key Concepts**:
- **Symbolic Variables**: Represent unknown values
- **Constraints**: Conditions on symbolic variables
- **Choice Points**: Branch points in execution
- **Backtracking**: Exploring alternative paths

#### c. muggl-common

**Shared utilities and data structures**

#### d. muggl-solvers

**Constraint solver abstraction layer**

**Interface**: Common API for different solvers

**Implementations**:

##### muggl-solver-jacop
- **Solver**: JaCoP (Java Constraint Programming)
- **Domain**: Finite domain constraints
- **Use Case**: Integer constraints, combinatorial problems

##### muggl-solver-muconst
- **Solver**: Custom Muli constraint solver
- **Domain**: Muli-specific constraints
- **Use Case**: Optimized for Muli semantics

##### muggl-solver-z3
- **Solver**: Z3 SMT solver
- **Domain**: SMT (Satisfiability Modulo Theories)
- **Use Case**: Complex logical and arithmetic constraints

**Solver Selection**: Configured at runtime, typically automatic based on constraint types

### 3. muli-classpath

**Purpose**: Manage classpath and provide runtime library support

**Integration**: Used by both compiler and runtime for library dependencies

## Execution Flow

### Compile-Time

```
1. User writes .muli file with free variables and constraints
2. muli-lang parser reads file (ExtendJ frontend)
3. Muli aspects extend AST with constraint-logic nodes
4. Type checking ensures correctness
5. Bytecode generation (standard JVM bytecode with annotations)
6. Output: .class files
```

### Runtime

```
1. MuliRunner loads .class file
2. Muggl-core initializes symbolic execution
3. For each symbolic variable:
   a. Create choice point
   b. Explore branch
   c. Collect constraints
4. Send constraints to solver
5. If SAT:
   - Continue execution with solution
   - Yield result
6. If UNSAT:
   - Backtrack to previous choice point
   - Try alternative
7. Repeat until search space exhausted
```

### Example: Finding Integer Solutions

```java
// .muli code
int x = Muli.freeInt();
Muli.assume(x > 0 && x < 10);
System.out.println(x);
```

**Execution**:
1. `freeInt()` creates symbolic variable
2. `assume()` adds constraint: x ∈ {1,2,...,9}
3. Solver finds satisfying value (e.g., x=1)
4. Prints: 1
5. Backtrack, solver finds next (x=2)
6. Prints: 2
7. Continue until all solutions found

## Search Strategies

Different algorithms explore solution space:

- **DFS (Depth-First)**: Deep exploration, memory efficient
- **BFS (Breadth-First)**: Level-by-level, finds shortest paths
- **IDDFS (Iterative Deepening)**: Combines DFS memory efficiency with BFS completeness

Strategy selected via configuration or API calls.

## Configuration

**muli-env runtime**:
- `muli-env/muli-runtime/src/main/resources/log4j.properties`: Logging
- VM arguments: Solver selection, search strategy

**Build configuration**:
- Root `build.gradle`: Multi-project setup
- Individual `build.gradle` files per module

## Integration Points

### Compiler → Runtime
- Compiler generates standard bytecode
- Runtime interprets bytecode symbolically
- No direct coupling; loose integration via bytecode

### Runtime → Solvers
- Abstract `Solver` interface in `muggl-solvers`
- Concrete implementations in solver-specific modules
- Runtime dispatches constraints to active solver

### Examples → Runtime
- Examples are standard classes with `main()` methods
- Runtime loads via classloader
- Tests use JUnit framework

## Extension Points

**Adding Language Features** (muli-lang):
1. Define AST nodes in `.ast` files
2. Add semantics in `.jrag` aspects
3. Extend parser/scanner if syntax changes needed

**Adding Solver** (muli-env):
1. Implement `Solver` interface
2. Create new `muggl-solver-*` module
3. Register in `muggl-solvers`

**Adding Search Algorithm** (muli-env):
1. Extend `AbstractSearchAlgorithm`
2. Implement search logic
3. Register in configuration

## Build System

**Gradle multi-project build**:
- Root coordinates all subprojects
- Each module has own `build.gradle`
- Dependencies managed via Gradle

**Makefile**: High-level convenience targets for common tasks

## References

- ExtendJ: https://extendj.org/
- JastAdd: http://jastadd.org/
- Muggl: https://github.com/wwu-pi/muggl
- JaCoP: https://github.com/radsz/jacop
- Z3: https://github.com/Z3Prover/z3
