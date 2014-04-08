package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 15:03
 */
public class Main {


    private static CountDownLatch latch;
    private static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        BasicConfigurator.configure();
        latch = new CountDownLatch(1);

        try {
            ClientManager client = ClientManager.createClient();
            client.getProperties().put(ClientManager.RECONNECT_HANDLER,new TyrusReconnectHandler()) ;
            client.getScheduledExecutorService().shutdownNow();
            WebsocketClientGirzly socket = new WebsocketClientGirzly();
            client.connectToServer(socket, new URI("ws://localhost:8080/server/hello/1"));
//            latch.await();

            int i=0;
            while(true){
                socket.send(String.valueOf(i));
                i++;
                Thread.sleep(2000);
            }
        } catch (DeploymentException  |IOException| URISyntaxException | InterruptedException  e) {
            logger.error("ERROR",e);
        }
    }
}
