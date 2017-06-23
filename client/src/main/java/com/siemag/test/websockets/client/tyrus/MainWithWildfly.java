package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 15:03
 */
public class MainWithWildfly {

    //    http://www.programmingforliving.com/2013/08/jsr-356-java-api-for-websocket-client-api.html
    private static Logger logger = Logger.getLogger(MainWithWildfly.class);
    private static String url1 = "ws://localhost:8080/server/hello/1";
    private static String url2 = "ws://localhost:8080/server/hello/2";
    private static String url3 = "ws://localhost:8080/server/hello/3";
    private static String url4 = "ws://localhost:8080/server/hello/4";

    public static void main(String[] args) throws IOException, InterruptedException {
        BasicConfigurator.configure();



        int i = 0;

        WebsocketClientGirzlyV2 socket1 = new WebsocketClientGirzlyV2(url1);
//        WebsocketClientGirzlyV2 socket2 = new WebsocketClientGirzlyV2(url2);
//        WebsocketClientGirzlyV2 socket3 = new WebsocketClientGirzlyV2(url3);
//        WebsocketClientGirzlyV2 socket4 = new WebsocketClientGirzlyV2(url4);
        while (true) {
            try {
                String text = String.valueOf(i);
                logger.info("Sending to sockets "+text);
                socket1.send(text + " from client1");
//                socket2.send(text + " from client1");
//                socket3.send(text + " from client1");
//                socket4.send(text + " from client1");
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
//        while (true){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
