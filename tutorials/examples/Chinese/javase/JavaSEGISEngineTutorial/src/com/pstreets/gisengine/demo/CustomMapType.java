/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pstreets.gisengine.demo;

import com.mapdigit.gis.raster.ICustomMapType;
import com.mapdigit.gis.raster.MapType;

/**
 *
 * @author james
 */
public class CustomMapType implements ICustomMapType {

    private static int serverIndex=1;

    public String getTileURL(int mtype, int x, int y, int zoomLevel) {
        String returnURL="";
        serverIndex+=1;
        serverIndex%=3;
        int maxTiles=(int)Math.pow(2, zoomLevel);
        switch(mtype){
            case MapType.GENERIC_MAPTYPE_5:
                returnURL= "http://p" + serverIndex+".map.qq.com/maptiles/" ;
                   y=maxTiles-y-1;
                   returnURL+=+zoomLevel+"/"+(int)(x/16)+"/"+(int)(y/16)+"/"+x+"_"+y+".gif";
                break;
            
        }
        return returnURL;
    }

}
