package org.example.entities.Impl;


import org.example.config.Signs;
import org.example.entities.Entity;
import org.example.entities.Obstacle;
import org.example.entities.Plant;

import java.util.Random;


public class Tree extends Entity implements Obstacle {

    public Tree(){
        this.sign = Signs.TREE[new Random().nextInt(Signs.TREE.length)];
    }
}
