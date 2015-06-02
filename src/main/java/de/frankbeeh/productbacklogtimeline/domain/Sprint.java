package de.frankbeeh.productbacklogtimeline.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import de.frankbeeh.productbacklogtimeline.domain.util.CustomLocalDateSerializer;
import de.frankbeeh.productbacklogtimeline.domain.util.ISO8601LocalDateDeserializer;

/**
 * A Sprint.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SPRINT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sprint implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "name", length = 40, nullable = false)
    private String name;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "capacity_forecast", precision=10, scale=2, nullable = false)
    private BigDecimal capacityForecast;

    @Column(name = "effort_forecast", precision=10, scale=2, nullable = false)
    private BigDecimal effortForecast;

    @Column(name = "capacity_done", precision=10, scale=2, nullable = false)
    private BigDecimal capacityDone;

    @Column(name = "effort_done", precision=10, scale=2, nullable = false)
    private BigDecimal effortDone;

    @ManyToOne
    private ProductTimestamp productTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCapacityForecast() {
        return capacityForecast;
    }

    public void setCapacityForecast(BigDecimal capacityForecast) {
        this.capacityForecast = capacityForecast;
    }

    public BigDecimal getEffortForecast() {
        return effortForecast;
    }

    public void setEffortForecast(BigDecimal effortForecast) {
        this.effortForecast = effortForecast;
    }

    public BigDecimal getCapacityDone() {
        return capacityDone;
    }

    public void setCapacityDone(BigDecimal capacityDone) {
        this.capacityDone = capacityDone;
    }

    public BigDecimal getEffortDone() {
        return effortDone;
    }

    public void setEffortDone(BigDecimal effortDone) {
        this.effortDone = effortDone;
    }

    public ProductTimestamp getProductTimestamp() {
        return productTimestamp;
    }

    public void setProductTimestamp(ProductTimestamp productTimestamp) {
        this.productTimestamp = productTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Sprint sprint = (Sprint) o;

        if ( ! Objects.equals(id, sprint.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", capacityForecast='" + capacityForecast + "'" +
                ", effortForecast='" + effortForecast + "'" +
                ", capacityDone='" + capacityDone + "'" +
                ", effortDone='" + effortDone + "'" +
                '}';
    }
}
