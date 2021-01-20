package immutability;

import immutability.immutable.Coordinates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ImmutableCoordinatesTest {

    @Test
    public void canRepositionCoords(){

        Coordinates startPosition = Coordinates.create(3,6);
        Coordinates endPosition = startPosition.transformPositionBy(-10, 5);

        Assertions.assertEquals(-7, endPosition.getX());
        Assertions.assertEquals(11, endPosition.getY());
    }

    @Test
    public void canTransformPositionWithoutMoving(){
        Coordinates startPosition = Coordinates.create(1,4);
        Coordinates endPosition = startPosition.transformPositionBy(0, 0);

        Assertions.assertEquals(startPosition.getX(), endPosition.getX());
        Assertions.assertEquals(startPosition.getY(), endPosition.getY());
        Assertions.assertNotEquals(startPosition, endPosition);
    }

}
