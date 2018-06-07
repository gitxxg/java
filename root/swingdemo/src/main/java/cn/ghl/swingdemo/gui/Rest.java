package cn.ghl.swingdemo.gui;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;

import cn.ghl.swingdemo.pojo.MyRequestHeader;
import cn.ghl.swingdemo.pojo.MyRequestMethod;
import cn.ghl.swingdemo.pojo.MyRequestNode;
import cn.ghl.swingdemo.pojo.MyRequestNodeSave;
import cn.ghl.swingdemo.pojo.MyRequestNodeTree;
import cn.ghl.swingdemo.utils.HttpRequestUtil;
import cn.ghl.swingdemo.utils.file.ConfigFileUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rest {

    private static Logger LOG = LoggerFactory
        .getLogger(Rest.class);

    private static final Gson gson = new GsonBuilder().create();

    private DefaultTableModel tableModel = new DefaultTableModel();

    private final String FILE_SUFFIX = ".GHL";

    private File configFile;

    private TreePath movePath;

    private JPopupMenu popMenu;

    //-----

    private JTextField textFieldUrl;

    private JButton buttonRun;

    private JTextArea textAreaResponse;

    private JTextArea textAreaRequest;

    private JTable tableHeaders;

    private JButton addRowButton;

    private JButton delRowButton;

    private JComboBox comboBox_method;

    private JTree tree;

    private JPanel panel_main;

    private JTextField textField_timeout;

    private JButton saveButton;

    private JButton openButton;

    private JTextField textFieldName;

    private JPanel panel_top;

    private JPanel panel_bottom;

    private JPanel scrollPanelResponse;

    Rest() {

        String[] columnNames = {
            "Name", "Value"
        };
        Object[][] data = {
            {"Content-Type", "application/json"}
        };
        tableModel.setDataVector(data, columnNames);
        tableHeaders.setModel(tableModel);

        addRowButton.addActionListener(e ->
            tableModel.addRow(new Object[]{"", ""})
        );

        delRowButton.addActionListener(e -> {
            int rowIndex = tableHeaders.getSelectedRow();
            if (rowIndex != -1) {
                tableModel.removeRow(rowIndex);
            }
        });

        buttonRun.addActionListener(e -> {
            String method = (String) comboBox_method.getSelectedItem();
            if ("GET".equalsIgnoreCase(method)) {

                MyRequestNode myRequest = getMyRequestFromPanel();
                // 执行http get
                String result = HttpRequestUtil.httpGet(myRequest);
                textAreaResponse.setText(result);
                LOG.debug("Http Get Result: {}", result);
            } else if ("POST".equalsIgnoreCase(method)) {

                MyRequestNode myRequest = getMyRequestFromPanel();
                // 执行http post
                String result = HttpRequestUtil
                    .httpPost(myRequest);
                textAreaResponse.setText(result);
                LOG.debug(result);
                LOG.debug("Http Post Result: {}", result);
            } else {
                textAreaResponse.setText(method);

            }
        });

        saveButton.addActionListener(e -> save());

        openButton.addActionListener(e -> open());

        popMenu = new JPopupMenu();

        JMenuItem addItem = new JMenuItem("新增");
        addItem.setHorizontalAlignment(SwingConstants.LEFT);
        addItem.addActionListener(e -> {
            MyRequestNodeTree node = (MyRequestNodeTree) tree
                .getLastSelectedPathComponent();
            MyRequestNodeTree newNode = new MyRequestNodeTree(new MyRequestNode("node"));
            ((DefaultTreeModel) tree.getModel())
                .insertNodeInto(newNode, node, node
                    .getChildCount());
            tree.expandPath(tree.getSelectionPath());
        });
        popMenu.add(addItem);

        JMenuItem delItem = new JMenuItem("删除");
        delItem.setHorizontalAlignment(SwingConstants.LEFT);
        delItem.addActionListener(e -> {
            MyRequestNodeTree node = (MyRequestNodeTree) tree
                .getLastSelectedPathComponent();
            if (node.isRoot()) {
                return;
            }
            ((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
        });
        popMenu.add(delItem);

        initJTree();

    }

    private void initJTree() {

        MyRequestNodeTree root = new MyRequestNodeTree(new MyRequestNode("root"));
        MyRequestNodeTree node = new MyRequestNodeTree(new MyRequestNode("node"));
        //获取JTree对应的TreeModel对象
        root.add(node);
        DefaultTreeModel model = new DefaultTreeModel(root);
        tree.setModel(model);
        //设置JTree可编辑
        tree.setEditable(false);

        tree.addMouseListener(new MouseAdapter() {

            //按下鼠标点击
            @Override
            public void mouseClicked(MouseEvent e) {
                //如果需要唯一确定某个节点，必须通过TreePath来获取。
                TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
                if (tp != null) {
                    movePath = tp;
                }
            }

            //按下鼠标时候获得被拖动的节点
            @Override
            public void mousePressed(MouseEvent e) {
                //如果需要唯一确定某个节点，必须通过TreePath来获取。
                TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
                if (tp != null) {
                    movePath = tp;
                    if (e.getButton() == BUTTON3) {
                        // 鼠标右键
                        popMenu.show(tree, e.getX(), e.getY());
                    } else if (e.getButton() == BUTTON1) {
                        // 鼠标左键
                        if (e.getClickCount() == 1) {
                            // 鼠标单击
                            LOG.debug("ClickCount() == 1");
                            LOG.debug("TreePath: {}", tp.toString());
                            MyRequestNodeTree selectTreeNode = (MyRequestNodeTree) tp
                                .getLastPathComponent();
                            LOG.debug("selectTreeNode: {}", gson.toJson(selectTreeNode.getNode()));
                            setMyRequestNodeToPanel(selectTreeNode.getNode());
                        } else if (e.getClickCount() == 2) {
                            // 鼠标双击
                            LOG.debug("ClickCount() == 2");
                            LOG.debug("TreePath: {}", tp.toString());

                            MyRequestNodeTree selectTreeNode = (MyRequestNodeTree) tp
                                .getLastPathComponent();
                            LOG.debug("selectTreeNode: {}", gson.toJson(selectTreeNode.getNode()));
                            //setMyRequestNodeToPanel(selectTreeNode.getNode());
                        }
                    }
                }
            }

            //鼠标松开时获得需要拖到哪个父节点
            @Override
            public void mouseReleased(MouseEvent e) {
                //根据鼠标松开时的TreePath来获取TreePath
                TreePath tp = tree.getPathForLocation(e.getX(), e.getY());

                if (tp != null && movePath != null) {
                    //阻止向子节点拖动
                    if (movePath.isDescendant(tp) && movePath != tp) {
                        LOG.debug("目标节点是被移动节点的子节点, 无法移动!");
                    } else if (movePath != tp) {
                        //既不是向子节点移动，而且鼠标按下、松开的不是同一个节点
                        LOG.debug("移动节点，从{}到{}", movePath.toString(), tp.toString());
                        //add方法可以先将原节点从原父节点删除，再添加到新父节点中
                        ((MyRequestNodeTree) tp.getLastPathComponent()).add(
                            (MyRequestNodeTree) movePath.getLastPathComponent());
                        movePath = null;
                        tree.updateUI();
                    }
                }
            }
        });

    }

    private void save() {
        try {
            File saveFile = getSaveFile();
            if (saveFile != null) {
                if (tree.getSelectionPath() != null) {
                    MyRequestNode selectRequest = getMyRequestFromPanel();
                    MyRequestNodeTree selectTreeNode = (MyRequestNodeTree) tree.getSelectionPath()
                        .getLastPathComponent();
                    selectTreeNode.setNode(selectRequest);
                    tree.updateUI();
                }
                MyRequestNodeSave nodeSave = getMyRequestNodeSave();
                LOG.debug("save MyRequestNodeSave: {}", gson.toJson(nodeSave));
                ConfigFileUtil.writeObject(saveFile, nodeSave);
                String filePath = saveFile.getAbsolutePath();
                setFilePathToTitle(filePath);
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
    }

    private File getSaveFile() {
        try {
            if (configFile != null) {
                return configFile;
            }
            //弹出文件选择框
            JFileChooser jfc = new JFileChooser();
            //文件过滤
            jfc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(FILE_SUFFIX);
                }

                @Override
                public String getDescription() {
                    return FILE_SUFFIX;
                }
            });

            //下面的方法将阻塞，直到【用户按下保存按钮且“文件名”文本框不为空】或【用户按下取消按钮】
            int option = jfc.showSaveDialog(null);
            //假如用户选择了保存
            if (option == JFileChooser.APPROVE_OPTION) {
                File saveFile = jfc.getSelectedFile();
                //从文件名输入框中获取文件名
                String fileName = jfc.getName(saveFile);
                //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
                if (!fileName.contains(FILE_SUFFIX)) {
                    saveFile = new File(jfc.getCurrentDirectory(), fileName + FILE_SUFFIX);
                    LOG.debug("new file: {}", saveFile.getName());
                }
                configFile = saveFile;
                return configFile;
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
        return null;
    }

    private void open() {
        try {
            File openFile = getOpenFile();
            if (openFile != null) {
                MyRequestNodeSave nodeSave = (MyRequestNodeSave) ConfigFileUtil
                    .readObject(openFile, MyRequestNodeSave.class);
                String filePath = openFile.getAbsolutePath();
                setFilePathToTitle(filePath);
                LOG.debug("open MyRequestNode: {}", gson.toJson(nodeSave));
                updateTree(nodeSave);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getOpenFile() {
        try {

            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return f.getName().endsWith(FILE_SUFFIX);
                }

                @Override
                public String getDescription() {
                    return FILE_SUFFIX;
                }
            });

            jfc.showDialog(new JLabel(), "选择");
            File openFile = jfc.getSelectedFile();
            if (openFile == null) {
                return null;
            }
            //从文件名输入框中获取文件名
            String fileName = jfc.getName(openFile);
            //假如用户填写的文件名不带我们制定的后缀名，那么我们给它添上后缀
            if (!fileName.contains(FILE_SUFFIX)) {
                openFile = new File(jfc.getCurrentDirectory(), fileName + FILE_SUFFIX);
                LOG.debug("new file: {}", openFile.getName());
            }

            configFile = openFile;
            return configFile;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void updateTree(MyRequestNodeSave nodeSave) {
        MyRequestNodeTree treeNode = createMyRequestTreeNode(nodeSave);
        DefaultTreeModel model = new DefaultTreeModel(treeNode);
        tree.setModel(model);
        tree.updateUI();
    }

    private MyRequestNodeTree createMyRequestTreeNode(MyRequestNodeSave nodeSave) {
        if (nodeSave == null) {
            return null;
        }
        MyRequestNodeTree treeNode = new MyRequestNodeTree(nodeSave.getNode());
        if (nodeSave.getChildNodes() != null) {
            for (MyRequestNodeSave childSaveNode : nodeSave.getChildNodes()) {
                MyRequestNodeTree childTreeNode = createMyRequestTreeNode(childSaveNode);
                treeNode.add(childTreeNode);
            }
        }
        return treeNode;
    }


    private MyRequestNodeSave getMyRequestNodeSave() {
        MyRequestNodeTree treeNode = (MyRequestNodeTree) tree.getModel().getRoot();
        return visitTreeNodes(treeNode);
    }

    private MyRequestNodeSave visitTreeNodes(MyRequestNodeTree treeNode) {
        if (treeNode == null) {
            return null;
        }
        MyRequestNodeSave saveNode = new MyRequestNodeSave();
        if (treeNode.getUserObject() != null) {
            saveNode.setNode(treeNode.getNode());
        }

        if (treeNode.getChildCount() > 0) {
            LOG.debug("treeNode.getChildCount()={}", treeNode.getChildCount());
            for (Enumeration e = treeNode.children(); e.hasMoreElements(); ) {
                MyRequestNodeTree childTreeNode = (MyRequestNodeTree) e.nextElement();
                MyRequestNodeSave childSaveNode = visitTreeNodes(childTreeNode);
                if (childSaveNode != null) {
                    if (saveNode.getChildNodes() == null) {
                        saveNode.setChildNodes(new ArrayList<>());
                    }
                    saveNode.getChildNodes().add(childSaveNode);
                }
            }
        }
        return saveNode;
    }


    private void setMyRequestNodeToPanel(MyRequestNode myRequestNode) {
        if (myRequestNode == null) {
            myRequestNode = new MyRequestNode("node");
        }
        this.textFieldUrl.setText(myRequestNode.getUrl());
        this.comboBox_method.setSelectedItem(myRequestNode.getMethod() == null ?
            MyRequestMethod.GET.getCode() : myRequestNode.getMethod().getCode());
        this.textField_timeout.setText("" + myRequestNode.getTimeout());
        this.textAreaRequest.setText(myRequestNode.getJsonParam());
        this.textFieldName.setText(myRequestNode.getName());

        this.textAreaResponse.setText("");

        String[] columnNames = {
            "Name", "Value"
        };

        Object[][] data = {
        };

        tableModel.setDataVector(data, columnNames);
        List<MyRequestHeader> headers = myRequestNode.getHeaders();
        if (headers != null) {
            headers.forEach(
                header -> tableModel.addRow(new Object[]{header.getKey(), header.getValue()}));
        }

        tableHeaders.setModel(tableModel);
    }

    private MyRequestNode getMyRequestFromPanel() {

        MyRequestNode myRequest = new MyRequestNode("node");

        String name = textFieldName.getText();

        // 获取http post请求的url
        String url = textFieldUrl.getText();
        LOG.debug(url);

        // 获取http post请求内容
        String jsonParam = textAreaRequest.getText();

        // 获取http post请求的消息头
        List<MyRequestHeader> headers = new ArrayList<>();
        int columnCount = tableHeaders.getColumnCount();
        int rowCount = tableHeaders.getRowCount();
        if (columnCount >= 2) {
            for (int row = 0; row < rowCount; row++) {
                String tableKey = (String) tableHeaders.getValueAt(row, 0);
                String tableValue = (String) tableHeaders.getValueAt(row, 1);
                MyRequestHeader header = new MyRequestHeader(tableKey, tableValue);
                headers.add(header);
            }
        }

        LOG.debug("get headers: {}", gson.toJson(headers));

        String method = (String) comboBox_method.getSelectedItem();

        myRequest.setName(name);
        myRequest.setUrl(url);
        myRequest.setHeaders(headers);
        myRequest.setJsonParam(jsonParam);
        myRequest.setMethod(MyRequestMethod.findByCode(method));

        String strTimeout = textField_timeout.getText();
        if (NumberUtils.isNumber(strTimeout)) {
            int timeout = NumberUtils.createInteger(strTimeout);
            myRequest.setTimeout(timeout);
        }

        return myRequest;
    }

    JPanel getPanel_main() {
        return panel_main;
    }

    public void setFilePathToTitle(String filePath) {

    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            JFrame frame = new JFrame("Rest");
            frame.setContentPane(new Rest().panel_main);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            frame.setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
