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
package com.pstreets.gisengine.demo.midp.drawing;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.drawing.AbstractGraphicsFactory;
import com.mapdigit.gis.drawing.IFont;
import com.mapdigit.gis.drawing.IImage;

import java.io.IOException;
import javax.microedition.lcdui.Font;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06NOV2009  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * MIDP implementation of the AbstractGraphicsFactory class.
 * <hr><b>&copy; Copyright 2009 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/11/09
 * @author      Guidebee Pty Ltd.
 */
public class MIDPGraphicsFactory extends AbstractGraphicsFactory{



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
    public static MIDPGraphicsFactory getInstance(){
        return INSTANCE;
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
        return MIDPImage.createImage(width,height);
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
        return MIDPImage.createImage(rgb,width,height);
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
         return MIDPImage.createImage(bytes,offset,len);
    }

   
    private final static MIDPGraphicsFactory INSTANCE=new MIDPGraphicsFactory();

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * private construct.
     */
    private MIDPGraphicsFactory(){

    }

}
