package album.modeles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Album extends Sujet implements Serializable {
   private HashMap<String, Photo> photos = new HashMap(10);
   private HashMap<String, Page> pages = new HashMap(10);
   private transient IntegerProperty nbPages;
   private Integer nbPagesSerializable;
   private String[] pageOnView = new String[2];

   public Album() {
      this.pageOnView[0] = "0";
      this.pageOnView[1] = "1";
      this.nbPages = new SimpleIntegerProperty(0);
      this.ajouterPage();
   }

   public HashMap<String, Photo> getPhotos() {
      return this.photos;
   }

   public HashMap<String, Page> getPages() {
      return this.pages;
   }

   public Collection<Page> getPage() {
      return this.pages.values();
   }

   public String[] getPageOnView() {
      return this.pageOnView;
   }

   public IntegerProperty getNbPages() {
      return this.nbPages;
   }

   public void incrementer() {
      this.nbPages.setValue(this.nbPages.intValue() + 1);
   }

   public void ajouterPhoto(String url) {
      boolean contains = false;
      Iterator var3 = this.photos.values().iterator();

      while(var3.hasNext()) {
         Photo p = (Photo)var3.next();
         if (p.getUrl().equals(url)) {
            contains = true;
         }
      }

      if (!contains) {
         Photo photo = new Photo(url);
         this.photos.put(photo.getId(), photo);
      }

      this.reagir();
   }

   public void ajouterPhotoInPage(Photo photo) {
      ((Page)this.pages.get(photo.getNumeroPage())).ajouterPhoto(photo);
      this.reagir();
   }

   public void supprimerPhotoInPage(Photo photo) {
      ((Page)this.pages.get(photo.getNumeroPage())).supprimerPhoto(photo);
      this.reagir();
   }

   public void ajouterPage() {
      Page page = new Page();
      this.pages.put(page.getPageNumber(), page);
      this.nextPage();
      String var10001 = this.pageOnView[0];
      this.setPageOnView("Page " + var10001 + " - Page " + this.pageOnView[1]);
      this.incrementer();
      this.reagir();
   }

   public void relocate(String pageNumber, String id, double x, double y) {
      Page page = (Page)this.pages.get(pageNumber);
      ((Photo)page.getPhotos().get(id)).setX(x);
      ((Photo)page.getPhotos().get(id)).setY(y);
   }

   public void nextPage() {
      if (this.pages.values().size() - 1 > Integer.parseInt(this.pageOnView[1])) {
         this.pageOnView[0] = this.pageOnView[1];
         int tmp = Integer.parseInt(this.pageOnView[0]) + 1;
         this.pageOnView[1] = tmp.makeConcatWithConstants<invokedynamic>(tmp);
      }

      this.reagir();
   }

   public void previousPage() {
      if (Integer.parseInt(this.pageOnView[0]) > -1) {
         this.pageOnView[1] = this.pageOnView[0];
         String[] var10000 = this.pageOnView;
         int var10002 = Integer.parseInt(this.pageOnView[0]);
         var10000[0] = (var10002 - 1).makeConcatWithConstants<invokedynamic>(var10002 - 1);
      }

      this.reagir();
   }

   public void setPageOnView(String numeroPage) {
      if (numeroPage != null) {
         if (numeroPage.contains("Tranche")) {
            this.pageOnView[0] = "-1";
            this.pageOnView[1] = "0";
         } else {
            String tmp = numeroPage.substring(numeroPage.indexOf("e ") + 1);
            tmp = tmp.substring(0, tmp.indexOf(" -"));
            tmp = tmp.replaceAll("\\s", "");
            numeroPage = numeroPage.substring(numeroPage.indexOf("- Page ") + 1);
            numeroPage = numeroPage.substring(numeroPage.indexOf("e ") + 1);
            numeroPage = numeroPage.replaceAll("\\s", "");
            String[] var10000 = this.pageOnView;
            int var10002 = Integer.parseInt(tmp);
            var10000[0] = (var10002 - 1).makeConcatWithConstants<invokedynamic>(var10002 - 1);
            var10000 = this.pageOnView;
            var10002 = Integer.parseInt(numeroPage);
            var10000[1] = (var10002 - 1).makeConcatWithConstants<invokedynamic>(var10002 - 1);
         }

         this.reagir();
      }

   }

   public void save(File filename) {
      try {
         ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
         this.nbPagesSerializable = this.nbPages.intValue();
         oos.writeObject(this);
         this.reagir();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public void load(File filename) {
      try {
         ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
         Album a = (Album)ois.readObject();
         this.photos = a.photos;
         this.pages = a.pages;
         this.pageOnView = a.pageOnView;
         this.nbPages.set(a.nbPagesSerializable);
         ArrayList<Photo> photoToRemove = new ArrayList(this.photos.size());
         Iterator var5 = this.photos.values().iterator();

         Photo photo;
         while(var5.hasNext()) {
            photo = (Photo)var5.next();
            File f = new File(photo.getUrl().replaceAll("file:/", ""));
            if (!f.exists()) {
               photoToRemove.add(photo);
            }
         }

         var5 = photoToRemove.iterator();

         while(var5.hasNext()) {
            photo = (Photo)var5.next();
            this.photos.remove(photo.getId(), photo);
         }

         this.reagir();
      } catch (Exception var8) {
         var8.printStackTrace();
      }

   }
}
