package privateconstructors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShouldHavePrivateConstructorsTest {

    @Test
    void utilityClassShouldHavePrivateConstructor(){

        Assertions.assertTrue(UtilityClass.getTrue());

    }
}
