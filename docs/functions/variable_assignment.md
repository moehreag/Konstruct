---
layout: default
title: Variable Assignment and Line Ends
nav_order: 2
parent: Functions
---

# Variable Assignment and Line Ends

With Konstruct you can define variables locally and then nullify any statement with line ends.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Variable Assignment

To assign a variable you use the format `[<name> = (<value>)]`. To fetch the variable you use `{<name>}`. Variables can be assigned almost anywhere. When evaluated they equate to just a blank string. This means in the context `[calc([num = (5 + 5)] {num} + {num})]` this will return 20.

The value of a variable can include functions and other variables, but the name of the variable has to just be a literal string. Including variables or functions within a name **will** cause an error.

---

## Line ends

Line ends are a great way to do some operation and store it, and then completely discard it. Its action is essentially preventing anything before it from returning anything. If you end your Konstruct command with a line end **it will return nothing**

```
This is cool;[# This returns nothing!!!!]
```

```
Blah blah blah;
Super cool
[# This will just return Super cool]
```

These are similar to comments, but unlike them any content within them will still be fully evaluated.

These can be used to help space out variable definitions and do some calculations. For other functions that may return something but the output is not wanted, this also is useful.
    
Variable definition example:

```
[expression = (5 + 5)];
[expression2 = ({expression} / 3)];
[calc({expression2}]
```