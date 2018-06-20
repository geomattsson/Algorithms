package Model;

import Model.Color.ColorFactory;
import Model.Shape.ShapeFactory;

/**
 * Created by Yousif on 2018-03-05.
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice){

        if(choice.equalsIgnoreCase("SHAPE")){
            return new ShapeFactory();

        }else if(choice.equalsIgnoreCase("COLOR")){
            return new ColorFactory();
        }

        return null;
    }
}
