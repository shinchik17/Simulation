package org.example.entities;


import org.example.config.Signs;

import java.util.Random;

public class Grass extends Entity{

    public Grass() {
    this.sign = Signs.GRASS[new Random().nextInt(Signs.GRASS.length)];
    }
}
