package annotations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

public class DisabledWithoutReasonTest {

    /*

Disabled tests without a reason can prove problematic over the long term
because we forget why we disabled it.

Over time the code base moves on, the test is not kept in step with
the code so eventually becomes redundant.

It is generally a good idea to add a description.

We can write a rule for that, and a Quick Fix that reminds us to add the actual description.

Task:

- match the Disabled annotation without any parameters
- change the Disabled annotation to have a parameter with marker text "TODO: add a description here"


Solution:

General

~~~~~~~~
name: remember to add disabled description
short description: @Disabled should really have a description explaining why
Level: Error
~~~~~~~~


Search

~~~~~~~~
search:
  annotation:
    type: "org.junit.jupiter.api.Disabled"
    without:
      parameters:
      - {}
~~~~~~~~

QuickFix:

~~~~~~~~
availableFixes:
- name: "Add param"
  actions:
  - rewrite:
      to: "{{{ . }}}(\"TODO: add a description here\")"
      target: "self"
~~~~~~~~


 */
    // checkstyle google checks finds no problems with this method
    // checkstyle sun checks finds no problems with this method
    // SonarLint flags this as an issue but SonarLint docs currently show examples for Junit4 rather than Junit 5
    @Disabled
    void thisTestMethodHasNoDisabledReason(){
        Assertions.fail("This test is disabled so should not run and we won't fail");
    }

    @Disabled("I have a reason")
    void thisTestMethodHasDisabledReason(){
        Assertions.fail("This test is disabled so should not run and we won't fail");
    }
}
