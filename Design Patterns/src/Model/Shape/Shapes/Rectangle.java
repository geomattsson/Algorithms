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
public class Rectangle extends Shape implements Serializable {
    private transient Pane pane;

    public Rectangle(Subject subject) {
        super(subject);
    }

    public void draw(Pane pane) {
        this.pane = pane;
        setShape(new javafx.scene.shape.Rectangle(100,100));
        getShape().setTranslateX(getX());
        getShape().setTranslateY(getY());
        getShape().setStroke(Color.BLACK);
        getShape().setFill(getColor());
        getShape().setStrokeWidth(getStrokeWidth());
        pane.getChildren().add(getShape());
        setType(ShapeType.RECTANGLE);
    }

    @Override
    public void remove() {
        pane.getChildren().remove(super.getShape());
    }

    @Override
    public Cloneable makeClone() {
        return new Rectangle(super.getSubject());
    }
}
