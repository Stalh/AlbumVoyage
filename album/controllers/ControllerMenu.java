package album.controllers;

import album.modeles.Album;
import javafx.event.ActionEvent;

public class ControllerMenu extends Controller {
   public ControllerMenu(Album album) {
      super(album);
      this.album.ajouterControllers(this);
   }

   public void addPage(ActionEvent actionEvent) {
      this.album.ajouterPage();
   }
}
