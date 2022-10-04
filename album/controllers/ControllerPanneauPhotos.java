package album.controllers;

import album.Main;
import album.gestionnaireGraphique.GestionnaireGraphiques;
import album.modeles.Album;
import album.modeles.Photo;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class ControllerPanneauPhotos extends Controller implements Initializable {
   @FXML
   private VBox general;
   @FXML
   private VBox details;
   @FXML
   private Label dragndrapLabel;
   @FXML
   private ListView listViewColumn1;
   @FXML
   private ListView listViewColumn2;
   private ObservableList<Pane> imagesColumn1 = FXCollections.observableArrayList();
   private ObservableList<Pane> imagesColumn2 = FXCollections.observableArrayList();

   public ControllerPanneauPhotos(Album album, GestionnaireGraphiques gestionnaireGraphiques) {
      super(album, gestionnaireGraphiques);
      this.album.ajouterControllers(this);
   }

   @FXML
   public void download(ActionEvent actionEvent) throws MalformedURLException {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choisir une image");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter[]{new ExtensionFilter("extensions d'images ", new String[]{"*.jpg", "*.png"})});
      Stage loadStage = new Stage();
      loadStage.setTitle("Ouvrir");
      List<File> selectedFile = fileChooser.showOpenMultipleDialog(loadStage);
      if (selectedFile != null) {
         Iterator var5 = selectedFile.iterator();

         while(var5.hasNext()) {
            File f = (File)var5.next();
            this.album.ajouterPhoto(f.toURI().toURL().toString());
         }
      }

      this.album.reagir();
   }

   @FXML
   public void showAlbums(ActionEvent actionEvent) {
      this.general.setVisible(false);
      this.details.setVisible(true);
      this.album.reagir();
   }

   @FXML
   public void showAllAlbums(ActionEvent actionEvent) {
      this.details.setVisible(false);
      this.general.setVisible(true);
      this.album.reagir();
   }

   public void dragOver(DragEvent dragEvent) {
      if (dragEvent.getDragboard().hasFiles()) {
         dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
      }

      dragEvent.consume();
   }

   public void dragDrop(DragEvent dragEvent) throws MalformedURLException {
      List<File> file = dragEvent.getDragboard().getFiles();
      List<File> fileFromDirectory = new ArrayList((int)this.getNumberFiles(file));
      Iterator var4 = file.iterator();

      File f;
      while(var4.hasNext()) {
         f = (File)var4.next();
         if (f.isDirectory()) {
            this.getFileFromDirectory(f, fileFromDirectory);
         } else if (this.isImage(f)) {
            this.album.ajouterPhoto(f.toURI().toURL().toString());
         }
      }

      var4 = fileFromDirectory.iterator();

      while(var4.hasNext()) {
         f = (File)var4.next();
         this.album.ajouterPhoto(f.toURI().toURL().toString());
      }

      this.album.reagir();
   }

   public void getFileFromDirectory(File directory, List<File> fileFromDirectory) {
      List<File> file = Arrays.asList(directory.listFiles());
      Iterator var4 = file.iterator();

      while(var4.hasNext()) {
         File f = (File)var4.next();
         if (f.isDirectory()) {
            this.getFileFromDirectory(f, fileFromDirectory);
         } else if (this.isImage(f)) {
            fileFromDirectory.add(f);
         }
      }

   }

   public boolean isImage(File f) {
      String extension = f.getName().substring(f.getName().lastIndexOf(46) + 1);
      return extension.equals("jpg") || extension.equals("png");
   }

   public long getNumberFiles(List<File> directory) {
      long res = 0L;
      Iterator var4 = directory.iterator();

      while(var4.hasNext()) {
         File f = (File)var4.next();
         if (f.isDirectory()) {
            res += this.getNumberFiles(f);
         } else if (this.isImage(f)) {
            ++res;
         }
      }

      return res;
   }

   public long getNumberFiles(File directory) {
      List<File> file = Arrays.asList(directory.listFiles());
      long res = 0L;
      Iterator var5 = file.iterator();

      while(var5.hasNext()) {
         File f = (File)var5.next();
         if (f.isDirectory()) {
            res += this.getNumberFiles(f);
         } else if (this.isImage(f)) {
            ++res;
         }
      }

      return res;
   }

   public void mettreAJour() {
      if (this.album.getPhotos().size() > 0) {
         this.dragndrapLabel.setVisible(false);
      }

      if (this.album.getPhotos().size() != 0) {
         this.imagesColumn1.clear();
         this.imagesColumn2.clear();
      }

      int column = 0;
      Iterator var2 = this.album.getPhotos().values().iterator();

      while(var2.hasNext()) {
         Photo photo = (Photo)var2.next();
         ControllerPhoto cp;
         FXMLLoader loader;
         VBox vBox;
         Pane pane;
         if (this.gestionnaireGraphiques.getImage(photo.getId()) == null) {
            cp = new ControllerPhoto(this.album, photo, false, this.gestionnaireGraphiques);
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("observateurs/ObservateurPhoto.fxml"));
            loader.setControllerFactory((ic) -> {
               return cp;
            });

            try {
               vBox = (VBox)loader.load();
               pane = new Pane();
               pane.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)}));
               pane.setMinHeight(0.0D);
               pane.setMinWidth(0.0D);
               cp.getImageView().fitHeightProperty().bind(pane.heightProperty());
               cp.getImageView().fitWidthProperty().bind(pane.widthProperty());
               pane.getChildren().add(vBox);
               pane.setPrefSize(150.0D, 150.0D);
               if (column % 2 == 0) {
                  this.imagesColumn1.add(pane);
               } else {
                  this.imagesColumn2.add(pane);
               }

               ++column;
            } catch (IOException var8) {
               var8.printStackTrace();
            }
         } else {
            cp = new ControllerPhoto(this.album, this.gestionnaireGraphiques.getImage(photo.getId()), photo, false, this.gestionnaireGraphiques);
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("observateurs/ObservateurPhoto.fxml"));
            loader.setControllerFactory((ic) -> {
               return cp;
            });

            try {
               vBox = (VBox)loader.load();
               pane = new Pane();
               pane.setBackground(new Background(new BackgroundFill[]{new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)}));
               pane.setMinHeight(0.0D);
               pane.setMinWidth(0.0D);
               cp.getImageView().fitHeightProperty().bind(pane.heightProperty());
               cp.getImageView().fitWidthProperty().bind(pane.widthProperty());
               pane.getChildren().add(vBox);
               pane.setPrefSize(150.0D, 150.0D);
               if (column % 2 == 0) {
                  this.imagesColumn1.add(pane);
               } else {
                  this.imagesColumn2.add(pane);
               }

               ++column;
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }
      }

   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.listViewColumn1.setItems(this.imagesColumn1);
      this.listViewColumn2.setItems(this.imagesColumn2);
      this.listViewColumn1.getStyleClass().add("ListView1_noScrollBar");
      this.listViewColumn2.getStyleClass().add("ListView2");
      this.listViewColumn1.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
         if (t1 != null) {
            Button b = new Button("Transferer");
            b.setId("Boutton" + ((Pane)t1).getId());
            b.setOnAction((event) -> {
               ImageView imgView = (ImageView)((VBox)((Pane)t1).getChildren().get(0)).getChildren().get(0);
               double x = 150.0D;
               double y = 150.0D;
               double width = imgView.getImage().getWidth();
               double height = imgView.getImage().getHeight();
               String path = imgView.getImage().getUrl();
               if (Integer.parseInt(this.album.getPageOnView()[0]) > -1) {
                  this.album.ajouterPhotoInPage(new Photo(x, y, width, height, path, this.album.getPageOnView()[0]));
               } else {
                  this.album.ajouterPhotoInPage(new Photo(x, y, width, height, path, this.album.getPageOnView()[1]));
               }

            });
            if (o != null) {
               Button bu = (Button)((Pane)o).lookup("#Boutton" + ((Pane)o).getId());
               if (bu != null) {
                  ((Pane)o).getChildren().remove(bu);
               }
            }

            b.setLayoutX(((Pane)t1).getLayoutX() + ((Pane)t1).getWidth() / 2.0D - 40.0D);
            b.setLayoutY(((Pane)t1).getLayoutY() + ((Pane)t1).getHeight() / 2.0D + 20.0D);
            ((Pane)t1).getChildren().add(b);
         }

      });
      this.listViewColumn2.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
         if (t1 != null) {
            Button b = new Button("Transferer");
            b.setId("Boutton" + ((Pane)t1).getId());
            b.setOnAction((event) -> {
               ImageView imgView = (ImageView)((VBox)((Pane)t1).getChildren().get(0)).getChildren().get(0);
               double x = 150.0D;
               double y = 150.0D;
               double width = imgView.getImage().getWidth();
               double height = imgView.getImage().getHeight();
               String path = imgView.getImage().getUrl();
               if (Integer.parseInt(this.album.getPageOnView()[0]) > -1) {
                  this.album.ajouterPhotoInPage(new Photo(x, y, width, height, path, this.album.getPageOnView()[0]));
               } else {
                  this.album.ajouterPhotoInPage(new Photo(x, y, width, height, path, this.album.getPageOnView()[1]));
               }

            });
            if (o != null) {
               Button bu = (Button)((Pane)o).lookup("#Boutton" + ((Pane)o).getId());
               if (bu != null) {
                  ((Pane)o).getChildren().remove(bu);
               }
            }

            b.setLayoutX(((Pane)t1).getLayoutX() + ((Pane)t1).getWidth() / 2.0D - 40.0D);
            b.setLayoutY(((Pane)t1).getLayoutY() + ((Pane)t1).getHeight() / 2.0D + 20.0D);
            ((Pane)t1).getChildren().add(b);
         }

      });
      Platform.runLater(new Runnable() {
         public void run() {
            ScrollBar s1 = (ScrollBar)ControllerPanneauPhotos.this.listViewColumn1.lookup(".scroll-bar");
            if (s1 != null) {
               ScrollBar s2 = (ScrollBar)ControllerPanneauPhotos.this.listViewColumn2.lookup(".scroll-bar");
               if (s2 != null) {
                  s1.valueProperty().bindBidirectional(s2.valueProperty());
               }
            }

         }
      });
   }
}
