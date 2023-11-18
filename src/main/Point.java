package main;

public class Point {
    public int lab;
    public double distance;

    public Point(int lab, double distance) {
        this.lab = lab;
        this.distance = distance;
    }

    public int getLab() {
        return lab;
    }

    public double getDistance() {
        return distance;
    }
}
