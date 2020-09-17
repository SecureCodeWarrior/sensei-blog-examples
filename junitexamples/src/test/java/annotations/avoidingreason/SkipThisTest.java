package annotations.avoidingreason;

import org.junit.jupiter.api.Assertions;


public class SkipThisTest {

    /*

     */

    void SKIPTHIScanWeAddTwoNumbers() {
        Assertions.fail("this test was skipped and should not run");
    }



    /*

Reversing out the above change.

Since we can use this in demos we may want to reverse it.

And we might choose to do that through a Git revert or a bunch of Ctrl+Z

Or we could create a recipe.

Thinking it through I want to find a method annotated with @Disabled
but only in the class SkipThisTest where I do the demo:

Recipe Settings Search

~~~~~~~~
search:
  method:
    annotation:
      type: "Disabled"
    in:
      class:
        name: "SkipThisTest"
~~~~~~~~

But to avoid making the code look like it is an error when I finish,
I change the general setting on the recipe to be a Warning.

This highlights it in the code but doesn't make it look like a major problem.

And for the Quick fix, since we have matched the method I use the rewrite action
and populate the template using the variables.

I basically add every variable except the modifier (since I want to get rid of the annotation),
and add the SKIPTHIS text into the template.

This makes it easy to revert the changes during a demo, and it is isolated to this demo class,
and has the side-effect of highlighting the code we are working on in the demo.


     */
}


