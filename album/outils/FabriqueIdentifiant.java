package album.outils;

public class FabriqueIdentifiant {
   private int noPage = -1;
   private int noPhoto = -1;
   private static FabriqueIdentifiant INSTANCE = new FabriqueIdentifiant();

   private FabriqueIdentifiant() {
   }

   public static FabriqueIdentifiant getInstance() {
      return INSTANCE;
   }

   public String getIdentifiantPhoto() {
      ++this.noPhoto;
      return this.noPhoto.makeConcatWithConstants<invokedynamic>(this.noPhoto);
   }

   public String getIdentifiantPage() {
      ++this.noPage;
      return this.noPage.makeConcatWithConstants<invokedynamic>(this.noPage);
   }

   public void reset() {
      this.noPage = -1;
      this.noPhoto = -1;
   }
}
