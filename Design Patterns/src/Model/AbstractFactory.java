package Model;

import Model.Color.Color;
import Model.Shape.Shape;

/**
 * Created by Yousif on 2018-03-05.
 */
public abstract class AbstractFactory {
    public abstract Shape getShape(String type);

}
