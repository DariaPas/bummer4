module eus.ehu.bummer4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires com.google.gson;
    requires javafx.web;
    opens eus.ehu.bummer4 to com.google.gson, javafx.fxml;
    exports eus.ehu.bummer4;

}