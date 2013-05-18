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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.Typeface;

import com.mapdigit.gis.drawing.IFont;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//22JAN2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* Android implemeation of the IGraphics interface.
* <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* @version     1.00, 22/01/11
* @author      Guidebee Pty Ltd.
*/
public class AndroidGraphics implements IGraphics{

	
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * constructor.
     * @param graphics
     */
    public AndroidGraphics(Canvas canvas) {
        graphics = canvas;
        currentPaint=new Paint();

    }
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw image at given location.
     * @param img
     * @param x
     * @param y
     */
    public void drawImage(IImage img, int x, int y) {
        graphics.drawBitmap(((AndroidImage) img).image, x, y,null);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw a line.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
    	currentPaint.setStyle(Paint.Style.STROKE);
        graphics.drawLine(x1, y1, x2, y2, currentPaint);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw rectangle.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void drawRect(int x, int y, int width, int height) {
    	currentPaint.setStyle(Paint.Style.STROKE);
        graphics.drawRect(x, y, x+width, y+height, currentPaint);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * draw string. only need for vector map.
     * @param str
     * @param x
     * @param y
     */
	public void drawString(String str, int x, int y) {
		 graphics.drawText(str, x, y, currentPaint);
	}

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * fill a rectangle.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void fillRect(int x, int y, int width, int height) {
    	currentPaint.setStyle(Paint.Style.FILL );
        graphics.drawRect(x, y, x+width, y+height, currentPaint);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * fill triangle. Only need for vector map.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param x3
     * @param y3
     */
	public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
		Path path=new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        path.lineTo(x3, y3);
        path.lineTo(x1, y1);
        currentPaint.setStyle(Paint.Style.FILL);
        graphics.drawPath(path, currentPaint);
	}

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * set the clip region.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public void setClip(int x, int y, int width, int height) {
    	graphics.clipRect(0,0,graphics.getWidth(),graphics.getHeight(),Region.Op.UNION );
        graphics.clipRect(x, y, x+width, y+height);
    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * set color.
     * @param RGB
     */
    public void setColor(int RGB) {
    	currentPaint.setColor(RGB);
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 22JAN2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * set font.only need for vector map.
     * @param font
     */
	public void setFont(IFont font) {
		currentPaint.setTypeface((Typeface)font.getNativeFont());
	}
	
	public Canvas getNativeCanvas(){
		return graphics;
	}
	
	/**
	 * internal native graphics object.
	 */
    private Canvas graphics;
    
    /**
	 * internal native graphics object.
	 */
    private Paint currentPaint;

}
