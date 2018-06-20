package Model.Shape.Shapes;

import Model.Cloneable;
import Model.Observer.Subject;
import Model.Shape.Shape;
import Model.Shape.ShapeType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Line extends Shape implements Serializable {
    private transient Pane pane;

    public Line(Subject subject) {
        super(subject);
    }

    @Override
    public void draw(Pane pane) {
        setType(ShapeType.LINE);
        this.pane = pane;
        setShape(new javafx.scene.shape.Line(5, 85, 175, 5));
        getShape().setStroke(Color.BLACK);
        getShape().setFill(Color.BLACK);
        getShape().setStrokeWidth(getStrokeWidth());
        getShape().setTranslateX(getX());
        getShape().setTranslateY(getY());
        pane.getChildren().add(getShape());
    }



    @Override
    public void remove() {
        pane.getChildren().remove(super.getShape());
    }

    @Override
    public boolean isFilled() {
        return false;
    }

    @Override
    public Cloneable makeClone() {
        return new Line(super.getSubject());
    }
}
