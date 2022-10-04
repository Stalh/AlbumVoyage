package album.controllers;

import album.Main;
import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;
import album.modeles.Page;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ControllerModeles extends Controller implements Initializable {
   @FXML
   private BorderPane borderPane;
   @FXML
   private Pane menu;
   @FXML
   private HBox pages;
   @FXML
   private HBox menuViewPage;
   @FXML
   private ComboBox pageChoice;
   @FXML
   private Label nbPages;
   private ObservableList<String> pagesName;

   public ControllerModeles() {
   }

   public ControllerModeles(Album album, GestionnaireGraphiques gestionnaireGraphiques) {
      super(album, gestionnaireGraphiques);
      this.album.ajouterControllers(this);
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.nbPages.textProperty().bind(this.album.getNbPages().asString());
      this.pagesName = FXCollections.observableArrayList();
      int cmp = 0;
      Iterator var4 = this.album.getPage().iterator();

      Page page;
      while(var4.hasNext()) {
         page = (Page)var4.next();
         String name = null;
         if (cmp == 0) {
            name = "Tranche - Page " + (cmp + 1);
         } else {
            name = "Page " + cmp + " - Page " + (cmp + 1);
         }

         ++cmp;
         if (!this.pagesName.contains(name)) {
            this.pagesName.add(name);
         }
      }

      this.pageChoice.setItems(this.pagesName);
      FXMLLoader loader2;
      Pane pane;
      ControllerPagePhoto cpp;
      if (this.album.getPage().size() < 2) {
         this.pages.setSpacing(50.0D);
         ControllerPageTexte cpt = new ControllerPageTexte(this.album, this.gestionnaireGraphiques);
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("observateurs/ObservateurPageTexte.fxml"));
         loader.setControllerFactory((ic) -> {
            return cpt;
         });

         try {
            Pane pane = (Pane)loader.load();
            this.pages.getChildren().add(pane);
         } catch (IOException var11) {
            var11.printStackTrace();
         }

         cpp = new ControllerPagePhoto(this.album, this.gestionnaireGraphiques, (Page)this.album.getPages().get("0"));
         loader2 = new FXMLLoader();
         loader2.setLocation(Main.class.getResource("observateurs/ObservateurPagePhoto.fxml"));
         loader2.setControllerFactory((ic) -> {
            return cpp;
         });

         try {
            pane = (Pane)loader2.load();
            this.pages.getChildren().add(pane);
         } catch (IOException var10) {
            var10.printStackTrace();
         }

         this.pageChoice.setPromptText("Tranche - Page 1");
      } else {
         this.pages.setSpacing(20.0D);
         var4 = this.album.getPage().iterator();

         while(var4.hasNext()) {
            page = (Page)var4.next();
            cpp = new ControllerPagePhoto(this.album, this.gestionnaireGraphiques, page);
            loader2 = new FXMLLoader();
            loader2.setLocation(Main.class.getResource("observateurs/ObservateurPagePhoto.fxml"));
            loader2.setControllerFactory((ic) -> {
               return cpp;
            });

            try {
               pane = (Pane)loader2.load();
               this.pages.getChildren().add(pane);
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }

         ComboBox var10000 = this.pageChoice;
         int var10001 = Integer.parseInt(this.album.getPageOnView()[0]) + 1;
         var10000.setPromptText("Page " + var10001 + " - Page " + (Integer.parseInt(this.album.getPageOnView()[1]) + 1));
      }

      this.pageChoice.getSelectionModel().selectedItemProperty().addListener((observableValue, hBox, t1) -> {
         if (t1 != null && !t1.equals(this.pageChoice.getPromptText())) {
            String numeroPage = (String)t1;
            this.album.setPageOnView(numeroPage);
            this.pageChoice.setPromptText(numeroPage);
         }

      });
   }

   public void mettreAJour() {
      int cmp = 0;
      Iterator var2 = this.album.getPage().iterator();

      Page page;
      while(var2.hasNext()) {
         page = (Page)var2.next();
         String name = null;
         if (cmp == 0) {
            name = "Tranche - Page " + (cmp + 1);
         } else {
            name = "Page " + cmp + " - Page " + (cmp + 1);
         }

         ++cmp;
         if (!this.pagesName.contains(name)) {
            this.pagesName.add(name);
         }
      }

      FXMLLoader loader;
      Pane pane;
      ControllerPagePhoto cpp;
      if (this.album.getPage().size() >= 2 && !this.album.getPageOnView()[0].equals("-1")) {
         this.pages.setSpacing(20.0D);
         this.pages.getChildren().clear();
         var2 = this.album.getPage().iterator();

         while(true) {
            do {
               if (!var2.hasNext()) {
                  ComboBox var10000 = this.pageChoice;
                  int var10001 = Integer.parseInt(this.album.getPageOnView()[0]) + 1;
                  var10000.setPromptText("Page " + var10001 + " - Page " + (Integer.parseInt(this.album.getPageOnView()[1]) + 1));
                  return;
               }

               page = (Page)var2.next();
            } while(!page.getPageNumber().equals(this.album.getPageOnView()[0]) && !page.getPageNumber().equals(this.album.getPageOnView()[1]));

            cpp = new ControllerPagePhoto(this.album, this.gestionnaireGraphiques, page);
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("observateurs/ObservateurPagePhoto.fxml"));
            loader.setControllerFactory((ic) -> {
               return cpp;
            });

            try {
               pane = (Pane)loader.load();
               this.pages.getChildren().add(pane);
            } catch (IOException var7) {
               var7.printStackTrace();
            }
         }
      } else {
         this.pages.setSpacing(50.0D);
         this.pages.getChildren().clear();
         ControllerPageTexte cpt = new ControllerPageTexte(this.album, this.gestionnaireGraphiques);
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("observateurs/ObservateurPageTexte.fxml"));
         loader.setControllerFactory((ic) -> {
            return cpt;
         });

         try {
            Pane pane = (Pane)loader.load();
            this.pages.getChildren().add(pane);
         } catch (IOException var9) {
            var9.printStackTrace();
         }

         cpp = new ControllerPagePhoto(this.album, this.gestionnaireGraphiques, (Page)this.album.getPages().get("0"));
         loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("observateurs/ObservateurPagePhoto.fxml"));
         loader.setControllerFactory((ic) -> {
            return cpp;
         });

         try {
            pane = (Pane)loader.load();
            this.pages.getChildren().add(pane);
         } catch (IOException var8) {
            var8.printStackTrace();
         }

         this.pageChoice.setPromptText("Tranche - Page 1");
      }
   }

   public void previousPage(ActionEvent actionEvent) {
      this.album.previousPage();
      this.pageChoice.setValue(this.pageChoice.getPromptText());
   }

   public void nextPage(ActionEvent actionEvent) {
      this.album.nextPage();
      this.pageChoice.setValue(this.pageChoice.getPromptText());
   }
}
