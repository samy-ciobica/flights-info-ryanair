
package com.ryanair.flights.model.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Route {

    @JsonProperty("airportFrom")
    private String airportFrom;
    @JsonProperty("airportTo")
    private String airportTo;
    @JsonProperty("connectingAirport")
    private Object connectingAirport;
    @JsonProperty("newRoute")
    private Boolean newRoute;
    @JsonProperty("seasonalRoute")
    private Boolean seasonalRoute;
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("group")
    private String group;
    @JsonProperty("similarArrivalAirportCodes")
    private List<String> similarArrivalAirportCodes = null;
    @JsonProperty("tags")
    private List<Object> tags = null;
    @JsonProperty("carrierCode")
    private String carrierCode;

    public Route() {
    }

    public Route(String airportFrom, String airportTo) {
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
    }

    public Route(String airportFrom, String airportTo, Object connectingAirport, Boolean newRoute, Boolean seasonalRoute, String operator, String group, List<String> similarArrivalAirportCodes, List<Object> tags, String carrierCode) {
        this.airportFrom = airportFrom;
        this.airportTo = airportTo;
        this.connectingAirport = connectingAirport;
        this.newRoute = newRoute;
        this.seasonalRoute = seasonalRoute;
        this.operator = operator;
        this.group = group;
        this.similarArrivalAirportCodes = similarArrivalAirportCodes;
        this.tags = tags;
        this.carrierCode = carrierCode;
    }

    @JsonProperty("airportFrom")
    public String getAirportFrom() {
        return airportFrom;
    }

    @JsonProperty("airportFrom")
    public void setAirportFrom(String airportFrom) {
        this.airportFrom = airportFrom;
    }

    @JsonProperty("airportTo")
    public String getAirportTo() {
        return airportTo;
    }

    @JsonProperty("airportTo")
    public void setAirportTo(String airportTo) {
        this.airportTo = airportTo;
    }

    @JsonProperty("connectingAirport")
    public Object getConnectingAirport() {
        return connectingAirport;
    }

    @JsonProperty("connectingAirport")
    public void setConnectingAirport(Object connectingAirport) {
        this.connectingAirport = connectingAirport;
    }

    @JsonProperty("newRoute")
    public Boolean getNewRoute() {
        return newRoute;
    }

    @JsonProperty("newRoute")
    public void setNewRoute(Boolean newRoute) {
        this.newRoute = newRoute;
    }

    @JsonProperty("seasonalRoute")
    public Boolean getSeasonalRoute() {
        return seasonalRoute;
    }

    @JsonProperty("seasonalRoute")
    public void setSeasonalRoute(Boolean seasonalRoute) {
        this.seasonalRoute = seasonalRoute;
    }

    @JsonProperty("operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @JsonProperty("group")
    public String getGroup() {
        return group;
    }

    @JsonProperty("group")
    public void setGroup(String group) {
        this.group = group;
    }

    @JsonProperty("similarArrivalAirportCodes")
    public List<String> getSimilarArrivalAirportCodes() {
        return similarArrivalAirportCodes;
    }

    @JsonProperty("similarArrivalAirportCodes")
    public void setSimilarArrivalAirportCodes(List<String> similarArrivalAirportCodes) {
        this.similarArrivalAirportCodes = similarArrivalAirportCodes;
    }

    @JsonProperty("tags")
    public List<Object> getTags() {
        return tags;
    }

    @JsonProperty("tags")
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    @JsonProperty("carrierCode")
    public String getCarrierCode() {
        return carrierCode;
    }

    @JsonProperty("carrierCode")
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("airportFrom", airportFrom).append("airportTo", airportTo).append("connectingAirport", connectingAirport).append("newRoute", newRoute).append("seasonalRoute", seasonalRoute).append("operator", operator).append("group", group).append("similarArrivalAirportCodes", similarArrivalAirportCodes).append("tags", tags).append("carrierCode", carrierCode).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(airportFrom).append(connectingAirport).append(airportTo).append(newRoute).append(carrierCode).append(similarArrivalAirportCodes).append(operator).append(seasonalRoute).append(group).append(tags).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Route) == false) {
            return false;
        }
        Route rhs = ((Route) other);
        return new EqualsBuilder().append(airportFrom, rhs.airportFrom).append(connectingAirport, rhs.connectingAirport).append(airportTo, rhs.airportTo).append(newRoute, rhs.newRoute).append(carrierCode, rhs.carrierCode).append(similarArrivalAirportCodes, rhs.similarArrivalAirportCodes).append(operator, rhs.operator).append(seasonalRoute, rhs.seasonalRoute).append(group, rhs.group).append(tags, rhs.tags).isEquals();
    }

}
