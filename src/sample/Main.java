package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    //Loading the OpenCV core library
    // https://medium.com/@aadimator/how-to-set-up-opencv-in-intellij-idea-6eb103c1d45c
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String args[]) {
        launch(args);
    }

    private VideoCapture videoCapture;

    @Override
    public void start(Stage stage) throws Exception {
        videoCapture = new VideoCapture(0); // The number is the ID of the camera

        ImageView imageView = new ImageView();
        HBox hbox = new HBox(imageView);
        Scene scene = new Scene(hbox);
        stage.setTitle("openCvVideoExample");
        stage.setScene(scene);
        stage.show();
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                imageView.setImage(getVideoFrameAsImage());
            }
        }.start();
    }

    public Image getVideoFrameAsImage() {
        Mat mat = new Mat();
        videoCapture.read(mat);
        return convertToJavaFxImage(mat);
    }

    public Image convertToJavaFxImage(Mat mat) {
        MatOfByte bytes = new MatOfByte();
        Imgcodecs.imencode(".bmp", mat, bytes);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes.toArray());
        Image img = new Image(inputStream);
        return img;
    }

}