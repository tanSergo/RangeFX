package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Sergo on 08.10.2016.
 */
public class Robot {
    private static Point2D myXY;
    private Point2D newXY;
    private Integer id;
    private GraphicsContext context;
    private Point2D goalPoint;

    public Robot(Integer i, Point2D XY, GraphicsContext context, Point2D gp) {
        this.myXY = XY;
        this.id = i;
        this.context = context;
        this.goalPoint = new Point2D(
                Range.goalPoint.getX() - (id<5?(Range.numberOfRobots/2-1-id)*18 : (Range.numberOfRobots/2+4-id)*18),
                Range.goalPoint.getY() + (id<5?15:0)
        );
    }

    public Point2D getMyXY() {
        return myXY;
    }

    public void takeNewXY(){
        System.out.println(Diastimeter.lookAround());
        this.setNewXY(Algorithm.newPosition(getMyXY(), goalPoint, id) );
    }

    private void setNewXY(Point2D newXY) {
        this.newXY = newXY;
    }

    private Point2D getNewXY() {
        return newXY;
    }

    public void renewPositionOnRange(){
        context.clearRect(myXY.getX(),myXY.getY(),10,10);
//        context.setFill(Color.FORESTGREEN);
        context.setFill(Color.BLUE);
        Range.drawRect(context,newXY);
        myXY = newXY;
//        context.fillOval(newXY.getX(), newXY.getY(), 10, 10);
    }

    private static class Diastimeter {
        public static final Integer radius = 20;

        public static List<Point2D> lookAround(){
            List<Point2D> obstacles = new ArrayList<>();
            for (int i=0; i<Range.obstacleRects.size(); i++){
/*

                    if (Range.obstacleRects.get(i).get(0).getX() <= (myXY.getX() + radius)// 1 условие
                            &&Range.obstacleRects.get(i).get(0).getY() <= (myXY.getY() + radius) //)// 2 условие
                            && (Range.obstacleRects.get(i).get(0).getX()
                            + abs(Range.obstacleRects.get(i).get(1).getX()-Range.obstacleRects.get(i).get(0).getX())
                            */
                /*разница м/у координатами во внутреннем листе obstacleRects*//*

                            <= (myXY.getX() + radius)
                            &&Range.obstacleRects.get(i).get(0).getY() <= (myXY.getY() + radius))
                            );
*/

                if (Range.obstacleRects.get(i).get(0).getX() <= (myXY.getX() + radius)// 1 условие
                        &&Range.obstacleRects.get(i).get(0).getY() <= (myXY.getY() + radius) // 2 условие
                        &&Range.obstacleRects.get(i).get(0).getX() >= myXY.getX() - radius // 3 условие
                        &&Range.obstacleRects.get(i).get(0).getY() >= myXY.getY() - radius // 4 условие
                        ) {
                    obstacles.add(new Point2D(Range.obstacleRects.get(i).get(0).getX()
                            , Range.obstacleRects.get(i).get(0).getY()));
                }


                if (Range.obstacleRects.get(i).get(1).getX() <= (myXY.getX() + radius)// 1 условие
                        &&Range.obstacleRects.get(i).get(0).getY() <= (myXY.getY() + radius) // 2 условие
                        &&Range.obstacleRects.get(i).get(1).getX() >= myXY.getX() - radius // 3 условие
                        &&Range.obstacleRects.get(i).get(0).getY() >= myXY.getY() - radius // 4 условие
                        ) {
                    obstacles.add(new Point2D(Range.obstacleRects.get(i).get(1).getX()
                            , Range.obstacleRects.get(i).get(0).getY()));
                }


                if (Range.obstacleRects.get(i).get(0).getX() <= (myXY.getX() + radius)// 1 условие
                        &&Range.obstacleRects.get(i).get(1).getY() <= (myXY.getY() + radius) // 2 условие
                        &&Range.obstacleRects.get(i).get(0).getX() >= myXY.getX() - radius // 3 условие
                        &&Range.obstacleRects.get(i).get(1).getY() >= myXY.getY() - radius // 4 условие
                        ) {
                    obstacles.add(new Point2D(Range.obstacleRects.get(i).get(0).getX()
                            , Range.obstacleRects.get(i).get(1).getY()));
                }


                if (Range.obstacleRects.get(i).get(1).getX() <= (myXY.getX() + radius)// 1 условие
                        &&Range.obstacleRects.get(i).get(1).getY() <= (myXY.getY() + radius) // 2 условие
                        &&Range.obstacleRects.get(i).get(1).getX() >= myXY.getX() - radius // 3 условие
                        &&Range.obstacleRects.get(i).get(1).getY() >= myXY.getY() - radius // 4 условие
                        ) {
                    obstacles.add(new Point2D(Range.obstacleRects.get(i).get(1).getX()
                            , Range.obstacleRects.get(i).get(1).getY()));
                }
            }
            return obstacles;
        }
    }
}
