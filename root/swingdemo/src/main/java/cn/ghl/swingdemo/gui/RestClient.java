package cn.ghl.swingdemo.gui;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 1/2/2018
 */
public class RestClient extends Rest {

    private static JFrame frame;

    private RestClient() {
        super();
    }

    @Override
    public void setFilePathToTitle(String filePath) {
        frame.setTitle("Rest - " + filePath);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            frame = new JFrame("Rest");
            frame.setContentPane(new RestClient().getPanel_main());
            frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
