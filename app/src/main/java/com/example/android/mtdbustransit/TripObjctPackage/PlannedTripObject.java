
package com.example.android.mtdbustransit.TripObjctPackage;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class PlannedTripObject {

    @Expose
    private String time;
    @SerializedName("new_changeset")
    @Expose
    private Boolean newChangeset;
    @Expose
    private Status status;
    @Expose
    private Rqst rqst;
    @Expose
    private List<Itinerary> itineraries = new ArrayList<Itinerary>();

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The newChangeset
     */
    public Boolean getNewChangeset() {
        return newChangeset;
    }

    /**
     * 
     * @param newChangeset
     *     The new_changeset
     */
    public void setNewChangeset(Boolean newChangeset) {
        this.newChangeset = newChangeset;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The rqst
     */
    public Rqst getRqst() {
        return rqst;
    }

    /**
     * 
     * @param rqst
     *     The rqst
     */
    public void setRqst(Rqst rqst) {
        this.rqst = rqst;
    }

    /**
     * 
     * @return
     *     The itineraries
     */
    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    /**
     * 
     * @param itineraries
     *     The itineraries
     */
    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

}
