package annotations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;


public class SkipThisTest {

    /*
        When I was learning JUnit, I could only keep so much in my head at any one time
        and I constantly forgot out to skip tests when they were not working. I had to train
        myself to use `@Disabled`
        instead I used to rename the test and removed the @Test annotation.

        It wasn't good, but it got the job done. If only I'd had something like Sensei
        where I could have created a rule to help me remember. That's what this example
        is all about.

        Task:

        Create a rule which finds methods which have been 'skipped' or 'disabled'
        then create a quickfix which renames the method and adds an @Test annotation.


        Notes:

        - When creating the annotation use "@Disabled" not "Disabled"
        - Adding the annotation as a fully qualified class path will also add an `import` statement

        ## Recipe Settings

search:
  method:
    name:
      matches: "SKIPTHIS.*"



## QuickFix Settings


availableFixes:
- name: "Add @Disabled annotation"
  actions:
  - addAnnotation:
      annotation:
        java: "@org.junit.jupiter.api.Disabled"
        kotlin: ""
  - rewrite:
      to:
        java: "{{#sed}}s/(.*) SKIPTHIS(.*)/$1 $2/,{{{.}}}{{/sed}}"


        Recipe Notes:

        - Used a sed to amend

     */

    void SKIPTHIScanWeAddTwoNumbers(){
        Assertions.fail("this test was skipped and should not run");
    }

    // checkstyle google checks finds no problems with this method
    // checkstyle sun checks finds no problems with this method
    @Disabled
    void thisTestMethodHasNoDisabledComment(){
        Assertions.fail("This test is disabled so should not run and we won't fail");
    }
}
