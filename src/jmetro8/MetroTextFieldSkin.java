package jfxtras.styles.jmetro8;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Created by pedro_000 on 12/5/13.
 */
public class MetroTextFieldSkin extends TextFieldSkin{
    private StackPane rightButton;
    private Region rightButtonGraphic;

    public MetroTextFieldSkin(TextField textField) {
        super(textField);

        rightButton = new StackPane();
        rightButton.getStyleClass().setAll("right-button");
        rightButton.setFocusTraversable(false);

        rightButtonGraphic = new Region();
        rightButtonGraphic.getStyleClass().setAll("right-button-graphic");
        rightButtonGraphic.setFocusTraversable(false);

        rightButtonGraphic.setMaxWidth(Region.USE_PREF_SIZE);
        rightButtonGraphic.setMaxHeight(Region.USE_PREF_SIZE);

        rightButton.setVisible(false);
        rightButtonGraphic.setVisible(false);

        rightButton.getChildren().add(rightButtonGraphic);
        getChildren().add(rightButton);

        setupListeners();
    }

    private void setupListeners() {

        final TextField textField = getSkinnable();
        rightButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                textField.setText("");
            }
        });
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                rightButton.setVisible(!textField.getText().isEmpty());
                rightButtonGraphic.setVisible(!textField.getText().isEmpty());
            }
        });
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                rightButton.setVisible(textField.isFocused() && !textField.getText().isEmpty());
                rightButtonGraphic.setVisible(textField.isFocused() && !textField.getText().isEmpty());
            }
        });
    }

    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        final double clearGraphicWidth = snapSize(rightButtonGraphic.prefWidth(-1));
        final double clearButtonWidth = rightButton.snappedLeftInset() + clearGraphicWidth + rightButton.snappedRightInset();

        rightButton.resize(clearButtonWidth, h);
        positionInArea(rightButton,
                (x+w) - clearButtonWidth, y,
                clearButtonWidth, h, 0, HPos.CENTER, VPos.CENTER);
    }
}