package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.container.grizzly.client.GrizzlyClientContainer;
import org.glassfish.tyrus.container.grizzly.client.GrizzlyClientSocket;

import javax.websocket.DeploymentException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 15:03
 */
public class Main {

//    http://www.programmingforliving.com/2013/08/jsr-356-java-api-for-websocket-client-api.html
    private static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        BasicConfigurator.configure();

        try {
            ClientManager client = ClientManager.createClient();
            client.getProperties().put(ClientManager.RECONNECT_HANDLER,new TyrusReconnectHandler()) ;


//            client.getScheduledExecutorService().shutdownNow();
            WebsocketClientGirzly socket = new WebsocketClientGirzly();
             client.connectToServer(socket, new URI("ws://localhost:8080/server/hello/1"));
//            latch.await();

            int i=0;
            while(true){
                try {
                    String text = String.valueOf(i);
                    logger.info("Try to send "+text);
                    socket.send(text);
                    logger.info("Send "+text);
                } catch (Exception e) {
                    logger.error("CAN'T SEND",e);
                  // IGNORE
                }
                i++;
                Thread.sleep(2000);
            }
        } catch (DeploymentException  |IOException| URISyntaxException | InterruptedException  e) {
            logger.error("ERROR",e);
        }
    }
}
