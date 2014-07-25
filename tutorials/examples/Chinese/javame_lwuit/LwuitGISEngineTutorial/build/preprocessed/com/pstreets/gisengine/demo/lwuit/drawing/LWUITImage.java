//------------------------------------------------------------------------------
//                         COPYRIGHT 2009 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 06NOV2009  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.lwuit.drawing;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import com.sun.lwuit.Image;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06NOV2009  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Lwuit implemeation of the IImage interface.
 * <hr><b>&copy; Copyright 2009 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/11/09
 * @author      Guidebee Pty Ltd.
 */
class LWUITImage implements IImage {

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from a native image.
     * @param stream
     * @return
     * @throws java.io.IOException
     */
    public static IImage createImage(Image image)
            {
        LWUITImage lwuitImage = new LWUITImage();
        lwuitImage.image = image;
        return lwuitImage;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from input stream.
     * @param stream
     * @return
     * @throws java.io.IOException
     */
    public static IImage createImage(java.io.InputStream stream)
            throws java.io.IOException {
        LWUITImage lwuitImage = new LWUITImage();
        lwuitImage.image = Image.createImage(stream);
        return lwuitImage;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from rgb array.
     * @param rgb
     * @param width
     * @param height
     * @return
     */
    public static IImage createImage(int[] rgb, int width, int height) {
        LWUITImage lwuitImage = new LWUITImage();
        lwuitImage.image = Image.createImage(rgb, width, height);
        return lwuitImage;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create a empty image with given width and height.
     * @param width
     * @param height
     * @return
     */
    public static IImage createImage(int width,int height) {
        LWUITImage lwuitImage = new LWUITImage();
        lwuitImage.image = Image.createImage(width, height);
        return lwuitImage;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from byte array.
     * @param bytes
     * @param offset
     * @param len
     * @return
     */
    public static IImage createImage(byte[] bytes,int offset,int len) {
        LWUITImage lwuitImage = new LWUITImage();
        lwuitImage.image = Image.createImage(bytes, offset, len);
        return lwuitImage;

    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get graphics object of the image.
     * @return
     */
    public IGraphics getGraphics() {
        LWUITGraphics lwuitGraphics = new LWUITGraphics(image.getGraphics());
        return lwuitGraphics;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the rgb array of the image.
     * @return
     */
    public int[] getRGB() {
        return image.getRGB();
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
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
    // 06NOV2009  James Shen                 	          Initial Creation
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
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get native image object.
     * @return
     */
    public Object getNativeImage() {
        return image;
    }

    /**
     * Lwuit Image object.
     */
    Image image = null;

}
