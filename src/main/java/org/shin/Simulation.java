package org.shin;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shin.entities.Map;
import org.shin.utils.Actions;

import java.io.IOException;
import java.io.Writer;

// TODO: сюда впихнуть nextTurn(), startSimulation(), pauseSimulation()
public class Simulation extends Thread {
    public static final Logger logger = LogManager.getLogger();
    private Map map;
    private Writer renderWriter;
    private int timeout;
    private boolean isRunning;

    public Simulation(Map map, Writer renderWriter, int timeout) {
        this.map = map;
        this.renderWriter = renderWriter;
        this.timeout = timeout;
    }


    @Override
    public void run() {
        while (isRunning) {
            try {
                this.nextTurn();
                Thread.sleep(timeout * 1000L);
            } catch (InterruptedException | IOException e) {
                logger.error(e.toString());
            }
        }

    }

    public void nextTurn() throws IOException {
        logger.info("NEXT TURN");
        Actions.moveAll(map, renderWriter);

    }

    public void startSimulation() {
        this.isRunning = true;
        logger.info("START");
        try {
            Actions.initActions(map, renderWriter);
        } catch (IOException e) {
            logger.error(e.toString());
        }

        this.start();

    }

    public void stopSimulation() {
        this.isRunning = false;
        logger.info("Quitting");
    }


}
