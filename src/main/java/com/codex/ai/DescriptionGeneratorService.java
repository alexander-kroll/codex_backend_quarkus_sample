package com.codex.ai;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Random;

/**
 * Service for generating creative descriptions for heroes and villains.
 * This is a mock implementation. In production, this could integrate with
 * an AI service like OpenAI, Azure OpenAI, or other LLM providers.
 */
@ApplicationScoped
public class DescriptionGeneratorService {

    private final Random random = new Random();

    private static final String[] HERO_ADJECTIVES = {
        "brave", "mighty", "noble", "fearless", "gallant", "valiant", "legendary", "heroic"
    };

    private static final String[] HERO_ABILITIES = {
        "super strength", "lightning speed", "indestructible armor", "magical powers", 
        "tactical genius", "healing abilities", "flight", "energy projection"
    };

    private static final String[] HERO_ORIGINS = {
        "from a distant planet", "chosen by ancient gods", "transformed by a scientific accident",
        "trained by mystical warriors", "born with extraordinary gifts", "empowered by cosmic energy"
    };

    private static final String[] VILLAIN_ADJECTIVES = {
        "sinister", "cunning", "ruthless", "malevolent", "diabolical", "treacherous", "wicked", "evil"
    };

    private static final String[] VILLAIN_ABILITIES = {
        "mind control", "dark magic", "technological supremacy", "reality manipulation",
        "shadow powers", "toxic abilities", "master strategist", "unstoppable force"
    };

    private static final String[] VILLAIN_GOALS = {
        "seeking world domination", "plotting revenge against society", "harvesting cosmic power",
        "destroying all heroes", "reshaping reality", "unleashing chaos upon the world"
    };

    public String generateHeroDescription(String name) {
        Log.infof("Generating hero description for: %s", name);
        
        String adjective = HERO_ADJECTIVES[random.nextInt(HERO_ADJECTIVES.length)];
        String ability = HERO_ABILITIES[random.nextInt(HERO_ABILITIES.length)];
        String origin = HERO_ORIGINS[random.nextInt(HERO_ORIGINS.length)];

        return String.format("Meet %s, the %s warrior %s. Gifted with %s, %s has sworn to protect the innocent " +
            "and stand against evil wherever it may lurk. With unwavering courage and determination, this hero " +
            "fights for justice and the greater good of humanity.",
            name, adjective, origin, ability, name);
    }

    public String generateVillainDescription(String name) {
        Log.infof("Generating villain description for: %s", name);
        
        String adjective = VILLAIN_ADJECTIVES[random.nextInt(VILLAIN_ADJECTIVES.length)];
        String ability = VILLAIN_ABILITIES[random.nextInt(VILLAIN_ABILITIES.length)];
        String goal = VILLAIN_GOALS[random.nextInt(VILLAIN_GOALS.length)];

        return String.format("%s, the %s adversary, wields the power of %s. Currently %s, this villain " +
            "will stop at nothing to achieve their dark ambitions. Heroes beware - %s shows no mercy " +
            "to those who stand in their way.",
            name, adjective, ability, goal, name);
    }

    public String generateSpecialPower() {
        String[] powers = {
            "Thunder Strike", "Solar Beam", "Quantum Shield", "Time Manipulation", 
            "Elemental Mastery", "Psychic Burst", "Teleportation", "Energy Absorption"
        };
        return powers[random.nextInt(powers.length)];
    }

    public String generateEvilPlan() {
        String[] plans = {
            "Steal the world's resources", "Create an army of minions", "Open a portal to another dimension",
            "Control all governments", "Harness the power of the sun", "Freeze time itself",
            "Build a doomsday device", "Enslave humanity"
        };
        return plans[random.nextInt(plans.length)];
    }
}
