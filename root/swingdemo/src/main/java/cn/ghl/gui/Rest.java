package cn.ghl.gui;

import cn.ghl.pojo.MyHttpRequest;
import cn.ghl.utils.HttpRequestUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rest {

    private static Logger LOG = LoggerFactory
        .getLogger(Rest.class);

    private DefaultTableModel tableModel = new DefaultTableModel();

    private final String CONFIG_FILE = "/restConfig";

    private JPanel panel_right;

    private JTextField textFieldUrl;

    private JPanel inPutText;

    private JPanel buttons;

    private JButton buttonRun;

    private JTabbedPane tabbedPane1;

    private JTextArea textAreaResponse;

    private JPanel textArea;

    private JTextArea textAreaRequest;

    private JTable tableHeaders;

    private JButton addRowButton;

    private JButton delRowButton;

    private JComboBox comboBox_method;

    private JTree tree1;

    private JPanel panel_left;

    private JPanel panel_main;

    private JTextField textField_timeout;

    private JLabel lable_timeout;

    private JPanel panel_top;

    private JButton saveButton;

    private JButton openButton;

    public Rest() {

        String[] columnNames = {
            "Name", "Value"
        };

        Object[][] data = {
            {"Content-Type", "application/json"}
        };

        tableModel.setDataVector(data, columnNames);

        tableHeaders.setModel(tableModel);

        addRowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object[] tmpRow = {"", ""};
                tableModel.addRow(tmpRow);
            }
        });

        delRowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowIndex = tableHeaders.getSelectedRow();
                if (rowIndex != -1) {
                    tableModel.removeRow(rowIndex);
                }
            }
        });

        open();

        //initJTree();

        buttonRun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String method = (String) comboBox_method.getSelectedItem();
                if ("GET".equalsIgnoreCase(method)) {
                    doGet();
                } else if ("POST".equalsIgnoreCase(method)) {
                    doPost();
                } else {
                    textAreaResponse.setText(method);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                open();
            }
        });
    }

    private void save() {
        try {
            //序列化文件輸出流
            OutputStream outputStream = new FileOutputStream(CONFIG_FILE);
            //构建缓冲流
            BufferedOutputStream buf = new BufferedOutputStream(outputStream);
            //构建字符输出的对象流
            ObjectOutputStream obj = new ObjectOutputStream(buf);

            MyHttpRequest myHttpRequest = getMyHttpRequest();

            //对象
            obj.writeObject(myHttpRequest);
            //关闭流
            obj.close();
            buf.close();
            outputStream.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void open() {
        try {

            InputStream inputStream = new FileInputStream(CONFIG_FILE);

            //构建缓冲流
            BufferedInputStream buf = new BufferedInputStream(inputStream);
            //构建字符输入的对象流
            ObjectInputStream obj = new ObjectInputStream(buf);

            MyHttpRequest myHttpRequest = (MyHttpRequest) obj.readObject();

            LOG.debug(myHttpRequest.toString());

            this.textFieldUrl.setText(myHttpRequest.getUrl());
            this.comboBox_method.setSelectedItem(myHttpRequest.getMethod());
            this.textField_timeout.setText("" + myHttpRequest.getTimeout());
            this.textAreaRequest.setText(myHttpRequest.getJsonParam());

            //关闭流
            obj.close();
            buf.close();
            inputStream.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void initJTree() {
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("软件部");
        node1.add(new DefaultMutableTreeNode("小花"));
        node1.add(new DefaultMutableTreeNode("小虎"));
        node1.add(new DefaultMutableTreeNode("小龙"));

        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("销售部");
        node2.add(new DefaultMutableTreeNode("小叶"));
        node2.add(new DefaultMutableTreeNode("小雯"));
        node2.add(new DefaultMutableTreeNode("小夏"));

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("职员管理");

        top.add(new DefaultMutableTreeNode("总经理"));
        top.add(node1);
        top.add(node2);

        DefaultTreeModel model = new DefaultTreeModel(top);

        tree1.setModel(model);
    }

    private MyHttpRequest getMyHttpRequest() {

        // 获取http post请求的url
        String url = textFieldUrl.getText();
        LOG.debug(url);

        // 获取http post请求内容
        String jsonParam = textAreaRequest.getText();

        // 获取http post请求的消息头
        Map headers = new HashMap();
        int columnCount = tableHeaders.getColumnCount();
        int rowCount = tableHeaders.getRowCount();
        if (columnCount >= 2) {
            for (int row = 0; row < rowCount; row++) {
                /*
                for (int column = 0; column < columnCount; column++) {
                    Object tableValue = tableHeaders.getValueAt(row, column);
                    System.out.print(tableValue);
                }
                */
                String name = (String) tableHeaders.getValueAt(row, 0);
                String value = (String) tableHeaders.getValueAt(row, 1);
                headers.put(name, value);
            }
        }

        String method = (String) comboBox_method.getSelectedItem();

        MyHttpRequest myHttpRequest = new MyHttpRequest();
        myHttpRequest.setUrl(url);
        myHttpRequest.setHeaders(headers);
        myHttpRequest.setJsonParam(jsonParam);
        myHttpRequest.setMethod(method);

        String strTimeout = textField_timeout.getText();
        if (NumberUtils.isNumber(strTimeout)) {
            int timeout = NumberUtils.createInteger(strTimeout);
            myHttpRequest.setTimeout(timeout);
        }

        return myHttpRequest;
    }


    private void doPost() {
        MyHttpRequest myHttpRequest = getMyHttpRequest();

        // 执行http post
        String result = HttpRequestUtils.httpPost(myHttpRequest);
        textAreaResponse.setText(result);
        LOG.debug(result);
    }

    private void doGet() {
        MyHttpRequest myHttpRequest = getMyHttpRequest();

        // 执行http get
        String result = HttpRequestUtils.httpGet(myHttpRequest);
        textAreaResponse.setText(result);
        LOG.debug(result);
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            JFrame frame = new JFrame("Rest");
            frame.setContentPane(new Rest().panel_main);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
