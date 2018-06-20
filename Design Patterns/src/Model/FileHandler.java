package Model;

import Model.Shape.Shape;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Yousif on 2018-03-12.
 */
public class FileHandler {
    public static void save(ModelHandler model, String fileName) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(model);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static ModelHandler open(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ModelHandler model = (ModelHandler) in.readObject();
            in.close();
            fileIn.close();
            return model;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
    }

}
