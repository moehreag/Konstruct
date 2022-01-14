---
layout: default
title: Literals
nav_order: 1
has_children: true
---
Literals are raw text that Konstruct does not modify in any way.

## Defining Literals

By default, any text that isn't a variable or a function is automatically expressed as a literal.

```
Input: This is a literal

- <root>
  - <literal This is a literal>
```

Literals can automatically be concatenated with other node types, such as functions or variables. No formatting is required for a string to be viewed as a literal. This means that if you just type something, chances are it will be returned the exact same way.

```
Input: This is a literal and this is a {placeholder}

- <root>
  - <literal This is a literal and this is a >
  - <variable placeholder>
```

You may come across issues if you start to use backslashes (`\ `'s) within Konstruct. They may disappear.

```
Input: This is a \n character

- <root>
  - <literal This is a n character>
```

This is because backslashes will escape whatever character is next. (This includes `\ `'s!). This allows characters specifically reserved for arguments or functions to be used and not be parsed by Konstruct.

```
Input: This is a backslash (\\) and this is how you write an \{argument\}.

- <root>
  - <literal This is a backslash (\) and this is how you write an {argument}.>
```

## Other Ways to Define Literals

Konstruct comes with a few useful ways to define literals.

One base one (called a force literal) is by using single quotations (`'`'s). Any character within two of these, except backslashes, are treated as literals.

```
Input: 'This is a cool way to do \'stuff\' ;]`

- <root> 
  - <literal This is a cool way to do 'stuff' ;]>
```

(`;]` would cause a compilation error since it would be viewed as an unmatched function end)

-----

Another way to define literals is to use a `Strong Literal`. No characters within can be escaped. This is a really powerful tool for creating easily read regex or other complicated strings.

```
Input: '''Nothing here can be \'ESCAPED\'. It all just \"EXISTS"\'''

- <root>
  - <literal Nothing here can be \'ESCAPED\'. It all just \"EXISTS\">
  ```