package Model.Shape.Shapes;

import Model.Cloneable;
import Model.Observer.Subject;
import Model.Shape.Shape;
import Model.Shape.ShapeType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Star extends Shape implements Serializable{
    private transient Pane pane;

    public Star(Subject subject) {
        super(subject);
    }

    @Override
    public void draw(Pane pane) {
        setType(ShapeType.STAR);
        this.pane = pane;
        javafx.scene.shape.Polygon shape = new javafx.scene.shape.Polygon();
        double shs = 25; // Used to scale the star
        shape.getPoints().addAll(0.0, shs * 3,
                shs * 2, shs * 2,
                shs * 3, 0.0,
                shs * 4, shs * 2,
                shs * 6, shs * 3,
                shs * 4, shs * 4,
                shs * 3, shs * 6,
                shs * 2, shs * 4);

        shape.setStroke(Color.BLACK);
        shape.setFill(getColor());
        shape.setStrokeWidth(getStrokeWidth());
        shape.setTranslateX(200.0);
        shape.setTranslateY(200.0);
        super.setX(200.0);
        super.setY(200.0);
        super.setShape(shape);
        pane.getChildren().add(shape);
    }

    @Override
    public void remove() {
        pane.getChildren().remove(super.getShape());
    }

    @Override
    public Cloneable makeClone() {
        return new Star(super.getSubject());
    }
}
