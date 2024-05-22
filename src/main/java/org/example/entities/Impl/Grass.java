package org.example.entities.Impl;


import org.example.config.Signs;
import org.example.entities.Entity;
import org.example.entities.Obstacle;
import org.example.entities.Plant;

import java.util.Random;

public class Grass extends Entity implements Plant {

    public Grass() {
    this.sign = Signs.GRASS[new Random().nextInt(Signs.GRASS.length)];
    }
}
