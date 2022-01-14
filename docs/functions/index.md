---
layout: default
title: Functions
nav_order: 3
has_children: true
---

# Functions

Functions are ways to evaluate arguments.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Defining Functions

Functions can be defined using the syntax `[<function name>(<arguments>...)]`. One example could be `[calc(5 + 5)]`. To define arguments put a `(` after the argument name and then specify the arguments. For each argument use a `,` to deliminate. If you want to include `,`'s within the text you can escape them or use a strong/force literal. `(this,this)` would be seen as two variables while `('this,this')` would be seen as one variable.

After the arguments and the closing `)` no text should go after that and should immediately resolve the function by using `]`. 

(For function name it should be alphanumeric, otherwise the name could be pruned or invalid)

---

## Comments

Comments are written like a function, but do not get parsed. Make sure that when writing comments there aren't any unescaped characters that have different meaning. To write a comment write a function, but make the first character of the name a `#`. 

(Whitespace will be retained around it so make sure you have the correct spaces. Comments can also nullify newlines.)

#### EXAMPLE
{: .no_toc }
```
Input: I like to calculate [# This is just a literal! Nothing fancy].
Output: I like to calculate .
```

---

## Nested Functions

Functions can be easily nested within the arguments. Each argument is treated as a separate `<root>` node. Functions can be concatenated with literals and variables used within arguments. All that matters is to separate make sure to use the argument deliminator (`,`)

You can use force literals or strong literals inside variables to retain all characters and not have to escape out of them.

#### EXAMPLE
{: .no_toc }
```
Input: Here is a [function({argument},Function: [function(5 literal... {argument3},'''This is a , comma with a \''')]{argument2})]!

- <root>
  - <literal Here is a >
  - <function function(<root>,<root>)>
    - <root>
      - <variable argument>
    - <root>
      - <literal Function: >
      - <function function(<root>,<root>)>
        - <root>
          - <literal 5 literal... >
          - <variable argument3>
        - <root>
          - <literal This is a , comma with a \>
      - <variable argument2>
  - <literal !>
```

If multiple lines in your input is an option, it is recommended breaking up arguments and functions with returns to improve readability. All whitespace that isn't a literal will be pruned

#### EXAMPLE
{: .no_toc }
```
Here is a [
    function(
        {argument},
        Function: [
            function(
                5 literal... {argument3},
                '''This is a , comma with a \'''
            )
        ]{argument2})]!
```