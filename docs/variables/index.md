---
layout: default
title: Variables
nav_order: 2
has_children: true
---

# Variables

Variables are a quick way to access information based off of keys.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Defining Variables

Variables are very easy to define. All you have to do is wrap them within curly braces. 

#### EXAMPLE
{: .no_toc }
```
Input: This is a {variable}

- <root>
  - <literal This is a >
  - <variable placeholder>
```

Variables should be defined in starting lower camelCase (Same with functions). Case matters, so be sure to follow what the developer has provided.

Variables should be alphanumeric for the most part to not create unnecessarily complex variables.

---

## Getting Values

When a variable is parsed, some data will be provided that the developer has chosen. Essentially everything with the curly braces (including them) will be replaced with some value.

#### Example
{: .no_toc }

```
Input: My x value is currently {x}.
Output: My x value is currently 203.
```

---

## Other Information

Variables work fine right next to literals or within arguments. You don't have to define a literal next to placeholder, they can be touching. Like:

```
Running v{konstructVersion}!
```

This will compile fine and the variable is just where the curly braces are. This allows for easy concatenation. Nothing inside a variable will be evaluated so functions cannot be put inside of them.