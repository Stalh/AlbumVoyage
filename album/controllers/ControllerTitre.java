package album.controllers;

import album.modeles.Album;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ControllerTitre extends Controller implements Initializable {
   @FXML
   private Label promotion;
   private RotateTransition r;

   public ControllerTitre() {
   }

   public ControllerTitre(Album album) {
      super(album);
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.promotion.setAlignment(Pos.CENTER);
      KeyFrame kf1 = new KeyFrame(Duration.seconds(0.1D), (evt) -> {
         this.promotion.setTextFill(Color.color(Math.random(), Math.random(), Math.random(), 1.0D));
      }, new KeyValue[0]);
      Timeline timeline = new Timeline(new KeyFrame[]{kf1});
      timeline.setCycleCount(-1);
      timeline.play();
      this.r = new RotateTransition(Duration.seconds(3.0D), this.promotion);
      this.r.setFromAngle(0.0D);
      this.r.setToAngle(360.0D);
      this.r.setAutoReverse(true);
      this.r.setCycleCount(1);
      this.r.setAxis(new Point3D(1.0D, 10.0D, 1.0D));
   }

   public void rotate(MouseEvent mouseEvent) {
      if (this.r.getStatus() == Status.RUNNING) {
         this.r.pause();
      } else {
         this.r.play();
      }

   }
}
