
package com.ryanair.flights.model.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "stops",
        "legs"
})
public class FlightResult {

    @JsonProperty("stops")
    private Integer stops;
    @JsonProperty("legs")
    private List<Leg> legs = null;

    public FlightResult() {
    }

    public FlightResult(Integer stops, List<Leg> legs) {
        this.stops = stops;
        this.legs = legs;
    }

    @JsonProperty("stops")
    public Integer getStops() {
        return stops;
    }

    @JsonProperty("stops")
    public void setStops(Integer stops) {
        this.stops = stops;
    }

    @JsonProperty("legs")
    public List<Leg> getLegs() {
        return legs;
    }

    @JsonProperty("legs")
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("stops", stops).append("legs", legs).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(stops).append(legs).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof FlightResult) == false) {
            return false;
        }
        FlightResult rhs = ((FlightResult) other);
        return new EqualsBuilder().append(stops, rhs.stops).append(legs, rhs.legs).isEquals();
    }

}
