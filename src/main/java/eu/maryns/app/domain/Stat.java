package eu.maryns.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import eu.maryns.app.domain.enumeration.CandleStickGranularity;

/**
 * A Stat.
 */
@Entity
@Table(name = "stat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Stat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "last_updated")
    private Instant lastUpdated;

    @Enumerated(EnumType.STRING)
    @Column(name = "granularity")
    private CandleStickGranularity granularity;

    @Column(name = "number_of_candles")
    private Integer numberOfCandles;

    @Column(name = "first")
    private Instant first;

    @Column(name = "last")
    private Instant last;


    @ManyToOne
    private Instrument instrument;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public Stat lastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public CandleStickGranularity getGranularity() {
        return granularity;
    }

    public Stat granularity(CandleStickGranularity granularity) {
        this.granularity = granularity;
        return this;
    }

    public void setGranularity(CandleStickGranularity granularity) {
        this.granularity = granularity;
    }

    public Integer getNumberOfCandles() {
        return numberOfCandles;
    }

    public Stat numberOfCandles(Integer numberOfCandles) {
        this.numberOfCandles = numberOfCandles;
        return this;
    }

    public void setNumberOfCandles(Integer numberOfCandles) {
        this.numberOfCandles = numberOfCandles;
    }

    public Instant getFirst() {
        return first;
    }

    public Stat first(Instant first) {
        this.first = first;
        return this;
    }

    public void setFirst(Instant first) {
        this.first = first;
    }

    public Instant getLast() {
        return last;
    }

    public Stat last(Instant last) {
        this.last = last;
        return this;
    }

    public void setLast(Instant last) {
        this.last = last;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Stat instrument(Instrument instrument) {
        this.instrument = instrument;
        return this;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
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
        Stat stat = (Stat) o;
        if (stat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Stat{" +
            "id=" + getId() +
            ", lastUpdated='" + getLastUpdated() + "'" +
            ", granularity='" + getGranularity() + "'" +
            ", numberOfCandles='" + getNumberOfCandles() + "'" +
            ", first='" + getFirst() + "'" +
            ", last='" + getLast() + "'" +
            "}";
    }
}
