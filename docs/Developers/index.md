---
layout: default
title: Developers
nav_order: 4
---

# Using this Library

This library is really easy to set up and integrate with any project! Functions and variables can be easily defined and added. Every method and variable is documented within the code.

<details open markdown="block">
  <summary>
    Table of contents
  </summary>
  {: .text-delta }
1. TOC
{:toc}
</details>

## Gradle

In `build.gradle` declare the following repository:

```
url 'https://jitpack.io'
```

Then for dependencies add:

```
implementation 'com.github.DarkKronicle.Konstruct:core:<version>'

// Add this if you want access to the prebuilt functions
implementation 'com.github.DarkKronicle.Konstruct:addons:<version>'
```

## Quick Start

To start declare a node processor for your general node processing. This stores all the variables and functions. It is recommended to not recreate this each time you have to parse, but instead store it.

```JAVA
NodeProcessor processor = new NodeProcessor();
```

You can then add functions from `addons` into processor.

```JAVA
processor.addFunction(new CalculatorFunction());
processor.addFunction(new RoundFunction());
processor.addFunction(new GetFunction());
```

When you are ready to parse text into a `Node` use a `NodeBuilder`. It is highly recommended to cache the `Node` after each parse so that text doesn't have to be reparsed. You can wrap this in a catch block because if there are formatting exceptions it will throw a `NodeException`.

```JAVA
Node node = new NodeBuilder(input).build()
```

Then to parse just use `NodeProcessor`

```JAVA
processor.parse(node);
```


## Creating a Function

You can create a function easily by either implementing from `NamedFunction` or `Function`. A named function is essentially the same as a normal function, but it allows for a name to be specified to use by default. Function names have to be alphanumeric and it is recommended to use camelCase with a lowercase first letter.

```JAVA
public class LowerFunction implements NamedFunction {
    @Override
    public String parse(ParseContext context, List<Node> input) {
        return Function.parseArgument(context, input, 0).toLowerCase();
    }

    @Override
    public IntRange getArgumentCount() {
        return IntRange.of(1);
    }
    
    @Override
    public String getName() {
        return "lower";
    }
    
}
```

## Adding Variables

Adding variables is easy with a `NodeProcessor`. You can either add a `String` or `Supplier<String>`

```JAVA
processor.addVariable("name", "DarkKronicle");
```

## Specifying Settings

When parsing a node you can specify `TokenSettings`. This changes what characters do what (i.e. argument deliminator as `|` instead of `,`)

```JAVA
Node node = new NodeBuilder(input, settings).build();
```

## Examples

View the [examples](https://github.com/DarkKronicle/Konstruct/tree/main/examples/src/main/java) for more information on how to use.