
package com.example.android.mtdbustransit.TripObjctPackage;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Params {

    @SerializedName("destination_stop_id")
    @Expose
    private String destinationStopId;
    @SerializedName("origin_stop_id")
    @Expose
    private String originStopId;

    /**
     * 
     * @return
     *     The destinationStopId
     */
    public String getDestinationStopId() {
        return destinationStopId;
    }

    /**
     * 
     * @param destinationStopId
     *     The destination_stop_id
     */
    public void setDestinationStopId(String destinationStopId) {
        this.destinationStopId = destinationStopId;
    }

    /**
     * 
     * @return
     *     The originStopId
     */
    public String getOriginStopId() {
        return originStopId;
    }

    /**
     * 
     * @param originStopId
     *     The origin_stop_id
     */
    public void setOriginStopId(String originStopId) {
        this.originStopId = originStopId;
    }

}
