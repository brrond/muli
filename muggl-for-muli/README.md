# Muggl-for-Muli

Forked and adapted version of [Muggl](https://github.com/wwu-pi/muggl) (Muenster Generator of Glass-box Test Cases) for Muli.

## What is Muggl?

Muggl is a symbolic execution engine for Java bytecode. It executes programs symbolically, tracking constraints rather than concrete values, enabling:
- Path exploration with backtracking
- Constraint collection for solver-based execution
- Test case generation

## Muli Integration

Muli uses Muggl as its execution backend:
- **muggl-core**: Bytecode interpreter with symbolic execution
- **muggl-common**: Shared utilities
- **muggl-solvers**: Constraint solver interfaces
- **muggl-solver-jacop**: JaCoP solver binding
- **muggl-solver-muconst**: Custom Muli constraint solver
- **muggl-solver-z3**: Z3 SMT solver binding

## Structure

This is a standalone Muggl repository adapted for Muli. The main integration point is through `muli-env` which uses these Muggl components.

**Note**: For Muli development, work in `muli-env` subprojects which contain the integrated Muggl modules.
