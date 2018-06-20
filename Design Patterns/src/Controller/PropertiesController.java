package Controller;

import Model.Command.PropertiesCommand;
import Model.ModelHandler;
import Model.Observer.Subject;
import Model.Shape.Group;
import Model.Shape.Shape;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * Created by Yousif on 2018-03-12.
 */
public class PropertiesController {
    private ModelHandler model;

    @FXML
    private TextField width;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ChoiceBox colorList;

    @FXML
    private CheckBox groupBox;

    public PropertiesController() {
        model = ModelHandler.getModelHandler();
    }

    public void initialize() {
        width.setText(String.valueOf(model.getShape().getStrokeWidth()));
        if (!model.getShape().getColor().equals(Color.TRANSPARENT)) {
            checkBox.setSelected(true);
        }
        colorList.setItems(FXCollections.observableArrayList(
                "RED", "BLUE", "GREEN", "BLACK"));
    }

    @FXML
    public void handleCancelButtonAction(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void handleOkButtonAction(ActionEvent event) {
        for (Shape shape : model.getSelectedShapes()) {
            double width = shape.getStrokeWidth();
            if (!this.width.getText().isEmpty()) {
                width = Double.parseDouble(this.width.getText());
            }
            PropertiesCommand cmd = new PropertiesCommand(shape, width, checkBox.isSelected(), getColor());
            cmd.execute();
            if (groupBox.isSelected()) {
                model.getCurrentGroup().addShape(shape);
            }
        }
        Shape shape = model.getShape();
        Subject subject = shape.getSubject();

        if(model.isInGroup(shape)) {
            handleGroup(shape);
        }
        else if (model.getSelectedShapes().size() == 0) {
            double width = shape.getStrokeWidth();
            if (!this.width.getText().isEmpty()) {
                width = Double.parseDouble(this.width.getText());
            }
            PropertiesCommand cmd = new PropertiesCommand(shape, width, checkBox.isSelected(), getColor());
            cmd.execute();
            subject = shape.getSubject();
        } else {
            if(groupBox.isSelected()) {
                model.addGroup();
            }
        }
        subject.setState(5);
        subject.notifyAllObservers();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    @FXML
    public void handleDeleteButtonAction(ActionEvent event) {
        for (Shape shape : model.getSelectedShapes()) {
            model.removeShape(shape);
            shape.remove();
        }
        if(model.getSelectedShapes().size() == 0) {
         Shape shape = model.getShape();
         model.removeShape(shape);
         shape.remove();
        }
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

    private Color getColor() {
        Color color = Color.TRANSPARENT;
        String c = (String) colorList.getValue();
        if(c == null) return color;
        switch(c) {
            case "RED":
                color = Color.RED;
                break;
            case "BLUE":
                color = Color.BLUE;
                break;
            case "GREEN":
                color = Color.GREEN;
                break;
            case "BLACK":
                color = Color.BLACK;
                break;
            default: break;
        }
        return color;
    }

    private void handleGroup(Shape shape) {
        Group group = model.getCurrentGroup();
        double width = shape.getStrokeWidth();
        if (!this.width.getText().isEmpty()) {
            width = Double.parseDouble(this.width.getText());
        }
        PropertiesCommand cmd = new PropertiesCommand(group, width, checkBox.isSelected(), getColor());
        cmd.execute();
    }
}
