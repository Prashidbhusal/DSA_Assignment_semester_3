package Week8to11;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

// Class to add route
class AddrouteFrame {
    AddrouteFrame(){
//        Initializing swing components
        JFrame f = new JFrame("Add routes Panel");
        JLabel routeId = new JLabel("route ID");
        JLabel routename = new JLabel("Route name");
        JLabel description  = new JLabel("Description");
        JTextField routeText  =  new JTextField();
        JTextField distanceText = new JTextField();
        JTextArea descriptionText = new JTextArea();
        JButton backButton = new JButton("Back");
        JButton addroute  = new JButton("Add route");

//      Adding Swing Components in frame
        f.add(routeId).setBounds(50, 150, 100, 30);
        f.add(routeText).setBounds(150, 150, 150, 30);
        f.add(routename).setBounds(50, 220, 100, 30);
        f.add(distanceText).setBounds(150, 220, 150, 30);
        f.add(description).setBounds(50, 290, 100, 30);
        f.add(descriptionText).setBounds(150, 290, 150, 150);
        f.add(addroute).setBounds(80, 460, 200, 30);
        f.add(backButton).setBounds(5,25,150,30);

//        Add route action listener
        addroute.addActionListener(e -> {
            String routeName, distanceName, descriptionName;
            routeName = routeText.getText();
            distanceName = distanceText.getText();
            descriptionName = descriptionText.getText();
            FileWriter fw;

//            Declaring list to store routes data
            List<String> list = new ArrayList<>();
            List<String> routeIdList = new ArrayList<>();
            File file = new File("routes.txt");
            if(file.exists()){
                try {
                    list = Files.readAllLines(file.toPath(), Charset.defaultCharset());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
//            Splitting route list with ';'
            for(String line : list){
                String [] res = line.split(";");
                routeIdList.add(res[0]);
            }
            try {
//                Form Validations
                if(!routeIdList.contains(routeName)){
                    if(routeName.isEmpty() || distanceName.isEmpty() || descriptionName.isEmpty()){
                        JOptionPane.showMessageDialog(f,"Fill up the full form");
                    }
                    else {
                        fw = new FileWriter("routes.txt", true);
                        fw.write(routeName + ";" + distanceName + ";" + descriptionName + "\n");
                        fw.close();
                        JOptionPane.showMessageDialog(f, "route Added Successfully");
                        routeText.setText("");
                        distanceText.setText("");
                        descriptionText.setText("");
                    }

                }
                else {
                    JOptionPane.showMessageDialog(f,"route ID Already Taken");

                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        backButton.addActionListener(e -> {
            f.dispose();
            new Select();
        });

        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setBounds(800, 200, 370, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}