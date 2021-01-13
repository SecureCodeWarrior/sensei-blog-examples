# Java Gotchas - Bitwise vs Boolean Operators

> "Java Gotcha" - a common mistake pattern that is easy to accidentally implement.

A fairly simple Java Gotcha to fall into is using a Bitwise operator instead of a Boolean Comparison operator.

e.g. a simple mistype can result in writing "&" when you meant to write "&&"

A common heuristic we learn when reviewing code is that "&" or "|" when used in a conditional statement in probably not intended. In this post we will explore this heuristic a little and identify ways we can fix this.

## What's the Problem? Bitwise operations work fine with Booleans

Using Bitwise operators with Booleans is perfectly valid, so Java will not report a syntax error.

If I construct a JUnit Test to explore a truth table for both Bitwise or `|` and Bitwise and `&` then we will see that the outputs from the Bitwise operator match the truth table.

### AND Truth Table

```
    @Test
    void bitwiseOperatorsAndTruthTable(){
        Assertions.assertEquals(true, true & true);
        Assertions.assertEquals(false, true & false);
        Assertions.assertEquals(false, false & true);
        Assertions.assertEquals(false, false & false);
    }
```

### OR Truth Table

```


    @Test
    void bitwiseOperatorsOrTruthTable(){
        Assertions.assertEquals(true, true | true);
        Assertions.assertEquals(true, true | false);
        Assertions.assertEquals(true, false | true);
        Assertions.assertEquals(false, false | false);
    }
```