package service;

import model.Orientation;
import model.Robot;
import org.junit.Assert;
import org.junit.Test;

public class MarsAppTest {

    private MarsMap marsMap = new MarsMap(5, 3);
    private MarsMap marsMapNegX = new MarsMap(-5, 2);
    private MarsMap marsMapNegY = new MarsMap(1, -3);

    @Test
    public void whenCoordinateXIsBiggerThanMaxValue_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(9, 1, Orientation.E, "RFRFRFRF");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertEquals("BAD COORDINATES", position);
    }
    @Test
    public void whenCoordinateYIsBiggerThanMaxValue_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(4, 7, Orientation.E, "RFRFRFRF");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertEquals("BAD COORDINATES", position);
    }
    @Test
    public void whenRobotMoves_thenReturnFinalPosition(){

        //Given
        Robot robot1 = new Robot(0, 0, Orientation.N, "FRFLFRFFFRFF");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertTrue(position.contains("4 0 S"));
        Assert.assertFalse(position.contains("LOST"));
    }
    @Test
    public void whenRobotMoves_thenGettingLost(){

        //Given
        Robot robot1 = new Robot(2, 1, Orientation.E, "LLFFF");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertTrue(position.contains("0 1 W"));
        Assert.assertTrue(position.contains("LOST"));
    }
    @Test
    public void whenCoordinateYIsBiggerThan50_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(14, 77, Orientation.E, "RFFLLRLLRLRLF");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertEquals("COORDINATES BIGGER THAN 50", position);
    }
    @Test
    public void whenCoordinateXIsBiggerThan50_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(66, 22, Orientation.E, "FRLFLRFLFF");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertEquals("COORDINATES BIGGER THAN 50", position);
    }
    @Test
    public void whenAxisXIsNegative_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(10, 5, Orientation.E, "RFRFRFRF");
        //When
        final String position = marsMapNegX.gridPosition(robot1);

        Assert.assertEquals("AXIS MUST BE POSITIVE", position);
    }
    @Test
    public void whenAxisYIsNegative_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(10, 5, Orientation.E, "RFRFRFRF");
        //When
        final String position = marsMapNegY.gridPosition(robot1);

        Assert.assertEquals("AXIS MUST BE POSITIVE", position);
    }
    @Test
    public void whenInstructionIsBiggerThan100_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(1, 1, Orientation.E,
                "RFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFR" +
                        "RFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFRFRF" +
                        "RFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFRFRF" +
                        "RFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFRFRFRFRFRFRRFRFRFRFFRFRFRFRFRFRFRFRFRFFRF");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertEquals("INSTRUCTION BIGGER THAN 100", position);
    }
    @Test
    public void whenInstructionIsEmpty_thenReturnErrorMessage(){

        //Given
        Robot robot1 = new Robot(4, 2, Orientation.E,"");
        //When
        final String position = marsMap.gridPosition(robot1);

        Assert.assertEquals("INSTRUCTION EMPTY", position);
    }

}
