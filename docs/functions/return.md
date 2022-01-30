---
layout: default
title: Return and Line Ends
nav_order: 2
parent: Functions
---

# Line Ends and Returns

With Konstruct you can nullify any statement with line ends.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Line ends

Line ends are a great way to do some operation and store it, and then completely discard it. Its action is essentially preventing anything before it from returning anything. If you end your Konstruct command with a line end **it will return nothing**

Content without a line end works like a `return` statement. When values are fetched from Konstruct script if no return value is used it will just be the lat statements without `;`'s.

```
Input: 'This is cool'; # This returns just null

Output: null
```

```
Input: x = 5; x + 3

Output: 8
```


## Return Statements

Returns are a type of function. To call them just do `return()` or `return(<value>)`. Regardless of the previous statements `return` will override all of that and send back that value.

```
def coolFunction() {
    x = 5 + 5;
    return(x) # Returns only 10;
}
coolFunction() # Will output 10
```

Example of two equivalent statements:

```
'5 + 5'
```

Is the same as

```
return('5 + 5')
```

(If it's the last statement)