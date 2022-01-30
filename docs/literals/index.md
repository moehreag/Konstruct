---
layout: default
title: Literals
nav_order: 5
has_children: true
---

# Literals

Literals are raw text that Konstruct does not modify in any way.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Defining Literals

#### EXAMPLE
{: .no_toc }
```
Input: This is a literal

- <root>
  - <literal This is a literal>
```

----

### Concatenation

Literals can automatically be concatenated with other node types, such as functions or variables. No formatting is required for a string to be viewed as a literal. This means that if you just type something, chances are it will be returned the exact same way.

#### EXAMPLE
{: .no_toc }
```
Input:
'string' + 5

Output:
string5
```

---

### Escape sequences
You may come across issues if you start to use backslashes (`\ `'s) within Konstruct. They may disappear.

#### EXAMPLE
{: .no_toc }
```
Input:
'I like using \'s in my literals'

Output:
I like using 's in my literals
```

This is because backslashes will escape whatever character is next. (This includes `\ `'s!). This allows characters specifically reserved for arguments or functions to be used and not be parsed by Konstruct.

-----

## Other Ways to Define Literals

Konstruct comes with a few useful ways to define literals.

### Strong Literal

Another way to define literals is to use a `Strong Literal`. No characters within can be escaped. This is a really powerful tool for creating easily read regex or other complicated strings.

#### EXAMPLE
{: .no_toc }
```
Input:
'''Nothing here can be \'ESCAPED\'. It all just \"EXISTS"\'''

Output:
Nothing here can be \'ESCAPED\'. It all just \"EXISTS"\
  ```