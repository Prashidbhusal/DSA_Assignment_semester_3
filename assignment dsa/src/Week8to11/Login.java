package Week8to11;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

class LoginFrame {
    LoginFrame() {
//        Initializing components is swing
        JFrame f = new JFrame("System Login Panel");
        JLabel userLabel = new JLabel("USERNAME");
        JLabel passwordLabel = new JLabel("PASSWORD");
        JTextField userTextField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("LOGIN");
        JButton backButton = new JButton("Back");
        JButton registerButton = new JButton("Register");
        JButton resetButton = new JButton("RESET");
        JCheckBox showPassword = new JCheckBox("Show Password");

//        Adding components in frame
        f.add(userLabel).setBounds(50, 150, 100, 30);
        f.add(passwordLabel).setBounds(50, 220, 100, 30);
        f.add(userTextField).setBounds(150, 150, 150, 30);
        f.add(passwordField).setBounds(150, 220, 150, 30);
        f.add(loginButton).setBounds(50, 300, 100, 30);
        f.add(resetButton).setBounds(200, 300, 100, 30);
        f.add(showPassword).setBounds(150, 250, 150, 30);
        f.add(backButton).setBounds(5,25,100,30);
        f.add(registerButton).setBounds(115,25,100,30);

//        Login Action Listener
        loginButton.addActionListener(e -> {
            String username, password;
            username = userTextField.getText();
            password = passwordField.getText();
//            Storing login data from file into list
            List<String> list = new ArrayList<>();
            List<String> usernameList = new ArrayList<>();
            List<String> passwordList = new ArrayList<>();
            File file = new File("file.txt");
            if(file.exists()){
                try {
                    list = Files.readAllLines(file.toPath(), Charset.defaultCharset());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(list.isEmpty())
                    return;
            }
            for(String line : list){
                String [] res = line.split(";");
                usernameList.add(res[0]);
                passwordList.add(res[1]);
            }
//          Validating username and password
            if (usernameList.contains(username)){
                int index = usernameList.indexOf(username);
                if(usernameList.get(index).equals(username) && passwordList.get(index).equals(password)){
                    JOptionPane.showMessageDialog(f,"Login Successful!");
                    f.dispose();
                    new Select();
                }
                else {
                    JOptionPane.showMessageDialog(f,"Invalid Password!");
                }
            }
            else {
                JOptionPane.showMessageDialog(f,"User not registered!");
            }

        });

        resetButton.addActionListener(e -> {
            userTextField.setText("");
            passwordField.setText("");
        });

        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        backButton.addActionListener(e -> {
            f.dispose();
            new Dashboard();
        });

        registerButton.addActionListener(e -> {
            f.dispose();
            new RegisterFrame();
        });

        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setBounds(800, 200, 370, 520);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}