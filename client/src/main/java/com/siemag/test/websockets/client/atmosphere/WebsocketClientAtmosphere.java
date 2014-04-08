package com.siemag.test.websockets.client.atmosphere;

import org.apache.log4j.Logger;
import org.atmosphere.wasync.*;
import org.atmosphere.wasync.impl.AtmosphereClient;
import org.atmosphere.wasync.impl.AtmosphereRequest;
import org.atmosphere.wasync.impl.DefaultOptionsBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A Sessionwrapper for websockets. Underlying implementation is the wasync library.
 *
 * @author: vuru
 * Date: 26.03.2014
 * Time: 18:22
 */
public abstract class WebsocketClientAtmosphere<T> {
    private Logger logger = Logger.getLogger(getClass());
    protected final StringBuilder connectionURL;
    private Socket socket;
    private int reconnectionIntervall = 3 * 1000;
    private AtomicBoolean lock = new AtomicBoolean(Boolean.FALSE);
    private AtmosphereRequest.AtmosphereRequestBuilder requestBuilder;
    private DefaultOptionsBuilder optionsBuilder;
    private AtmosphereClient client;


    /**
     * Connect on a channel to http://wmsserver:8080/wms/...
     *
     * @param channelname
     * @param pathParams
     */
    public WebsocketClientAtmosphere(String channelname, String... pathParams) {
        this("localhost", 8080, "server", channelname, pathParams);
    }

    public WebsocketClientAtmosphere(String server, int port, String appName, String channelname, String... pathParams) {
        connectionURL = new StringBuilder("http://")
                .append(server)
                .append(":")
                .append(String.valueOf(port))
                .append("/")
                .append(appName)
                .append(getAdressForChannel(channelname, pathParams));
        connect();
    }

    private void connect() {
        client = ClientFactory.getDefault().newClient(AtmosphereClient.class);
        requestBuilder = client.newRequestBuilder();
        optionsBuilder = client.newOptionsBuilder();

        requestBuilder.transport(Request.TRANSPORT.WEBSOCKET).uri(connectionURL.toString());
        optionsBuilder.pauseBeforeReconnectInSeconds(10).reconnect(true);

        if (getAtmosphereDecoder() != null) {
            requestBuilder.decoder(getAtmosphereDecoder());
        }

        if (getAtmosphereEncoder() != null) {
            requestBuilder.encoder(getAtmosphereEncoder());
        }

        socket = client.create(optionsBuilder.build());

        socket.on(Event.ERROR, new Function<T>() {
            @Override
            public void on(T o) {
                logger.error("Connection lost to WS " + connectionURL.toString() + " start reconnection " + o.toString());
            }
        });

        socket.on(Event.CLOSE, new Function<T>() {
            @Override
            public void on(T o) {
                logger.error("Connection closed start recoennetion " + o.toString());
            }
        });


        socket.on(Event.MESSAGE, onMessageHandler());

        if (onOpenHandler() != null) {
            socket.on(Event.OPEN,
                    onOpenHandler());
        }

        if (onCloseHandler() != null) {
            socket.on(Event.CLOSE, onCloseHandler());
        }

        if (onReopenHandler() != null) {
            socket.on(Event.REOPENED, onReopenHandler());
        }
//        for (final Event event : Event.values()) {
//            socket.on(event, new Function<T>() {
//                @Override
//                public void on(T o) {
//                    System.err.println(event.name() + " --->> " + o.toString());
//                }
//            });
//        }

        try {
            socket.open(requestBuilder.build(),15,TimeUnit.SECONDS);


        } catch (IOException  e) {

            logger.error("Server not available ", e);
        }

    }



    public void sendObject(T obj) {
        try {
            socket.fire(obj);
        } catch (Exception e) {
            logger.warn("Can't send message " + obj + toString(), e);
        }
    }


    /**
     * Handle the receive of the message
     *
     * @return
     */
    public abstract Function<T> onMessageHandler();

    /**
     * Do Something after the socket open
     *
     * @return
     */
    public Function<String> onOpenHandler() {
        return null;
    }

    /**
     * Handle before socke close
     *
     * @return
     */
    public Function<T> onCloseHandler() {
        return null;
    }

    /**
     * Handle reconnmection event
     *
     * @return
     */
    public Function<T> onReopenHandler() {
        return null;
    }

    /**
     * The decoder must not be inner class. Define it as a static inner class or a single class
     *
     * @return
     */
    public Decoder<String, T> getAtmosphereDecoder() {
        return null;
    }

    /**
     * The encoder must not be inner class. Define it as a static inner class or a single class
     *
     * @return
     */
    public Encoder<T, String> getAtmosphereEncoder() {
        return null;
    }

    private String getAdressForChannel(String channelName, String... pathParams) {
        StringBuilder builder = new StringBuilder().append("/").append(channelName);

        if (pathParams != null && pathParams.length > 0) {
            for (String pathParam : pathParams) {
                if (!builder.toString().endsWith("/")) {
                    builder.append("/");
                }
                builder.append(pathParam);
            }
        } else {
            builder.append("/");
        }
        return builder.toString();
    }


}
