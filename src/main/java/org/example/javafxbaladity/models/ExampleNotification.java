package org.example.javafxbaladity.models;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import io.github.palexdev.materialfx.enums.NotificationState;
import io.github.palexdev.materialfx.factories.InsetsFactory;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;

public class ExampleNotification extends MFXSimpleNotification {
    private StringProperty headerText = new SimpleStringProperty();
    private StringProperty contentText = new SimpleStringProperty();

    public ExampleNotification(StringProperty hText,StringProperty cText) throws IOException {

        headerText=hText;
        contentText=cText;

        MFXFontIcon fi = new MFXFontIcon();
        fi.setDescription("desc.getDescription()");
        fi.setSize(16);
        MFXIconWrapper icon = new MFXIconWrapper(fi, 32);
        Label headerLabel = new Label();
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        headerLabel.textProperty().bind(headerText);
        MFXIconWrapper readIcon = new MFXIconWrapper("fas-eye", 16, 32);
        ((MFXFontIcon) readIcon.getIcon()).descriptionProperty().bind(Bindings.createStringBinding(
                () -> (getState() == NotificationState.READ) ? "fas-eye" : "fas-eye-slash",
                notificationStateProperty()
        ));
        StackPane.setAlignment(readIcon, Pos.CENTER_RIGHT);
        StackPane placeHolder = new StackPane(readIcon);
        placeHolder.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(placeHolder, Priority.ALWAYS);
        HBox header = new HBox(10, icon, headerLabel, placeHolder);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(InsetsFactory.of(5, 0, 5, 0));
        header.setMaxWidth(Double.MAX_VALUE);

        Label contentLabel = new Label();
        contentLabel.setFont(Font.font("System", FontWeight.MEDIUM, 12));
        contentLabel.getStyleClass().add("content");
        contentLabel.textProperty().bind(contentText);
        contentLabel.setWrapText(true);
        contentLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        contentLabel.setAlignment(Pos.TOP_LEFT);

        BorderPane container = new BorderPane();
        container.getStyleClass().add("notification");
        container.setTop(header);
        container.setCenter(contentLabel);
        //container.setBottom(actionsBar);
        //container.getStylesheets().add("design_V3\\design\\src\\main\\resources\\css\\ExampleNotification.css");
        container.setStyle("-fx-border-color: black;-fx-border-width: 1px;-fx-border-radius: 20px");
        container.setMinWidth(400);
        container.setMinHeight(150);

        container.setMaxHeight(150);
        container.setMaxWidth(400);
        setContent(container);


    }
    public String getHeaderText() {
        return headerText.get();
    }

    public StringProperty headerTextProperty() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText.set(headerText);
    }

    public String getContentText() {
        return contentText.get();
    }

    public StringProperty contentTextProperty() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText.set(contentText);
    }
}


