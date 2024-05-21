package org.example.entities.Impl;


import org.example.config.Signs;
import org.example.entities.Entity;

import java.util.Random;

public class Grass extends Entity {

    public Grass() {
    this.sign = Signs.GRASS[new Random().nextInt(Signs.GRASS.length)];
    }
}
