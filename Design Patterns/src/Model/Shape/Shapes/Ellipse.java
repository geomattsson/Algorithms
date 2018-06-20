package Model.Shape.Shapes;

import Model.Cloneable;
import Model.Observer.Subject;
import Model.Shape.Shape;
import Model.Shape.ShapeType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Ellipse extends Shape {
    private transient Pane pane;

    public Ellipse(Subject subject) {
        super(subject);
    }

    @Override
    public void draw(Pane pane) {
        this.pane = pane;
        javafx.scene.shape.Ellipse shape = new javafx.scene.shape.Ellipse();
        shape.setCenterX(50.0f);
        shape.setCenterY(50.0f);
        shape.setRadiusX(50.0f);
        shape.setRadiusY(25.0f);
        super.setShape(shape);
        getShape().setStroke(Color.BLACK);
        getShape().setFill(getColor());
        getShape().setStrokeWidth(getStrokeWidth());
        getShape().setTranslateX(getX());
        getShape().setTranslateY(getY());
        pane.getChildren().add(getShape());
        setType(ShapeType.ELLIPSE);
    }

    @Override
    public void remove() {
        pane.getChildren().remove(super.getShape());
    }


    @Override
    public Cloneable makeClone() {
        return new Ellipse(super.getSubject());
    }
}
