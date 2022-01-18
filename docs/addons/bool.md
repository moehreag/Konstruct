---
layout: default
title: Booleans
nav_order: 1
parent: Addons
---

# Booleans

Booleans are a useful tool to add conditionals and much power to how Konstruct evaluates.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Adding

To add booleans you can use `BooleanFuction.addAllConditionalFunctions(processor)` to easily add all the useful boolean functions. You can also add each one individually.

## How Booleans Work

Booleans in Konstruct are defined by either a 0 (false) or a 1 (true). 

Internally they technically work as any number that is not zero is true, and any number that is 0 or is not a number is false, but 0 and 1 should be used whenever applicable.

---

## Bool Function

Runs gate operations based on two booleans.

Name
: `bool`

Arguments
: `<bool1>`, `and/or/nand/nor/xor/xnor`, `<bool2>`

Output
: Returns the output of the gate operation. View [surrey](http://www.ee.surrey.ac.uk/Projects/CAL/digital-logic/gatesfunc/index.html) for information on what each logic gate does.

#### EXAMPLE
{: .no_toc }
```
Input: [if([bool(0, or, 1)], This is true!, This is false...)]
Output: This is true!
```

---

## If Function

Run's a statement if true, and optionally one if false. The `else` part returns a blank string if not specified.

Name
: `if`

Arguments
: `<bool>`, `<runIfTrue>`, `[<runIfFalse>]`

Output
: Returns `<runIfTrue>` if the `<bool>` is true, otherwise it returns `<runIfFalse>`. If `<runIfFalse>` is not specified it returns a blank string.

#### EXAMPLE
{: .no_toc }
```
Input: [if(1, This is true!, This is false...)]
Output: This is true!
```
