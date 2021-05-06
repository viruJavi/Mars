package application;

import model.LostSignal;
import model.Orientation;
import model.Robot;
import service.MarsMap;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainApp {

    private static final Logger LOG = Logger.getLogger("MainApp.class");

    public static void main(String[] args) {

        MarsMap marsMap = new MarsMap(5, 3);
        List<Robot> listRobots = new ArrayList<>();
        listRobots.add(new Robot(1, 1, Orientation.E, "RFRFRFRF"));
        listRobots.add(new Robot(3, 2, Orientation.N, "FRRFLLFFRRFLL"));
        listRobots.add(new Robot(0, 3, Orientation.W, "LLFFFLFLFL"));

        listRobots.add(new Robot(4, 1, Orientation.W, "FLFFLF"));
        listRobots.add(new Robot(3, 0, Orientation.S, "FLFFLF"));
        listRobots.add(new Robot(1, 2, Orientation.E, "FLFRFFFRFFRFFRF"));
        listRobots.add(new Robot(0, 3, Orientation.N, "FFFFFFFLL"));
        listRobots.add(new Robot(0, 3, Orientation.W, "FRFRRFRRFLFLF"));

        listRobots.forEach(robot -> LOG.log(Level.INFO, "ROBOT POS --> {0}", marsMap.gridPosition(robot)));

        for(LostSignal lostSignal : marsMap.getLostSignals()){
            LOG.warning("[LOST SIGNAL: " + lostSignal.getCoordinateX() + " " + lostSignal.getCoordinateY() + "]");
        }

    }

}
