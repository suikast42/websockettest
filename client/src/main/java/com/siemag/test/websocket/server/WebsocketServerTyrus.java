package com.siemag.test.websocket.server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;

import javax.websocket.DeploymentException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author: vuru
 * Date: 06.06.2014
 * Time: 11:41
 */
public class WebsocketServerTyrus {
    private Logger logger = Logger.getLogger(getClass());

    public void runServer() {
        Server server = new Server("localhost", 8080, "/server", null, HelloWorldEndpoint.class);

        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            logger.info("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

    public void runServerAnsRestartEvery(int onlineTimeInSeconds, int offlineTimeInSeconds) {
        logger.info("Online every " + onlineTimeInSeconds + " seconds  offline every " + offlineTimeInSeconds + " seconds");
        Server server = new Server("localhost", 8080, "/server", null, HelloWorldEndpoint.class);

        int count = 0;
        while (true) {
            try {
                logger.info("Loop " + (count++));
                server.start();
                Thread.sleep(onlineTimeInSeconds * 1000);
                server.stop();
                Thread.sleep(offlineTimeInSeconds * 1000);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void runServerAnsRestartEveryAsThread(final int onlineTimeInSeconds, final int offlineTimeInSeconds) {
        new Thread(() -> runServerAnsRestartEvery(onlineTimeInSeconds, offlineTimeInSeconds)).start();
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new WebsocketServerTyrus().runServerAnsRestartEvery(60, 60);
    }
}
