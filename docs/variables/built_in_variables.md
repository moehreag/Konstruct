---
layout: default
title: Built in Variables
nav_order: 2
parent: Variables
---

# Built in Variables

Konstruct comes with a few variables to provide useful information. Developers **cannot override these.**

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Current Functions and Variables

There are ways to access the variables and functions you can use quite easily.

### functions

To access functions just use the variable `functions`

#### EXAMPLE
{: .no_toc }
```
Input: Functions - {functions}
Output: Functions - randInt, round, owo, get, replace, calc, romannumeral
```

### variables

To access variables just use the variable `variables`

#### EXAMPLE
{: .no_toc }
```
Input: Variables - {variables}
Output: Variables - variables, functions, konstructVersion
```

---

## Konstruct Version

The running Konstruct version is stored in a variable `konstructVersion`.

#### EXAMPLE
{: .no_toc }
```
Input: Running v{konstructVersion}
Output: Running v1.0.0
```

## Other Reserved Symbols

There are other variables that are referenced in specific situations and otherwise don't always have a value.

### %

This is variable to use in function with the `!` global function modifier. More is explained in the function section. This will store parse results of the rest of the equation.
