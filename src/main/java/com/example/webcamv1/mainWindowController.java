package com.example.webcamv1;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class mainWindowController {
  @FXML
  public Button button;
  @FXML
  public Canvas canvas;
  private final String density = "Ñ@#W$9876543210?!abc;:+=-,._                    ";
  @FXML
  public ImageView imageView;
  private VideoCapture capture;
  private Mat _image;

  @FXML
  void initialize() {
    capture = new VideoCapture(0);

  }

  private void Draw(Image image) {
    GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    _image = new Mat();
    capture.read(_image);


    graphicsContext.stroke();
  }

  private void DrawWithSymbols(GraphicsContext graphicsContext, Image image) {
    graphicsContext.setFill(Color.WHITE);
    PixelReader pixelReader = image.getPixelReader();
    int densityLength = density.length();
    int width = (int) image.getWidth(), height = (int) image.getHeight();
    int charX = 0, charY;
    for (int x = 0; x < width; x++) {
      charY = 0;
      for (int y = 0; y < height; y++) {
        int argb = pixelReader.getArgb(x, y);
        int densityPixel = (argb >> 16) & 255;
        int charIndex = map(densityPixel, 0, 255, 0, densityLength - 1);
        char symbol = density.charAt(charIndex);
        graphicsContext.fillText(String.valueOf(symbol), charX, charY);
        charY += 10;
      }
      charX += 10;
    }
  }

  private int map(int x, int in_min, int in_max, int out_min, int out_max) {
    return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
  }

  private Image ResizeAndConvertToGrayScale(Image image, int ratio) {
    int width = (int) image.getWidth() / ratio, height = (int) image.getHeight() / ratio;
    WritableImage writableImage = new WritableImage(width, height);
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    PixelReader pixelReader = image.getPixelReader();
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        int sumR = 0, sumG = 0, sumB = 0;
        for (int k = 0; k < ratio; k++) {
          for (int l = 0; l < ratio; l++) {
            int argb = pixelReader.getArgb(x * ratio + k, y * ratio + l);
            sumR += (argb >> 16) & 255;
            sumG += (argb >> 16) & 255;
            sumB += (argb >> 16) & 255;
          }
        }
        sumR /= ratio*ratio;
        sumG /= ratio*ratio;
        sumB /= ratio*ratio;
        int GrayScale = (sumB + sumG + sumR) / 3;
        int color = 0xFF << 24 | GrayScale << 16 | GrayScale << 8 | GrayScale;
        pixelWriter.setArgb(x, y, color);
      }
    }
    return writableImage;
  }
}