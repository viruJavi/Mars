package utils;

import model.Robot;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidationUtils {

    private static final Logger LOG = Logger.getLogger("ValidationUtils.class");

    private ValidationUtils(){}

    public static String validateInputs(Robot robot, int axisX, int axisY){
        String validations="";

        if(robot.getCoordinateX() > axisX || robot.getCoordinateY() > axisY){
            LOG.log(Level.WARNING, "The coordinates are outside the X axis - Y axis of the map");
            validations = "BAD COORDINATES";
        }
        if(robot.getCoordinateX()> 50 || robot.getCoordinateY()> 50){
            LOG.log(Level.WARNING, "Coordinates cannot be greater than 50");
            validations = "COORDINATES BIGGER THAN 50";
        }
        if(axisX < 0 || axisY < 0){
            LOG.log(Level.WARNING, "Axis must be positive");
            validations = "AXIS MUST BE POSITIVE";
        }
        if(robot.getInstruction().length() > 100){
            LOG.log(Level.WARNING, "Instruction cannot be greater than 100");
            validations = "INSTRUCTION BIGGER THAN 100";
        }
        if(robot.getInstruction().isEmpty()){
            LOG.log(Level.WARNING, "Instruction cannot be empty");
            validations = "INSTRUCTION EMPTY";
        }

        return validations;
    }

}
