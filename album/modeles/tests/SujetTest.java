package album.modeles.tests;

import album.controllers.Controller;
import album.controllers.ControllerModeles;
import album.modeles.Album;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SujetTest {
   Album album;
   ControllerModeles cm;
   ControllerModeles cm2;
   ControllerModeles cm3;

   @BeforeEach
   void setUp() {
      this.album = new Album();
      this.cm = new ControllerModeles();
      this.cm2 = new ControllerModeles();
      this.cm3 = new ControllerModeles();
   }

   @Test
   void ajouterControllers() {
      this.album.ajouterControllers((Controller)null);

      assert this.album.getControllers().size() == 1 : "Bug quand il n'y a pas de controllers ou plus de 1 controllers dans le modèles";

      this.album.ajouterControllers(this.cm);

      assert this.album.getControllers().size() == 2 : "Bug quand il y a moins de 2 de controllers ou plus de 2 controllers dans le modèles";

      this.album.ajouterControllers(this.cm2);
      this.album.ajouterControllers(this.cm3);

      assert this.album.getControllers().size() == 4 : "Bug quand il y a moins de 3 de controllers ou plus de 3 controllers dans le modèles";

   }
}
