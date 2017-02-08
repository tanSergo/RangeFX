package sample;

/**
 * Created by Sergo on 29.10.2016.
 */
public class Sheduler {

    static void nextStep(){
        System.out.println("Button pressed!");
        for (int i=0;i<Range.numberOfRobots;i++){
            Range.robots[i].takeNewXY();
            Range.robots[i].renewPositionOnRange();
        }
    }
}
