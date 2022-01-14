---
layout: default
title: Addons
nav_order: 4
---

# Addons

Addons are other functions that are packaged within Konstruct. These aren't enabled by default, but it is highly recommended to enable them. In this page `<>`'s refer to an input argument and `[]`'s are an optional argument.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

---

## Calculate

The calculate function evaluates any math expression using [mXparser](http://mathparser.org). There are a ***ton*** of different equations and math operators that can be used. To see all of them browse around [the documentation](http://mathparser.org/mxparser-math-collection/). 

Name
: `calc`

Arguments
: `<expression>`

Output
: Evaluation of expression. If there is an error it will return `NaN`.

#### EXAMPLE
{: .no_toc }
```
Input: [calc('15 * 15')]
Output: 225.0
```

---

## Get

This function returns a specified argument.

Name
: `get`

Arguments
: `<argumentNumber>`, `<args>...`

Output
: Uses `argumentNumber` as the index to fetch from the rest of the `args`. The `args` start at index 0. If the number is not a valid index it will return the 1st value in `args`.

#### EXAMPLE
{: .no_toc }
```
Input: [get(2, First Option, 'Second Option', '''Third Option''')]
Output: Third Option
```

---

## Random Integer

This function returns a random int from `min` to `max` (inclusive)

Name
: `randInt`

Arguments
: `<min>`, `<max>`

Output
: Returns a random int from `min` to `max` (inclusive)

#### EXAMPLE
{: .no_toc }
```
Input: [randInt(3, 8)]
Output: 7
```

---

## Round

This function rounds a number to `n` specified decimal places.

Name
: `round`

Arguments
: `<number>`, `[<places (default 0)>]`

Output
: Rounds a number to a specified place. If no `places` is specified it will round to the int. No decimal is added if it's set to 0.

#### EXAMPLE
{: .no_toc }
```
Input: [round(5.3333, 2)]
Output: 5.33
```

---

## Replace

This function replaces matches in a string.

Name
: `replace`

Arguments
: `<findString>`, `<input>`, `<replaceTo>`, `[literal/regex/upper_lower]`

Output
: Replaces all matches in `<input>` of `<findString>` with `<replaceTo>`. An optional value of find type of `literal`, `regex`, or `upper_lower`. `literal` is the default.

#### EXAMPLE
{: .no_toc }
```
Input: [replace('''\[(.+)\]''', 'I can use [regex] now in functions', $1, regex)]
Output: I can use regex now in functions
```

---

## OwO

This function uses [OwO](https://github.com/MaowImpl/owo) to *owo* text.

Name
: `owo`

Arguments
: `<text>`

Output
: OwO's `<text>`

#### EXAMPLE
{: .no_toc }
```
Input: [owo(This is very very cool lol <3)]
Output: Thais is vewy vewy coow wow <3
```

---

## Roman Numeral

Converts an integer to a romal numeral.

Name
: `romannumeral`

Arguments
: `<number>`

Output
: Converts `<number>` to roman numeral.

#### EXAMPLE
{: .no_toc }
```
Input: I am [romannumeral(321)] years old.
Output: I am CCCXXI years old.
```
