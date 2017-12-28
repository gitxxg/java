package cn.ghl.gui;

import javax.swing.*;

public class MainApp {
    private JTextArea textOne;
    private JButton buttonOne;
    private JPanel JP1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainApp");
        frame.setContentPane(new MainApp().JP1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
