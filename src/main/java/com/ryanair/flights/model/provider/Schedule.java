
package com.ryanair.flights.model.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule {

    @JsonProperty("month")
    private Integer month;
    @JsonProperty("days")
    private List<Day> days = null;

    public Schedule() {
    }

    public Schedule(Integer month, List<Day> days) {
        this.month = month;
        this.days = days;
    }

    @JsonProperty("month")
    public Integer getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(Integer month) {
        this.month = month;
    }

    @JsonProperty("days")
    public List<Day> getDays() {
        return days;
    }

    @JsonProperty("days")
    public void setDays(List<Day> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("month", month).append("days", days).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(month).append(days).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Schedule) == false) {
            return false;
        }
        Schedule rhs = ((Schedule) other);
        return new EqualsBuilder().append(month, rhs.month).append(days, rhs.days).isEquals();
    }

}
