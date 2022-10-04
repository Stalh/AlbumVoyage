package album.controllers;

import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;

public abstract class ControllerPage extends Controller {
   public ControllerPage(Album album, GestionnaireGraphiques gestionnaireGraphiques) {
      super(album, gestionnaireGraphiques);
   }
}
