//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 06FEB2011  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim.drawing;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.DataInputStream;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06FEB2011  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * RIM implemeation of the IImage interface.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/02/11
 * @author      Guidebee Pty Ltd.
 */
class RIMImage implements IImage {

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from rgb array.
     * @param rgb
     * @param width
     * @param height
     * @return
     */
    public static IImage createImage(int[] rgb, int width, int height) {
        RIMImage RIMImage = new RIMImage();
        RIMImage.image = new Bitmap( width, height);
        RIMImage.image.setARGB(rgb,0,width,0,0,width,height);
        return RIMImage;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create a empty image with given width and height.
     * @param width
     * @param height
     * @return
     */
    public static IImage createImage(int width,int height) {
        RIMImage RIMImage = new RIMImage();
        RIMImage.image = new Bitmap( width, height);
        return RIMImage;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from byte array.
     * @param bytes
     * @param offset
     * @param len
     * @return
     */
    public static IImage createImage(byte[] bytes,int offset,int len) {
        RIMImage RIMImage = new RIMImage();
        RIMImage.image = Bitmap.createBitmapFromBytes(bytes, offset, len,1);
        return RIMImage;

    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get graphics object of the image.
     * @return
     */
    public IGraphics getGraphics() {
        RIMGraphics RIMGraphics = new RIMGraphics(Graphics.create(image));
        return RIMGraphics;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the rgb array of the image.
     * @return
     */
    public int[] getRGB() {
       return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                              Initial Creation
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
    // 06NOV2009  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get width of the image.
     * @return
     */
    public int getWidth() {
        return image.getWidth();
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get native image object.
     * @return
     */
    public Object getNativeImage() {
        return image;
    }

    /**
     * RIM Image object.
     */
    Bitmap image = null;

}
