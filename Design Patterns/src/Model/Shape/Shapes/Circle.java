package Model.Shape.Shapes;

import Model.Cloneable;
import Model.Observer.Subject;
import Model.Shape.Shape;
import Model.Shape.ShapeType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Created by Yousif on 2018-03-05.
 */
public class Circle extends Shape implements Serializable {
    private transient Pane pane;

    public Circle(Subject subject) {
        super(subject);
    }

    @Override
    public void draw(Pane pane) {
        setType(ShapeType.CIRCLE);
        this.pane = pane;
        setShape(new javafx.scene.shape.Circle(20));
        getShape().setStroke(Color.BLACK);
        getShape().setFill(getColor());
        getShape().setStrokeWidth(getStrokeWidth());
        getShape().setTranslateX(getX());
        getShape().setTranslateY(getY());
        super.setShape(getShape());
        pane.getChildren().add(getShape());
    }

    @Override
    public void remove() {
        pane.getChildren().remove(super.getShape());
    }

    @Override
    public Cloneable makeClone() {
        return new Circle(super.getSubject());
    }
}

