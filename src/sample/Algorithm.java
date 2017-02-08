package sample;

import javafx.geometry.Point2D;

/**
 * Created by Sergo on 29.10.2016.
 */
public class Algorithm {

    static Point2D newPosition(Point2D currentXY, Point2D gp, Integer id){
        Double a = stepX(currentXY.getX(), gp);
        if (a.equals(gp.getX())&&(currentXY.getY()-gp.getY())>5) return new Point2D(a,currentXY.getY()-10);
        return new Point2D(a,lineTrajectory(a, gp, id));
    }

    private static Double lineTrajectory(Double x, Point2D gp, Integer id){
        return ((x - gp.getX())
                *(Range.startPositions[id].getY()-gp.getY()))/(Range.startPositions[id].getX()-gp.getX())
                + gp.getY();
/*
        return ((x - gp.getX())
                *(Range.startPositions[Range.startPositions.length-1].getY()-gp.getY())
                )/(Range.startPositions[Range.startPositions.length-1].getX()-gp.getX())
                + gp.getY();
*/
    }

    private static Double stepX(Double x, Point2D gp){
//        Double val = gp.getX() - x;
//        if (val<=5) return 0d;
//        return x < Range.goalPoint.getX()-10 ? x+10 : x+5;
        return (x < gp.getX()-20 ? x + 20 : (x < gp.getX()-10 ? x + 10 : (x < gp.getX()-5 ? x + 5 : gp.getX())));
    }
}
