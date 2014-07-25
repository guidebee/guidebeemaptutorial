/*
 * Copyright © 2009 Guidebee, Inc. All rights reserved.
 * Use is subject to license terms.
 */


package com.pstreets.gisengine.demo.midp.drawing;

import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import javax.microedition.lcdui.Image;

/**
 *
 * @author james
 */
public class MIDPImage implements IImage{
    Image image=null;

    

    public static IImage createImage(int[] rgb, int width, int height) {
        MIDPImage midpImage=new MIDPImage();
        midpImage.image=Image.createRGBImage(rgb,width,height,true);
        return midpImage;
    }

    public static IImage createImage(int width,
                                int height){
        MIDPImage midpImage=new MIDPImage();
        midpImage.image=Image.createImage(width,height);
        return midpImage;
    }

    public static IImage createImage(byte[] bytes,
                                int offset,
                                int len){
        MIDPImage midpImage=new MIDPImage();
        midpImage.image=Image.createImage(bytes,offset,len);
        return midpImage;

    }

   
    public IGraphics getGraphics() {
       MIDPGraphics midpGraphics=new MIDPGraphics(image.getGraphics());
        return midpGraphics;
    }

    public int[] getRGB() {
        int []rgbArray=new int[image.getWidth()*image.getHeight()];
        image.getRGB(rgbArray, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
        return rgbArray;
    }

    

    public int getHeight() {
        return image.getHeight();
    }

    public int getWidth() {
        return image.getWidth();
    }

    public Object getNativeImage() {
        return image;
    }
}
