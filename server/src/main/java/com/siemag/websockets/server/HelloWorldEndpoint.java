package com.siemag.websockets.server;

import org.apache.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @author: vuru
 * Date: 18.03.14
 * Time: 15:55
 */
@ServerEndpoint("/hello/{channelID}")
public class HelloWorldEndpoint {

    private Logger logger = Logger.getLogger(getClass());
    @OnMessage
    public String onMessage(String message,Session  session,@PathParam(value ="channelID" ) String channelID) {
        logger.info("Received : from "+channelID+" " + message );
        return message;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam(value ="channelID" ) String channelID) {
        logger.info("WebSocket opened on channelID " +channelID);
        session.getAsyncRemote().sendObject("Hi you're connected on channel "+channelID) ;
    }
    @OnClose
    public void onClose(Session session,CloseReason reason) {
        logger.info("Closing a WebSocket due to   session.getPathParameters().get(\"channelID\")  " + session.getPathParameters().get("channelID"));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("Error in serverend  session.getPathParameters().get(\"channelID\")  "+ session.getPathParameters().get("channelID"),throwable);
    }
}
