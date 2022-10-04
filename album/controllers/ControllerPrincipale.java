package album.controllers;

import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class ControllerPrincipale extends Controller {
   @FXML
   protected Pane panneauPhoto;

   public ControllerPrincipale(Album album, GestionnaireGraphiques gestionnaireGraphiques) {
      super(album, gestionnaireGraphiques);
      this.album.ajouterControllers(this);
   }

   public void mettreAJour() {
      if (this.gestionnaireGraphiques.isPhotosPressed()) {
         this.panneauPhoto.setVisible(true);
      }

   }
}
