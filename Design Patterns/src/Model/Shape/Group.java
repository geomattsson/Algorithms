package Model.Shape;

import Model.Cloneable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Group extends Shape {
    ArrayList<Shape> shapes;
    private Pane pane;

    public Group() {
        shapes = new ArrayList<>();
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
    }
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }
    public Shape getShape(int x) {
        return shapes.get(x);
    }
    public ArrayList<Shape> getAll() {
        return shapes;
    }

    public void clear() {
        shapes.clear();
    }

    public boolean isInGroup(Shape shape) {
        System.out.println(shapes.size());
        for(Shape s : shapes) {
            if(s.equals(shape)) return true;
        }
        return false;
    }

    @Override
    public void fill(boolean filled, Color color) {
        for(Shape s : shapes)
            s.fill(filled, color);
    }

    @Override
    public boolean isFilled() {
        if(shapes.size() > 0) return shapes.get(0).isFilled();
        return false;
    }

    @Override
    public void setStrokeWidth(double strokeWidth) {
        for(Shape s : shapes)
            s.setStrokeWidth(strokeWidth);
    }

    @Override
    public void draw(Pane pane) {
        this.pane = pane;
        for(Shape s : shapes)
            s.draw(pane);
    }

    @Override
    public void remove() {
        // Remove all shapes!
        for(Shape s : shapes)
            s.remove();
        // Remove me too!
        pane.getChildren().remove(super.getShape());
    }

    @Override
    public Cloneable makeClone() {
        return new Group();
    }
}
