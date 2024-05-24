package org.shin.entities.Impl;


import org.shin.config.Signs;
import org.shin.entities.Entity;
import org.shin.entities.Obstacle;

import java.util.Random;


public class Tree extends Entity implements Obstacle {

    public Tree(){
        this.sign = Signs.TREE[new Random().nextInt(Signs.TREE.length)];
    }
}
