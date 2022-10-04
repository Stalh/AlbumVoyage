package album.outils;

import album.controllers.ControllerPhoto;
import album.modeles.Photo;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class DragResizer {
   private static final int RESIZE_MARGIN = 5;
   private final Region region;
   private ControllerPhoto cp;
   private Photo photo;
   private double y;
   private double x;
   private boolean initMinHeight;
   private boolean initMinWidth;
   private boolean draggableZoneX;
   private boolean draggableZoneY;
   private boolean dragging;

   public DragResizer(Region aRegion, ControllerPhoto cp, Photo photo) {
      this.region = aRegion;
      this.cp = cp;
      this.photo = photo;
   }

   private boolean isInDraggableZone(MouseEvent event) {
      this.draggableZoneY = event.getY() > this.region.getHeight() - 5.0D;
      this.draggableZoneX = event.getX() > this.region.getWidth() - 5.0D;
      return this.draggableZoneY || this.draggableZoneX;
   }

   public static void makeResizable(Region region, ControllerPhoto cp, Photo photo) {
      DragResizer resizer = new DragResizer(region, cp, photo);
      region.setOnMousePressed((event) -> {
         resizer.mousePressed(event);
      });
      region.setOnMouseDragged((event) -> {
         resizer.mouseDragged(event);
      });
      region.setOnMouseMoved((event) -> {
         resizer.mouseOver(event);
      });
      region.setOnMouseReleased((event) -> {
         resizer.mouseReleased(event);
      });
   }

   private void mouseReleased(MouseEvent event) {
      this.dragging = false;
      this.region.setCursor(Cursor.DEFAULT);
      this.cp.setDragged(false);
   }

   private void mouseOver(MouseEvent event) {
      if (!this.isInDraggableZone(event) && !this.dragging) {
         this.region.setCursor(Cursor.DEFAULT);
      } else if (this.draggableZoneX && this.draggableZoneY) {
         this.region.setCursor(Cursor.NW_RESIZE);
      } else if (this.draggableZoneY) {
         this.region.setCursor(Cursor.S_RESIZE);
      } else if (this.draggableZoneX) {
         this.region.setCursor(Cursor.E_RESIZE);
      }

   }

   private void mouseDragged(MouseEvent event) {
      if (this.dragging) {
         double mousex;
         double newWidth;
         if (this.draggableZoneY) {
            mousex = event.getY();
            newWidth = this.region.getMinHeight() + (mousex - this.y);
            if (newWidth > 50.0D) {
               this.region.setMinHeight(newWidth);
               this.cp.getImageView().setFitHeight(newWidth);
               this.photo.setHeight(newWidth);
               this.y = mousex;
            }
         }

         if (this.draggableZoneX) {
            mousex = event.getX();
            newWidth = this.region.getMinWidth() + (mousex - this.x);
            if (newWidth > 50.0D) {
               this.region.setMinWidth(newWidth);
               this.cp.getImageView().setFitWidth(newWidth);
               this.photo.setWidth(newWidth);
               this.x = mousex;
            }
         }

      }
   }

   private void mousePressed(MouseEvent event) {
      if (this.isInDraggableZone(event)) {
         this.dragging = true;
         this.cp.setDragged(true);
         if (!this.initMinHeight) {
            this.region.setMinHeight(this.region.getHeight());
            this.initMinHeight = true;
         }

         this.y = event.getY();
         if (!this.initMinWidth) {
            this.region.setMinWidth(this.region.getWidth());
            this.initMinWidth = true;
         }

         this.x = event.getX();
      }
   }
}
