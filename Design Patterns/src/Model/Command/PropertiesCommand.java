package Model.Command;

import Model.Shape.Shape;
import javafx.scene.paint.Color;

public class PropertiesCommand implements Command {
    private Shape shape;
    private double width;
    private boolean fill;
    private Color color;

    public PropertiesCommand(Shape shape, double width, boolean fill, Color color) {
        this.shape = shape;
        this.width = width;
        this.fill = fill;
        this.color = color;
    }

    @Override
    public void execute() {
        undo.push(new PropertiesCommand(shape, shape.getStrokeWidth(), shape.isFilled(), shape.getColor()));
        run();
    }

    @Override
    public void undo() {
        if(redo.size() > 9) redo.remove(9);
        redo.push(new PropertiesCommand(shape, shape.getStrokeWidth(), shape.isFilled(), shape.getColor()));
        run();
    }

    @Override
    public void redo() {
        if(undo.size() > 9) undo.remove(9);
        undo.push(new PropertiesCommand(shape, shape.getStrokeWidth(), shape.isFilled(), shape.getColor()));
        run();

    }

    private void run() {
        shape.fill(fill, color);
        shape.setStrokeWidth(width);
        shape.setColor(color);
    }
}
