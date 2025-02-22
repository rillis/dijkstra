package br.com.rillis.sort;

import java.awt.*;

public class Node {
    private Point point;
    private int value = -1;
    boolean wall = false;

    public Node(Point point){
        this.point = point;
    }

    public Node(Point point, int value){
        this.point = point;
        this.value = value;
    }

    public Node(Point point, boolean wall){
        this.point = point;
        this.wall = wall;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public int getX(){
        return point.x;
    }

    public int getY(){
        return point.y;
    }
}
