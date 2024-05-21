package org.example.entities.Impl;


import org.example.config.Signs;
import org.example.entities.Entity;

import java.util.Random;


public class Tree extends Entity {

    public Tree(){
        this.sign = Signs.TREE[new Random().nextInt(Signs.TREE.length)];
    }
}
