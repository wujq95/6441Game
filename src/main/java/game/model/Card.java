package model;

import java.util.Random;

/**
 * initial Card model
 */
public enum Card {
    infantry,
    cavalry,
    artillery;

    public static Card getRandomCard() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }
}
