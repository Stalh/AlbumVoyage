package album.modeles;

import album.controllers.Controller;
import java.util.ArrayList;
import java.util.Iterator;

public class Sujet {
   protected ArrayList<Controller> controllers = new ArrayList(10);

   public ArrayList<Controller> getControllers() {
      return this.controllers;
   }

   public void ajouterControllers(Controller controller) {
      this.controllers.add(controller);
   }

   public void reagir() {
      Iterator var1 = this.controllers.iterator();

      while(var1.hasNext()) {
         Controller c = (Controller)var1.next();
         c.mettreAJour();
      }

   }
}
