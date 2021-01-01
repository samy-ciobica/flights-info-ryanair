
package com.ryanair.flights.model.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flight {

    @JsonProperty("number")
    private String number;
    @JsonProperty("departureTime")
    private LocalTime departureTime;
    @JsonProperty("arrivalTime")
    private LocalTime arrivalTime;

    public Flight(String number, LocalTime departureTime, LocalTime arrivalTime) {
        this.number = number;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight() {
    }

    @JsonProperty("number")
    public String getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(String number) {
        this.number = number;
    }

    @JsonProperty("departureTime")
    public LocalTime getDepartureTime() {
        return departureTime;
    }

    @JsonProperty("departureTime")
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    @JsonProperty("arrivalTime")
    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    @JsonProperty("arrivalTime")
    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("number", number).append("departureTime", departureTime).append("arrivalTime", arrivalTime).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(departureTime).append(number).append(arrivalTime).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Flight) == false) {
            return false;
        }
        Flight rhs = ((Flight) other);
        return new EqualsBuilder().append(departureTime, rhs.departureTime).append(number, rhs.number).append(arrivalTime, rhs.arrivalTime).isEquals();
    }

}
