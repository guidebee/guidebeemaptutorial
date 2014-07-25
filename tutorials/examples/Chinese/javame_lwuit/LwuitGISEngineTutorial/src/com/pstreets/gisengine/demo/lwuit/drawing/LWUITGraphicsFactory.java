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
import java.io.IOException;

import com.mapdigit.gis.drawing.AbstractGraphicsFactory;
import com.mapdigit.gis.drawing.IFont;
import com.mapdigit.gis.drawing.IImage;
import com.sun.lwuit.Font;
import com.sun.lwuit.Image;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06NOV2009  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Lwuit implementation of the AbstractGraphicsFactory class.
 * <hr><b>&copy; Copyright 2009 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/11/09
 * @author      Guidebee Pty Ltd.
 */
public class LWUITGraphicsFactory extends AbstractGraphicsFactory{

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the graphics factory instance.
     * @return
     */
    public static LWUITGraphicsFactory getInstance(){
        return INSTANCE;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create a image.
     * @param stream
     * @return
     */
    public IImage createImage(Image image) {
        return LWUITImage.createImage(image);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create a image.
     * @param stream
     * @return
     */
    public IImage createImage(java.io.InputStream stream) {
        try {
            return LWUITImage.createImage(stream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image.
     * @param width
     * @param height
     * @return
     */
    public IImage createImage(int width, int height) {
        return LWUITImage.createImage(width,height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image.
     * @param rgb
     * @param width
     * @param height
     * @return
     */
    public IImage createImage(int[] rgb, int width, int height) {
        return LWUITImage.createImage(rgb,width,height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image.
     * @param bytes
     * @param offset
     * @param len
     * @return
     */
    public IImage createImage(byte[] bytes, int offset, int len) {
         return LWUITImage.createImage(bytes,offset,len);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create font.
     * @param nativeFont
     * @return
     */
    public IFont createFont(Object nativeFont) {
        if (nativeFont instanceof Font) {
            LWUITFont lwuitFont = new LWUITFont();
            lwuitFont.font = (Font)nativeFont;
            return lwuitFont;
        }else{
            throw new IllegalArgumentException("Font type is not valid");
        }

    }

    /**
     * single insntace.
     */
    private final static LWUITGraphicsFactory INSTANCE=new LWUITGraphicsFactory();


    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * private construct.
     */
    private LWUITGraphicsFactory(){

    }



}
