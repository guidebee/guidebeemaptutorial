﻿//------------------------------------------------------------------------------
//                         COPYRIGHT 2010 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY 
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 20SEP2010  James Shen                 	          Code review
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- IMPORTS ------------------------------------
using System;
using System.Drawing;
using System.IO;
using Mapdigit.Gis.Drawing;

//--------------------------------- PACKAGE ------------------------------------

namespace GTKMapDemo.Drawing
{
    /// <summary>
    /// AbstractGraphicsFactory implementation.
    /// </summary>
    public class GTKGraphicsFactory : AbstractGraphicsFactory
    {

        private static readonly GTKGraphicsFactory Instance = new GTKGraphicsFactory();

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 20SEP2010  James Shen                 	          Code review
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Initializes a new instance of the <see cref="NETGraphicsFactory"/> class.
        /// </summary>
        private GTKGraphicsFactory()
        {

        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 20SEP2010  James Shen                 	          Code review
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the instance.
        /// </summary>
        /// <returns></returns>
        public static GTKGraphicsFactory GetInstance()
        {
            return Instance;
        }

       



        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 20SEP2010  James Shen                 	          Code review
        ////////////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates the image.
        /// </summary>
        /// <param name="width">the width of the image.</param>
        /// <param name="height">the height of the image.</param>
        /// <returns>an image instance</returns>
        public override IImage CreateImage(int width, int height)
        {
            return GTKImage.CreateImage(width, height);
        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 20SEP2010  James Shen                 	          Code review
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
            return GTKImage.CreateImage(rgb, width, height);
        }

        ////////////////////////////////////////////////////////////////////////////
        //--------------------------------- REVISIONS ------------------------------
        // Date       Name                 Tracking #         Description
        // ---------  -------------------  -------------      ----------------------
        // 20SEP2010  James Shen                 	          Code review
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
            return GTKImage.CreateImage(bytes, offset, len);
        }

        
    }
}