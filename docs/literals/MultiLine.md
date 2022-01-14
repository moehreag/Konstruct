---
layout: default
title: Multi Line Formatting
parent: Literals
nav_order: 2
---
Konstruct has support for files and text that is multiple lines. Newline characters and whitespace will be retained by Konstruct. Arguments and functions that are in middle of declaration will not have newlines and whitespace retained (unless explicitly defined using a force literal or strong literal).

Example Input:

```
Hey whats up! This is a script for the video on Konstruct v{konstructVersion} [# This gets replaced with the version].

Using this cool stuff I can determine that I calculate what \'15 * 15\' is... on the top of my head that is [
    calc(
        15 * 15
    )
]. I knew that because I am amazing. [# This will eat up all the newlines here...


]'''Do you know I'm amazing? Thank you <3. I can'''[owo(' owo very cool stuff')].
```

Evaluated:

```
Hey whats up! This is a script for the video on Konstruct v1.0.3 .

Using this cool stuff I can determine that I calculate what '15 * 15' is... on the top of my head that is 225.0. I knew that because I am amazing. Do you know I'm amazing? Thank you <3. I can owo vewy coow stuff.
```