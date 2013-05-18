

package com.pstreets.gisengine.demo;

import com.mapdigit.gis.raster.MapTiledZone;
import com.mapdigit.util.Log;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;


public class FileMapTiledZone extends MapTiledZone{

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 14AUG2009  James Shen                 	          Code review
    ////////////////////////////////////////////////////////////////////////////
    /**
     * constructor.
     * @param fileName  file name of the map zone.
     * @param isMarkSupported  does device support mark or not.
     * @throws FileNotFoundException 
     */
    public FileMapTiledZone(String fileName,boolean isMarkSupported) 
     throws FileNotFoundException  {
        super(null);
        this.fileName=fileName;
        this.isMarkSupported=isMarkSupported;
        

    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 14AUG2009  James Shen                 	          Code review
    ////////////////////////////////////////////////////////////////////////////
    /**
     * constructor.
     * @param fileName  file name of the map zone.
     * @throws FileNotFoundException 
     */
    public FileMapTiledZone(String fileName) throws FileNotFoundException  {
        this(fileName,false);
    }

    public void ensureClose()  {
        if (!isMarkSupported) {
        	try {
				super.ensureClose();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
            	dataInputStream.close();
                fileConnection.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

     public DataInputStream skipBytes(long offset) {
        try {
        	fileConnection=new FileInputStream(fileName);
            dataInputStream=new DataInputStream(fileConnection);
            if (offset > 0) {
            	fileConnection.skip(offset);
            }

        } catch (Exception e) {
            Log.p("seek error:" + e.getMessage(), Log.ERROR);
        }
        return dataInputStream;
    }

    private FileInputStream fileConnection=null;
    private DataInputStream dataInputStream;


}
