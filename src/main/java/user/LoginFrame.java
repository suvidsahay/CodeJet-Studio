package user;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import editor.Editor;
public class LoginFrame extends JFrame {
    JLabel userL = new JLabel("Username");
    JTextField userT = new JTextField(10);
    JLabel passwordL = new JLabel("Password");
    JPasswordField passwordT = new JPasswordField (10);

    JButton loginB = new JButton("Login");
    JButton registerB = new JButton("Register");

    JPanel userP = new JPanel(new FlowLayout());
    JPanel passwordP = new JPanel( new FlowLayout());

    public LoginFrame() {
        super("CodeJet Studio");

        setLayout(new GridLayout(4,1));

        userP.add(userL);
        userP.add(userT);

        passwordP.add(passwordL);
        passwordP.add(passwordT);

        loginB.setSize(30,10);
        loginB.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent actionEvent) {
                 String username = userT.getText();
                 String password = passwordT.getPassword().toString();
                 User l = new User(username, password);
                 if(l.loginUser()) {
                     dispose();
                     Editor e = new Editor();
                 }
             }
         });
        registerB.setSize(30, 10);
        registerB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
                RegisterFrame rf = new RegisterFrame();
            }
        });
        add(userP);
        add(passwordP);
        add(loginB);
        add(registerB);

        setSize(720,480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
