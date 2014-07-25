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
import com.mapdigit.gis.drawing.IFont;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06FEB2011  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * RIM implemeation of the IFont interface.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/02/11
 * @author      Guidebee Pty Ltd.
 */
public class RIMFont implements IFont{

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the navtive font object.
     * @return native font object.
     */
    public Object getNativeFont() {
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the width of the char array.
     * @param ch
     * @param offset
     * @param length
     * @return
     */
    public int charsWidth(char[] ch, int offset, int length) {
        return 0;
    }


}
