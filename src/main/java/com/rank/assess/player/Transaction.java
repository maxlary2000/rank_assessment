package com.rank.assess.player;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
public class Transaction {
    @Id
    private Long id;
    private BigDecimal amount;
    private String promo;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "player_id")
    private Player player;

    public Transaction(Long id, BigDecimal amount) {
        this.id = id;
        this.amount = amount;
    }

    public Transaction() {
    }

    public Transaction(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPromo() {
        return promo;
    }

    public void setPromo(String promo) {
        this.promo = promo;
    }
}
