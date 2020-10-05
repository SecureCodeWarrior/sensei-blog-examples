package annotations.disabledreason;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

class DisabledWithoutReasonTest {

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
