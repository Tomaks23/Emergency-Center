package com.bolnica;

import lombok.extern.log4j.Log4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
@Log4j
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(8005);
        ReleaseController rl = new ReleaseController();
        rl.start();
        log.debug("Server startovan. Ceka se klijent. ");
        while (true) {

            Socket s = listener.accept();
            log.info("Klijent je konektovan: " +s);
           // System.out.println("Klijent je konektovan:" + s);
            new ClientHandler(s);
        }
    }
}