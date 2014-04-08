package com.siemag.test.websockets.client.tyrus;

import org.apache.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.CloseReason;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 15:31
 */
public class TyrusReconnectHandler extends ClientManager.ReconnectHandler {

    private Logger logger = Logger.getLogger(Main.class);
    @Override
    public boolean onDisconnect(CloseReason closeReason) {
        return true;
    }

    @Override
    public boolean onConnectFailure(Exception exception) {
        return true;
    }
}
