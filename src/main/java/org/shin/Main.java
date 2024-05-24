package org.shin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shin.entities.Map;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger();


    public static void main(String[] args) {


        try (var writer = new BufferedWriter(new OutputStreamWriter(System.out));
             var scanner = new Scanner(System.in))
        {

            Map map = new Map(10, 3);
            Simulation simulation = new Simulation(map, writer, 1);
            simulation.startSimulation();


            if (scanner.nextLine() != null) {
                simulation.stopSimulation();
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
