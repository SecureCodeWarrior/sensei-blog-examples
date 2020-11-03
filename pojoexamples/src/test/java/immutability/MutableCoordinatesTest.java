package immutability;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MutableCoordinatesTest {

    @Test
    public void canRepositionCoords(){

        MutableCoordinates position = new MutableCoordinates();
        position.setX(3);
        position.setY(6);

        position.transformPositionBy(-10, 5);

        Assertions.assertEquals(-7, position.getX());
        Assertions.assertEquals(11, position.getY());
    }

    @Test
    public void canTransformPositionWithoutMoving(){
        MutableCoordinates position = new MutableCoordinates();
        position.setX(1);
        position.setY(4);

        position.transformPositionBy(0, 0);

        Assertions.assertEquals(1, position.getX());
        Assertions.assertEquals(4, position.getY());
    }

}
