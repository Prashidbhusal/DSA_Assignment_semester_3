package Week8to11;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

class RegisterFrame {
    RegisterFrame(){
//        Initializing components in swing
        JFrame f = new JFrame("System Registration Panel");
        JLabel userLabel = new JLabel("USERNAME");
        JLabel passwordLabel = new JLabel("PASSWORD");
        JTextField userTextField = new JTextField();
        JButton backButton = new JButton("Back");
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("REGISTER");
        JButton resetButton = new JButton("RESET");
        JCheckBox showPassword = new JCheckBox("Show Password");

//        Adding components in frame
        f.add(userLabel).setBounds(50, 150, 100, 30);
        f.add(passwordLabel).setBounds(50, 220, 100, 30);
        f.add(userTextField).setBounds(150, 150, 150, 30);
        f.add(passwordField).setBounds(150, 220, 150, 30);
        f.add(registerButton).setBounds(50, 300, 100, 30);
        f.add(resetButton).setBounds(200, 300, 100, 30);
        f.add(showPassword).setBounds(150, 250, 150, 30);
        f.add(backButton).setBounds(5,25,100,30);

//        Register Action Listener
        registerButton.addActionListener(e -> {
            String username, password;
            username = userTextField.getText();
            password = passwordField.getText();
            FileWriter fw;
            List<String> list = new ArrayList<>();
            List<String> usernameList = new ArrayList<>();
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
            }
            try {
                if(!usernameList.contains(username)){
                    fw = new FileWriter("file.txt",true);
                    fw.write(username+";"+password+"\n");
                    fw.close();
                    JOptionPane.showMessageDialog(f,"User Registered Successfully");
                    f.dispose();
                    new LoginFrame();
                }
                else {
                    JOptionPane.showMessageDialog(f,"Username Already Taken");

                }


            } catch (IOException ioException) {
                ioException.printStackTrace();
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

        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setBounds(800, 200, 370, 520);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}