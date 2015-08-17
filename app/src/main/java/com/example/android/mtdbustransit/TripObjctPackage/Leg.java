
package com.example.android.mtdbustransit.TripObjctPackage;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("org.jsonschema2pojo")
public class Leg {

    @Expose
    private List<Service> services = new ArrayList<Service>();
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * 
     * @param services
     *     The services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
