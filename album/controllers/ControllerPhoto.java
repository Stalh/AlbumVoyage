package album.controllers;

import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;
import album.modeles.Photo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

public class ControllerPhoto extends Controller implements Initializable {
   Photo photo;
   private boolean isOnPage;
   private Image imageInAlbum;
   private boolean isDragged;
   @FXML
   private Image image;
   @FXML
   private ImageView imageView;
   @FXML
   private VBox vbox;
   private ContextMenu contextMenu;

   public ControllerPhoto(Album album, Image image, Photo photo, boolean isOnPage, GestionnaireGraphiques gestionnaireGraphiques) {
      this.gestionnaireGraphiques = gestionnaireGraphiques;
      this.photo = photo;
      this.album = album;
      this.imageInAlbum = image;
      this.isOnPage = isOnPage;
   }

   public ControllerPhoto(Album album, Photo photo, boolean isOnPage, GestionnaireGraphiques gestionnaireGraphiques) {
      this.gestionnaireGraphiques = gestionnaireGraphiques;
      this.photo = photo;
      this.album = album;
      this.isOnPage = isOnPage;
   }

   public ImageView getImageView() {
      return this.imageView;
   }

   public Image getImage() {
      return this.image;
   }

   public void setDragged(boolean dragged) {
      this.isDragged = dragged;
   }

   public void dragndrop(MouseEvent me) {
      if (!this.isDragged) {
         Dragboard dragboard = this.imageView.startDragAndDrop(new TransferMode[]{TransferMode.MOVE});
         ClipboardContent content = new ClipboardContent();
         content.putString(this.imageView.getId());
         SnapshotParameters param = new SnapshotParameters();
         param.setDepthBuffer(true);
         content.putImage(this.imageView.snapshot(param, (WritableImage)null));
         dragboard.setContent(content);
         me.consume();
      }

   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.contextMenu = new ContextMenu();
      MenuItem supprimer = new MenuItem("Supprimer");
      this.contextMenu.getItems().add(supprimer);
      supprimer.setOnAction((event) -> {
         this.album.supprimerPhotoInPage(this.photo);
      });
      if (this.imageInAlbum != null) {
         this.imageView.setImage(this.imageInAlbum);
         this.imageView.setPreserveRatio(false);
         this.imageView.setFitWidth(this.photo.getWidth());
         this.imageView.setFitHeight(this.photo.getHeight());
         this.imageView.setId(this.photo.getId());
         this.vbox.setLayoutX(this.photo.getX());
         this.vbox.setLayoutY(this.photo.getY());
      } else {
         this.image = new Image(this.photo.getUrl());
         this.imageView.setImage(this.image);
         this.photo.setWidth(this.image.getWidth());
         this.photo.setHeight(this.image.getHeight());
         this.imageView.setId(this.photo.getId());
         this.vbox.setLayoutX(this.photo.getX());
         this.vbox.setLayoutY(this.photo.getY());
         this.gestionnaireGraphiques.getImage().put(this.photo.getId(), this.image);
      }

   }

   public void contextMenuRequested(ContextMenuEvent contextMenuEvent) {
      if (this.isOnPage) {
         this.contextMenu.show(this.vbox, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
         contextMenuEvent.consume();
      }

   }
}
