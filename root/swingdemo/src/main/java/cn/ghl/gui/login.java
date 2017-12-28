package cn.ghl.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton GET;
    private JTextArea textArea1;
    private JButton DELETE;
    private JButton PUT;
    private JButton POST;
    private JTabbedPane tabbedPane1;
    private JSpinner spinner1;
    private JTextArea textArea2;
    private JTextArea textArea3;

    public login() {
        GET.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = textField1.getText();
                System.out.println("login................");
                System.out.println(text);
                textField2.setText(text);
                textArea1.setText(text);
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            JFrame frame = new JFrame("login");
            frame.setContentPane(new login().panel1);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setResizable(false);
        } catch (Exception e) {
            System.out.println("Substance Raven Graphite failed to initialize: " + e);
        }
    }
}
