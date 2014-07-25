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
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.system.Bitmap;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 06FEB2011  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * RIM implemeation of the IGraphics interface.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/02/11
 * @author      Guidebee Pty Ltd.
 */
public class RIMGraphics implements IGraphics {

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * constructor.
     * @param graphics
     */
    public RIMGraphics(Graphics graphics) {
        this.graphics = graphics;

    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * set the clip region.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void setClip(int x, int y, int width, int height) {
        
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw image at given location.
     * @param img
     * @param x
     * @param y
     */
    public void drawImage(IImage img, int x, int y) {
        Bitmap bitmap=((RIMImage) img).image;
        graphics.drawBitmap(x,y,bitmap.getWidth(),bitmap.getHeight(),bitmap,0,0);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * set color.
     * @param RGB
     */
    public void setColor(int RGB) {
        graphics.setColor(RGB);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * fill a rectangle.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillRect(int x, int y, int width, int height) {
        graphics.fillRect(x, y, width, height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * set font.
     * @param font
     */
    public void setFont(IFont font) {
       
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw string.
     * @param str
     * @param x
     * @param y
     */
    public void drawString(String str, int x, int y) {
       
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw rectangle.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void drawRect(int x, int y, int width, int height) {
        graphics.drawRect(x, y, width, height);
    }

    /**
     * RIM native graphics object
     */
    private Graphics graphics;

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw a line.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        graphics.drawLine(x1, y1, x2, y2);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 06FEB2011  James Shen                              Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * fill triangle.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        
    }

}
