package album.modeles;

import album.outils.FabriqueIdentifiant;
import java.io.Serializable;

public class Photo implements Serializable {
   private double x;
   private double y;
   private double width;
   private double height;
   private String id;
   private String numeroPage;
   private String url;

   public Photo(String url) {
      this.url = url;
      this.id = FabriqueIdentifiant.getInstance().getIdentifiantPhoto();
   }

   public Photo(double x, double y, double width, double height, String url, String numeroPage) {
      this.id = FabriqueIdentifiant.getInstance().getIdentifiantPhoto();
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.url = url;
      this.numeroPage = numeroPage;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public String getUrl() {
      return this.url;
   }

   public String getId() {
      return this.id;
   }

   public double getWidth() {
      return this.width;
   }

   public double getHeight() {
      return this.height;
   }

   public String getNumeroPage() {
      return this.numeroPage;
   }

   public void setX(double x) {
      this.x = x;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setWidth(double width) {
      this.width = width;
   }

   public void setHeight(double height) {
      this.height = height;
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      s.append("Hauteur : " + this.height + "\nLargeur : " + this.width + "\nX : " + this.x + "\nY : " + this.y + "\nURL : " + this.url + "\nPage : " + this.numeroPage + "\n");
      return s.toString();
   }
}
