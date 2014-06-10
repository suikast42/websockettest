package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 15:03
 */
public class MainV2 {

    //    http://www.programmingforliving.com/2013/08/jsr-356-java-api-for-websocket-client-api.html
    private static Logger logger = Logger.getLogger(MainV2.class);
    private static String url = "ws://localhost:8080/server/hello/1";

    public static void main(String[] args) {
        BasicConfigurator.configure();
        WebsocketClientGirzlyV2 socket = new WebsocketClientGirzlyV2(url);

        int i = 0;
        while (true) {
            try {
                String text = String.valueOf(i);
                socket.send(text + " from client1");
            } catch (Exception e) {
                logger.error("CAN'T SEND", e);
                // IGNORE
            }
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //
            }
        }
    }
}
