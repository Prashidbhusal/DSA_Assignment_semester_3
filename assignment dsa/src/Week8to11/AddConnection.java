package Week8to11;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

//Class to add connection between route
class AddConnectionFrame {
    AddConnectionFrame(){
//        Initialising components for swing
        JFrame f = new JFrame("Add route Panel");
        JLabel route1 = new JLabel("route A");
        JLabel route2 = new JLabel("route B");
        JLabel dTextLabel = new JLabel("Distance (in km)");

        JButton backButton = new JButton("Back");
        JButton connect  = new JButton("Connect");
        JButton disconnect  = new JButton("Disconnect");
        JButton optimalPath = new JButton("Optimal Path");
        JLabel distanceLabel = new JLabel("");
        JLabel pathLabel = new JLabel("");

        JTextField dTextField = new JTextField();
        dTextField.setText("0");

//      Adding components in frame
        f.add(route1).setBounds(50, 150, 100, 30);
        f.add(route2).setBounds(330, 150, 150, 30);
        f.add(connect).setBounds(110, 300, 200, 30);
        f.add(disconnect).setBounds(110, 400, 200, 30);
        f.add(backButton).setBounds(5,25,150,30);
        f.add(optimalPath).setBounds(110,350,200,30);
        f.add(distanceLabel).setBounds(110, 450, 500, 30);
        f.add(pathLabel).setBounds(110, 500, 500, 30);
        f.add(dTextField).setBounds(50, 250, 150, 30);
        f.add(dTextLabel).setBounds(50, 220, 100, 30);

//        Getting list of routes from txt file
        List<String> list = new ArrayList<>();
        File file = new File("routes.txt");
        ArrayList<String> route1List = new ArrayList<>();
        if(file.exists()){
            try {
                list = Files.readAllLines(file.toPath(), Charset.defaultCharset());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if(list.isEmpty())
                return;
        }
//      Add list route data in route list
        for(String line : list){
                String [] res = line.split(";");
            route1List.add(res[1]);
        }

//      Adding route to a separate 1D list
        String[] addedroutes = new String[route1List.size()];
        for (int i=0; i<route1List.size(); i++) {
            addedroutes[i] = route1List.get(i);
        }

//        Declaring combobox to select route data
        JComboBox<String> combobox = new JComboBox<>(new Vector<>(route1List));
        JComboBox<String> combobox2 = new JComboBox<>(new Vector<>(route1List));
        f.add(combobox).setBounds(15,185,180,30);
        f.add(combobox2).setBounds(250,185,180,30);

//        Declaring arraylist to store connected data
        ArrayList<String []> connectedData = new ArrayList<>();
        try {
            File file_Obj = new File("connection.txt");
            Scanner myReader = new Scanner(file_Obj);

            while(myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = data.split(",");
                connectedData.add(arr);
            }
            myReader.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        Reading matrix from txt file if data exist
        Graph graphObj = new Graph(20);
        int len = connectedData.size();

        if (len!=0) {
            for (int i=0; i<len; i++) {
                for (int j=0; j<connectedData.get(i).length; j++) {
                    graphObj.adjacencyMatrix[i][j] = Integer.parseInt(connectedData.get(i)[j]);
                }
            }
        }
        else {
            return;
        }

//        Action event for connection button
        connect.addActionListener(e -> {
            String route1Name, route2name;
            Integer  distance;
            route1Name = (String) combobox.getSelectedItem();
            route2name = (String) combobox2.getSelectedItem();
            distance =  Integer.parseInt(dTextField.getText());

            if(distance<=0){
                JOptionPane.showMessageDialog(f,"Please fillup distance greater than 0");
                return;
            }

//          Storing selected item from combobox
            assert route1Name != null;
            if (!(route1Name.equals(route2name))){
                int firstIndex = Week8to11.linearSearch.getIndex(addedroutes,route1Name);
                int secondIndex = Week8to11.linearSearch.getIndex(addedroutes, route2name);
                StringBuilder builder = new StringBuilder();

//             Calling graph object and storing connection data to adjacency matrix
                graphObj.addEdge(firstIndex, secondIndex,distance );
                int[][] matrix = graphObj.adjacencyMatrix;
                for (int[] ints : matrix) {
                    int k = 0;
                    for (int j = 0; j < ints.length - 1; j++) {
                        builder.append(ints[j]).append(",");
                        k += 1;
                    }
                    builder.append(ints[k]).append("\n");
                }

                try {
                    FileWriter myWriter = new FileWriter("connection.txt");
                    myWriter.write(builder.toString());
                    myWriter.close();
                    JOptionPane.showMessageDialog(f, "Connection success");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(f,"Cannot Connect Same route");

            }
        });

//      Action event for disconnect button

        disconnect.addActionListener(e -> {
            String route1Name, route2name;
            route1Name = (String) combobox.getSelectedItem();
            route2name = (String) combobox2.getSelectedItem();
//          If route is already connected
            assert route1Name != null;
            if (!(route1Name.equals(route2name))){
                int firstIndex = new linearSearch().getIndex(addedroutes,route1Name);
                int secondIndex = new linearSearch().getIndex(addedroutes, route2name);
                StringBuilder changedData = new StringBuilder();

//                Replacing 1 with 0 if connection is removed
                graphObj.removeEdge(firstIndex, secondIndex);
                int[][] matrix = graphObj.adjacencyMatrix;
                for (int[] ints : matrix) {
                    int k = 0;
                    for (int j = 0; j < ints.length - 1; j++) {
                        changedData.append(ints[j]).append(",");
                        k += 1;
                    }
                    changedData.append(ints[k]).append("\n");
                }

//                Rewriting updated file into connection.txt
                try {
                    FileWriter myWriter = new FileWriter("connection.txt");
                    myWriter.write(changedData.toString());
                    myWriter.close();
                    JOptionPane.showMessageDialog(f, "Disconnected Successfully");
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            else {
                JOptionPane.showMessageDialog(f,"route are not connected to disconnect");

            }
        });

//        Action listener for optimal path button
        optimalPath.addActionListener(e -> {
            String route1Name, route2name;
            route1Name = (String) combobox.getSelectedItem();
            route2name = (String) combobox2.getSelectedItem();
            assert route1Name != null;
            if (!(route1Name.equals(route2name))){
                int firstIndex = Week8to11.linearSearch.getIndex(addedroutes,route1Name);
                int secondIndex = Week8to11.linearSearch.getIndex(addedroutes, route2name);
                int[] previousPath = graphObj.shortestPath(firstIndex);
                StringBuilder path = new StringBuilder();
                String pathDistance = "";
                int lastIndex = secondIndex;
                ArrayList<Integer> path_arr = new ArrayList<>();
                path_arr.add(lastIndex);
                while(previousPath[lastIndex]!=-1) {
                    path_arr.add(previousPath[lastIndex]);
                    lastIndex = previousPath[lastIndex];
                }

                int distance = graphObj.distance(firstIndex,secondIndex);
                pathDistance += "The shortest distance is: " + distance +" km";
                if (path_arr.size()!=1) {
                    int k=path_arr.size()-1;
                    for (int i=path_arr.size()-1; i>=1; i--) {
                        path.append(addedroutes[path_arr.get(i)]).append(" -> ");
                        k--;
                    }
                    path.append(addedroutes[path_arr.get(k)]);
                }

                distanceLabel.setText("");
                distanceLabel.setText(pathDistance);

                pathLabel.setText("");
                pathLabel.setText(path.toString());

            }
            else {
                JOptionPane.showMessageDialog(f,"Cannot Connect Same route");

            }
        });

        backButton.addActionListener(e -> {
            f.dispose();
            new Select();
        });

//
        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setBounds(800, 200, 450, 600);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}