package org.example.entities.Impl;


import org.example.config.Signs;
import org.example.entities.Entity;
import org.example.entities.Obstacle;

import java.util.Random;


public class Rock extends Entity implements Obstacle {

    public Rock(){
        this.sign = Signs.ROCK[new Random().nextInt(Signs.ROCK.length)];
    }



}
