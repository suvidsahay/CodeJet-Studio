package editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.Container;
import java.io.*;

public class Editor extends JFrame implements ActionListener {

    private JTextArea textArea = new JTextArea();
    private ProjectStructure project = new ProjectStructure();

    public Editor() {        

        setLayout(new BorderLayout());
        Container c = getContentPane();


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        c.add(scrollPane,BorderLayout.CENTER);
        c.add(project,BorderLayout.WEST);

        JMenuBar menuBar= new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open File");
        JMenuItem openDirectory = new JMenuItem("Open Directory");
        JMenuItem saveFile = new JMenuItem("Save");

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        openDirectory.addActionListener(this);
        saveFile.addActionListener(this);

        file.add(newFile);
        file.add(openFile);
        file.add(openDirectory);
        file.add(saveFile);
        scrollPane.setViewportView(textArea);

        setJMenuBar(menuBar);

        setVisible(true);
        setSize(1920,1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = actionEvent.getActionCommand();
        if( s.equals("Open File") ) {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int dialog = fileChooser.showOpenDialog(null);
            if (dialog == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
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
            else
                JOptionPane.showMessageDialog(this, "the user cancelled the operation");
        }
        else if (s.equals( "Open Directory" )) {
            JFileChooser directoryChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int dialog = directoryChooser.showOpenDialog(null);
            if( dialog == JFileChooser.APPROVE_OPTION) {
                File file = directoryChooser.getSelectedFile();
                project.displayFilesInDirectory(file);
                revalidate();
                repaint();
            }
            else
                JOptionPane.showMessageDialog(this, "the user cancelled the operation");
        }
    }
}
