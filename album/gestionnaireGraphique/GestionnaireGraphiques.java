package album.gestionnaireGraphique;

import album.modeles.Album;
import java.io.Serializable;
import java.util.HashMap;
import javafx.scene.image.Image;

public class GestionnaireGraphiques implements Serializable {
   Album album;
   private HashMap<String, Image> images;
   private boolean photosPressed;

   public GestionnaireGraphiques(Album album) {
      this.album = album;
      if (album.getPhotos().size() < 1) {
         this.images = new HashMap(20);
      } else {
         this.images = new HashMap(album.getPhotos().size());
      }

      this.photosPressed = true;
   }

   public boolean isPhotosPressed() {
      return this.photosPressed;
   }

   public HashMap<String, Image> getImage() {
      return this.images;
   }

   public Image getImage(String id) {
      return (Image)this.images.get(id);
   }

   public void setPhotosPressed(boolean photosPressed) {
      this.photosPressed = photosPressed;
      this.album.reagir();
   }
}
