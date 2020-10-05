package annotations.avoidingreason;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;


class SkipThisTest {
    
    @Deprecated
    void canWeAddTwoNumbers() {
        Assertions.fail("this test was skipped and should not run");
    }

}


