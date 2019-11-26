package com.ss4.opencampus.mainViews.reviewMessage;

/**
 * @author Axel Zumwalt
 *
 * The message class describes the behavior of an message object which is created and added to a list
 * when someone comments on the logged in users USpot.
 */
public class ReviewMessage {

    private int USpotId;
    private String USpotName;
    //Not used currently
    private boolean isRead;

    /**
     * Default Constructor for an OpenCampus ReviewMessage
     */
    public ReviewMessage() {
    }

    /**
     * ReviewMessage constructor
     */
    public ReviewMessage(int USpotId, String USpotName, boolean isRead) {
        this.USpotId = USpotId;
        this.USpotName = USpotName;
        this.isRead = isRead;
    }

    /**
     * Sets the USpot Id for the ReviewMessage
     *
     * @param USpotId The ID for the USpot being reviewed.
     */
    public void setUSpotId(int USpotId) {
        this.USpotId = USpotId;
    }

    /**
     * Gets the USpot ID for the ReviewMessage
     *
     * @return The USpotID of the USpot that was reviewed.
     */
    public int getUSpotId() {
        return this.USpotId;
    }

    /**
     * Set the USpot name for the ReviewMessage
     *
     * @param USpotName Name of the reviewed USpot
     */
    public void setUSpotName(String USpotName) {
        this.USpotName = USpotName;
    }

    /**
     * Get the name of the USpot for the ReviewMessage
     *
     * @return The name of the USpot that was reviewed.
     */
    public String getUSpotName() {
        return this.USpotName;
    }

    /**
     * Set the read state of the ReviewMessage
     *
     * @param isRead True if the message has been read, false otherwise
     */
    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * Returns the read state of the ReviewMessage
     *
     * @return True if message is read, false if otherwise.
     */
    public boolean getIsRead() {
        return this.isRead;
    }
}
