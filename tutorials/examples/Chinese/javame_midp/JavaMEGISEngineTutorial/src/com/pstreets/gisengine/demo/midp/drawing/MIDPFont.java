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
import com.mapdigit.gis.drawing.IFont;
import javax.microedition.lcdui.Font;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06NOV2009  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * MIDP implemeation of the IFont interface.
 * <hr><b>&copy; Copyright 2009 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/11/09
 * @author      Guidebee Pty Ltd.
 */
public class MIDPFont implements IFont {

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the navtive font object.
     * @return native font object.
     */
    public Object getNativeFont() {
        return font;
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06NOV2009  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * get the width of the char array.
     * @param ch
     * @param offset
     * @param length
     * @return
     */
    public int charsWidth(char[] ch, int offset, int length) {
        return font.charsWidth(ch, offset, length);
    }
    Font font;
}
