
package com.ryanair.flights.model.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryanair.flights.utils.DateTimeSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "departureAirport",
        "arrivalAirport",
        "departureDateTime",
        "arrivalDateTime"
})
public class Leg {

    @JsonProperty("departureAirport")
    private String departureAirport;
    @JsonProperty("arrivalAirport")
    private String arrivalAirport;
    @JsonProperty("departureDateTime")
    @JsonSerialize(using = DateTimeSerializer.class)
    private LocalDateTime departureDateTime;
    @JsonProperty("arrivalDateTime")
    @JsonSerialize(using = DateTimeSerializer.class)
    private LocalDateTime arrivalDateTime;

    public Leg() {
    }

    public Leg(String departureAirport, String arrivalAirport, LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
    }

    @JsonProperty("departureAirport")
    public String getDepartureAirport() {
        return departureAirport;
    }

    @JsonProperty("departureAirport")
    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    @JsonProperty("arrivalAirport")
    public String getArrivalAirport() {
        return arrivalAirport;
    }

    @JsonProperty("arrivalAirport")
    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    @JsonProperty("departureDateTime")
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    @JsonProperty("departureDateTime")
    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    @JsonProperty("arrivalDateTime")
    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    @JsonProperty("arrivalDateTime")
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("departureAirport", departureAirport).append("arrivalAirport", arrivalAirport).append("departureDateTime", departureDateTime).append("arrivalDateTime", arrivalDateTime).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(departureDateTime).append(arrivalDateTime).append(departureAirport).append(arrivalAirport).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Leg) == false) {
            return false;
        }
        Leg rhs = ((Leg) other);
        return new EqualsBuilder().append(departureDateTime, rhs.departureDateTime).append(arrivalDateTime, rhs.arrivalDateTime).append(departureAirport, rhs.departureAirport).append(arrivalAirport, rhs.arrivalAirport).isEquals();
    }

}
