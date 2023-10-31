package com.example.tiff_image_reader;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    Button nextBtn;
    File tiffFile;
    int refreshNumber = 0;

    @FXML
    protected void onHelloButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select your wav file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TIFF Images", "*.tif", "*.tiff"));
//        File tiffFile =  fileChooser.showOpenDialog(null);

        fileChooser.setInitialDirectory(new File("C:\\Users\\User\\Downloads\\test samples\\test samples\\Q2"));
        tiffFile =  fileChooser.showOpenDialog(null);

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

                imageView1.setImage(writableImage);

                BufferedImage bufferedImage2 = ImageIO.read(tiffFile);
                WritableImage writableImage2 = new javafx.scene.image.WritableImage(imageWidth, imageHeight);
                PixelWriter pixelWriter2 = writableImage2.getPixelWriter();

                //making the greyscale image
                for (int x = 0; x < imageWidth; x++) {
                    for (int y = 0; y < imageHeight; y++) {
                        int argb = bufferedImage2.getRGB(x, y);
                        int r = (argb >> 16) & 0xff;
                        int g = (argb >> 8) & 0xff;
                        int b = argb & 0xff;
                        int gray = (r + g + b) / 3;
                        int grayscaleArgb = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;

                        pixelWriter2.setArgb(x, y, grayscaleArgb);
                    }
                }

                imageView2.setImage(writableImage2);

                //make the next button visible
                nextBtn.setVisible(true);

            } catch (IOException e) {
                System.out.println("Error reading File");
            }
        }
    }

    @FXML
    protected void onNextClicked() throws IOException {
        if(refreshNumber == 0){ //50% darker
            BufferedImage bufferedImage = ImageIO.read(tiffFile);
            int imageWidth = bufferedImage.getWidth();
            int imageHeight = bufferedImage.getHeight();
            WritableImage writableImage = new javafx.scene.image.WritableImage(imageWidth, imageHeight);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            for (int x = 0; x < imageWidth; x++) {
                for (int y = 0; y < imageHeight; y++) {
                    int argb = bufferedImage.getRGB(x, y);
                    int r = (argb >> 16) & 0xff;
                    int g = (argb >> 8) & 0xff;
                    int b = argb & 0xff;
                    r = r/2;
                    g=g/2;
                    b=b/2;
                    int dim = (0xFF << 24) | (r << 16) | (g << 8) | b;
                    pixelWriter.setArgb(x, y, dim);
                }
            }

            imageView1.setImage(writableImage);

            BufferedImage bufferedImage2 = ImageIO.read(tiffFile);
            WritableImage writableImage2 = new javafx.scene.image.WritableImage(imageWidth, imageHeight);
            PixelWriter pixelWriter2 = writableImage2.getPixelWriter();

            //Making the greyscale image
            for (int x = 0; x < imageWidth; x++) {
                for (int y = 0; y < imageHeight; y++) {
                    int argb = bufferedImage2.getRGB(x, y);
                    int r = (argb >> 16) & 0xff;
                    int g = (argb >> 8) & 0xff;
                    int b = argb & 0xff;
                    int gray = (r + g + b) / 6;
                    int grayscaleArgb = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;

                    pixelWriter2.setArgb(x, y, grayscaleArgb);
                }
            }

            imageView2.setImage(writableImage2);
            refreshNumber++;
        } else if (refreshNumber == 1) { //ordered dithering
            BufferedImage bufferedImage = ImageIO.read(tiffFile);
            int imageWidth = bufferedImage.getWidth();
            int imageHeight = bufferedImage.getHeight();
            WritableImage writableImage = new javafx.scene.image.WritableImage(imageWidth, imageHeight);
            PixelWriter pixelWriter = writableImage.getPixelWriter();

            //Making the greyscale image
            for (int x = 0; x < imageWidth; x++) {
                for (int y = 0; y < imageHeight; y++) {
                    int argb = bufferedImage.getRGB(x, y);
                    int r = (argb >> 16) & 0xff;
                    int g = (argb >> 8) & 0xff;
                    int b = argb & 0xff;
                    int gray = (r + g + b) / 3;
                    int grayscaleArgb = (0xFF << 24) | (gray << 16) | (gray << 8) | gray;

                    pixelWriter.setArgb(x, y, grayscaleArgb);
                }
            }

            imageView1.setImage(writableImage);

            BufferedImage bufferedImage2 = ImageIO.read(tiffFile);
            WritableImage writableImage2 = new javafx.scene.image.WritableImage(imageWidth, imageHeight);
            PixelWriter pixelWriter2 = writableImage2.getPixelWriter();

            //Making the greyscale image
            for (int x = 0; x < imageWidth; x++) {
                for (int y = 0; y < imageHeight; y++) {
                    int argb = bufferedImage2.getRGB(x, y)/2;
                    pixelWriter2.setArgb(x, y, argb);
                }
            }

            imageView2.setImage(writableImage2);
            refreshNumber++;
        } else if (refreshNumber == 2) {

        }
    }

    public void exitProgram(javafx.event.ActionEvent actionEvent) {
        Platform.exit();
    }
}