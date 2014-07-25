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
using Mapdigit.Gis.Drawing;


//--------------------------------- PACKAGE ------------------------------------

namespace WM6GISEngineTutorial.Drawing
{
    /// <summary>
    /// AbstractGraphicsFactory implementation.
    /// </summary>
    public class NETGraphicsFactory : AbstractGraphicsFactory
    {

        private static readonly NETGraphicsFactory Instance = new NETGraphicsFactory();

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 01FEB2011  James Shen                 	          Initial Creation
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of the <see cref="NETGraphicsFactory"/> class.
        /// </summary>
        private NETGraphicsFactory()
        {

        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 01FEB2011  James Shen                 	          Initial Creation
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the instance.
        /// </summary>
        /// <returns></returns>
        public static NETGraphicsFactory GetInstance()
        {
            return Instance;
        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 01FEB2011  James Shen                 	          Initial Creation
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates the image.
        /// </summary>
        /// <param name="width">the width of the image.</param>
        /// <param name="height">the height of the image.</param>
        /// <returns>an image instance</returns>
        public override IImage CreateImage(int width, int height)
        {
            return NETImage.CreateImage(width, height);
        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 01FEB2011  James Shen                 	          Initial Creation
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Create an image instance from rgb array
        /// </summary>
        /// <param name="rgb">the rgb array.</param>
        /// <param name="width">the width of the image.</param>
        /// <param name="height">the height of the image.</param>
        /// <returns>an image instance</returns>
        public override IImage CreateImage(int[] rgb, int width, int height)
        {
            return NETImage.CreateImage(rgb, width, height);
        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 01FEB2011  James Shen                 	          Initial Creation
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Create an image instance.
        /// </summary>
        /// <param name="bytes">the byte array</param>
        /// <param name="offset">the start position for the image.</param>
        /// <param name="len">he lenght of the image..</param>
        /// <returns>an image instance</returns>
        public override IImage CreateImage(byte[] bytes, int offset, int len)
        {
            return NETImage.CreateImage(bytes, offset, len);
        }
       
    }
}