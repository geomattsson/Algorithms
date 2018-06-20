package Controller;

import Model.Command.Command;
import Model.FileHandler;
import Model.ModelHandler;
import Model.Shape.Group;
import Model.Shape.Shape;
import Model.Shape.ShapeType;
import Model.Shape.Shapes.Circle;
import Model.Shape.Shapes.Line;
import Model.Shape.Shapes.Rectangle;
import Model.Shape.Shapes.Star;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.IOException;


public class MainController {

    private static final String PROPERTIES = "Properties";
    private static final String OPEN_FILE = "Open file";
    private static final String SAVE_FILE = "Save file";
    private ModelHandler model;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Pane pane;

    @FXML
    private ListView listView;

    public MainController() {
        model = ModelHandler.getModelHandler();
    }

    public void initialize() {
        for (String s : model.getShapeList()) {
            listView.toString();
            Button button = new Button(s);
            setButtonOnAction(s, button);
            listView.getItems().add(button);
        }
    }

    private void setButtonOnAction(String type, Button button) {
        EventHandler<MouseEvent> event =
                mouseEvent -> create(type);
        button.setOnMousePressed(event);
    }

    private void create(String type) {
        Shape shape = model.getShapeFactory().getShape(type);
        model.getShapes().add(shape);
        shape.draw(pane);
        shape.setOnMouseDragged(onMouseDraggedEventHandler);
        shape.setOnMousePressed(onMousePressedEventHandler);
    }

    public EventHandler<MouseEvent> onMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    javafx.scene.shape.Shape shape = ((javafx.scene.shape.Shape)(mouseEvent.getSource()));
                    model.setOrgSceneX(mouseEvent.getSceneX());
                    model.setOrgSceneY(mouseEvent.getSceneY());
                    model.setOrgTranslateX(((javafx.scene.shape.Shape)(mouseEvent.getSource())).getTranslateX());
                    model.setOrgTranslateY(((javafx.scene.shape.Shape)(mouseEvent.getSource())).getTranslateY());
                    model.setCurrentShape(shape);

                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                        if (mouseEvent.getClickCount() == 2) {
                            try {
                                showPropertiesWindow();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                        if (!model.getShape().isMarked()) {
                            model.getShape().setMarked(true);
                            model.getCurrentShape().setStroke(Color.RED);
                            model.getSelectedShapes().add(model.getShape());
                        } else {
                            model.getShape().setMarked(false);
                            model.getCurrentShape().setStroke(Color.BLACK);
                            model.getSelectedShapes().remove(model.getShape());
                        }
                    }
                }
            };

    protected void showPropertiesWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/properties_window.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle(PROPERTIES);
        stage.setScene(new Scene(root, 400, 150));

        stage.show();
    }

    public EventHandler<MouseEvent> onMouseDraggedEventHandler =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    double offsetX = mouseEvent.getSceneX() - model.getOrgSceneX();
                    double offsetY = mouseEvent.getSceneY() - model.getOrgSceneY();
                    double newTranslateX = model.getOrgTranslateX() + offsetX;
                    double newTranslateY = model.getOrgTranslateY() + offsetY;

                    if (model.isInGroup(model.getShape())) {
                        Group g = model.getCurrentGroup();
                        for(Shape s : g.getAll()) {
                            offsetX = mouseEvent.getSceneX() - model.getOrgSceneX();
                            offsetY = mouseEvent.getSceneY() - model.getOrgSceneY();
                            newTranslateX = s.getX() + (offsetX);
                            newTranslateY = s.getY() + (offsetY);

                            s.moveTo(newTranslateX, newTranslateY);
                        }
                        model.setOrgSceneX(mouseEvent.getSceneX());
                        model.setOrgSceneY(mouseEvent.getSceneY());
                        model.setCurrentGroup(new Group());
                    } else {
                        model.getShape().moveTo(newTranslateX, newTranslateY);
                    }
                }
            };


    public void save() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(SAVE_FILE);
            File file = fileChooser.showSaveDialog(borderPane.getScene().getWindow());
            FileHandler.save(model, file.getPath());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }


    public void open() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(OPEN_FILE);
            File file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
            ModelHandler tmpModel = FileHandler.open(file.getPath());
            for (Shape shape : tmpModel.getShapes()) {
                Shape tmpShape;
                tmpShape = model.getShapeFactory().getShape(shape.getType().toString());
                tmpShape.setX(shape.getX());
                tmpShape.setY(shape.getY());
                tmpShape.draw(pane);
                if (!shape.getType().equals(ShapeType.LINE)) {
                    tmpShape.fill(true, Color.valueOf(shape.getColorString()));
                }
                tmpShape.setStrokeWidth(shape.getStrokeWidth());
                tmpShape.setOnMouseDragged(onMouseDraggedEventHandler);
                tmpShape.setOnMousePressed(onMousePressedEventHandler);
                tmpShape.setMarked(false);
                model.getShapes().add(tmpShape);
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
    public void doUndo() {
        if(!Command.undo.isEmpty()) {
            Command cmd = Command.undo.pop();
            cmd.undo();
        }
    }

    public void doRedo() {
        if(!Command.redo.isEmpty()) {
            Command cmd = Command.redo.pop();
            cmd.redo();
        }
    }

}

