package com.siemag.test.websockets.client.tyrus;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.BooleanType;
import org.apache.log4j.Logger;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.CloseReason;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author: vuru
 * Date: 08.04.2014
 * Time: 15:31
 */
public class TyrusReconnectHandler extends ClientManager.ReconnectHandler {


    private Logger logger = Logger.getLogger(Main.class);


    public TyrusReconnectHandler() {
    }


    @Override
    public boolean onDisconnect(CloseReason closeReason) {
        logger.error("Do Reconnect "+closeReason.toString());
        return true;
    }

    @Override
    public boolean onConnectFailure(Exception exception) {
        logger.error("Do Reconnect "+exception.getMessage());
        return true;
    }


}
