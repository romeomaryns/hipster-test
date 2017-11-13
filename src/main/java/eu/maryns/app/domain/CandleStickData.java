package eu.maryns.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandleStickData.
 */
@Entity
@Table(name = "candle_stick_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CandleStickData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "o")
    private Double o;

    @Column(name = "h")
    private Double h;

    @Column(name = "l")
    private Double l;

    @Column(name = "c")
    private Double c;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getO() {
        return o;
    }

    public CandleStickData o(Double o) {
        this.o = o;
        return this;
    }

    public void setO(Double o) {
        this.o = o;
    }

    public Double getH() {
        return h;
    }

    public CandleStickData h(Double h) {
        this.h = h;
        return this;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getL() {
        return l;
    }

    public CandleStickData l(Double l) {
        this.l = l;
        return this;
    }

    public void setL(Double l) {
        this.l = l;
    }

    public Double getC() {
        return c;
    }

    public CandleStickData c(Double c) {
        this.c = c;
        return this;
    }

    public void setC(Double c) {
        this.c = c;
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
        CandleStickData candleStickData = (CandleStickData) o;
        if (candleStickData.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candleStickData.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandleStickData{" +
            "id=" + getId() +
            ", o='" + getO() + "'" +
            ", h='" + getH() + "'" +
            ", l='" + getL() + "'" +
            ", c='" + getC() + "'" +
            "}";
    }
}
