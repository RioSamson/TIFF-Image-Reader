module com.example.tiff_image_reader {
    requires javafx.controls;
    requires javafx.fxml;
    requires jai.core;
    requires java.desktop;


    opens com.example.tiff_image_reader to javafx.fxml;
    exports com.example.tiff_image_reader;
}