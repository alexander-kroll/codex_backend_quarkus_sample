package com.codex.fight;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
public class Fight extends PanacheEntity {

    @NotNull
    @Column(nullable = false)
    public Long heroId;

    @NotNull
    @Column(nullable = false)
    public Long villainId;

    @NotNull
    @Column(nullable = false, length = 50)
    public String heroName;

    @NotNull
    @Column(nullable = false, length = 50)
    public String villainName;

    @Column(nullable = false)
    public Long winnerId;

    @NotNull
    @Column(nullable = false, length = 10)
    public String winnerType; // "HERO" or "VILLAIN"

    @NotNull
    @Column(nullable = false)
    public Instant fightDate;

    @Column(length = 500)
    public String battleLog;
}
