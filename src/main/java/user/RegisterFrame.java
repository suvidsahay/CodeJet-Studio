package user;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import editor.*;

public class RegisterFrame extends JFrame {
    JLabel nameL = new JLabel("Full Name");
    JTextField nameT = new JTextField(10);
    JLabel userL = new JLabel("Username");
    JTextField userT = new JTextField(10);
    JLabel passwordL = new JLabel("Password");
    JPasswordField passwordT = new JPasswordField (10);
    JLabel passwordRepeatL = new JLabel("Repeat Password");
    JPasswordField passwordRepeatT = new JPasswordField (10);
    JButton registerB = new JButton("Register");

    JPanel nameP = new JPanel(new FlowLayout());
    JPanel userP= new JPanel( new FlowLayout());
    JPanel passwordP = new JPanel(new FlowLayout());
    JPanel passwordRepeatP = new JPanel( new FlowLayout());

    RegisterFrame () {
        super("CodeJet Studio");

        setLayout(new GridLayout(5, 1));
        nameP.add(nameL);
        nameP.add(nameT);

        userP.add(userL);
        userP.add(userT);

        passwordP.add(passwordL);
        passwordP.add(passwordT);

        passwordRepeatP.add(passwordRepeatL);
        passwordRepeatP.add(passwordRepeatT);

        registerB.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent actionEvent) {
              String name = nameT.getText();
              String username = userT.getText();
              char[] password = passwordT.getPassword();
              char[] passwordrepeat = passwordRepeatT.getPassword();
              User newUser = new User(username, password.toString());
              newUser.register(name,username,password.toString());
              if(newUser.registerUser()) {
                dispose();
                new Editor();
              }
          }
      });
      
        add(nameP);
        add(userP);
        add(passwordP);
        add(passwordRepeatP);
        add(registerB);

        setSize(720,480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
