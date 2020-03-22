import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;

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
