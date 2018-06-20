package Model.Shape;

import Model.Cloneable;
import Model.Observer.Observer;
import Model.Observer.Subject;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by Yousif on 2018-03-05.
 */

public abstract class Shape extends Observer implements Cloneable, Serializable {
    private ShapeType type;
    private transient Color color = Color.TRANSPARENT;
    private String colorString;
    private transient javafx.scene.shape.Shape shape;
    private double strokeWidth = 2;
    private double x = 100;
    private double y = 100;
    private boolean marked = false;

    public Shape(Subject subject) {
        this.subject = subject;
    }
    public Shape(){}

    public abstract void draw(Pane pane);
    public abstract void remove();

    public void setShape(javafx.scene.shape.Shape shape) {
        this.shape = shape;
    }
    public javafx.scene.shape.Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setOnMousePressed(EventHandler<MouseEvent> onMousePressedEventHandler) {
        shape.setOnMousePressed(onMousePressedEventHandler);
    }
    public void setOnMouseDragged(EventHandler<MouseEvent> onMouseDraggedEventHandler) {
        shape.setOnMouseDragged(onMouseDraggedEventHandler);
    }

    public void fill(boolean filled, Color color) {
        if (filled) {
            setColor(color);
            colorString = color.toString();
        } else {
            setColor(Color.TRANSPARENT);
            colorString = Color.TRANSPARENT.toString();
        }
        shape.setFill(getColor());
    }

    public boolean isFilled() {
        return shape.getFill().isOpaque();
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
        shape.setStrokeWidth(getStrokeWidth());
    }


    public boolean isMarked() {
        return marked;
    }
    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void moveTo(double x, double y) {
        this.x = x;
        this.y = y;
        shape.setTranslateX(x);
        shape.setTranslateY(y);
    }

    public ShapeType getType() {
        return type;
    }
    public void setType(ShapeType type) {
        this.type = type;
    }

    public String getColorString() {
        return colorString;
    }
    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public Subject getSubject() { return subject; }

    @Override
    public void update() {
        int x = subject.getState();
        System.out.println("Update State(" + type.toString() + ") is: " + x);
    }
}

