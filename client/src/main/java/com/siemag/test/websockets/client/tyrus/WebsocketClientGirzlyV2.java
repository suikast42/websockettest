package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 14:51
 */
@ClientEndpoint
public class WebsocketClientGirzlyV2 {

    private class SimpleReconnectionHandler extends ClientManager.ReconnectHandler {
        @Override
        public boolean onDisconnect(CloseReason closeReason) {
            return true;
        }

        @Override
        public boolean onConnectFailure(Exception exception) {
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
        ClientManager client = ClientManager.createClient();
        client.getProperties().put(ClientManager.RECONNECT_HANDLER, new SimpleReconnectionHandler());

        try {
            client.connectToServer(this, connectionURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return this;
    }

}





