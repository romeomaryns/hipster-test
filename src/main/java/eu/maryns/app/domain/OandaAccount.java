package eu.maryns.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A OandaAccount.
 */
@Entity
@Table(name = "oanda_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OandaAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "alias")
    private String alias;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "currency")
    private String currency;

    @Column(name = "created_by_user_id")
    private Integer createdByUserID;

    @Column(name = "created_time")
    private Instant createdTime;

    @Column(name = "pl")
    private Double pl;

    @Column(name = "resettable_pl")
    private Double resettablePL;

    @Column(name = "resettabled_pl_time")
    private Instant resettabledPLTime;

    @Column(name = "commission")
    private Double commission;

    @Column(name = "margin_rate")
    private Double marginRate;

    @Column(name = "margin_call_enter_time")
    private Instant marginCallEnterTime;

    @Column(name = "margin_call_extension_count")
    private Integer marginCallExtensionCount;

    @Column(name = "last_margin_call_extension_time")
    private Instant lastMarginCallExtensionTime;

    @Column(name = "open_trade_count")
    private Integer openTradeCount;

    @Column(name = "open_position_count")
    private Integer openPositionCount;

    @Column(name = "pending_order_count")
    private Integer pendingOrderCount;

    @Column(name = "hedging_enabled")
    private Boolean hedgingEnabled;

    @Column(name = "unrealized_pl")
    private Double unrealizedPL;

    @Column(name = "n_av")
    private Double nAV;

    @Column(name = "margin_used")
    private Double marginUsed;

    @Column(name = "margin_available")
    private Double marginAvailable;

    @Column(name = "position_value")
    private Double positionValue;

    @Column(name = "margin_closeout_unrealized_pl")
    private Double marginCloseoutUnrealizedPL;

    @Column(name = "margin_closeout_nav")
    private Double marginCloseoutNAV;

    @Column(name = "margin_closeout_margin_used")
    private Double marginCloseoutMarginUsed;

    @Column(name = "margin_closeout_percent")
    private Double marginCloseoutPercent;

    @Column(name = "margin_closeout_position_value")
    private Double marginCloseoutPositionValue;

    @Column(name = "withdrawal_limit")
    private Double withdrawalLimit;

    @Column(name = "margin_call_margin_used")
    private Double marginCallMarginUsed;

    @Column(name = "margin_call_percent")
    private Double marginCallPercent;

    @Column(name = "last_transaction_id")
    private String lastTransactionID;

    @OneToMany(mappedBy = "oandaAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Position> positions = new HashSet<>();

    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public OandaAccount alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Double getBalance() {
        return balance;
    }

    public OandaAccount balance(Double balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public OandaAccount currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getCreatedByUserID() {
        return createdByUserID;
    }

    public OandaAccount createdByUserID(Integer createdByUserID) {
        this.createdByUserID = createdByUserID;
        return this;
    }

    public void setCreatedByUserID(Integer createdByUserID) {
        this.createdByUserID = createdByUserID;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public OandaAccount createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public Double getPl() {
        return pl;
    }

    public OandaAccount pl(Double pl) {
        this.pl = pl;
        return this;
    }

    public void setPl(Double pl) {
        this.pl = pl;
    }

    public Double getResettablePL() {
        return resettablePL;
    }

    public OandaAccount resettablePL(Double resettablePL) {
        this.resettablePL = resettablePL;
        return this;
    }

    public void setResettablePL(Double resettablePL) {
        this.resettablePL = resettablePL;
    }

    public Instant getResettabledPLTime() {
        return resettabledPLTime;
    }

    public OandaAccount resettabledPLTime(Instant resettabledPLTime) {
        this.resettabledPLTime = resettabledPLTime;
        return this;
    }

    public void setResettabledPLTime(Instant resettabledPLTime) {
        this.resettabledPLTime = resettabledPLTime;
    }

    public Double getCommission() {
        return commission;
    }

    public OandaAccount commission(Double commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getMarginRate() {
        return marginRate;
    }

    public OandaAccount marginRate(Double marginRate) {
        this.marginRate = marginRate;
        return this;
    }

    public void setMarginRate(Double marginRate) {
        this.marginRate = marginRate;
    }

    public Instant getMarginCallEnterTime() {
        return marginCallEnterTime;
    }

    public OandaAccount marginCallEnterTime(Instant marginCallEnterTime) {
        this.marginCallEnterTime = marginCallEnterTime;
        return this;
    }

    public void setMarginCallEnterTime(Instant marginCallEnterTime) {
        this.marginCallEnterTime = marginCallEnterTime;
    }

    public Integer getMarginCallExtensionCount() {
        return marginCallExtensionCount;
    }

    public OandaAccount marginCallExtensionCount(Integer marginCallExtensionCount) {
        this.marginCallExtensionCount = marginCallExtensionCount;
        return this;
    }

    public void setMarginCallExtensionCount(Integer marginCallExtensionCount) {
        this.marginCallExtensionCount = marginCallExtensionCount;
    }

    public Instant getLastMarginCallExtensionTime() {
        return lastMarginCallExtensionTime;
    }

    public OandaAccount lastMarginCallExtensionTime(Instant lastMarginCallExtensionTime) {
        this.lastMarginCallExtensionTime = lastMarginCallExtensionTime;
        return this;
    }

    public void setLastMarginCallExtensionTime(Instant lastMarginCallExtensionTime) {
        this.lastMarginCallExtensionTime = lastMarginCallExtensionTime;
    }

    public Integer getOpenTradeCount() {
        return openTradeCount;
    }

    public OandaAccount openTradeCount(Integer openTradeCount) {
        this.openTradeCount = openTradeCount;
        return this;
    }

    public void setOpenTradeCount(Integer openTradeCount) {
        this.openTradeCount = openTradeCount;
    }

    public Integer getOpenPositionCount() {
        return openPositionCount;
    }

    public OandaAccount openPositionCount(Integer openPositionCount) {
        this.openPositionCount = openPositionCount;
        return this;
    }

    public void setOpenPositionCount(Integer openPositionCount) {
        this.openPositionCount = openPositionCount;
    }

    public Integer getPendingOrderCount() {
        return pendingOrderCount;
    }

    public OandaAccount pendingOrderCount(Integer pendingOrderCount) {
        this.pendingOrderCount = pendingOrderCount;
        return this;
    }

    public void setPendingOrderCount(Integer pendingOrderCount) {
        this.pendingOrderCount = pendingOrderCount;
    }

    public Boolean isHedgingEnabled() {
        return hedgingEnabled;
    }

    public OandaAccount hedgingEnabled(Boolean hedgingEnabled) {
        this.hedgingEnabled = hedgingEnabled;
        return this;
    }

    public void setHedgingEnabled(Boolean hedgingEnabled) {
        this.hedgingEnabled = hedgingEnabled;
    }

    public Double getUnrealizedPL() {
        return unrealizedPL;
    }

    public OandaAccount unrealizedPL(Double unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
        return this;
    }

    public void setUnrealizedPL(Double unrealizedPL) {
        this.unrealizedPL = unrealizedPL;
    }

    public Double getnAV() {
        return nAV;
    }

    public OandaAccount nAV(Double nAV) {
        this.nAV = nAV;
        return this;
    }

    public void setnAV(Double nAV) {
        this.nAV = nAV;
    }

    public Double getMarginUsed() {
        return marginUsed;
    }

    public OandaAccount marginUsed(Double marginUsed) {
        this.marginUsed = marginUsed;
        return this;
    }

    public void setMarginUsed(Double marginUsed) {
        this.marginUsed = marginUsed;
    }

    public Double getMarginAvailable() {
        return marginAvailable;
    }

    public OandaAccount marginAvailable(Double marginAvailable) {
        this.marginAvailable = marginAvailable;
        return this;
    }

    public void setMarginAvailable(Double marginAvailable) {
        this.marginAvailable = marginAvailable;
    }

    public Double getPositionValue() {
        return positionValue;
    }

    public OandaAccount positionValue(Double positionValue) {
        this.positionValue = positionValue;
        return this;
    }

    public void setPositionValue(Double positionValue) {
        this.positionValue = positionValue;
    }

    public Double getMarginCloseoutUnrealizedPL() {
        return marginCloseoutUnrealizedPL;
    }

    public OandaAccount marginCloseoutUnrealizedPL(Double marginCloseoutUnrealizedPL) {
        this.marginCloseoutUnrealizedPL = marginCloseoutUnrealizedPL;
        return this;
    }

    public void setMarginCloseoutUnrealizedPL(Double marginCloseoutUnrealizedPL) {
        this.marginCloseoutUnrealizedPL = marginCloseoutUnrealizedPL;
    }

    public Double getMarginCloseoutNAV() {
        return marginCloseoutNAV;
    }

    public OandaAccount marginCloseoutNAV(Double marginCloseoutNAV) {
        this.marginCloseoutNAV = marginCloseoutNAV;
        return this;
    }

    public void setMarginCloseoutNAV(Double marginCloseoutNAV) {
        this.marginCloseoutNAV = marginCloseoutNAV;
    }

    public Double getMarginCloseoutMarginUsed() {
        return marginCloseoutMarginUsed;
    }

    public OandaAccount marginCloseoutMarginUsed(Double marginCloseoutMarginUsed) {
        this.marginCloseoutMarginUsed = marginCloseoutMarginUsed;
        return this;
    }

    public void setMarginCloseoutMarginUsed(Double marginCloseoutMarginUsed) {
        this.marginCloseoutMarginUsed = marginCloseoutMarginUsed;
    }

    public Double getMarginCloseoutPercent() {
        return marginCloseoutPercent;
    }

    public OandaAccount marginCloseoutPercent(Double marginCloseoutPercent) {
        this.marginCloseoutPercent = marginCloseoutPercent;
        return this;
    }

    public void setMarginCloseoutPercent(Double marginCloseoutPercent) {
        this.marginCloseoutPercent = marginCloseoutPercent;
    }

    public Double getMarginCloseoutPositionValue() {
        return marginCloseoutPositionValue;
    }

    public OandaAccount marginCloseoutPositionValue(Double marginCloseoutPositionValue) {
        this.marginCloseoutPositionValue = marginCloseoutPositionValue;
        return this;
    }

    public void setMarginCloseoutPositionValue(Double marginCloseoutPositionValue) {
        this.marginCloseoutPositionValue = marginCloseoutPositionValue;
    }

    public Double getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public OandaAccount withdrawalLimit(Double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
        return this;
    }

    public void setWithdrawalLimit(Double withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public Double getMarginCallMarginUsed() {
        return marginCallMarginUsed;
    }

    public OandaAccount marginCallMarginUsed(Double marginCallMarginUsed) {
        this.marginCallMarginUsed = marginCallMarginUsed;
        return this;
    }

    public void setMarginCallMarginUsed(Double marginCallMarginUsed) {
        this.marginCallMarginUsed = marginCallMarginUsed;
    }

    public Double getMarginCallPercent() {
        return marginCallPercent;
    }

    public OandaAccount marginCallPercent(Double marginCallPercent) {
        this.marginCallPercent = marginCallPercent;
        return this;
    }

    public void setMarginCallPercent(Double marginCallPercent) {
        this.marginCallPercent = marginCallPercent;
    }

    public String getLastTransactionID() {
        return lastTransactionID;
    }

    public OandaAccount lastTransactionID(String lastTransactionID) {
        this.lastTransactionID = lastTransactionID;
        return this;
    }

    public void setLastTransactionID(String lastTransactionID) {
        this.lastTransactionID = lastTransactionID;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public OandaAccount positions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

    public OandaAccount addPositions(Position position) {
        this.positions.add(position);
        position.setOandaAccount(this);
        return this;
    }

    public OandaAccount removePositions(Position position) {
        this.positions.remove(position);
        position.setOandaAccount(null);
        return this;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public User getUser() {
        return user;
    }

    public OandaAccount user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        OandaAccount oandaAccount = (OandaAccount) o;
        if (oandaAccount.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), oandaAccount.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OandaAccount{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", balance='" + getBalance() + "'" +
            ", currency='" + getCurrency() + "'" +
            ", createdByUserID='" + getCreatedByUserID() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", pl='" + getPl() + "'" +
            ", resettablePL='" + getResettablePL() + "'" +
            ", resettabledPLTime='" + getResettabledPLTime() + "'" +
            ", commission='" + getCommission() + "'" +
            ", marginRate='" + getMarginRate() + "'" +
            ", marginCallEnterTime='" + getMarginCallEnterTime() + "'" +
            ", marginCallExtensionCount='" + getMarginCallExtensionCount() + "'" +
            ", lastMarginCallExtensionTime='" + getLastMarginCallExtensionTime() + "'" +
            ", openTradeCount='" + getOpenTradeCount() + "'" +
            ", openPositionCount='" + getOpenPositionCount() + "'" +
            ", pendingOrderCount='" + getPendingOrderCount() + "'" +
            ", hedgingEnabled='" + isHedgingEnabled() + "'" +
            ", unrealizedPL='" + getUnrealizedPL() + "'" +
            ", nAV='" + getnAV() + "'" +
            ", marginUsed='" + getMarginUsed() + "'" +
            ", marginAvailable='" + getMarginAvailable() + "'" +
            ", positionValue='" + getPositionValue() + "'" +
            ", marginCloseoutUnrealizedPL='" + getMarginCloseoutUnrealizedPL() + "'" +
            ", marginCloseoutNAV='" + getMarginCloseoutNAV() + "'" +
            ", marginCloseoutMarginUsed='" + getMarginCloseoutMarginUsed() + "'" +
            ", marginCloseoutPercent='" + getMarginCloseoutPercent() + "'" +
            ", marginCloseoutPositionValue='" + getMarginCloseoutPositionValue() + "'" +
            ", withdrawalLimit='" + getWithdrawalLimit() + "'" +
            ", marginCallMarginUsed='" + getMarginCallMarginUsed() + "'" +
            ", marginCallPercent='" + getMarginCallPercent() + "'" +
            ", lastTransactionID='" + getLastTransactionID() + "'" +
            "}";
    }
}
