module org.example.javafxbaladity {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    /*requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;*/
    requires java.sql;
    requires javafx.media;

    opens org.example.javafxbaladity to javafx.fxml;
    exports org.example.javafxbaladity;
    exports org.example.javafxbaladity.gui;
    opens org.example.javafxbaladity.gui to javafx.fxml;

    requires MaterialFX;
    requires tess4j;
    requires javafx.swing;
    requires org.bytedeco.opencv;
    requires java.mail;
    requires twilio;

    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires itextpdf;

    requires java.desktop;

    requires org.apache.pdfbox;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires com.calendarfx.view;
    requires jdk.jsobject;


    opens org.example.javafxbaladity.models to javafx.base;

}