package modifier.visibility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Junit5VisibilityTest {

    @Test
    public void thisDoesNotNeedToBePublic(){
        Assertions.assertTrue(true);
    }

    @Test
    public void thisDoesNotNeedToBePublicEither(){
        Assertions.assertTrue(true);
    }
}
