package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.container.grizzly.client.GrizzlyClientSocket;
import org.glassfish.tyrus.core.Utils;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 14:51
 */
@ClientEndpoint
public class WebsocketClientGirzlyV2 {

    private static class SimpleReconnectionHandler extends ClientManager.ReconnectHandler {
        private static AtomicInteger onDisconnectCounter = new AtomicInteger(0) ;
        private static AtomicInteger onConnectFailureCounter = new AtomicInteger(0) ;
        private Logger logger = Logger.getLogger(getClass());
        public boolean onDisconnect(CloseReason closeReason) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.debug("onDisconnect "+onDisconnectCounter.incrementAndGet());
            return true;
        }

        @Override
        public boolean onConnectFailure(Exception exception) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.debug("onConnectFailure "+onConnectFailureCounter.incrementAndGet());
            return true;
        }
    }

    private Logger logger = Logger.getLogger(getClass());
    private Session mySession;
    private final URI connectionURI;

    public WebsocketClientGirzlyV2(String uri) {
        try {
            connectionURI = new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        startConnection();
    }

    @OnOpen
    public final void onOpen(Session session) {
        mySession = session;
        logger.info("Connected ... " + mySession.getId());
        mySession.getAsyncRemote().sendText("start");
//        mySession = session;
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("Revceived on client side " + message);
    }

    @OnClose
    public final void onClose(Session session, CloseReason closeReason) {
    }

    @OnError
    public final void onError(Session session, Throwable t) {
    }

    public final void send(String text) {
        logger.info(String.format("Sending %s ", text));
        mySession.getAsyncRemote().sendText(text);
        logger.info(String.format("%s sended", text));
    }

    private final WebsocketClientGirzlyV2 startConnection() {
        logger.info("Connecting to "+connectionURI.toString());
        ClientManager client = ClientManager.createClient();
        client.getProperties().put(ClientProperties.RECONNECT_HANDLER, new SimpleReconnectionHandler());
//        client.getProperties().put(GrizzlyClientSocket.WORKER_THREAD_POOL_CONFIG, new SimpleReconnectionHandler());
//        client.getProperties().put(GrizzlyClientSocket.SELECTOR_THREAD_POOL_CONFIG, new SimpleReconnectionHandler());
        try {
            client.connectToServer(this, connectionURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("Connected to "+connectionURI.toString());
        return this;
    }

}





