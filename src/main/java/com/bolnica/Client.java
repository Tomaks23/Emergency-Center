package com.bolnica;

import lombok.extern.log4j.Log4j;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.Socket;

@Log4j
public class Client extends Gui {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 8005;
    public static Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;


    {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    Client() {

        initButtonListeners();
    }

    public static void main(String[] args) {
        new Client();

    }

    private void initButtonListeners() {

        submitBtn.addActionListener(submitBtnListener());

        status.addActionListener(statusListener());

        inputCardNum.addKeyListener(getKeyAdapterForDigit(inputCardNum));

        inputName.addKeyListener(getKeyAdapterForChar(inputName));

        inputLastName.addKeyListener(getKeyAdapterForChar(inputLastName));

    }

    private ActionListener statusListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                printWriter.println("Osvezi");
                serverTextArea.setText(getResponseFromServer());

            }
        };
    }
    private String getResponseFromServer(){
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            log.error("Request declined.",e);
        }
        return "error";
    }

    private ActionListener submitBtnListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               handleSubmit();
               resetFields();
            }
        };
    }

    private void handleSubmit() {
        String sobastr = getSelectedRoom();
        log.info("Izabrana soba " + sobastr);
        printWriter.println(sobastr);


        serverResponse();
    }

    private String getSelectedRoom() {
        return boxRoom.getItemAt(boxRoom.getSelectedIndex());

    }

    private void serverResponse() {

        String res1 = getResponseFromServer();
        String res2 = getResponseFromServer();
        serverTextArea.setText(res1 + "\n" + res2);

    }

    private KeyAdapter getKeyAdapterForDigit(JTextField inputCardNum) {
        return new KeyAdapter() {
            final Border b = inputName.getBorder();

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                    inputCardNum.setBorder(new LineBorder(Color.red, 1));
                } else {
                    inputCardNum.setBorder(b);
                }

            }
        };

    }

    private KeyAdapter getKeyAdapterForChar(JTextField inputCardNum) {
        return new KeyAdapter() {
            final Border b = inputName.getBorder();

            @Override
            public void keyTyped(KeyEvent e) {

                if (Character.isDigit(e.getKeyChar()) && (e.getKeyChar() != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                    inputCardNum.setBorder(new LineBorder(Color.red, 1));
                } else {
                    inputCardNum.setBorder(b);
                }

            }
        };

    }

    private void resetFields() {
        inputName.setText("");
        inputLastName.setText("");
        inputCardNum.setText("");
        buttonGroup.clearSelection();

    }


}
