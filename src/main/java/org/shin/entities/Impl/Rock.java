package org.shin.entities.Impl;


import org.shin.config.Signs;
import org.shin.entities.Entity;
import org.shin.entities.Obstacle;

import java.util.Random;


public class Rock extends Entity implements Obstacle {

    public Rock(){
        this.sign = Signs.ROCK[new Random().nextInt(Signs.ROCK.length)];
    }



}
