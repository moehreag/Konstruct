---
layout: default
title: Quick Start
nav_order: 1
has_children: false
---

Demonstration of Syntax

```
# Comments can be defined either single line
#|
Or multi line
|#
5 #| even midline |# + 5

# Variables and functions don't need to be typed

coolVariable = 5 # Semi colons only used if discard is needed
result = coolVariable + 5 # Evaluates to 10
; # Semi colons can go anywhere

# Function definition can have any amount of arguments and the names can be alphanumeric

def add(num1, num2) { # Curly braces required
    num1 + num2 # Equivilent to return(num1 + num2)
}

# Return statements stop evaluation of the rest of the code. 
# If there is no return statement, the last expression parsed is used.
# Last expression results get cleared with a semi colon.

'Strings can be declared anywhere';

'Backslashing \' works!' # Outputs: Backslashing ' works
"'this' stuff can be cool" # Outputs: 'this' stuff can be cool

'''Multiline/unbackslashable \!\! string definitions.''' # Outputs: Multiline/unbackslashable \!\! string definitions.

# List declarations
coolList = list(1, 2, 3, 4, 5)

# Dot operators
# Specific objects can have dots applied to them. <list>.get(<index>)
result = coolList.get(2) + 3 # Outputs 5. Index starts at 0

;
# Boolean stuff with gates! All logic gates supported :D
true and false # false
;
true or false # true
;
true nor false # false
;
(true xor false) nor (false xand true)

# Expression evaluation
5 + (5 / (4 * 3.1)) # Most basic operators + - / * % // exist
# // is floor divide (i.e. 5 // 3 = 1)

# Conditionals
true == false
5 == 5
5 > 3
5.3 < 3

# While
i = 0
while (i < 10) {
    # Loops 10 times
    i++;
} 

# if (<conditional>) {<ifStuff>} elif (<Othercondition>) {<elseIf stuff>} else {else stuff}
if (true) {
    'This is true!'
} elif (false) {
    return('This is false!')
} else {
    'Uh oh...'
}
```