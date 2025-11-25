package com.codex.fight;

import com.codex.hero.Hero;
import com.codex.villain.Villain;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.Random;

@ApplicationScoped
public class FightService {

    private final Random random = new Random();

    @Transactional
    public Fight performFight(Long heroId, Long villainId) {
        Log.infof("Starting fight between hero %d and villain %d", heroId, villainId);
        
        Hero hero = Hero.findById(heroId);
        Villain villain = Villain.findById(villainId);

        if (hero == null || villain == null) {
            throw new IllegalArgumentException("Hero or Villain not found");
        }

        // Calculate total power for each fighter
        int heroPower = calculatePower(hero.strength, hero.agility, hero.intelligence, hero.level);
        int villainPower = calculatePower(villain.strength, villain.agility, villain.intelligence, villain.level);

        Log.infof("Hero %s power: %d, Villain %s power: %d", hero.name, heroPower, villain.name, villainPower);

        // Add some randomness to the fight (20% variance)
        int heroRoll = heroPower + random.nextInt(heroPower / 5 + 1);
        int villainRoll = villainPower + random.nextInt(villainPower / 5 + 1);

        Fight fight = new Fight();
        fight.heroId = heroId;
        fight.villainId = villainId;
        fight.heroName = hero.name;
        fight.villainName = villain.name;
        fight.fightDate = Instant.now();

        // Determine the winner
        if (heroRoll > villainRoll) {
            fight.winnerId = heroId;
            fight.winnerType = "HERO";
            fight.battleLog = String.format("%s (power: %d) defeated %s (power: %d) in an epic battle!", 
                hero.name, heroRoll, villain.name, villainRoll);
            Log.infof("Hero %s wins the fight!", hero.name);
        } else if (villainRoll > heroRoll) {
            fight.winnerId = villainId;
            fight.winnerType = "VILLAIN";
            fight.battleLog = String.format("%s (power: %d) defeated %s (power: %d) with their evil might!", 
                villain.name, villainRoll, hero.name, heroRoll);
            Log.infof("Villain %s wins the fight!", villain.name);
        } else {
            // Tie - hero wins by default
            fight.winnerId = heroId;
            fight.winnerType = "HERO";
            fight.battleLog = String.format("%s and %s fought to a draw, but %s emerges victorious!", 
                hero.name, villain.name, hero.name);
            Log.info("Fight was a draw, hero wins by default");
        }

        fight.persist();
        return fight;
    }

    private int calculatePower(int strength, int agility, int intelligence, int level) {
        return (strength * 2 + agility + intelligence) * level;
    }
}
