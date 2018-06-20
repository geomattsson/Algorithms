package Model.Color;

import Model.AbstractFactory;
import Model.Color.Colors.Red;
import Model.Shape.Shape;
import Model.Shape.Shapes.Circle;

/**
 * Created by Yousif on 2018-03-05.
 */
public class ColorFactory extends AbstractFactory {
    @Override
    public Shape getShape(String type) {
        return null;
    }
}
