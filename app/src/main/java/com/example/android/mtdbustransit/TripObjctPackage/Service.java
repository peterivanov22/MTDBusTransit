
package com.example.android.mtdbustransit.TripObjctPackage;

import com.google.gson.annotations.Expose;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Service {

    @Expose
    private Begin begin;
    @Expose
    private End end;
    @Expose
    private Route route;
    @Expose
    private Trip trip;

    /**
     * 
     * @return
     *     The begin
     */
    public Begin getBegin() {
        return begin;
    }

    /**
     * 
     * @param begin
     *     The begin
     */
    public void setBegin(Begin begin) {
        this.begin = begin;
    }

    /**
     * 
     * @return
     *     The end
     */
    public End getEnd() {
        return end;
    }

    /**
     * 
     * @param end
     *     The end
     */
    public void setEnd(End end) {
        this.end = end;
    }

    /**
     * 
     * @return
     *     The route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * 
     * @param route
     *     The route
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * 
     * @return
     *     The trip
     */
    public Trip getTrip() {
        return trip;
    }

    /**
     * 
     * @param trip
     *     The trip
     */
    public void setTrip(Trip trip) {
        this.trip = trip;
    }

}
