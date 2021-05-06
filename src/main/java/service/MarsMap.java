package service;


import model.LostSignal;
import model.Orientation;
import model.Robot;
import utils.ValidationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MarsMap {

    private int axisX;
    private int axisY;

    private static final Logger LOG = Logger.getLogger("MarsMap.class");


    public MarsMap(int axisX, int axisY) {
        this.axisX = axisX;
        this.axisY = axisY;
    }


    private List<LostSignal> lostSignals = new ArrayList<>();

    public String gridPosition(Robot robot){
        String validations = ValidationUtils.validateInputs(robot, this.axisX, this.axisY);

        return Optional.of(validations).filter(String::isEmpty)
                .map(v -> calculateFinalPosition(robot))
                .orElse(validations);
    }

    private String calculateFinalPosition(Robot robot){
        for(char instruction : robot.getInstruction().toCharArray()) {
            switch (instruction) {
                case 'L':
                    turnsLeft(robot);
                    break;
                case 'R':
                    turnsRight(robot);
                    break;
                case 'F':
                    if (!checkLostSignal(robot)) {
                        forward(robot);
                        checkRobotLost(robot);
                    }
                    break;
                default:
                    LOG.log(Level.INFO, "Instruction not supported, at the moment");
            }
            if(robot.isLost())
                break;
        }

        return  String.valueOf(robot.getCoordinateX() + " ")
                + String.valueOf(robot.getCoordinateY() + " ")  +  robot.getOrientation().toString() + " "
                + (robot.isLost() ? "LOST" : "");
    }

    private void turnsLeft(Robot robot){
        switch (robot.getOrientation()){
            case N:
                robot.setOrientation(Orientation.W);
                break;
            case S:
                robot.setOrientation(Orientation.E);
                break;
            case W:
                robot.setOrientation(Orientation.S);
                break;
            case E:
                robot.setOrientation(Orientation.N);
                break;
            default:
        }

    }
    private void turnsRight(Robot robot){
        switch (robot.getOrientation()){
            case N:
                robot.setOrientation(Orientation.E);
                break;
            case S:
                robot.setOrientation(Orientation.W);
                break;
            case W:
                robot.setOrientation(Orientation.N);
                break;
            case E:
                robot.setOrientation(Orientation.S);
            default:
        }
    }

    private void forward(Robot robot) {
        switch (robot.getOrientation()) {
            case N:
                robot.setCoordinateY(robot.getCoordinateY() + 1);
                break;
            case S:
                robot.setCoordinateY(robot.getCoordinateY() - 1);
                break;
            case W:
                robot.setCoordinateX(robot.getCoordinateX() - 1);
                break;
            case E:
                robot.setCoordinateX(robot.getCoordinateX() + 1);
            default:
        }
    }

    private void checkRobotLost(Robot robot){
        if((Orientation.N.value.equals(robot.getOrientation().value)
                || Orientation.S.value.equals(robot.getOrientation().value))){
            if(robot.getCoordinateY() <0) {
                lostSignals.add(new LostSignal(robot.getCoordinateX(), robot.getCoordinateY() + 1,
                        robot.getOrientation()));
                robot.setLost(Boolean.TRUE);
                robot.setCoordinateY(robot.getCoordinateY() + 1);
            }else if(robot.getCoordinateY() > this.axisY){
                lostSignals.add(new LostSignal(robot.getCoordinateX(), robot.getCoordinateY() - 1,
                        robot.getOrientation()));
                robot.setLost(Boolean.TRUE);
                robot.setCoordinateY(robot.getCoordinateY() - 1);
            }

        }
        if((Orientation.W.value.equals(robot.getOrientation().value)
                || Orientation.E.value.equals(robot.getOrientation().value))){
            if(robot.getCoordinateX() <0){
                lostSignals.add(new LostSignal(robot.getCoordinateX() + 1, robot.getCoordinateY(),
                        robot.getOrientation()));
                robot.setLost(Boolean.TRUE);
                robot.setCoordinateX(robot.getCoordinateX() + 1);
            }else if(robot.getCoordinateX() > this.axisX){
                lostSignals.add(new LostSignal(robot.getCoordinateX() - 1, robot.getCoordinateY(),
                        robot.getOrientation()));
                robot.setLost(Boolean.TRUE);
                robot.setCoordinateX(robot.getCoordinateX() - 1);
            }
        }
    }
    private boolean checkLostSignal(Robot robot){
        boolean ignoreForward = false;

        final Optional<Robot> optionalRobot = lostSignals.stream()
                .filter(lostSignal -> robot.getCoordinateX() == lostSignal.getCoordinateX()
                && robot.getCoordinateY() == lostSignal.getCoordinateY()
                        && (robot.getOrientation().equals(lostSignal.getOrientation()) ||
                        lostSignal.getOrientation().equals(twoOrientationLost(robot))))
                .map(lostSignal -> robot)
                .findFirst();

        if(optionalRobot.isPresent()){
            ignoreForward = true;
        }
        return ignoreForward;
    }

    private Orientation twoOrientationLost(Robot robot){
        String orientation = robot.getOrientation().name();
        if(robot.getCoordinateX()==this.axisX && robot.getCoordinateY() == 0){
            if(Orientation.S.value.equals(robot.getOrientation().value)){
                orientation = Orientation.E.name();
            }else if(Orientation.E.value.equals(robot.getOrientation().value)){
                orientation = Orientation.S.name();
            }
        }
        if(robot.getCoordinateX()==this.axisX && this.axisY == robot.getCoordinateY()){
            if(Orientation.N.value.equals(robot.getOrientation().value)){
                orientation = Orientation.E.name();
            }else if(Orientation.E.value.equals(robot.getOrientation().value)){
                orientation = Orientation.N.name();
            }
        }
        if(robot.getCoordinateX()==0 && robot.getCoordinateY() == 0){
            if(Orientation.W.value.equals(robot.getOrientation().value)){
                orientation = Orientation.S.name();
            }else if(Orientation.S.value.equals(robot.getOrientation().value)){
                orientation = Orientation.W.name();
            }
        }
        if(robot.getCoordinateX()== 0 && this.axisY == robot.getCoordinateY()){
            if(Orientation.N.value.equals(robot.getOrientation().value)){
                orientation = Orientation.W.name();
            }else if(Orientation.W.value.equals(robot.getOrientation().value)){
                orientation = Orientation.N.name();
            }
        }
        return Orientation.valueOf(orientation);
    }

    public List<LostSignal> getLostSignals() {
        return lostSignals;
    }

}
