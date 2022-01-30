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

## Calling Functions

Functions can be called using the syntax `<function name>(<arguments>...)`. One example could be `calc('5 + 5')`. To define arguments put a `(` after the argument name and then specify the arguments. For each argument use a `,` to deliminate.

(For function name it should be alphanumeric, otherwise the name could be pruned or invalid)

---

## Defining Functions

Functions are defined as such:

```
def add2(arg1, arg2) {
    arg1 + arg2
};
add2(5, 5) # Outputs 10
```

Argument names can be alphanumeric they work like python where the type isn't declared.
