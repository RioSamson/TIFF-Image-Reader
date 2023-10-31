package com.example.tiff_image_reader;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class HelloController {

    @FXML
    ImageView imageView1;
    @FXML
    ImageView imageView2;

    @FXML
    protected void onHelloButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your wav file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TIFF Images", "*.tif", "*.tiff"));
//        File tiffFile =  fileChooser.showOpenDialog(null);

        fileChooser.setInitialDirectory(new File("C:\\Users\\User\\Downloads\\test samples\\test samples\\Q2"));
        File tiffFile =  fileChooser.showOpenDialog(null);

//        RenderableGraphics
        if(tiffFile != null){
            try {
                BufferedImage bufferedImage = ImageIO.read(tiffFile);
                int imageWidth = bufferedImage.getWidth();
                int imageHeight = bufferedImage.getHeight();
                WritableImage writableImage = new javafx.scene.image.WritableImage(imageWidth, imageHeight);
                PixelWriter pixelWriter = writableImage.getPixelWriter();

                for (int x = 0; x < imageWidth; x++) {
                    for (int y = 0; y < imageHeight; y++) {
                        int argb = bufferedImage.getRGB(x, y);
                        pixelWriter.setArgb(x, y, argb);
                    }
                }

//                imageView1.setFitHeight(imageHeight/1.5);
//                imageView1.setFitWidth(imageWidth/1.5);
                imageView1.setImage(writableImage);

                imageView2.setImage(writableImage);

            } catch (IOException e) {
                System.out.println("Error reading File");
            }
        } 
    }

    public void exitProgram(javafx.event.ActionEvent actionEvent) {
        Platform.exit();
    }
}