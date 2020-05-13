package editor;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.Container;
import java.io.*;
import java.time.Clock;

public class Editor extends JFrame implements ActionListener {

    public static JTextArea textArea = new JTextArea();
    private JPanel browser = new JPanel();
    private JTree directory;
    private JScrollPane scrollPaneBro = new JScrollPane();
    private JScrollPane scrollPaneText = new JScrollPane();
    File currentFile;
    File currentDirectory;

    public Editor() {
        setLayout(new BorderLayout());

        JMenuBar menuBar= new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem openDirectory = new JMenuItem("Open Directory");
        JMenuItem saveFile = new JMenuItem("Save");

        openDirectory.addActionListener(this);
        saveFile.addActionListener(this);

        file.add(openDirectory);
        file.add(saveFile);

        add(menuBar, BorderLayout.NORTH);


        scrollPaneBro.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneBro.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPaneText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPaneBro, BorderLayout.WEST);
        add(scrollPaneText, BorderLayout.CENTER);
        scrollPaneBro.setViewportView(browser);
        scrollPaneText.setViewportView(textArea);

        setVisible(true);
        setSize(1920,1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void openFile(File file) {
        try {
            String fileDataLine = "", fileData = "";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            while ((fileDataLine = br.readLine()) != null) {
                fileData = fileData + "\n" + fileDataLine;
            }
            textArea.setText(fileData);

        } catch (Exception evt) {
            JOptionPane.showMessageDialog(this, evt.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = actionEvent.getActionCommand();
        if( s.equals("Save") ) {
            String textData = "";
            textData = textArea.getText();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                BufferedReader reader = new BufferedReader(new StringReader(textData));
                writer.write("");
                String[] lines = textData.split(System.getProperty("line.separator"));
                for(int i = 0; i < lines.length; i++) {
                    writer.append(lines[i]);
                    writer.append("\n");
                    System.out.println(lines[i]);
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (s.equals( "Open Directory" )) {
            JFileChooser directoryChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int dialog = directoryChooser.showOpenDialog(null);
            if( dialog == JFileChooser.APPROVE_OPTION) {
                File file = directoryChooser.getSelectedFile();
                displayFilesInDirectory(file);
            }
            else
                JOptionPane.showMessageDialog(this, "the user cancelled the operation");
        }
    }

    void displayFilesInDirectory(File file){

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
        generateTreeView(file, root);

        directory = new JTree(root);

        directory.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        directory.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        directory.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent treeSelectionEvent) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeSelectionEvent.getPath().getLastPathComponent();
                openFile((File)node.getUserObject());
                currentFile = (File)node.getUserObject();
                currentDirectory = (File)((File) node.getUserObject()).getParentFile();
            }
        });
        
        directory.setVisible(true);
        browser.add(directory);
        scrollPaneBro.setViewportView(browser);
        revalidate();
        repaint();
    }

    private void generateTreeView(File rootFile, DefaultMutableTreeNode parent) {
        File[] files = rootFile.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(files[i]);
                parent.add(node);
                if (files[i].isDirectory()) {
                    generateTreeView(files[i], node);
                }
            }
        }
    }
}
