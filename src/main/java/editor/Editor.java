package editor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Editor extends JFrame implements ActionListener {
    public static List<JTextPane> textArea = new ArrayList<JTextPane>();
    private JPanel browser = new JPanel();
    private JTree directory;
    private JScrollPane scrollPaneBro = new JScrollPane();
    private JScrollPane scrollPaneText = new JScrollPane();
    private JTabbedPane tabbedPane = new JTabbedPane();
    File currentFile = null;
    File currentDirectory = new File("$HOME");
    CaretListenerLabel caretListenerLabel = new CaretListenerLabel();
    public static int textAreaIndex = -1;
    public static int currentTextArea;
    Font font = new Font("Bitstream Regular", Font.TRUETYPE_FONT, 20);
    final JPopupMenu popupmenu = new JPopupMenu("File");
    JMenuItem newFile = new JMenuItem("New File");
    JMenuItem newDirectory = new JMenuItem("New Directory");

    public Editor() {
        setLayout(new BorderLayout());

        JMenuBar menuBar= new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        JMenu run = new JMenu("Run");
        menuBar.add(file);
        menuBar.add(run);

        JMenuItem openDirectory = new JMenuItem("Open Directory");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem runProgram = new JMenuItem("Run");
        JMenuItem buildProgram = new JMenuItem("Build");

        openDirectory.addActionListener(this);
        saveFile.addActionListener(this);
        runProgram.addActionListener(this);
        buildProgram.addActionListener(this);

        file.add(openDirectory);
        file.add(saveFile);
        run.add(runProgram);
        run.add(buildProgram);

        add(menuBar, BorderLayout.NORTH);

        scrollPaneBro.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneBro.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPaneText.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneText.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        add(scrollPaneBro, BorderLayout.WEST);
        add(scrollPaneText, BorderLayout.CENTER);
        scrollPaneBro.setViewportView(browser);
        scrollPaneText.setViewportView(tabbedPane);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                currentTextArea = tabbedPane.getSelectedIndex();
            }
        });
        

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
            textArea.get(textAreaIndex).setText(fileData);

        } catch (Exception evt) {
            JOptionPane.showMessageDialog(this, evt.getMessage());
        }
    }

    void runCode() throws IOException, InterruptedException {
//        System.out.println(currentFile.getName().substring(0, currentFile.getName().lastIndexOf('.')));
//        System.out.println("gnome-terminal --command=\"bash -c \'cd " + currentDirectory.getPath() + " && g++ -o " + currentFile.getName().substring(0, currentFile.getName().lastIndexOf('.')) + " " + currentFile.getName() + " && " + "./main; $SHELL\'\"");
        String[] cmd = {
                "/bin/sh",
                "-c",
                "gnome-terminal --command=\"bash -c \'cd " + currentDirectory.getPath() + " && g++ -o " + currentFile.getName().substring(0, currentFile.getName().lastIndexOf('.')) + " " + currentFile.getName() + " && " + "./main; $SHELL\'\""
        };
        Process p = new ProcessBuilder(cmd).start();

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = p.waitFor();
        if (exitVal == 0) {
            System.out.println("Success!");
        } else {
            System.out.println("Fail!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = actionEvent.getActionCommand();
        System.out.println(s);
        if( s.equals("Save") ) {
            String textData = "";
            textData = textArea.get(currentTextArea).getText();
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                BufferedReader reader = new BufferedReader(new StringReader(textData));
                writer.write("");
                String[] lines = textData.split(System.getProperty("line.separator"));
                for(int i = 0; i < lines.length; i++) {
                    writer.append(lines[i]);
                    writer.append("\n");
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
        }else if(s.equals("Run")) {
            try {
                runCode();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void addNewTab() {
        textArea.add(new JTextPane());
        textAreaIndex ++;
        textArea.get(textAreaIndex).setFont(font);
//        textArea.get(textAreaIndex).addCaretListener(caretListenerLabel);
        tabbedPane.add(textArea.get(textAreaIndex), textAreaIndex);
        JButton cross = new JButton("x");
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
                File selectedFile;
                selectedFile = (File)node.getUserObject();
                addNewTab();
                tabbedPane.setTitleAt(textAreaIndex, selectedFile.getName());
                openFile(selectedFile);
                currentFile = selectedFile;
                currentDirectory = (File)selectedFile.getParentFile();
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
