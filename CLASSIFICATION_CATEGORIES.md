# Functional Defects
Rules that check for defects that affect the behavior of the program.

## Check
Errors pertaining to the validation of data, whether that is external (user input) or internal (result values). Most often the absence of a check for return values or user input when such a check might prevent errors.

## Concurrency
Rules that check violations regarding concurrency issues.

## Error Handling
Rules that check that errors and exceptions are properly handled. 

## Interface
Errors concerning interactions with different parts of the system. This can be:
- Interacting with external entities such as libraries, hardware, or the operating system.
- Interacting with internal parts of the systems via, for instance, function calls with the wrong parameters or calling the wrong function.
- How a class exposes itself via its public interface.
- Errors made during inheritance, such as errors made when overriding methods.

## Logic
Errors pertaining to program logic. Primarily concerned with comparisons, control flow, and algorithms.

## Migration
Rules that check violations regarding the migration of one version of the programming language to another. 

## Resource
Errors concerning the initialization, casting, or release of data, whether that data is external (databases) or internal (variables).

# Maintainability Defects
Rules that check for problems that only affect the future development efforts of changing or adding to the system, but have no impact on the correctness of the program.

## Best Practices
Rules that check the code for violations regarding coding conventions that affect the compiled output of the code. 

## Code Structure
Rules that check the structure, in terms of the file system or the coupling, for violations of common conventions.

## Documentation Conventions
Rules that check the documentation for violations of common conventions.

## Metric
A rule that measures a certain attribute of the code. The rule might only be to inform the developer of the value of the attribute, or it can give warnings/errors based on certain thresholds.

## Naming Conventions
Rules that check violations of common naming conventions. 

## Object Oriented Design
Rules that check for violations of proper object oriented design principles.

## Refactorings
Rules that identify code that should be refactored, divided into the following, more specific, categories.

### Redundancies
Rules that determine if a piece of code or artifact is redundant and can be safely deleted.

### Simplifications
Rules that determine if a specific piece of code could be simplified to improve readability and understandability.

## Style Conventions
Rules that check the code for violations of style conventions, that do not affect the compiled output of the code.

# Other
Rules that identify neither functional nor maintainability defects.

## Regular Expressions
Rules that rely on user defined regular expressions. Because the user can use them to check a multitude of things, it is not possible to categorize these rules in general.

## Tool Specific
Rules that configure how the tool works, rather than which defects it checks for (or does not check for). Examples of such rules might be: whether to colorize the output, how to write the results to file, or how much memory the tool can use.
