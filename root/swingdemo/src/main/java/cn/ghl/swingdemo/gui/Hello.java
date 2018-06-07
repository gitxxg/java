package cn.ghl.swingdemo.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/2/2018
 */
public class Hello {

    private JPanel Jp1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello");
        frame.setContentPane(new Hello().Jp1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
