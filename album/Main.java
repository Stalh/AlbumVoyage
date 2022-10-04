package album;

import album.controllers.ControllerMenu;
import album.controllers.ControllerModeles;
import album.controllers.ControllerNavigateur;
import album.controllers.ControllerPanneauPhotos;
import album.controllers.ControllerPrincipale;
import album.controllers.ControllerTitre;
import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
   public void start(Stage primaryStage) throws Exception {
      Album album = new Album();
      GestionnaireGraphiques gestionnaireGraphiques = new GestionnaireGraphiques(album);
      ControllerPrincipale cprincipale = new ControllerPrincipale(album, gestionnaireGraphiques);
      ControllerTitre ct = new ControllerTitre(album);
      ControllerNavigateur cn = new ControllerNavigateur(album, gestionnaireGraphiques);
      ControllerPanneauPhotos cpp = new ControllerPanneauPhotos(album, gestionnaireGraphiques);
      ControllerModeles cm = new ControllerModeles(album, gestionnaireGraphiques);
      ControllerMenu cmenu = new ControllerMenu(album);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(this.getClass().getResource("observateurs/ObservateurPrincipale.fxml"));
      loader.setControllerFactory((ic) -> {
         if (ic.equals(ControllerMenu.class)) {
            return cmenu;
         } else if (ic.equals(ControllerModeles.class)) {
            return cm;
         } else if (ic.equals(ControllerNavigateur.class)) {
            return cn;
         } else if (ic.equals(ControllerPanneauPhotos.class)) {
            return cpp;
         } else if (ic.equals(ControllerPrincipale.class)) {
            return cprincipale;
         } else {
            return ic.equals(ControllerTitre.class) ? ct : null;
         }
      });
      Parent root = (Parent)loader.load();
      primaryStage.setResizable(false);
      Scene scene = new Scene(root, 1575.0D, 885.0D);
      scene.getStylesheets().add(Main.class.getResource("outils/css/style.css").toURI().toString());
      primaryStage.setTitle("Album Photo");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   public static void main(String[] args) {
      launch(args);
   }
}
