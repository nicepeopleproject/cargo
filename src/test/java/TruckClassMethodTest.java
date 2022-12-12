import models.Box;
import models.BoxParams;
import models.Truck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TruckClassMethodTest {
    @Test
    public void hasEnoughSpaceToPlaceTooBigBox(){
        Truck truck = new Truck(1.0, 1.0, 1.0 ,1.0, new BoxParams(10, 10, 10));
        Box tooBigBox = new Box(100, 100, 100, 1000);

        assertEquals(false, truck.hasEnoughSpaceToPlace(tooBigBox));
    }
}
