package eu.maryns.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import eu.maryns.app.domain.enumeration.CandleStickGranularity;

/**
 * A CandleStick.
 */
@Entity
@Table(name = "candle_stick")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandleStick implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_time")
    private Instant time;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "jhi_complete")
    private Boolean complete;

    @Enumerated(EnumType.STRING)
    @Column(name = "granularity")
    private CandleStickGranularity granularity;

    @OneToOne
    @JoinColumn(unique = true)
    private CandleStickData mid;

    @ManyToOne
    private Instrument instrument;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTime() {
        return time;
    }

    public CandleStick time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Integer getVolume() {
        return volume;
    }

    public CandleStick volume(Integer volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Boolean isComplete() {
        return complete;
    }

    public CandleStick complete(Boolean complete) {
        this.complete = complete;
        return this;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public CandleStickGranularity getGranularity() {
        return granularity;
    }

    public CandleStick granularity(CandleStickGranularity granularity) {
        this.granularity = granularity;
        return this;
    }

    public void setGranularity(CandleStickGranularity granularity) {
        this.granularity = granularity;
    }

    public CandleStickData getMid() {
        return mid;
    }

    public CandleStick mid(CandleStickData candleStickData) {
        this.mid = candleStickData;
        return this;
    }

    public void setMid(CandleStickData candleStickData) {
        this.mid = candleStickData;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public CandleStick instrument(Instrument instrument) {
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
        CandleStick candleStick = (CandleStick) o;
        if (candleStick.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candleStick.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandleStick{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            ", volume='" + getVolume() + "'" +
            ", complete='" + isComplete() + "'" +
            ", granularity='" + getGranularity() + "'" +
            "}";
    }
}
