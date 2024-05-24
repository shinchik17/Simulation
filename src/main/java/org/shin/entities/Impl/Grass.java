package org.shin.entities.Impl;


import org.shin.config.Signs;
import org.shin.entities.Entity;
import org.shin.entities.Plant;

import java.util.Random;

public class Grass extends Entity implements Plant {

    public Grass() {
    this.sign = Signs.GRASS[new Random().nextInt(Signs.GRASS.length)];
    }


    @Override
    public String toString() {
        return String.format("Grass{@%s}", Integer.toHexString(hashCode()));
    }
}
