module org.example.javafxbaladity {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires javafx.media;
    requires MaterialFX;
    requires java.desktop;

    opens org.example.javafxbaladity to javafx.fxml;
    exports org.example.javafxbaladity;
    exports org.example.javafxbaladity.gui;
    opens org.example.javafxbaladity.gui to javafx.fxml;
    exports org.example.javafxbaladity.models;
    opens org.example.javafxbaladity.models to javafx.fxml;
}