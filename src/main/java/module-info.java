module com.example.webcamv1 {
  requires javafx.controls;
  requires javafx.fxml;

  requires org.kordamp.bootstrapfx.core;
  requires java.desktop;
  requires webcam.capture;
  requires javafx.swing;
  requires org.jetbrains.annotations;
  requires opencv;

  opens com.example.webcamv1 to javafx.fxml;
  exports com.example.webcamv1;
}