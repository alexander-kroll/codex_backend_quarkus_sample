package com.codex.hero;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Sort;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Random;

@Entity
public class Hero extends PanacheEntity {

    @NotNull
    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    public String name;

    @NotNull
    @Size(min = 10, max = 1000)
    @Column(nullable = false, length = 1000)
    public String description;

    @NotNull
    @Min(1)
    @Column(nullable = false)
    public Integer level;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    public Integer strength;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    public Integer agility;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    public Integer intelligence;

    @Column(length = 255)
    public String imageUrl;

    @Column(length = 100)
    public String specialPower;

    public static Hero findRandom() {
        long count = count();
        if (count == 0) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt((int) count);
        return findAll().page(randomIndex, 1).firstResult();
    }
}
