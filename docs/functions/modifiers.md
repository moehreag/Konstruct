---
layout: default
title: Modifiers
nav_order: 1
parent: Functions
---

# Modifiers

Modifiers are ways to add more behavior to functions.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Defining Modifiers

While defining a function the characters before the name (can be separated by a space, or just anything not alphanumeric) are split up and are modifiers.

#### EXAMPLE
{: .no_toc }
```
Input: [!func(cool)]

Name: func
Modifiers: !
```

---

## Global Modifier

The global modifier allow a function to be inputted with the rest of the text. This can allow for better visibility in functions. For this modifier to work the function **has to be the first character of the text**.

To access the rest of the evaluated string use the argument `%`. (In this example the variable with full syntax will be `{\%}` because I can't get docs to work without the `\ `.)

The following example *owo*'s the rest of the text.

#### EXAMPLE
{: .no_toc }
```
Input: [!owo({\%}]This is super duper duper cool and means that it\'s easier to write functions
Output: Thais is supew dupew dupew coow and means that it's easiew to wwite functions
```
