package register;

import javax.swing.*;

public class NewUser{
  public String name, username;
  char[] password, repeatpassword;

  public NewUser(String name, String username, char[] password, char[] repeatpassword) {
      this.name = name;
      this.username = username;
      this.password = password;
      this.repeatpassword = repeatpassword;
  }

  public boolean registerUser () {
      return true;
    
  }
}