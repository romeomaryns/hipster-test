package eu.maryns.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PositionSide.
 */
@Entity
@Table(name = "position_side")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PositionSide implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "units")
    private Double units;

    @Column(name = "average_price")
    private Double averagePrice;

    @Column(name = "pl")
    private Double pl;

    @Column(name = "unrealized_pl")
    private Double unrealizedPL;

    @Column(name = "resettable_pl")
    private Double resettablePL;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getUnits() {
        return units;
    }

    public PositionSide units(Double units) {
        this.units = units;
        return this;
    }

    public void setUnits(Double units) {
        this.units = units;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public PositionSide averagePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
        return this;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getPl() {
        return pl;
    }

    public PositionSide pl(Double pl) {
        this.pl = pl;
        return this;
    }

    public void setPl(Double pl) {
        this.pl = pl;
    }

    public Double getUnrealizedPL() {
        return unrealizedPL;
    }

    public PositionSide unrealizedPL(Double unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
        return this;
    }

    public void setUnrealizedPL(Double unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
    }

    public Double getResettablePL() {
        return resettablePL;
    }

    public PositionSide resettablePL(Double resettablePL) {
        this.resettablePL = resettablePL;
        return this;
    }

    public void setResettablePL(Double resettablePL) {
        this.resettablePL = resettablePL;
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
        PositionSide positionSide = (PositionSide) o;
        if (positionSide.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), positionSide.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PositionSide{" +
            "id=" + getId() +
            ", units='" + getUnits() + "'" +
            ", averagePrice='" + getAveragePrice() + "'" +
            ", pl='" + getPl() + "'" +
            ", unrealizedPL='" + getUnrealizedPL() + "'" +
            ", resettablePL='" + getResettablePL() + "'" +
            "}";
    }
}
