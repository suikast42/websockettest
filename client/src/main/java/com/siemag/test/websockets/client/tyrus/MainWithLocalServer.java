package com.siemag.test.websockets.client.tyrus;

import com.siemag.test.websocket.server.WebsocketServerTyrus;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 15:03
 */
public class MainWithLocalServer {

    //    http://www.programmingforliving.com/2013/08/jsr-356-java-api-for-websocket-client-api.html
    private static Logger logger = Logger.getLogger(MainWithLocalServer.class);
    private static String url1 = "ws://localhost:8080/server/hello/1";
    private static String url2 = "ws://localhost:8080/server/hello/2";
    private static String url3 = "ws://localhost:8080/server/hello/3";
    private static String url4 = "ws://localhost:8080/server/hello/4";

    public static void main(String[] args) throws IOException, InterruptedException {
        BasicConfigurator.configure();
        new WebsocketServerTyrus().runServerAnsRestartEveryAsThread(15,5);

        WebsocketClientGirzlyV2 socket1 = new WebsocketClientGirzlyV2(url1);
        int i=0;
        while (true) {
            try {
                String text =  String.valueOf(i) + " from client1";
                logger.info("Sending to sockets " + text);
                socket1.send(text);
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
