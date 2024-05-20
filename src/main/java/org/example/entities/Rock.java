package org.example.entities;


import org.example.config.Signs;
import java.util.Random;


public class Rock extends Entity implements Obstacle {

    public Rock(){
        this.sign = Signs.ROCK[new Random().nextInt(Signs.ROCK.length)];
    }



}
