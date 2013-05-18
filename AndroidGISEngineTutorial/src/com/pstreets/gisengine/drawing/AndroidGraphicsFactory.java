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
package com.pstreets.gisengine.drawing;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.drawing.AbstractGraphicsFactory;
import com.mapdigit.gis.drawing.IImage;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//22JAN2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* Android implementation of the AbstractGraphicsFactory class.
* <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* @version     1.00, 22/01/11
* @author      Guidebee Pty Ltd.
*/
public class AndroidGraphicsFactory extends AbstractGraphicsFactory{

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the graphics factory instance.
     * @return
     */
    public static AndroidGraphicsFactory getInstance(){
        return INSTANCE;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image with given width and height.
     * @param width
     * @param height
     * @return
     */
    public IImage createImage(int width, int height) {
    	System.gc();
        return AndroidImage.createImage(width,height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 10SEP2010  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image from an array.
     * @param rgb
     * @param width
     * @param height
     * @return
     */
    public IImage createImage(int[] rgb, int width, int height) {
    	System.gc();
        return AndroidImage.createImage(rgb,width,height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image with given byte array.
     * @param bytes
     * @param offset
     * @param len
     * @return
     */
    public IImage createImage(byte[] bytes, int offset, int len) {
    	 System.gc();
         return AndroidImage.createImage(bytes,offset,len);
    }

	/**
     * single insntace.
     */
    private final static AndroidGraphicsFactory INSTANCE=new AndroidGraphicsFactory();


    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * private construct.
     */
    private AndroidGraphicsFactory(){

    }
}
