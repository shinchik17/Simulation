package org.example.utils;

import org.example.entities.*;
import org.example.entities.Impl.*;

import java.util.ArrayList;
import java.util.Random;

public class EntityFactory {
    public static final ArrayList<String> possibleEntities = new ArrayList<>() {{
        add(Rock.class.getName());
        add(Grass.class.getName());
        add(Tree.class.getName());
        add(Hervibore.class.getName());
        add(Predator.class.getName());
    }};

    private final int maxGenerationInt = 20;
    private final int maxHealth = 5;
    private final int maxSpeed = 5;
    private final int maxAttackPower = 5;


    public Entity getRandomEntity() {
        int tempInt = new Random().nextInt(maxGenerationInt);

        return switch (tempInt){
            case 0 -> new Rock();
            case 1 -> new Grass();
            case 2 -> new Tree();
            case 3 -> new Hervibore(new Random().nextInt(maxHealth)+1, new Random().nextInt(maxSpeed)+1);
            case 4 -> new Predator(new Random().nextInt(maxHealth)+1, new Random().nextInt(maxSpeed)+1, new Random().nextInt(maxAttackPower)+1);
            default -> null;
        };
    }

}


//        try {
//            Class cl = Class.forName(possibleEntities.get(new Random().nextInt(possibleEntities.size())));
//            entity = (Entity) cl.getDeclaredConstructor().newInstance();
//        }
//        catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//                 | NoSuchMethodException | InvocationTargetException | SecurityException e) {
//            e.printStackTrace();
//        }