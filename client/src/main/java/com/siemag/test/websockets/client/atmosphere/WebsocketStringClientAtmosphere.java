package com.siemag.test.websockets.client.atmosphere;

import org.apache.log4j.Logger;
import org.atmosphere.wasync.*;

/**
 * A Sessionwrapper   for websockets. Underlying implementation is the wasync library.
 *
 * @author: vuru
 * Date: 26.03.2014
 * Time: 18:22
 */
public class WebsocketStringClientAtmosphere extends WebsocketClientAtmosphere<String> {
    private final Logger logger;

    public WebsocketStringClientAtmosphere(String... pathParams) {
        this("localhost", 8080, "server", "hello", pathParams);
    }

    public WebsocketStringClientAtmosphere(String server, int port, String appName, String channelname, String... pathParams) {
        super(server, port, appName, channelname, pathParams);
        logger = Logger.getLogger(connectionURL.toString());
    }



    @Override
    public Function<String> onMessageHandler() {
        final Logger logger = Logger.getLogger(connectionURL.toString());
        return new Function<String>() {
            @Override
            public void on( String s) {
                logger.info("I received: " + s );
            }
        };
    }
}
