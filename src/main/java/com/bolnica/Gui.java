package com.bolnica;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Optional;

public class Gui extends JFrame {
    public JLabel lblName = new JLabel("Ime *:");
    public JTextField inputName = new JTextField(15);
    public JLabel lblLastName = new JLabel("Prezime *: ");
    public JTextField inputLastName = new JTextField(15);
    public JLabel lblCardNum = new JLabel("Broj Knjizice *: ");
    public JTextField inputCardNum = new JTextField(11);
    public JLabel lblConscience = new JLabel("Da li je pacijent u svesnom stanju: ");
    public JRadioButton rbYes = new JRadioButton("Da");
    public JRadioButton rbNo = new JRadioButton("Ne");
    public ButtonGroup buttonGroup = new ButtonGroup();
    public JButton submitBtn = new JButton("Posalji");
    public JLabel lblRoom = new JLabel("Izaberite odeljenje:");
    public JComboBox<String> boxRoom = new JComboBox<>(ClientHandler.rooms);
    public JTextArea serverTextArea = new JTextArea();
    public JButton status = new JButton("Stanje na odeljenjima");

    public Gui() {
        prozor();
    }

    private void prozor() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        var c = new GridBagConstraints();
        setTitle("Bolnica");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        c.insets = new Insets(10, 5, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(lblName, c);
        c.gridx = 1;
        c.gridy = 0;
        panel.add(inputName, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(lblLastName, c);

        c.gridx = 1;
        c.gridy = 1;
        panel.add(inputLastName, c);

        c.gridx = 0;
        c.gridy = 2;
        panel.add(lblCardNum, c);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(inputCardNum, c);

        buttonGroup.add(rbYes);
        buttonGroup.add(rbNo);
        c.gridx = 0;
        c.gridy = 3;
        panel.add(lblConscience, c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(rbYes, c);
        c.gridx = 2;
        c.gridy = 3;
        panel.add(rbNo, c);

        c.gridx = 0;
        c.gridy = 4;
        panel.add(lblRoom, c);
        c.gridx = 1;
        c.gridy = 4;
        panel.add(boxRoom, c);

        c.gridx = 1;
        c.gridy = 5;
        submitBtn.setEnabled(false);
        panel.add(submitBtn, c);

        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        serverTextArea.setPreferredSize(new Dimension(350, 40));
        serverTextArea.setEditable(false);
        panel.add(serverTextArea, c);

        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        panel.add(status, c);

        disableSubmitButton();
        initListeners();
        add(panel);
        setVisible(true);

    }



    private void disableSubmitButton(){
        submitBtn.setEnabled(false);

    }

    private void enableSubmitButton(){
        submitBtn.setEnabled(true);
    }

    private boolean isComponentEmpty(){
        return Optional.of(inputName.getText().isEmpty()).orElse(true) ||
                Optional.of(inputLastName.getText().isEmpty()).orElse(true) ||
                Optional.of(inputCardNum.getText().isEmpty()).orElse(true);
    }

    private void validateComponents(){
        if(isComponentEmpty())
            disableSubmitButton();
        else
            enableSubmitButton();
    }

    private DocumentListener getTextFieldChangeListener() {
        return new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                validateComponents();
            }

            public void removeUpdate(DocumentEvent e) {
                validateComponents();
            }

            public void insertUpdate(DocumentEvent e) {
                validateComponents();
            }
        };
    }


    private void initListeners() {
        inputName.getDocument().addDocumentListener(getTextFieldChangeListener());
        inputLastName.getDocument().addDocumentListener(getTextFieldChangeListener());
        inputCardNum.getDocument().addDocumentListener(getTextFieldChangeListener());
    }


}
