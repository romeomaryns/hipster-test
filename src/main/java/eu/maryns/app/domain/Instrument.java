package eu.maryns.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Instrument.
 */
@Entity
@Table(name = "instrument")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Instrument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "pip_location")
    private Integer pipLocation;

    @Column(name = "display_precision")
    private Integer displayPrecision;

    @Column(name = "trade_units_precision")
    private Integer tradeUnitsPrecision;

    @Column(name = "minimum_trade_size")
    private Double minimumTradeSize;

    @Column(name = "maximum_trailing_stop_distance")
    private Double maximumTrailingStopDistance;

    @Column(name = "minimum_trailing_stop_distance")
    private Double minimumTrailingStopDistance;

    @Column(name = "maximum_position_size")
    private Double maximumPositionSize;

    @Column(name = "maximum_order_units")
    private Double maximumOrderUnits;

    @Column(name = "margin_rate")
    private Double marginRate;

    @Column(name = "commission")
    private Double commission;

    @OneToMany(mappedBy = "instrument")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Stat> stats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Instrument name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public Instrument type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Instrument displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getPipLocation() {
        return pipLocation;
    }

    public Instrument pipLocation(Integer pipLocation) {
        this.pipLocation = pipLocation;
        return this;
    }

    public void setPipLocation(Integer pipLocation) {
        this.pipLocation = pipLocation;
    }

    public Integer getDisplayPrecision() {
        return displayPrecision;
    }

    public Instrument displayPrecision(Integer displayPrecision) {
        this.displayPrecision = displayPrecision;
        return this;
    }

    public void setDisplayPrecision(Integer displayPrecision) {
        this.displayPrecision = displayPrecision;
    }

    public Integer getTradeUnitsPrecision() {
        return tradeUnitsPrecision;
    }

    public Instrument tradeUnitsPrecision(Integer tradeUnitsPrecision) {
        this.tradeUnitsPrecision = tradeUnitsPrecision;
        return this;
    }

    public void setTradeUnitsPrecision(Integer tradeUnitsPrecision) {
        this.tradeUnitsPrecision = tradeUnitsPrecision;
    }

    public Double getMinimumTradeSize() {
        return minimumTradeSize;
    }

    public Instrument minimumTradeSize(Double minimumTradeSize) {
        this.minimumTradeSize = minimumTradeSize;
        return this;
    }

    public void setMinimumTradeSize(Double minimumTradeSize) {
        this.minimumTradeSize = minimumTradeSize;
    }

    public Double getMaximumTrailingStopDistance() {
        return maximumTrailingStopDistance;
    }

    public Instrument maximumTrailingStopDistance(Double maximumTrailingStopDistance) {
        this.maximumTrailingStopDistance = maximumTrailingStopDistance;
        return this;
    }

    public void setMaximumTrailingStopDistance(Double maximumTrailingStopDistance) {
        this.maximumTrailingStopDistance = maximumTrailingStopDistance;
    }

    public Double getMinimumTrailingStopDistance() {
        return minimumTrailingStopDistance;
    }

    public Instrument minimumTrailingStopDistance(Double minimumTrailingStopDistance) {
        this.minimumTrailingStopDistance = minimumTrailingStopDistance;
        return this;
    }

    public void setMinimumTrailingStopDistance(Double minimumTrailingStopDistance) {
        this.minimumTrailingStopDistance = minimumTrailingStopDistance;
    }

    public Double getMaximumPositionSize() {
        return maximumPositionSize;
    }

    public Instrument maximumPositionSize(Double maximumPositionSize) {
        this.maximumPositionSize = maximumPositionSize;
        return this;
    }

    public void setMaximumPositionSize(Double maximumPositionSize) {
        this.maximumPositionSize = maximumPositionSize;
    }

    public Double getMaximumOrderUnits() {
        return maximumOrderUnits;
    }

    public Instrument maximumOrderUnits(Double maximumOrderUnits) {
        this.maximumOrderUnits = maximumOrderUnits;
        return this;
    }

    public void setMaximumOrderUnits(Double maximumOrderUnits) {
        this.maximumOrderUnits = maximumOrderUnits;
    }

    public Double getMarginRate() {
        return marginRate;
    }

    public Instrument marginRate(Double marginRate) {
        this.marginRate = marginRate;
        return this;
    }

    public void setMarginRate(Double marginRate) {
        this.marginRate = marginRate;
    }

    public Double getCommission() {
        return commission;
    }

    public Instrument commission(Double commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Set<Stat> getStats() {
        return stats;
    }

    public Instrument stats(Set<Stat> stats) {
        this.stats = stats;
        return this;
    }

    public Instrument addStats(Stat stat) {
        this.stats.add(stat);
        stat.setInstrument(this);
        return this;
    }

    public Instrument removeStats(Stat stat) {
        this.stats.remove(stat);
        stat.setInstrument(null);
        return this;
    }

    public void setStats(Set<Stat> stats) {
        this.stats = stats;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Instrument instrument = (Instrument) o;
        if (instrument.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instrument.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Instrument{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", pipLocation='" + getPipLocation() + "'" +
            ", displayPrecision='" + getDisplayPrecision() + "'" +
            ", tradeUnitsPrecision='" + getTradeUnitsPrecision() + "'" +
            ", minimumTradeSize='" + getMinimumTradeSize() + "'" +
            ", maximumTrailingStopDistance='" + getMaximumTrailingStopDistance() + "'" +
            ", minimumTrailingStopDistance='" + getMinimumTrailingStopDistance() + "'" +
            ", maximumPositionSize='" + getMaximumPositionSize() + "'" +
            ", maximumOrderUnits='" + getMaximumOrderUnits() + "'" +
            ", marginRate='" + getMarginRate() + "'" +
            ", commission='" + getCommission() + "'" +
            "}";
    }
}
