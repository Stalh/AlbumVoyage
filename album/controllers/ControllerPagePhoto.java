package album.controllers;

import album.Main;
import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;
import album.modeles.Page;
import album.modeles.Photo;
import album.outils.DragResizer;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ControllerPagePhoto extends ControllerPage implements Initializable {
   @FXML
   private Pane pageIG;
   private Page page;

   public ControllerPagePhoto(Album album, GestionnaireGraphiques gestionnaireGraphiques, Page page) {
      super(album, gestionnaireGraphiques);
      this.page = page;
   }

   public void randFormat(ActionEvent actionEvent) {
      this.album.reagir();
   }

   public void dragOver(DragEvent dragEvent) {
      if (dragEvent.getDragboard().hasString()) {
         dragEvent.acceptTransferModes(new TransferMode[]{TransferMode.MOVE});
      }

      dragEvent.consume();
   }

   public void dragDropped(DragEvent dragEvent) {
      try {
         Dragboard dragboard = dragEvent.getDragboard();
         boolean success = false;
         boolean newPhoto = false;
         String id = dragboard.getString();
         ImageView imageView = (ImageView)this.pageIG.lookup("#" + id);
         if (imageView == null) {
            imageView = (ImageView)this.pageIG.getParent().getParent().getParent().getParent().getParent().lookup("#" + id);
         }

         if (imageView != null) {
            double rationWidth = imageView.getBoundsInParent().getWidth() / imageView.getImage().getWidth();
            double rationHeight = imageView.getBoundsInParent().getHeight() / imageView.getImage().getHeight();
            if (!this.page.getPhotos().containsKey(imageView.getId())) {
               double x = dragEvent.getX() - imageView.getImage().getWidth() / 2.0D * rationWidth;
               double y = dragEvent.getY() - imageView.getImage().getHeight() / 2.0D * rationHeight;
               double width = imageView.getImage().getWidth();
               double height = imageView.getImage().getHeight();
               String url = imageView.getImage().getUrl();
               this.album.ajouterPhotoInPage(new Photo(x, y, width, height, url, this.page.getPageNumber()));
               newPhoto = true;
            }

            imageView.setLayoutX(dragEvent.getX() - imageView.getImage().getWidth() / 2.0D * rationWidth);
            imageView.setLayoutY(dragEvent.getY() - imageView.getImage().getHeight() / 2.0D * rationHeight);
            if (!newPhoto) {
               this.album.relocate(this.page.getPageNumber(), imageView.getId(), dragEvent.getX() - imageView.getImage().getWidth() / 2.0D * rationWidth, dragEvent.getY() - imageView.getImage().getHeight() / 2.0D * rationHeight);
            }

            success = true;
         }

         dragEvent.setDropCompleted(success);
         dragEvent.consume();
         this.album.reagir();
      } catch (Exception var20) {
         var20.printStackTrace();
      }

   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.pageIG.setClip(new Rectangle(0.0D, 0.0D, this.pageIG.getPrefWidth(), this.pageIG.getPrefHeight()));
      Iterator var3 = this.page.getPhotos().values().iterator();

      while(var3.hasNext()) {
         Photo photo = (Photo)var3.next();
         ControllerPhoto cp;
         FXMLLoader loader;
         VBox v;
         if (this.gestionnaireGraphiques.getImage(photo.getId()) == null) {
            cp = new ControllerPhoto(this.album, photo, true, this.gestionnaireGraphiques);
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("observateurs/ObservateurPhoto.fxml"));
            loader.setControllerFactory((ic) -> {
               return cp;
            });

            try {
               v = (VBox)loader.load();
               cp.getImageView().setFitHeight(150.0D);
               cp.getImageView().setPreserveRatio(true);
               double rationWidth = cp.getImageView().getBoundsInParent().getWidth() / cp.getImageView().getImage().getWidth();
               photo.setHeight(150.0D);
               photo.setWidth(photo.getWidth() * rationWidth);
               this.pageIG.getChildren().add(v);
            } catch (IOException var10) {
               var10.printStackTrace();
            }
         } else {
            cp = new ControllerPhoto(this.album, this.gestionnaireGraphiques.getImage(photo.getId()), photo, true, this.gestionnaireGraphiques);
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("observateurs/ObservateurPhoto.fxml"));
            loader.setControllerFactory((ic) -> {
               return cp;
            });

            try {
               v = (VBox)loader.load();
               cp.getImageView().setFitHeight(300.0D);
               DragResizer.makeResizable(v, cp, photo);
               cp.getImageView().setFitHeight(photo.getHeight());
               cp.getImageView().setFitWidth(photo.getWidth());
               this.pageIG.getChildren().add(v);
            } catch (IOException var11) {
               var11.printStackTrace();
            }
         }
      }

   }
}
