package immutability;

import immutability.mutable.Coordinates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MutableCoordinatesTest {

    @Test
    public void canRepositionCoords(){

        Coordinates position = new Coordinates();
        position.setX(3);
        position.setY(6);

        position.transform(-10, 5);

        Assertions.assertEquals(-7, position.getX());
        Assertions.assertEquals(11, position.getY());
    }

    @Test
    public void canTransformPositionWithoutMoving(){
        Coordinates position = new Coordinates();
        position.setX(1);
        position.setY(4);

        position.transform(0, 0);

        Assertions.assertEquals(1, position.getX());
        Assertions.assertEquals(4, position.getY());
    }

}
