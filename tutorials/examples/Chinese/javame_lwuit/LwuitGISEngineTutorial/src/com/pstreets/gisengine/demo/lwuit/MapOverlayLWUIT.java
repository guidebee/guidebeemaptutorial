package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.geometry.GeoPoint;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.demo.MapDemoLWUIT;

public class MapOverlayLWUIT extends MapDemoLWUIT {

    OverLayMapLayer mapLayer;
    
    public void startApp() {
        init();
        canvas.setTitle("Map Overlay");
       
        GeoLatLng center = new  GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 9, MapType.MICROSOFTCHINA);
        mapLayer = new OverLayMapLayer(canvas.getWidth(),
                canvas.getHeight());
        map.addMapLayer(mapLayer);
        canvas.show();
    }

    class OverLayMapLayer extends MapLayer {

        GeoLatLng pt1 = new GeoLatLng(32.345281, 118.84261);
        GeoLatLng pt2 = new GeoLatLng(32.05899, 118.62789);
        GeoLatLng pt3 = new GeoLatLng(32.011811, 118.798656);

        public OverLayMapLayer(int width, int height) {
            super(width, height);
        }

        public void paint(IGraphics graphics, int offsetX, int offsetY) {
            drawTriangle(graphics);
            drawPoint(graphics, pt1);
            drawPoint(graphics, pt2);
            drawPoint(graphics, pt3);

        }

        public void drawTriangle(IGraphics g) {
            GeoPoint ptOnScreen1 = map.fromLatLngToScreenPixel(pt1);
            GeoPoint ptOnScreen2 = map.fromLatLngToScreenPixel(pt2);
            GeoPoint ptOnScreen3 = map.fromLatLngToScreenPixel(pt3);
            g.setColor(0x0000FF);

            g.drawLine((int) ptOnScreen1.x, (int) ptOnScreen1.y,
                    (int) ptOnScreen2.x, (int) ptOnScreen2.y);
            g.drawLine((int) ptOnScreen2.x, (int) ptOnScreen2.y,
                    (int) ptOnScreen3.x, (int) ptOnScreen3.y);
            g.drawLine((int) ptOnScreen1.x, (int) ptOnScreen1.y,
                    (int) ptOnScreen3.x, (int) ptOnScreen3.y);
        }

        public void drawPoint(IGraphics g, GeoLatLng pt) {
            GeoPoint ptOnScreen = map.fromLatLngToScreenPixel(pt);
            int x = (int) ptOnScreen.x;
            int y = (int) ptOnScreen.y;
            g.setColor(0x00FF00);
            g.fillRect(x - 4, y - 4, 8, 8);

        }

       
    }
}
