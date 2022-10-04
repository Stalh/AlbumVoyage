package album.controllers;

import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;

public abstract class Controller {
   protected Album album;
   protected GestionnaireGraphiques gestionnaireGraphiques;

   public Controller() {
   }

   public Controller(Album album) {
      this.album = album;
   }

   public Controller(GestionnaireGraphiques gestionnaireGraphiques) {
      this.gestionnaireGraphiques = gestionnaireGraphiques;
   }

   public Controller(Album album, GestionnaireGraphiques gestionnaireGraphiques) {
      this.album = album;
      this.gestionnaireGraphiques = gestionnaireGraphiques;
   }

   public void mettreAJour() {
   }
}
