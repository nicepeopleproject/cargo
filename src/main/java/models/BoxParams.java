package models;

public class BoxParams {
    private double length;
    private double width;
    private double height;

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public BoxParams(double length, double width, double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }
}
