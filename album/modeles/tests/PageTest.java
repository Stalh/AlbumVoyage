package album.modeles.tests;

import album.modeles.Page;
import album.modeles.Photo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PageTest {
   Page page;

   @BeforeEach
   void setUp() {
      this.page = new Page();
   }

   @Test
   void ajouterPhoto() {
      assert this.page.getPhotos().size() == 0 : "Bug quand il y a une photo dans la page";

      Photo p = new Photo("null");
      this.page.ajouterPhoto(p);

      assert this.page.getPhotos().size() == 1 : "Bug quand il y a plus ou moins de 1 photo dans la page";

      Photo p2 = new Photo("null");
      Photo p3 = new Photo("null");
      this.page.ajouterPhoto(p2);
      this.page.ajouterPhoto(p3);

      assert this.page.getPhotos().size() == 3 : "Bug quand il y a plus ou moins de 3 photo dans la page";

   }

   @Test
   void supprimerPhoto() {
      assert this.page.getPhotos().size() == 0 : "Bug quand il y a une photo dans la page";

      Photo p = new Photo("null");
      this.page.ajouterPhoto(p);
      this.page.supprimerPhoto(p);

      assert this.page.getPhotos().size() == 0 : "Bug quand il y a plus ou moins de 1 photo dans la page";

      Photo p2 = new Photo("null");
      Photo p3 = new Photo("null");
      this.page.ajouterPhoto(p2);
      this.page.ajouterPhoto(p3);
      this.page.supprimerPhoto(p);

      assert this.page.getPhotos().size() == 2 : "Bug quand il y a plus ou moins de 2 photo dans la page";

      this.page.supprimerPhoto(p2);
      this.page.supprimerPhoto(p3);
      this.page.supprimerPhoto(p);

      assert this.page.getPhotos().size() == 0 : "Bug quand il y a plus ou moins de 1 photo dans la page";

   }
}
