---
layout: default
title: Home
nav_order: 0
has_children: false
---

# Konstruct

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

# Overview

Konstruct is a scripting language designed for easy to write scripts that come with lots of power. Konstruct takes an input and spits out a parsed object that can be evaluated at any point.

Konstruct is a basic scripting language with variable definition, function definition, expression evaluation, if statements, and other common features.

## Guide

Konstruct creates a list of nodes (objects that can be evaluated for a KonstructObject) and on evaluate concatenates all of them together. To help understand what is happening throughout the wiki, will be some tree diagrams on the breakdown of nodes. Each line represents a node and the first word after the bracket is the node type.

```
Input: variable = 'Hello' + (5 + 5 / 3)

- <root scope 0>
| - <assignment variable>
| | - <root scope 0>
| | | - <operator  (<node>) (<node>)>
| | | | - <literal Hello>
| | | | - <root scope 0>
| | | | | - <operator  (<node>) (<node>)>
| | | | | | - <int 5>
| | | | | | - <operator  (<node>) (<node>)>
| | | | | | | - <int 5>
| | | | | | | - <int 3>
```

`<root scope <num>` is a node that holds other nodes together. Scope references how deep in nested functions it is.

`<literal [string]>` is a node that holds raw text.

`<variable [name]>` is a node that can fetch data off of a key.

`<assignment [variable]>` is a node that will assign data to a variable.

`<function [name](args)>` is a node that can evaluate arguments. The number of `<root>`'s are how many arguments this function has.

It can be complicated to visualize, but the names should be pretty self explanatory.

## Input Builder

In certain situations Konstruct can parse scripts within a string. If this method is used, when something is surrounded in `[[]]`'s it will be evaluated as a Konstruct script. `5 + 5 is [[5 + 5]]` -> `5 + 5 is 10`.

## Examples

```
Input: Integral thing: [[integral = '2*int(sqrt(1-x^2), x, -1, 1)'; calc(integral)]]
Output: Integral thing: 3.1415920928388927

Input: Round: [[round(5 * 4 / 3)]]
Output: Round: 7

Input: I really like the [[get(2, 'first', 'second', 'third')]] option!
Output: I really like the third option!

Input: [[replace('''\[(.+)\]''', 'I can use [regex] now in functions', '$1', 'regex')]]
Output: I can use regex now in functions
```