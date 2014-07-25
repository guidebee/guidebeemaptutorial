//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY 
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 01FEB2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- IMPORTS ------------------------------------
using System;
using Mapdigit.Gis.Drawing;

//--------------------------------- PACKAGE ------------------------------------

namespace WM6GISEngineTutorial.Drawing
{
    /// <summary>
    /// IFont implementation.
    /// </summary>
    public class NETFont : IFont
    {

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 01FEB2011  James Shen                 	          Initial Creation
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Get the navtive font object.
        /// </summary>
        /// <returns>
        /// the nactive font associated with the IFont object
        /// </returns>
        public Object GetNativeFont()
        {
            return null;
        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 01FEB2011  James Shen                 	          Initial Creation
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// calculate the chars width with this font.
        /// </summary>
        /// <param name="ch">the char array.</param>
        /// <param name="offset">the start index of the char array.</param>
        /// <param name="length">the length of the chars.</param>
        /// <returns>the width of the char string</returns>
        public int CharsWidth(char[] ch, int offset, int length)
        {
            return 0;

        }

    }
}