package Week8to11;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Viewroute {
    Viewroute(){
//        Initialising components in swing
        JButton  backBtn, updateBtn, deleteBtn;
        JFrame f=new JFrame("View Books");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");
        backBtn = new JButton("Back to Dashboard");

//      Adding components in frame
        f.add(updateBtn).setBounds(5,200,200,30);
        f.add(deleteBtn).setBounds(5,250,200,30);
        f.add(backBtn).setBounds(5,15,200,30);


//      Declaring arraylist to store list of data from routes
        String[] column = {"route ID","Distance", "Description"};
        List<String> list = new ArrayList<>();
        ArrayList<String[]> route = new ArrayList<>();
        File file = new File("routes.txt");
        if(file.exists()){
            try {
                list = Files.readAllLines(file.toPath(), Charset.defaultCharset());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        for(String line : list){
            String [] res1 = line.split(";");
            route.add(res1);

        }
//        Storing route data
        String [][] data =new String[route.size()][route.get(0).length];
        for(int i=0;i<route.size();i++){
            System.arraycopy(route.get(i), 0, data[i], 0, route.get(i).length);
        }
        JTable jtEmp = new JTable(data, column);
        JScrollPane sp = new JScrollPane(jtEmp);
        f.add(sp);
        sp.setBounds(215,10,700,490);

//      Update Action Listener
        updateBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(f,"Sure? You want to update this data?", "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
//              Get selected row data and save edited data from JTable
                int row= jtEmp.getSelectedRow();
                if(row>=0) {
                    TableModel model= jtEmp.getModel();
                    model.addTableModelListener(tableModelEvent -> {
                        if(jtEmp.isEditing()){
                            String distance = (String) model.getValueAt(row,1);
                            String description = (String) model.getValueAt(row,2);
                            data[row][1]=distance;
                            data[row][2]=description;
                        }
                    });
//                    Saving edited data into main txt file
                    StringBuilder data_to_write = new StringBuilder();
                    for (String[] datum : data) {
                        int k = 0;
                        for (int j = 0; j < datum.length - 1; j++) {
                            data_to_write.append(datum[j]).append(";");
                            k++;
                        }
                        data_to_write.append(datum[k]).append("\n");
                    }
                    try {
                        FileWriter myWriter = new FileWriter("routes.txt");
                        myWriter.write(data_to_write.toString());
                        myWriter.close();
                        new Viewroute();
                        f.dispose();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(f, "Updated Successfully");
                    jtEmp.setModel(new DefaultTableModel(data, column));

                }else {
                    JOptionPane.showMessageDialog(f, "No row selected. (HINT: you cannot update route id)");
                }
            }
        });

//        Delete Button Action Listener

        deleteBtn.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(f,"Sure? You want to update this data?", "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION) {
//                Get selected row
                int row = jtEmp.getSelectedRow();
                if (row >= 0) {
                    String id = (String) jtEmp.getValueAt(row, 0);
//                    Remove  data in 2D array equivalent to selected row route id
                    String[][] dataDelete = Arrays.stream(data)
                            .filter(rows -> Arrays.stream(rows).noneMatch(i -> i.equals(id)))
                            .toArray(String[][]::new);
//                    Updating new data to connection file
                    StringBuilder data_to_write = new StringBuilder();
                    for (String[] datum : dataDelete) {
                        int k = 0;
                        for (int j = 0; j < datum.length - 1; j++) {
                            data_to_write.append(datum[j]).append(";");
                            k++;
                        }
                        data_to_write.append(datum[k]).append("\n");
                    }
                    try {
                        FileWriter myWriter = new FileWriter("routes.txt");
                        myWriter.write(data_to_write.toString());
                        myWriter.close();
                        new Viewroute();
                        f.dispose();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(f, "Deleted Successfully");
                    jtEmp.setModel(new DefaultTableModel(data, column));

                } else {
                    JOptionPane.showMessageDialog(sp, "Select Any Row to Delete");
                }
            }
        });

        backBtn.addActionListener(e -> {
            f.dispose();
            new Select();
        });

        f.setLayout(null);
        f.setVisible(true);
        f.setResizable(false);
        f.setBounds(400, 100, 1000, 570);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }
}