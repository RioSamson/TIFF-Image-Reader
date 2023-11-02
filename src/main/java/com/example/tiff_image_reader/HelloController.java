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

import static java.lang.Math.ceil;


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

            int n = 5;
            int ditherFactor = (int) (255/(n*n));
            //making the dither matrix
//            int[][] ditherMatrix = {{0, 8, 2, 10},
//                    {12, 4, 14, 6},
//                    {3, 11, 1, 9},
//                    {15, 7, 13, 5}};

//            int[][] ditherMatrix = {{0, 23, 11, 25,  1}, //bayern matrix 5x5
//                    {31, 15,  7, 27,  3},
//                    {13 , 9, 33 ,21 ,17},
//                            {29 , 5 ,19 ,35 , 8},
//                                    {2 ,28, 14, 32, 18}};

//            int[][] ditherMatrix = {
//                    {13 , 8,  4, 15, 12}, //chatgpt random matrix
//                    {0 , 7 ,18 ,11 ,20},
//                    {9 ,24 ,16 , 2 , 5},
//                    {21, 10 ,19 ,14 , 3},
//                    {6 ,22, 17, 23,  1}};
//
//            int[][] ditherMatrix = { //truly random matrix
//                    {6 ,19 ,23 , 9 ,22},
//                    {15,7  ,20 ,11 ,18},
//                    {4 ,16 ,12 ,2  ,13},
//                    {8 ,3  ,1  ,5  ,14},
//                    {21,0  ,24 ,10 ,17}};

            int[][] ditherMatrix = { //self made random matrix
                    {7,11,0,24,2},{9,21,5,19,16},{3,13,1,23,10},{14,8,12,18,6},{22,15,20,17,4}
            };


            for(int x=0; x<imageWidth; x++){ //for each x for each dither matrix square
                for(int y=0; y<imageHeight; y++){ //for each y for each dither matrix square
                    int i = x%n;
                    int j = y%n;
                    //Reading the rgb value from the grayscale image, not the original cuz r=g=b
                    int argb = bufferedImage2.getRGB(x, y);
                    int r = (argb >> 16) & 0xff;
                    int g = (argb >> 8) & 0xff;
                    int b = argb & 0xff;
                    int gray = (r + g + b) / 3;

                    int grayscaleArgb;
                    if((gray/ditherFactor) > ditherMatrix[i][j]){
                        grayscaleArgb = (0xFF << 24) | (255 << 16) | (255 << 8) | 255;
                    } else{
                        grayscaleArgb = (0xFF << 24) | (0 << 16) | (0 << 8) | 0;
                    }
                    pixelWriter2.setArgb(x, y, grayscaleArgb);

                }
            }

            //Making the greyscale image
//            for (int x = 0; x < imageWidth; x++) {
//                for (int y = 0; y < imageHeight; y++) {
//                    int argb = bufferedImage2.getRGB(x, y)/2;
//                    pixelWriter2.setArgb(x, y, argb);
//                }
//            }

            imageView2.setImage(writableImage2);
            refreshNumber++;
        } else if (refreshNumber == 2) { //auto level

        }
    }

    public void exitProgram(javafx.event.ActionEvent actionEvent) {
        Platform.exit();
    }
}