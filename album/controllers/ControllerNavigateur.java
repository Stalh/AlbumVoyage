package album.controllers;

import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ControllerNavigateur extends Controller implements Initializable {
   @FXML
   private Button buttonPhoto;
   @FXML
   private Button buttonCharger;
   @FXML
   private Button buttonSave;

   public ControllerNavigateur(Album album, GestionnaireGraphiques gestionnaireGraphiques) {
      super(album, gestionnaireGraphiques);
      this.album.ajouterControllers(this);
   }

   public void photos(ActionEvent actionEvent) {
      this.gestionnaireGraphiques.setPhotosPressed(true);
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
   }

   public void mouseEnteredPhoto(MouseEvent mouseEvent) {
      this.buttonPhoto.setStyle("-fx-background-color: #1d1d1d");
   }

   public void mouseExitedPhoto(MouseEvent mouseEvent) {
      this.buttonPhoto.setStyle("-fx-background-color: #141414");
   }

   public void mouseEnteredCharger(MouseEvent mouseEvent) {
      this.buttonCharger.setStyle("-fx-background-color: #1d1d1d");
   }

   public void mouseExitedCharger(MouseEvent mouseEvent) {
      this.buttonCharger.setStyle("-fx-background-color: #141414");
   }

   public void mouseEnteredSave(MouseEvent mouseEvent) {
      this.buttonSave.setStyle("-fx-background-color: #1d1d1d");
   }

   public void mouseExitedSave(MouseEvent mouseEvent) {
      this.buttonSave.setStyle("-fx-background-color: #141414");
   }

   public void sauvegarder(ActionEvent actionEvent) {
      FileChooser fileChooser = new FileChooser();
      ExtensionFilter extFilter = new ExtensionFilter("Album file (*.albm)", new String[]{"*.albm"});
      fileChooser.getExtensionFilters().add(extFilter);
      Stage saveStage = new Stage();
      saveStage.setTitle("Sauvegarder");
      File filename = fileChooser.showSaveDialog(saveStage);
      if (filename != null) {
         this.album.save(filename);
      }

   }

   public void charger(ActionEvent actionEvent) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choisir une image");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter[]{new ExtensionFilter("extensions d'album ", new String[]{"*.albm"})});
      Stage openStage = new Stage();
      openStage.setTitle("Sauvegarder");
      File filename = fileChooser.showOpenDialog(openStage);
      if (filename != null) {
         this.album.load(filename);
      }

   }
}
