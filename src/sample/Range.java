package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Point2D;

import java.util.*;

import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Created by Sergo on 08.10.2016.
 */
public class Range {

    static List<List<Point2D>> obstacleRects = new ArrayList<>();

    static final Integer canvaWidth = 1100;
    static final Integer canvaHeight = 1000;
//    static final Integer canvaWidth = 600;
//    static final Integer canvaHeight = 600;

    static final Integer numberOfRobots = 10;
    private static final Double percentageOfObstacles = 0.1;
//    static Point2D goalPoint = new Point2D(canvaWidth-80,60);
static Point2D goalPoint = new Point2D(80,60);
    // Начальные точки положений роботов (пока что задаются так)
    static Point2D[] startPositions = new Point2D[]{
            new Point2D(3,canvaHeight-15),new Point2D(21,canvaHeight-15),
            new Point2D(39,canvaHeight-15),new Point2D(57,canvaHeight-15),new Point2D(75,canvaHeight-15),
            new Point2D(3,canvaHeight-30),new Point2D(21,canvaHeight-30),
            new Point2D(39,canvaHeight-30),new Point2D(57,canvaHeight-30),new Point2D(75,canvaHeight-30)};

    static Robot[] robots = new Robot[numberOfRobots];
    private static Integer numberOfObstacles =
            Math.toIntExact(round((canvaHeight * canvaWidth / 100 - numberOfRobots * 4 - 1) * percentageOfObstacles / 100));

//    private static ArrayList<Point2D> obstaclePositionsSet = new ArrayList<Point2D>();
private static Set<Point2D> obstaclePositionsSet = new HashSet<>();


    static void initRange(GraphicsContext context, Integer canvaWidth, Integer canvaHeight){
        // Отрисовка рамки канвы
        context.setFill(Color.BLACK);
        context.fillRect(0,0,canvaWidth,2);
        context.fillRect(canvaWidth-2,0,2,canvaHeight);
        context.fillRect(0,canvaHeight-2,canvaWidth,2);
        // Задание (обозначение) целевой точки на карте
        setGoal(context);
        // Выставление роботов на карту и их инициализация
        setRobots(context,startPositions);
        // Расстановка препятствий слуйчайным образом и запоминание расстановки для последующего повторения
//        setObstacles(context,startPositions);
    }


    private static void setObstacles(GraphicsContext context, Point2D[] startPositions) {
        // Получение границ строя во избежание установки препятствий на роботов
        Point2D minXOfFormation = minPoint(startPositions,"X");
        Point2D maxXOfFormation = maxPoint(startPositions,"X");
        Point2D minYOfFormation = minPoint(startPositions,"Y");
        Point2D maxYOfFormation = maxPoint(startPositions,"Y");

//        Integer numberOfObstacles = round((canvaHeight*canvaWidth/100 - numberOfRobots*4 - 1)*percentageOfObstacles/100);
        Random r = new Random();
        for (int i=0;i<numberOfObstacles;i++){
            Integer rX = abs(r.nextInt()%canvaWidth);
            Integer rY = abs(r.nextInt()%canvaHeight);
/*
            if (rX<minXOfFormation.getX()&&rX>maxXOfFormation.getX()
                    &&rY<minYOfFormation.getY()&&rY>maxYOfFormation.getY())
*/
            if (rX>maxXOfFormation.getX()&&rY<minYOfFormation.getY()) {
                rX = round((rX/10)*10);
                if (!obstaclePositionsSet.add(new Point2D(rX + 5, rY + 5))) {
                    i--;
                }
            }
            else i--;
        }
        drawObstacles(context,obstaclePositionsSet);
    }

    private static void drawObstacles(GraphicsContext context, Set<Point2D> obstaclePositionsSet) {
        Iterator<Point2D> itr = obstaclePositionsSet.iterator();
        context.setFill(Color.BLACK);
        while (itr.hasNext()) {
            Point2D tmp = itr.next();
            drawRect(context, tmp);
        }
    }


    public static Integer flipBit(Integer position, Integer value) {
        return value ^ 1 << position;
    }

    private static Point2D minPoint(Point2D[] startPositions, String a) {
        if(a.equals("Y")) {
            int i_min = 0;
            double min = startPositions[i_min].getY();
            for (int i = 0; i < startPositions.length; i++) {
                if (startPositions[i].getY() < min) {
                    min = startPositions[i].getY();
                    i_min = i;
                }
            }
            return startPositions[i_min];
        }
        else {
            int i_min = 0;
            double min = startPositions[i_min].getX();
            for (int i = 0; i < startPositions.length; i++) {
                if (startPositions[i].getX() < min) {
                    min = startPositions[i].getX();
                    i_min = i;
                }
            }
            return startPositions[i_min];
        }
    }

    private static Point2D maxPoint(Point2D[] startPositions, String a) {
        if(a.equals("X")) {
            int i_max = 0;
            double max = startPositions[i_max].getX();
            for (int i = 0; i < startPositions.length; i++) {
                if (startPositions[i].getX() > max) {
                    max = startPositions[i].getX();
                    i_max = i;
                }
            }
            return startPositions[i_max];
        }
        else {
            int i_max = 0;
            double max = startPositions[i_max].getY();
            for (int i = 0; i < startPositions.length; i++) {
                if (startPositions[i].getY() > max) {
                    max = startPositions[i].getY();
                    i_max = i;
                }
            }
            return startPositions[i_max];
        }
    }

    private static void setGoal(GraphicsContext context) {
        context.setFill(Color.RED);
        drawRect(context, goalPoint);
    }

    private static void setRobots(GraphicsContext context,Point2D[] stPositions) {
        initRobots(stPositions,context);
        drawRobots(context,stPositions);
    }

    private static void initRobots(Point2D[] stPositions, GraphicsContext context) {
        for (int i=0;i<stPositions.length;i++){
            robots[i] = new Robot(i, stPositions[i],context, goalPoint);
        }
    }

    private static void drawRobots(GraphicsContext context, Point2D[] stPositions) {
        context.setFill(Color.FORESTGREEN);
        for (Point2D stPosition : stPositions) {
//            context.fillRect(stPosition.getX(), stPosition.getY(), 8, 8);
            drawRect(context, stPosition);
        }
    }

    public static void drawRect(GraphicsContext context, Point2D Position) {
//        context.fillOval(Position.getX(), Position.getY(), 10, 10);
        context.fillRect(Position.getX(), Position.getY(), 10, 10);
    }

    private static Point2D getGoalPoint(){
        return goalPoint;
    }
}
