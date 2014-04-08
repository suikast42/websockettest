package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.Logger;

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 14:51
 */
@ClientEndpoint
public class WebsocketClientGirzly {


    private Logger logger = Logger.getLogger(getClass());

    private Session mySession;

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected ... " + session.getId());
        try {
            session.getBasicRemote().sendText("start");
            mySession = session;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            logger.info("Received ...." + message);
            String userInput = bufferRead.readLine();
            return userInput;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }

    public void send(String text) {
        mySession.getAsyncRemote().sendText(text);
    }
}
