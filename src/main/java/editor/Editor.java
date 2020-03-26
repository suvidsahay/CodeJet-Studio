package editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;

public class Editor extends JFrame implements ActionListener {

    private JTextArea textArea = new JTextArea();

    public Editor() {
        JPanel directoryStructure = new JPanel();

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar= new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem newFile = new JMenuItem("New");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);

        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);

        add(scrollPane);
        scrollPane.setViewportView(textArea);

        pack();
        setJMenuBar(menuBar);

        setVisible(true);
        setSize(1920,1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String s = actionEvent.getActionCommand();
        if( s.equals("Open") ) {
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
    }
}
