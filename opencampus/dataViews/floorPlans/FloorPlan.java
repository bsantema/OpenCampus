package com.ss4.opencampus.dataViews.floorPlans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;

    /**
     * @author Morgan Smith
     * FloorPlan class to store FloorPlan object data
     * Getter and setter methods for FloorPlan properties
     **/
    public class FloorPlan implements Serializable {

        public int floorPlanID;
        public String name;
        public String level;
        public String fpImagePath;
        private byte[] picBytes;


        /**
         * Constructs a FloorPlan with nothing given
         */
        public FloorPlan() {
        }

        /**
         * Constructs a FloorPlan with the given parameters
         * @param floorPlanID ID of FloorPlan
         * @param name name of FloorPlan
         * @param level level of FloorPlan
         * @param fpImagePath fpImagePath of FloorPlan
         * @param picBytes picBytes of FloorPlan
         */
        public FloorPlan(int floorPlanID, String name, String level, String fpImagePath, byte[] picBytes) {
            this.floorPlanID = floorPlanID;
            this.name = name;
            this.level = level;
            this.fpImagePath = fpImagePath;
            this.picBytes = picBytes;
        }

        /**
         * Gets the Details of the FloorPlan
         * @return floorPlanID
         */
        public int getFloorPlanIDAsInt() {
            return floorPlanID;
        }

        /**
         * Sets the Details of the FloorPlan to a new value
         * @param floorPlanID new id for the FloorPlan
         */
        public void setFloorPlanID(int floorPlanID) {
            this.floorPlanID = floorPlanID;
        }

        /**
         * Gets the Details of the FloorPlan
         * @return floorPlanIDAsString
         */
        public String getFloorPlanID() {
            String floorPlanIDAsString = "" + floorPlanID;
            return floorPlanIDAsString;
        }

        /**
         * Gets the Name of the FloorPlan
         * @return floorPlanName
         */
        public String getFloorPlanName() {
            return name;
        }

        /**
         * Sets the Details of the FloorPlan to a new value
         * @param name new id for the FloorPlan
         */
        public void setFloorPlanName(String name) {
            this.name = name;
        }

        /**
         * Gets the Level of the FloorPlan
         * @return level
         */
        public String getFloorPlanLevel() {
            return level;
        }

        /**
         * Sets the Level of the FloorPlan to a new value
         * @param level new id for the FloorPlan
         */
        public void setFloorPlanLevel(String level) {
            this.level = level;
        }

        /**
         * Gets the ImagePath of the FloorPlan
         * @return fpImagePath
         */
        public String getFloorPlanImagePath() {
            return fpImagePath;
        }

        /**
         * Sets the Level of the FloorPlan to a new value
         * @param fpImagePath new id for the FloorPlan
         */
        public void setFloorPlanImagePath(String fpImagePath) {
            this.fpImagePath = fpImagePath;
        }

        /**
         * Gets the PicBytes of the FloorPlan
         * @return picBytes
         */
        public byte[] getFloorPlanPicBytes() {
            return picBytes;
        }

        /**
         * Sets the picBytes of the FloorPlan to a new value
         * @param picBytes new id for the FloorPlan
         */
        public void setFloorPlanPicBytes(byte[] picBytes) {
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
}
