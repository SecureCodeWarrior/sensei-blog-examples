# Amending Method and Class Visibility for JUnit 5

When I migrated from JUnit 4 to JUnit 5, I was used to writing all my test classes and methods as `public`. But with JUnit 5 they can be `package private`.

It took me a while to build the muscle memory to code to this, and I still slip up once in a while.

With Sensei I can create recipes that find the `public` methods and classes, and amend the declarations to be `package private` automatically.

## Amending the Class Declaration

To find the classes, I search for any class which has a child annotation of `@Test` from Junit 5 i.e. `org.junit.jupiter.api.Test`

And where the class has modifier `public`:

```
search:
  class:
    with:
      child:
        annotation:
          type: "org.junit.jupiter.api.Test"
    modifier: "public"
```

Then the quick fix changes the modifier to remove the visibility so that it is default, and the default is `package private` which is what I'm looking for.

```
availableFixes:
- name: "remove public visibility from JUnit 5  Test class"
  actions:
  - changeModifiers:
      visibility: ""
```

## Amending the Method Declarations

The method declaration amendment recipe is much the same as the class recipe.

First I search for `public` methods annotated with `@Test` from JUnit 5.

```
search:
  method:
    annotation:
      type: "org.junit.jupiter.api.Test"
    modifier: "public"
```

And then I change the modifier to be default visibility.

```
availableFixes:
- name: "Remove @Test method public visibility"
  actions:
  - changeModifiers:
      visibility: ""
```

This helps me catch the change error and offers me an instant fix if I occasionally slip up in my code.