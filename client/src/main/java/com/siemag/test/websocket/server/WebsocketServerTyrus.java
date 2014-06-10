package com.siemag.test.websocket.server;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.server.Server;

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
            logger.info ("Please press a key to stop the server.");
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new WebsocketServerTyrus().runServer();
    }
}
