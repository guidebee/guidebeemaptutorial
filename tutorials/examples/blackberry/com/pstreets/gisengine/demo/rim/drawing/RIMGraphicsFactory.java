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
import java.io.IOException;

import com.mapdigit.gis.drawing.AbstractGraphicsFactory;
import com.mapdigit.gis.drawing.IFont;
import com.mapdigit.gis.drawing.IImage;
import net.rim.device.api.ui.Font;
import net.rim.device.api.system.Bitmap;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06FEB2011  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * RIM implementation of the AbstractGraphicsFactory class.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/02/11
 * @author      Guidebee Pty Ltd.
 */
public class RIMGraphicsFactory extends AbstractGraphicsFactory{

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the graphics factory instance.
     * @return
     */
    public static RIMGraphicsFactory getInstance(){
        return INSTANCE;
    }
  
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image.
     * @param width
     * @param height
     * @return
     */
    public IImage createImage(int width, int height) {
        return RIMImage.createImage(width,height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image.
     * @param rgb
     * @param width
     * @param height
     * @return
     */
    public IImage createImage(int[] rgb, int width, int height) {
        return RIMImage.createImage(rgb,width,height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * create image.
     * @param bytes
     * @param offset
     * @param len
     * @return
     */
    public IImage createImage(byte[] bytes, int offset, int len) {
         return RIMImage.createImage(bytes,offset,len);
    }

    /**
     * single insntace.
     */
    private final static RIMGraphicsFactory INSTANCE=new RIMGraphicsFactory();


    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * private construct.
     */
    private RIMGraphicsFactory(){

    }



}
