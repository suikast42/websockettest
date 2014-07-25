package com.siemag.test.websockets.client.tyrus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: vuru
 * Date: 25.07.2014
 * Time: 10:13
 */
public class ConsoleExitHandler extends Thread {

    @Override
    public void run() {
        BufferedReader buffer=new BufferedReader(new InputStreamReader(System.in));
        while (true){
            boolean exit = false;
            try {
                exit = buffer.readLine().equals("exit");
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(exit){
                System.err.println("EXIT");
                System.exit(0);
            }
            System.err.println("Run");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
