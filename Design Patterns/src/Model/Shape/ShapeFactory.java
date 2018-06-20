package Model.Shape;

import Model.AbstractFactory;
import Model.Observer.Subject;
import Model.Shape.Shapes.*;
import java.io.Serializable;

/**
 * Created by Yousif on 2018-03-05.
 */

public class ShapeFactory extends AbstractFactory implements Serializable {
    private Subject subject;

    private Circle circle;
    private Rectangle rectangle;
    private Line line;
    private Star star;
    private Ellipse ellipse;

    public ShapeFactory() {
        subject = new Subject();
    }

    /**
     * When a new shape is added the add it here.
     * @param type
     * @return
     */
    @Override
    public Shape getShape(String type) {
        Shape shape;
        switch (ShapeType.valueOf(type)) {
            case CIRCLE:
                if (circle == null) {
                    circle = new Circle(subject);
                }
                shape = (Shape) circle.makeClone();
                break;
            case RECTANGLE:
                if (rectangle == null) {
                    rectangle = new Rectangle(subject);
                }
                shape = (Shape) rectangle.makeClone();
                break;
            case LINE:
                if(line == null)
                    line = new Line(subject);
                shape = (Shape) line.makeClone();
                break;
            case STAR:
                if(star == null)
                    star = new Star(subject);
                shape = (Shape) star.makeClone();
                break;
            case ELLIPSE:
                if(ellipse == null)
                    ellipse = new Ellipse(subject);
                shape = (Shape) ellipse.makeClone();
                break;
            default:
                return null;
        }
        subject.attach(shape);
        return shape;
    }
}
