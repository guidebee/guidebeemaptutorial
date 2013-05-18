//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 22JAN2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.drawing;
 
//--------------------------------- IMPORTS ------------------------------------
import java.nio.IntBuffer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;

import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//22JAN2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* android implementation of the IImage interface.
* <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* @version     1.00, 22/01/11
* @author      Guidebee Pty Ltd.
*/
public class AndroidImage implements IImage{
	
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from rgb array.
     * @param rgb
     * @param width
     * @param height
     * @return
     */
    public static IImage createImage(int[] rgb, int width, int height) {
    	try{
        AndroidImage AndroidImage = new AndroidImage();
        AndroidImage.image =  Bitmap.createBitmap(rgb, width, height, 
        		Bitmap.Config.ARGB_4444); 
        return AndroidImage;
    	}catch(OutOfMemoryError ome){
    		System.gc();
    		return null;
    	}
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create a empty image with given width and height.
     * @param width
     * @param height
     * @return
     */
    public static IImage createImage(int width,int height) {
    	try{
        AndroidImage androidImage = new AndroidImage();
        androidImage.image =Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        return androidImage;
    	}catch(OutOfMemoryError ome){
    		System.gc();
    		return null;
    	}
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from byte array.
     * @param bytes
     * @param offset
     * @param len
     * @return
     */
    public static IImage createImage(byte[] bytes,int offset,int len) {
    	try{
    	mOptions.inPurgeable = true;
        AndroidImage AndroidImage = new AndroidImage();
        AndroidImage.image = BitmapFactory.decodeByteArray (bytes,offset,len,mOptions);
        return AndroidImage;}catch(OutOfMemoryError ome){
    		System.gc();
    		return null;
    	}
    	

    }

    

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get graphics object of the image.
     * @return
     */
    public IGraphics getGraphics() {
    	if(androidGraphics==null){
    		Canvas canvas=new Canvas(image);
    		androidGraphics=new AndroidGraphics(canvas);
    	}
    	return androidGraphics;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the height of the image.
     * @return
     */
    public int getHeight() {
        return image.getHeight();
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get native image object.
     * @return
     */
    public Object getNativeImage() {
        return image;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the rgb array of the image.Only need for vector map.
     * @return
     */
	public int[] getRGB() {
		IntBuffer buffer=IntBuffer.allocate(image.getWidth()*image.getHeight());
    	image.copyPixelsToBuffer(buffer);
        return buffer.array();
	}

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get width of the image.
     * @return
     */
    public int getWidth() {
        return image.getWidth();
    }

	AndroidGraphics androidGraphics=null;
	/**
     * android Image object.
     */
    Bitmap image = null;
    
    private static  Options mOptions = new Options();
    
    
}
