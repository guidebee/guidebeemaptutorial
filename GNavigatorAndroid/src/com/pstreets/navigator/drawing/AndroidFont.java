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
import android.graphics.Paint;
import android.graphics.Typeface;

import com.mapdigit.gis.drawing.IFont;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//22JAN2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Android implementation of the IFont interface.Since we dont use vector map 
 * functions,just use an empty implementation.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 22/01/11
 * @author      Guidebee Pty Ltd.
 */
public class AndroidFont implements IFont{

	public AndroidFont(){
    	paint=new Paint();
    }
	
	public int charsWidth(char[] ch, int offset, int length) {
		 return (int)paint.measureText(ch, offset, length);
	}

	public Object getNativeFont() {
		return font;
	}
	
     private final Paint paint;
    
    /**
     * system font object.
     */
     Typeface font;

}
