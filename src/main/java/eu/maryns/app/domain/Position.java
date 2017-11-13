package eu.maryns.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "instrument")
    private String instrument;

    @Column(name = "pl")
    private Double pl;

    @Column(name = "unrealized_pl")
    private Double unrealizedPL;

    @Column(name = "resettable_pl")
    private Double resettablePL;

    @Column(name = "commission")
    private Double commission;

    @ManyToOne
    private OandaAccount oandaAccount;

    @OneToOne
    @JoinColumn(unique = true)
    private PositionSide longValue;

    @OneToOne
    @JoinColumn(unique = true)
    private PositionSide shortValue;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstrument() {
        return instrument;
    }

    public Position instrument(String instrument) {
        this.instrument = instrument;
        return this;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public Double getPl() {
        return pl;
    }

    public Position pl(Double pl) {
        this.pl = pl;
        return this;
    }

    public void setPl(Double pl) {
        this.pl = pl;
    }

    public Double getUnrealizedPL() {
        return unrealizedPL;
    }

    public Position unrealizedPL(Double unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
        return this;
    }

    public void setUnrealizedPL(Double unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
    }

    public Double getResettablePL() {
        return resettablePL;
    }

    public Position resettablePL(Double resettablePL) {
        this.resettablePL = resettablePL;
        return this;
    }

    public void setResettablePL(Double resettablePL) {
        this.resettablePL = resettablePL;
    }

    public Double getCommission() {
        return commission;
    }

    public Position commission(Double commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public OandaAccount getOandaAccount() {
        return oandaAccount;
    }

    public Position oandaAccount(OandaAccount oandaAccount) {
        this.oandaAccount = oandaAccount;
        return this;
    }

    public void setOandaAccount(OandaAccount oandaAccount) {
        this.oandaAccount = oandaAccount;
    }

    public PositionSide getLongValue() {
        return longValue;
    }

    public Position longValue(PositionSide positionSide) {
        this.longValue = positionSide;
        return this;
    }

    public void setLongValue(PositionSide positionSide) {
        this.longValue = positionSide;
    }

    public PositionSide getShortValue() {
        return shortValue;
    }

    public Position shortValue(PositionSide positionSide) {
        this.shortValue = positionSide;
        return this;
    }

    public void setShortValue(PositionSide positionSide) {
        this.shortValue = positionSide;
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
        Position position = (Position) o;
        if (position.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), position.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", instrument='" + getInstrument() + "'" +
            ", pl='" + getPl() + "'" +
            ", unrealizedPL='" + getUnrealizedPL() + "'" +
            ", resettablePL='" + getResettablePL() + "'" +
            ", commission='" + getCommission() + "'" +
            "}";
    }
}
