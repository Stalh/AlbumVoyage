package album.modeles.tests;

import album.modeles.Album;
import album.modeles.Page;
import album.modeles.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AlbumTest {
   Album album;
   Photo p;
   Photo p2;
   Photo p3;

   @BeforeEach
   void setUp() {
      this.album = new Album();
      this.p = new Photo("null");
      this.p2 = new Photo("test1");
      this.p3 = new Photo("test2");
   }

   @Test
   void incrementer() {
      assert this.album.getNbPages().intValue() == 1 : "Bug quand le compteur n'est pas a 1";

      this.album.incrementer();

      assert this.album.getNbPages().intValue() == 2 : "Bug quand le compteur n'est pas a 2";

      this.album.incrementer();
      this.album.incrementer();
      this.album.incrementer();

      assert this.album.getNbPages().intValue() == 5 : "Bug quand le compteur n'est pas a 5";

   }

   @Test
   void ajouterPhoto() {
      assert this.album.getPhotos().size() == 0 : "Bug quand il y a des photos dans le modèle";

      this.album.ajouterPhoto(this.p.getUrl());

      assert this.album.getPhotos().size() == 1 : "Bug quand il y a plus ou moins de 1 photo dans le modele";

      this.album.ajouterPhoto(this.p2.getUrl());
      this.album.ajouterPhoto(this.p3.getUrl());

      assert this.album.getPhotos().size() == 3 : "Bug quand il y a plus ou moins de 3 photo dans le modele";

      this.album.ajouterPhoto(this.p.getUrl());
      this.album.ajouterPhoto(this.p2.getUrl());
      this.album.ajouterPhoto(this.p3.getUrl());

      assert this.album.getPhotos().size() == 3 : "Bug quand il y a des photos en doubles dans le modele";

   }

   @Test
   void ajouterPhotoInPage() {
      Photo photo = new Photo(0.0D, 0.0D, 0.0D, 0.0D, (String)null, "0");
      Photo photo2 = new Photo(0.0D, 0.0D, 0.0D, 0.0D, (String)null, "0");
      Photo photo3 = new Photo(0.0D, 0.0D, 0.0D, 0.0D, (String)null, "1");
      this.album.ajouterPage();
      this.album.ajouterPage();
      this.album.ajouterPage();

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page2";

      this.album.ajouterPhotoInPage(photo);

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 1 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page2";

      this.album.ajouterPhotoInPage(photo2);
      this.album.ajouterPhotoInPage(photo3);

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 2 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 1 : "Bug quand il y a des photos dans la page2";

      this.album.ajouterPhotoInPage(photo);
      this.album.ajouterPhotoInPage(photo2);
      this.album.ajouterPhotoInPage(photo3);

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 2 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 1 : "Bug quand il y a des photos dans la page2";

   }

   @Test
   void supprimerPhotoInPage() {
      Photo photo = new Photo(0.0D, 0.0D, 0.0D, 0.0D, (String)null, "0");
      Photo photo2 = new Photo(0.0D, 0.0D, 0.0D, 0.0D, (String)null, "0");
      Photo photo3 = new Photo(0.0D, 0.0D, 0.0D, 0.0D, (String)null, "1");
      this.album.ajouterPage();
      this.album.ajouterPage();
      this.album.ajouterPage();

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page2";

      this.album.ajouterPhotoInPage(photo);
      this.album.supprimerPhotoInPage(photo);

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page2";

      this.album.ajouterPhotoInPage(photo2);
      this.album.ajouterPhotoInPage(photo3);
      this.album.supprimerPhotoInPage(photo2);

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 1 : "Bug quand il y a des photos dans la page2";

      this.album.ajouterPhotoInPage(photo);
      this.album.ajouterPhotoInPage(photo2);
      this.album.ajouterPhotoInPage(photo3);
      this.album.supprimerPhotoInPage(photo3);

      assert ((Page)this.album.getPages().get("0")).getPhotos().size() == 2 : "Bug quand il y a des photos dans la page";

      assert ((Page)this.album.getPages().get("1")).getPhotos().size() == 0 : "Bug quand il y a des photos dans la page2";

   }

   @Test
   void ajouterPage() {
      assert this.album.getPage().size() == 1 : "bug quand le modele possede une page";

      this.album.ajouterPage();

      assert this.album.getPage().size() == 2 : "bug quand le modele possede plus ou moins de 1 page";

   }

   @Test
   void nextPage() {
      assert this.album.getPageOnView()[0].equals("-1") && this.album.getPageOnView()[1].equals("0") : "Bug quand la tranche et page 1 ne sont pas les pages selectionné";

      this.album.nextPage();

      assert this.album.getPageOnView()[0].equals("-1") && this.album.getPageOnView()[1].equals("0") : "Bug quand la tranche et page 1 ne sont pas les pages selectionné";

      this.album.ajouterPage();
      this.album.nextPage();

      assert this.album.getPageOnView()[0].equals("0") && this.album.getPageOnView()[1].equals("1") : "Bug quand la page 1 et la page 2 ne sont pas les pages selectionné";

      this.album.ajouterPage();
      this.album.ajouterPage();
      this.album.nextPage();
      this.album.nextPage();
      this.album.nextPage();

      assert this.album.getPageOnView()[0].equals("2") && this.album.getPageOnView()[1].equals("3") : "Bug quand la page 2 et la page 3 ne sont pas les pages selectionné";
   }

   @Test
   void previousPage() {
      assert this.album.getPageOnView()[0].equals("-1") && this.album.getPageOnView()[1].equals("0") : "Bug quand la tranche et page 1 ne sont pas les pages selectionné";

      this.album.previousPage();

      assert this.album.getPageOnView()[0].equals("-1") && this.album.getPageOnView()[1].equals("0") : "Bug quand la tranche et page 1 ne sont pas les pages selectionné";

      this.album.ajouterPage();
      this.album.nextPage();
      this.album.previousPage();

      assert this.album.getPageOnView()[0].equals("-1") && this.album.getPageOnView()[1].equals("0") : "Bug quand la tranche et page 1 ne sont pas les pages selectionné";

      this.album.ajouterPage();
      this.album.ajouterPage();
      this.album.nextPage();
      this.album.nextPage();
      this.album.previousPage();

      assert this.album.getPageOnView()[0].equals("0") && this.album.getPageOnView()[1].equals("1") : "Bug quand la page 2 et la page 3 ne sont pas les pages selectionné";
   }
}
