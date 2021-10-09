package com.bolnica;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler extends Thread {


    final BufferedReader bufferedReader;
    final PrintWriter printWriter;
    final Socket socket;
    static AtomicInteger[] counter = {new AtomicInteger(0), new AtomicInteger(0), new AtomicInteger(0), new AtomicInteger(0)};
    public static String[] rooms = {"Neurologija", "Traumatologija", "Oftamologija", "Kovid"};


    public ClientHandler(Socket s) throws IOException {
        this.socket = s;
        bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);
        start();
    }


    @Override
    public void run() {


        try {
            while (true) {


                String odgovor = bufferedReader.readLine();
                int index = 0;
                while (index < rooms.length) {
                    if (odgovor.equals(rooms[index])) {
                        break;
                    }
                    index++;
                }
                if (index < rooms.length) {

                    StringBuilder text = new StringBuilder();
                    if (counter[index].get() < 10) {
                        int patientNum = counter[index].incrementAndGet();
                        text.append("Pacijent je smesten! \n");
                        System.out.println("Pacijent je smesten na " + odgovor + " ukupno pacijentata: " + patientNum);

                    } else {
                        text.append("Soba je vec puna! \n");
                        System.out.println("Soba na odeljenju " + odgovor + " je popunjena. Molimo sacekajte!");
                    }

                    text.append(printStatus());
                    printWriter.println(text);
                } else {

                    printWriter.println(printStatus());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println();

            try {
                bufferedReader.close();
                printWriter.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private StringBuilder printStatus() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rooms.length; i++) {
            result.append( rooms[i] + "- " + counter[i].get());
            result.append(", ");
        }
        result.deleteCharAt(result.length()-2);
        return result;
    }

}
