package org.example.entities;


import org.example.config.Signs;
import java.util.Random;


public class Tree extends Entity{

    public Tree(){
        this.sign = Signs.TREE[new Random().nextInt(Signs.TREE.length)];
    }
}
