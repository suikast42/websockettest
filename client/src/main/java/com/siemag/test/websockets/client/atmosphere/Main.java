package com.siemag.test.websockets.client.atmosphere;

import org.apache.log4j.BasicConfigurator;

/**
 * @author: vuru
 * Date: 18.03.14
 * Time: 16:08
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        WebsocketStringClientAtmosphere socket1 = new WebsocketStringClientAtmosphere("1");
//        WebsocketStringClientAtmosphere socket2 = new WebsocketStringClientAtmosphere("2");
//        WebsocketStringClientAtmosphere socket3 = new WebsocketStringClientAtmosphere("3");
//        WebsocketStringClientAtmosphere socket4 = new WebsocketStringClientAtmosphere("4");


        int i=0;
        while (true){
            socket1.sendObject(String.valueOf(i));
//            socket2.sendObject(String.valueOf(i));
//            socket3.sendObject(String.valueOf(i));
//            socket4.sendObject(String.valueOf(i));
            i++;
            Thread.sleep(1000);
        }
    }

}
