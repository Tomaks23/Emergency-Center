package com.bolnica;

import java.util.Random;

public class ReleaseController extends Thread {
    private final Random random = new Random();

    @Override
    public void run() {

        while (true) {

            try {
                sleep(1000 * (random.nextInt((10 - 5) + 1) + 5));

                int chooseRoom = random.nextInt(3 + 1);

                if (ClientHandler.counter[chooseRoom].get() > 0) {
                    System.out.println("Odeljenje broj " + (chooseRoom + 1) + " (" + ClientHandler.rooms[chooseRoom] + ")");
                    int numOfPatients = ClientHandler.counter[chooseRoom].decrementAndGet();
                    System.out.println("Soba " + ClientHandler.rooms[chooseRoom] + " se sada umanjuje za jednog pacijenta sada");
                    System.out.println("Soba " + ClientHandler.rooms[chooseRoom] + " sada ima " + numOfPatients + " pacijenta");
                }

            } catch (InterruptedException e) {
            }
        }
    }
}
