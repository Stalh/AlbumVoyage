package album.modeles;

import album.outils.FabriqueIdentifiant;
import java.io.Serializable;
import java.util.HashMap;

public class Page implements Serializable {
   private String pageNumber = FabriqueIdentifiant.getInstance().getIdentifiantPage();
   private HashMap<String, Photo> photos = new HashMap(10);

   public String getPageNumber() {
      return this.pageNumber;
   }

   public void ajouterPhoto(Photo photo) {
      this.photos.put(photo.getId(), photo);
   }

   public void supprimerPhoto(Photo photo) {
      this.photos.remove(photo.getId(), photo);
   }

   public HashMap<String, Photo> getPhotos() {
      return this.photos;
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      s.append("Page : " + this.pageNumber + "\n");
      return s.toString();
   }
}
