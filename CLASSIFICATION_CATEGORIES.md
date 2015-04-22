# Functional Defects
Rules that check for defects that affect the behavior of the program.

# Logic
Errors pertaining to program logic. Primarily concerned with comparisons, control flow, and algorithms.

# Check
Errors pertaining to the validation of data, whether that is external (user input) or internal (result values). Most often the absence of a check for return values or user input when such a check might prevent errors.

# Resource
Errors concerning the initialization, casting, or release of data, whether that data is external (databases) or internal (variables).

# Interface
Errors concerning interactions with different parts of the system. This can be:
- Interacting with external entities such as libraries, hardware, or the operating system.
- Interacting with internal parts of the systems via, for instance, function calls with the wrong parameters or calling the wrong function.
- How a class exposes itself via its public interface.
- Errors made during inheritance, such as errors made when overriding methods.

## Concurrency
Rules that check violations regarding concurrency issues.

## Error Handling
Rules that check that errors and exceptions are properly handled. 

## Migration
Rules that check violations regarding the migration of one version of the programming language to another. 

# Maintainability Defects
Rules that check for defects that do not affect the behavior of the program, but rather hamper the future development of the system by increasing its technical debt.

## Code Structure
Rules that check the structure, in terms of the file system or the coupling, for violations of common conventions.

## Naming Conventions
Rules that check violations of common naming conventions. 

## Coding Conventions
Rules that check common code conventions, divided into the following, more specific, categories.

### Style Conventions
Rules that check the code for violations of style conventions, that do not affect the compiled output of the code.

### Best Practices
Rules that check the code for violations regarding coding conventions that affect the compiled output of the code. 

## Documentation Conventions
Rules that check the documentation for violations of common conventions.

## Refactorings
Rules that identify code that should be refactored, divided into the following, more specific, categories.

### Simplifications
Rules that determine if a specific piece of code could be simplified to improve readability and understandability.

### Redundancies
Rules that determine if a piece of code or artifact is redundant and can be safely deleted.

## Metric
A rule that measures a certain attribute of the code. The rule might only be to inform the developer of the value of the attribute, or it can give warnings/errors based on certain thresholds.

## Object Oriented Design
Rules that check for violations of proper object oriented design principles.

# Other
Rules that identify neither functional nor maintainability defects.

## Tool Specific
Rules that are not meant to check the code for problems, but rather to configure the tool itself.

## Regular Expressions
Rules that rely on user defined regular expressions. Because the user can use them to check a multitude of things, it is not possible to categorize these rules in general.
