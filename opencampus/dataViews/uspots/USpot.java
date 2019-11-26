package com.ss4.opencampus.dataViews.uspots;

import android.graphics.BitmapFactory;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author Morgan Smith
 * USpot class to store USpot object data
 * Getter and setter methods for USpot properties
 * Conversion of Rating, Lat, and Long to string type.
 **/

public class USpot implements Serializable {

    public int usID;

    public String usName;

    public Double usRating;

    public Double usLatit;

    public Double usLongit;

    public String usCategory;

    private byte[] picBytes;
    
    /**
     * Constructs a USpot with nothing given
     */
    public USpot() {
    }
    
    /**
     * Constructs a USpot with the given parameters
     * @param usID id of USpot
     * @param usName name of USpot
     * @param usRating overall Rating of USpot
     * @param usLatit latitude of the USpot
     * @param usLongit longitude of the USpot
     * @param usCategory category the USpot is in
     * @param picBytes byte[] for the picture of the USpot
     */
    public USpot(int usID, String usName, Double usRating, Double usLatit, Double usLongit, String usCategory, byte[] picBytes) {
        this.usID = usID;
        this.usName = usName;
        this.usRating = usRating;
        this.usLatit = usLatit;
        this.usLongit = usLongit;
        this.usCategory = usCategory;
        this.picBytes = picBytes;
    }
    
    /**
     * Gets ID of the USpot
     * @return USpot ID
     */
    public int getUsID() { return usID; }
    
    /**
     * Gets the name of the USpot
     * @return USpot name
     */
    public String getUsName() {
        return usName;
    }
    
    /**
     * Gets the rating of the USpot
     * @return USpot rating
     */
    public Double getUsRating() {
        return usRating;
    }
    
    /**
     * Gets the latitude of the USpot
     * @return USpot latitude
     */
    public Double getUsLatit() {
        return usLatit;
    }
    
    /**
     * Gets the longitude of the USpot
     * @return USpot longitude
     */
    public Double getUsLongit() {
        return usLongit;
    }
    
    /**
     * Gets the category of the USpot
     * @return USpot category
     */
    public String getUsCategory() {
        return usCategory;
    }
    
    /**
     * Gets the byte[] of the image of the USpot
     * @return byte[] that represents USpot photo
     */
    public byte[] getPicBytes() {
        return picBytes;
    }

    //Converts Double latitude to string as a getter method
    
    /**
     * Gets the latitude of the USpot and returns it as a String
     * @return String version of latitude
     */
    public String getLatString() {
        return usLatit.toString();
    }

    //Converts Double longitude to string as a getter method
    
    /**
     * Gets the longitude of the USpot and returns it as a String
     * @return String version of longitude
     */
    public String getLongString() {
        return usLongit.toString();
    }
    
    /**
     * Gets the rating of the USpot and returns it as a String
     * @return String version of rating
     */
    public String getRatingString() {
        return usRating.toString();
    }
    
    /**
     * Sets the id of the USpot to a new value
     * @param usID new id for the USpot
     */
    public void setUsID(int usID) {
        this.usID = usID;
    }
    
    /**
     * Sets the name of the USpot to a new value
     * @param usName new name for the USpot
     */
    public void setUsName(String usName) {
        this.usName = usName;
    }
    
    /**
     * Sets the rating of the USpot to a new value
     * @param usRating new rating for the USpot
     */
    public void setUsRating(Double usRating) {
        this.usRating = usRating;
    }
    
    /**
     * Sets the latitude of the USpot to a new value
     * @param usLatit new latitude of the USpot
     */
    public void setUsLatit(Double usLatit) {
        this.usLatit = usLatit;
    }
    
    /**
     * Sets the longitude of the USpot to a new value
     * @param usLongit new longitude of the USpot
     */
    public void setUsLongit(Double usLongit) {
        this.usLongit = usLongit;
    }
    
    /**
     * Sets the category of the USpot to a new value
     * @param uspotCategory new category for the USpot
     */
    public void setUspotCategory(String uspotCategory) {
        this.usCategory = uspotCategory;
    }
    
    /**
     * Sets the byte[] of the USpot to a new value
     * @param picBytes new byte[] for the USpot. So, the USpot has a new picture
     */
    public void setPicBytes(byte[] picBytes) {
        this.picBytes = picBytes;
    }
    
    /**
     * Decodes the byte[] and returns a Bitmap to be used to display
     * the picture of the USpot
     * @return Bitmap of the decoded byte[]
     */
    public Bitmap setBitmap() {
        return BitmapFactory.decodeByteArray(picBytes, 0, picBytes.length);
    }
    
    /**
     * Overrides the .toString() method inherited to display the name and
     * id of the USpot
     * @return String with USpot information
     */
    @Override
    public String toString()
    {
        return "Title: " + usName + "\nID: " + usID;
    }
}

