package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.entities.*;
import org.example.entities.Impl.*;

import java.util.*;
import java.util.Map;

public class EntityFactory {
    public static final ArrayList<String> possibleEntities = new ArrayList<>() {{
        add(Rock.class.getName());
        add(Grass.class.getName());
        add(Tree.class.getName());
        add(Hervibore.class.getName());
        add(Predator.class.getName());
    }};

    private final Logger logger = LogManager.getLogger();
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
            case 3 -> createHervibore();
            case 4 -> createPredator();
            default -> null;
        };
    }

    public List<Entity> getRandomEntities(int amount) {
//    public List<Entity> getRandomEntities(int amount) {
        if (amount < 4) {
            throw new RuntimeException("Parameter 'amount of entities' to implement adequate map");
        }

        List<Entity> entities = new ArrayList<>() {};

        for (int i = 0; i < amount; i++) {
            entities.add(getRandomEntity());
        }
        balanceMap(entities);

        return entities;
    }


    public static HashMap<Class<?>, Integer> getTypesStats(List<Entity> entities){

        HashMap<Class<?>, Integer> populationMap = new HashMap<>();

        for (Entity entity : entities) {
            if (entity != null){
                populationMap.merge(entity.getClass(), 1, Integer::sum); // добавляем
            }
        }
        // добавляем кол-во пустых клеток
        populationMap.put(null, entities.size() - populationMap.values().stream().reduce(0, Integer::sum));

        return populationMap;
    }



    private void balanceMap(List<Entity> entities){
        logger.debug("BALANCING MAP");

        int origSize = entities.size();
        HashMap<Class<?>, Integer> popMap = getTypesStats(entities);

        while (!(popMap.containsKey(Hervibore.class) && popMap.containsKey(Grass.class)
                && popMap.containsKey(Predator.class)))
        {
            logger.debug(popMap);
            if (!popMap.containsKey(Hervibore.class)) {
                Class<?> mostFreq = popMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
                entities.remove(mostFreq);
                entities.add(createHervibore());
            }
            if (!popMap.containsKey(Grass.class)) {
                Class<?> mostFreq = popMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
                entities.remove(mostFreq);
                entities.add(createGrass());
            }
            if (!popMap.containsKey(Predator.class)) {
                Class<?> mostFreq = popMap.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
                entities.remove(mostFreq);
                entities.add(createPredator());
            }

            popMap = getTypesStats(entities);
        }

        Collections.shuffle(entities);

        if (origSize != entities.size()){
            throw new RuntimeException(String.format("Кол-во сущностей после баланса (%d) не совпадает с исходным (%d)",
                    entities.size(), origSize));
        }

        logger.debug("END BALANCING MAP");
    }


    public Hervibore createHervibore(){
        return new Hervibore(new Random().nextInt(maxHealth)+1,
                new Random().nextInt(maxSpeed)+1);
    }

    public Predator createPredator(){
        return new Predator(new Random().nextInt(maxHealth)+1,
                new Random().nextInt(maxSpeed)+1,
                new Random().nextInt(maxSpeed)+1);
    }

    public Grass createGrass(){
        return new Grass();
    }



}
