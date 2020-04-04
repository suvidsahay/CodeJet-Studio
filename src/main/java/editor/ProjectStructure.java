package editor;

import java.awt.Dimension;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProjectStructure extends JPanel{
    public ProjectStructure() {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        Dimension size = getPreferredSize();
        size.width = 250;
        setPreferredSize(size);

        setBorder(BorderFactory.createTitledBorder("Explorer"));
    }

    void displayFilesInDirectory(File file){
        File[] list = file.listFiles();
        JLabel files[] = new JLabel[list.length];
        removeAll();
        try{
            for ( int i = 0; i < list.length; i++) {
                files[i] = new JLabel(list[i].getName());
                add(files[i]);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        updateUI();
        revalidate();
        repaint();
    }
}