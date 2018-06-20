package Model;

import Model.Shape.Group;
import Model.Shape.Shape;
import Model.Shape.ShapeType;
import Model.Shape.Shapes.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Yousif on 2018-03-10.
 */
public class ModelHandler implements Serializable {
    private static ModelHandler modelHandler;
    private AbstractFactory shapeFactory;
    private ArrayList<Shape> shapes;
    private transient javafx.scene.shape.Shape currentShape;
    private Group currentGroup;
    private ArrayList<Group> groups;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private List<Shape> selectedShapes;
    private List<String> shapeList;

    public ModelHandler() {
        shapeFactory = FactoryProducer.getFactory("SHAPE");
        groups = new ArrayList<>();
        currentGroup = new Group();
        shapes = new ArrayList<>();
        shapeList = new ArrayList<>();
        selectedShapes = new ArrayList<>();
        shapeList = Stream.of(ShapeType.values())
                .map(ShapeType::name)
                .collect(Collectors.toList());
    }

    public static ModelHandler getModelHandler() {
        if (modelHandler == null) {
            modelHandler = new ModelHandler();
        }
        return modelHandler;
    }

    public Shape getShape() {
        for(Shape shape : shapes) {
            if (shape instanceof Circle) {
                if (shape.getShape().equals(currentShape)) {
                    return shape;
                }
            } else if (shape instanceof Rectangle) {
                if (shape.getShape().equals(currentShape)) {
                    return shape;
                }
            } else if (shape instanceof Line) {
                if(shape.getShape().equals(currentShape)) {
                    return shape;
                }
            } else if (shape instanceof Star) {
                if(shape.getShape().equals(currentShape)) {
                    return shape;
                }
            } else if (shape instanceof Ellipse) {
                if(shape.getShape().equals(currentShape))
                    return shape;
            }
        }
        return null;
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public AbstractFactory getShapeFactory() {
        return shapeFactory;
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    public double getOrgSceneX() {
        return orgSceneX;
    }

    public double getOrgSceneY() {
        return orgSceneY;
    }

    public double getOrgTranslateX() {
        return orgTranslateX;
    }

    public double getOrgTranslateY() {
        return orgTranslateY;
    }

    public void setOrgSceneX(double orgSceneX) {
        this.orgSceneX = orgSceneX;
    }

    public void setOrgSceneY(double orgSceneY) {
        this.orgSceneY = orgSceneY;
    }

    public void setOrgTranslateX(double orgTranslateX) {
        this.orgTranslateX = orgTranslateX;
    }

    public void setOrgTranslateY(double orgTranslateY) {
        this.orgTranslateY = orgTranslateY;
    }

    public javafx.scene.shape.Shape getCurrentShape() {
        return currentShape;
    }
    public void setCurrentShape(javafx.scene.shape.Shape currentShape) {
        this.currentShape = currentShape;
    }
    public Group getCurrentGroup() { return currentGroup; }
    public void setCurrentGroup(Group currentGroup) { this.currentGroup = currentGroup; }
    public void addGroup() {
        Group g = new Group();
        for(Shape s : currentGroup.getAll())
            g.addShape(s);
        groups.add(g);
        currentGroup = new Group();
    }
    public boolean isInGroup(Shape shape) {
        for(Group g : groups) {
            if(g.isInGroup(shape)) {
                this.setCurrentGroup(g);
                return true;
            }
        }
        return false;
    }

    public List<String> getShapeList() {
        return shapeList;
    }

    public List<Shape> getSelectedShapes() {
        return selectedShapes;
    }

    public void setShapes(ArrayList<Shape> shapes) {
        this.shapes = shapes;
    }
}
