## Basic Immutability in Java

Immutability is often promoted as a way of avoiding entire classes of problems e.g.

- unexpected extension of classes
- concurrent updates
- unexpected side-effects from method calls
- objects being in an invalid state

I was always a little suspicious of the overhead involved in coding for Immutability and I imagined immutable code to have a performance and memory overhead. But I spent some time investigating in more detail, converting some of my code to be more immutable and was impressed at the difference simple changes made.

I was also fortunate that I was able to grab some time from Aaron Bedra who, as well as being an expert in Immutability and the author of “Programming Clojure”, is also a member of the Secure Code Warrior [Technical Advisory Board](https://www.securecodewarrior.com/about-us).

Aaron provided some tips and reviewed some of my exploratory code. What follows in this text is my reflection on what I learned and how I used Sensei to help enforce some basic immutability principles. It should not be taken as a reflection of Aaron’s view of Immutability. Aaron did offer some ‘next step’ advice and I’ve added that towards the end of the text.

### A Mutable Class

The code that follows implements a very simple co-ordinates class. It consists of two properties `x` and `y` and these are set and retrieved via methods, with the co-ordinate properties being adjusted with a `transformPositionBy` method.

```
public class Coordinates {

    private int x;
    private int y;

    public Coordinates(){
        x=0;
        y=0;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void transformPositionBy(
                    int xadjust, int yadjust){
        this.x+=xadjust;
        this.y+=yadjust;
    }
}
```

This is very mutable, I can:

- change the co-ordinates after creation
- change the x and y values independently after creation
- extend the class and create a new 'type' of MutableCoordinates

Even a simple example like this can demonstrate many of the benefits of using Immutable coding.

### Immutability

The simplest form of an immutable version of this class would not allow me to change the values of the co-ordinate x and y fields after creation.

```
public final class Coordinates {

    private int x;
    private int y;

    public Coordinates(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
```

In my code, I could then be assured that when I have a Coordinates object:

- no other code, run in parallel, could impact my object because
    - once created the values of `x` and `y` can not be amended.
    - the class itself is `final` so no other programmer on the project could have subclassed it and snuck in some mutable features.

### Creating Immutable Objects With `static` Factory Methods

A common pattern for Immutable coding is the use of a `static` factory method, and making the constructor `private`.

The code above has a public constructor.

At its worst, a `public` default constructor would necessitate the creation of a mutable class because the only way to set the Coordinate values would be after construction.

```
public class Coordinates {

    private int x;
    private int y;

    public Coordinates(){
        x=0;
        y=0;
    }
...
```

With a `public` constructor that takes parameters, then we have to consider if we want to make it `private` or not.

e.g.

```
    private Coordinates(final int x, final int y ){
        this.x=x;
        this.y=y;
    }
```

Making the constructors private gives more control over the naming of `static` methods which create Objects, and also increases the ability to add caching of immutable objects if necessary e.g. the Integer class `valueOf` method maintains a cache for range `-128` to `127`.

e.g.

```
    private Coordinates(final int x, final int y ){
        this.x=x;
        this.y=y;
    }

    public final static Coordinates create(final int x, final int y ){
        return new Coordinates(x, y );
    }
```

Using a static factory method instead of constructor is a well debated point online.

For a simple example like this it seems unnecessary. It does allow easy future amendment for caching, but adding the method early might be a pre-mature optimisation.

For more complicated classes, which have multiple ways to create, then this approach may be easier to justify.

We don't need to use a `static` factory method to make a class immutable, but it is a common pattern that you will encounter in immutable code.

### Using `final` to Restrict Extension

Declaring the static method `final` might seem like a step too far, but if our class had not been defined as `final` then an extending class can hide the static method. Declaring the method as `final` prevents that at compile time, and prevents other programmers making the class mutable by stealth.

It seems to me that using `final` cuts down on the amount of code I have to review, because I know that there will be no impact on other code because it will not have been subclassed. And if I default to `final` then easing the constraint becomes a conscious project decision at the point we need extend the class.

Making it `final` also seems to comply with the "You Aren't Going To Need It" ([YAGNI](https://martinfowler.com/bliki/Yagni.html)) heuristic. I'm not going to need to extend it, until I get to the point that I do, and only at that point does it become necessary to remove the constraint that `final` imposed.

Looking around online the use of `final` on class or methods seems heavily debated but it is common pattern in immutable code.

### Make fields `final`

Making fields `final` can be a simple constraint to help the compiler prevent myself or a later programmer adding any unintended side-effects in our methods. When our fields are `final`, we can only assign them values during construction.

```
    private final int x;
    private final int y;
```

For immutability we should certainly declare any `public` fields as `final`. It might be more debatable for private fields, but if one of the benefits of immutability is making it easier to understand code quickly, then using `final` means I only have to look in the constructor to understand the field value assignment.

If the fields are not final then I may need to read more code to find out if any of the methods have mutable side-effects.

### Avoid `void` methods

`void` methods suggest that some sort of side-effect has taken place. If we want to write immutable code then `void` suggests we might have a risk in the code.

e.g the original mutable code had the `transformPositionBy` method.

```
    public void transformPositionBy(
                      int xadjust, int yadjust){
        this.x+=xadjust;
        this.y+=yadjust;
    }
```

Rather than use a void method which is implemented via a side-effect, I could create a new Coordinate object.

```
    public Coordinates transformPositionBy(
          final int xadjust, final int yadjust) {
        return new Coordinates(
                this.x + xadjust, this.y + yadjust);
    }
```

Removing any `void` methods would get rid of most setters, and reinforce the creation of objects as immutable.

### Immutability Throughout

Immutability makes it much harder to introduce certain classes of error.

But it is still possible to make errors when using immutable coding approaches, particularly when mixing mutable and immutable code.


#### Consume Immutable Objects

In the `transformPositionBy` method the parameters are primitive integers. If these could become Immutable and represent the problem domain then it might prevent more classes of error.

```
    public Coordinates transformPositionBy(
          final int xadjust, final int yadjust) {
        return new Coordinates(
                this.x + xadjust, this.y + yadjust);
```

When coding this, and writing a test I made a simple transposition error and passed in a `(y, x)` pair instead of an `(x,y)` pair. If I had passed in Domain object that represented an adjustment, or even a Coordinate to adjust by I would not have been able to make the error.

```
    public Coordinates transformPositionBy(
          final Coordinates adjust) {
        return new Coordinates(
              this.x + adjust.getX(),
              this.y + adjust.getY());
```

The full benefit of Immutable coding comes when you implement Immutability throughout and when you model Immutability at the domain of the application.

Aaron Bedra pointed this principle out during an early review of the example code, and I could see the value of it, because I had already made the transposition error that the principle would have saved me from.

Aaron went further than this and recommended using `XCoordinate` and `YCoordinate` to more accurately model the domain:

```
public final class XCoordinate {
    private static final Natural value;
    public static XCoordinate xCoordinate(Natural value) {
        return new XCoordinate(value)
    }
    public Natural getValue() {
        return value;
    }
}

public final class YCoordinate {
    private static final Natural value;
    public static YCoordinate yCoordinate(Natural value) {
        return new YCoordinate(value)
    }
    public Natural getValue() {
        return value;
    }
}

public final class ImmutableCoordinates {
  private final XCoordinate x;
  private final YCoordinate y;
```

Aaron also pointed out that the next step towards immutability would be, if java >= 14, them using records and simultaneously reducing the amount of code and if java >= 15 the ImmtuableCoordinate can become sealed.

### Basic Immutability Principles Explored

The text and code above illustrate some of the basic immutability principles that I explored.

- make classes `final` to prevent extension by default.
- make fields and variables final by default.
- no setters and getters
- construct an object fully
- support caching immutable objects by using static factory methods for creation
- return new objects rather than amending existing objects
- no void methods
- immutability throughout e.g. passing in immutable objects as parameters, return immutable objects or new objects.

Many of these principles seemed overly extreme when I first encountered them. Why should I make every class final? Why make every variable final? But the unifying principle of Immutability Throughout provides a default level of protection, that I have to choose to loosen, knowing that by loosening the protection I increase the possibility of adding classes of errors that I had prevented.

I found that the more I adopted immutable coding principles, the more my code became easier to read and understand because it was self-contained and did not have any side-effects.

The Java concurrency tutorial has some guidelines on [Immutable Objects](https://docs.oracle.com/javase/tutorial/essential/concurrency/imstrat.html). Concurrency is slightly less tricky when using Immutable objects because we don't have to worry about the object changing as we use it. Immutability can also help reduce security issues because we don't have to worry that the value of an object has changed after we validated it.

Immutability can also improve the availability and responsiveness of our applications in concurrent usage because we no longer have to pause threads or lock access to objects.

The subtle, and beneficial, code quality knock on effects of immutability by removing certain classes of problems has made it something I look forward to exploring and learning more.

To learn more:

- Clojure Objects are Immutable and you can learn from Aaron Bedra's book "Programming Clojure"
- "Secure By Design" by Daniel Deogun, Dan Bergh Johnsson, and Daniel Sawano has a section on Immutability and how it can affect security considerations.
- [Mutability and Immutability from MIT](https://web.mit.edu/6.005/www/fa15/classes/09-immutability/)
- [Sensei Recipes for Basic Immutability](https://github.com/SecureCodeWarrior/sensei-blog-examples/tree/master/pojoexamples/src/main/java/immutability)
- [Sensei Recipes to create Immutable return values](https://github.com/SecureCodeWarrior/public-cookbooks/tree/master/recipes/java-oop-best-practices)
- Aaron Bedra recommended the following functional and immutable Java libraries:
    - https://github.com/palatable/shoki - Purely functional, persistent data structures
    - https://github.com/palatable/lambda - Functional patterns for Java
