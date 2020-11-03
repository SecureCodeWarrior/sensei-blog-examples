package immutability;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImmutableCoordinatesTest {

    @Test
    public void canRepositionCoords(){

        ImmutableCoordinates startPosition = ImmutableCoordinates.create(3,6);
        ImmutableCoordinates endPosition = startPosition.transformPositionBy(-10, 5);

        Assertions.assertEquals(-7, endPosition.getX());
        Assertions.assertEquals(11, endPosition.getY());
    }

    @Test
    public void canTransformPositionWithoutMoving(){
        ImmutableCoordinates startPosition = ImmutableCoordinates.create(1,4);
        ImmutableCoordinates endPosition = startPosition.transformPositionBy(0, 0);

        Assertions.assertEquals(startPosition.getX(), endPosition.getX());
        Assertions.assertEquals(startPosition.getY(), endPosition.getY());
        Assertions.assertNotEquals(startPosition, endPosition);
    }

}
