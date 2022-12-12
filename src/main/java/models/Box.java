package models;

public class Box {
    private double length;
    private double width;
    private double height;

    private double density;

    public Box(double length, double width, double height, double density) {
        this.length = length;
        this.width = width;
        this.height = height;
        this.density = density;
    }


    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDensity() {
        return density;
    }
}
