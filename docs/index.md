Konstruct is a scripting-esk language designed to format input easily and provide many tools for customization. Konstruct takes an input and spits out a parsed object that can be evaluated at any point.

There are a few different object types in Konstruct. There are string literals, variables, functions, and arguments (which are essentially just string literals). Take a look at the menu to see how to use all of these.

## Guide

Konstruct creates a list of nodes (objects that return a string) and on evaluate concatenates all  of them together. To help understand what is happening throughout the wiki, will be some tree diagrams on the breakdown of nodes. Each line represents a node and the first word after the bracket is the node type.

```
Input: This is a literal and this is a {placeholder}. Over here is a [function(argument)].

- <root>
  - <literal This is a literal and this is a >
  - <variable placeholder>
  - <literal . Over here is a >
  - <function function(<root>)>
    - <root>
      - <literal argument>
  - <literal .>
```

`<root>` is a node that holds other nodes together.

`<literal [string]>` is a node that holds raw text.

`<variable [name]>` is a node that can fetch data off of a key.

`<function [name](args)>` is a node that can evaluate arguments. The number of `<root>`'s are how many arguments this function has.

Arguments are stored as `<root>`'s since they can store multiple nodes for each.

## Character Types

Within Konstruct the developer can choose whatever characters they want for force literals, arguments, functions, escape, and some other ones. These are the default that ones Konstruct uses (and this tutorial will use):

| Key   | Name             | Explanation                                                 |
|-------|------------------|-------------------------------------------------------------|
| `{`   | Argument start   | Represents the start of an argument                         |
| `}`   | Argument end     | Represents the end of an argument                           |
| `[`   | Function start   | Represents the start of a function                          |
| `]`   | Function end     | Represents the end of a function                            |
| `(`   | Argument start   | Represents the start of arguments                           |
| `)`   | Argument end     | Represents the end of arguments                             |
| `\ `  | Escape character | Escapes the character that follows it                       |
| `'`   | Force literal    | Defines a literal and characters within it will be literal  |
| `'''` | Strong literal   | Defines a literal where no characters within can be escaped |

## Examples

Calculations and nested functions
```
Input: I can round... [round([calc(7 / 3 * 2)],1)]
Output: I can round... 4.7
```

Randomized selections
```
Input: [get([randint(0,2)], wb, welcome, Welcome back!)]
Output: Welcome back!
```

Weird functions
```
Input: [!owo({%})]'''This is super duper duper cool and means that it's easier to write functions'''
Output: Thais is supew dupew dupew coow and means that it's easiew to wwite functions
```

Regex and replacing
```
Input: [replace('''\[(.+)\]''', 'I can use [regex] now in functions', $1, regex)]
Output: I can use regex now in functions
```

Comments
```
Input: Cool calculator! [calc((5 / 3)^3 / 4 + 3)] [#This calculates some cool stuff]
Output: Cool calculator! 3.0462962962962963
```