package com.ss4.opencampus.dataViews.buildings;

/**
 * @author Morgan Smith
 * Building class to store building object data
 * Getter and setter methods for building properties
 * Conversion of Lat and Long to string type.
 **/
 
public class Building {

    public String buildingID;

    public String buildingName;

    public String abbrev;

    public String address;

    public Double latitude;

    public Double longitude;
    
    /**
     * Constructs a Building with nothing given
     */
    public Building() {
    }
    
    /**
     * Constructs a Building with the given parameters
     * @param buildingName name of the Building
     * @param abbrev abbreviation for the Building
     * @param address address of the Building
     * @param latitude latitude of the Building
     * @param longitude longitude of the Building
     */
    public Building(String buildingID, String buildingName, String abbrev, String address, Double latitude, Double longitude) {
        this.buildingID = buildingID;
        this.buildingName = buildingName;
        this.abbrev = abbrev;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Gets the ID of the Building
     * @return Building ID
     */
    public String getBuildingID() {
        return buildingID;
    }

    /**
     * Gets the name of the Building
     * @return Building name
     */
    public String getBuildingName() {
        return buildingName;
    }
    
    /**
     * Gets the abbreviation of the Building
     * @return Building abbreviation
     */
    public String getAbbrev() {
        return abbrev;
    }
    
    /**
     * Gets the address of the Building
     * @return Building address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * Gets the latitude of the Building
     * @return Building latitude
     */
    public Double getLatitude() {
        return latitude;
    }
    
    /**
     * Gets the longitude of the Building
     * @return Building longitude
     */
    public Double getLongitude() {
        return longitude;
    }

    //Converts Double latitude to string as a getter method
    
    /**
     * Gets the latitude of the Building and returns it as a String
     * @return String version of latitude
     */
    public String getLatString() {
        return latitude.toString();
    }

    //Converts Double longitude to string as a getter method
    /**
     * Gets the longitude of the Building and returns it as a String
     * @return String version of longitude
     */
    public String getLongString() {
        return longitude.toString();
    }

    /**
     * Sets the name of the Building to a new value
     * @param buildingID new name for the Building
     */
    public void setBuildingID(String buildingID) {
        this.buildingID = buildingID;
    }

    /**
     * Sets the name of the Building to a new value
     * @param buildingName new name for the Building
     */
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    
    /**
     * Sets the abbreviation of the Building to a new value
     * @param abbrev new abbreviation for the Building
     */
    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }
    
    /**
     * Sets the address of the Building to a new value
     * @param address new address for the Building
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Sets the latitude of the Building to a new value
     * @param latitude new latitude for the Building
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    
    /**
     * Sets the longitude of the Building to a new value
     * @param longitude new longitude for the Building
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
