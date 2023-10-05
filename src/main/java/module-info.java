module com.example.tiff_image_reader {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tiff_image_reader to javafx.fxml;
    exports com.example.tiff_image_reader;
}