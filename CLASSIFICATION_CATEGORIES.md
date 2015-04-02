# Functional Defects
Rules that check for defects that affect the behavior of the program.

## Likely Code Error
Likely code errors that do not fall under any of the following, more specific, categories. 

### Omission
Likely code errors due to an omission (for instance, of an import or validation check).

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
Rules that check common code conventions.

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
