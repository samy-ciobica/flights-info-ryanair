
package com.ryanair.flights.model.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Day {

    @JsonProperty("day")
    private Integer day;
    @JsonProperty("flights")
    private List<Flight> flights = null;

    @JsonProperty("day")
    public Integer getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(Integer day) {
        this.day = day;
    }

    public Day(Integer day, List<Flight> flights) {
        this.day = day;
        this.flights = flights;
    }

    public Day() {
    }

    @JsonProperty("flights")
    public List<Flight> getFlights() {
        return flights;
    }

    @JsonProperty("flights")
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("day", day).append("flights", flights).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(flights).append(day).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Day) == false) {
            return false;
        }
        Day rhs = ((Day) other);
        return new EqualsBuilder().append(flights, rhs.flights).append(day, rhs.day).isEquals();
    }

}
