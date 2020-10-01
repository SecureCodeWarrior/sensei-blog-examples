# Adding a Private Constructor

In a utility class the fields and methods are static. There is no reason why I would ever instantiate it.

e.g. `UtilityClass utility = new UtilityClass()`

The code below is a simple implementation of a Utility class.

~~~~~~~~
public class UtilityClass {

    public static final Boolean ULTIMATE_TRUTH = true;

    public static boolean getTrue(){
        return ULTIMATE_TRUTH;
    }
}
~~~~~~~~

I could use Sensei to add a private constructor to make it impossible for me to instantiate.

## Searching for the Class

Initially I will create a recipe that matches on any class, because I'm really wanting to amend the code.

I create a new recipe on the Utility class called `create private constructor`.

And initially I'll search for a class.

~~~~~~~~
search:
  class: {}
~~~~~~~~

This will match any class, which is good enough to let me get started writing a `Quick Fix`, and once I have a `Quick Fix` that works, I'll refine the search to make it highlight when there is more likely to be a class that requires a private constructor.

## Quick Fix

For the Quick Fix, I will want to generate a private constructor.

In the example class this would look like:

~~~~~~~~
    private UtilityClass(){}
~~~~~~~~

To add the above code to my class, my Quick Fix will add a Method, and the name of the method will be a Moustache template that uses the name of the class.

~~~~~~~~
availableFixes:
- name: "add private constructor"
  actions:
  - addMethod:
      method: "private {{{ name }}}(){}"
~~~~~~~~

In the GUI Editor, I use the `Show Variables` to create the Moustache template, and then edit the field to add the `private` modifier, brackets and braces to make it syntactically correct.

And this would now allow me to add a private constructor to any class.

## Search for Missing Constructors

Ideally I don't want to create a recipe that flags an error against every class. So I'll add some additional conditions in the search so that we only match on classes that do not have a constructor.

~~~~~~~~
search:
  class:
    without:
      child:
        method:
          constructor: true
~~~~~~~~

The YAML is slightly different from the GUI.

In the GUI I configure it to look kfor a class without a child method which is a constructor 'yes'. We use 'yes' in the GUI instead of 'true' to make the GUI a little more human friendly.

This recipe will now only reveal itself for any class without a constructor.

## Narrow Search for Likely Culprits

So I might want to go further and look for the presence of static methods or fields.

I look for any class without a constructor and which has either all public static fields, or all public static methods.

~~~~~~~~
search:
  class:
    with:
      anyOf:
      - child:
          method:
            allOf:
            - modifier: "public"
            - modifier: "static"
      - child:
          field:
            allOf:
            - modifier: "static"
            - modifier: "public"
    without:
      child:
        method:
          constructor: true
~~~~~~~~

Since Sensei is used to help the programmer in the IDE, rather than statically analyse the code and report all errors, this filter is good enough to rule out most classes in my code base where I might have a good reason to have a default public constructor.

And, in some projects this would be a step too far because the utility classes might have private methods, so I might choose to look for 'any' `public static` methods.

~~~~~~~~
        - child:
            field:
              anyOf:
                - modifier: "static"
                - modifier: "public"
~~~~~~~~

## Hints

What I'm trying to do is create a simple enough recipe that includes all the situations I need it, but filter it so that it doesn't get suggested on every class.

And when working on recipes I try to de-risk them.

In this case I wasn't sure if I could create the private constructor so I created this first. Then refactored the search conditions to make them more specific.

Sometimes when working on recipes I'm not sure how to perform the search, so I work on that first.

I find recipes easier to create when I build them incrementally.
