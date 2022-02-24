package Week8to11;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class ViewConnection {
    ViewConnection(){
        DirectedSparseGraph<String, String> g = new DirectedSparseGraph<>();
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

        for(String line : list){
            String [] res = line.split(";");
            route1List.add(res[1]);
        }
        for (String value : route1List) {
            g.addVertex(value);
        }

        ArrayList<String []> dataConnection = new ArrayList<>();
        try {
            File connectionFile = new File("connection.txt");
            Scanner dataReader = new Scanner(connectionFile);
            while(dataReader.hasNextLine()) {
                String data = dataReader.nextLine();
                String[] collection = data.split(",");
                dataConnection.add(collection);
            }
            dataReader.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Graph graph = new Graph(20);

        int len = dataConnection.size();

        if (len!=0) {
            for (int i=0; i<len; i++) {
                for (int j=0; j<dataConnection.get(i).length; j++) {
                    graph.adjacencyMatrix[i][j] = Integer.parseInt(dataConnection.get(i)[j]);
                }
            }
        }
        else {
            return;
        }

        int [][] matrix = graph.adjacencyMatrix;

        ArrayList<String []> routeData = new ArrayList<>();
        try {
            File routeFile = new File("routes.txt");
            Scanner dataReader = new Scanner(routeFile);
            while(dataReader.hasNextLine()) {
                String data = dataReader.nextLine();
                String[] arr = data.split(";");
                routeData.add(arr);
            }

            dataReader.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        ArrayList<String> routeList = new ArrayList<>();

        for (String[] strings : routeData) {
            routeList.add(strings[1]);
        }

        ArrayList<String[]> connectedData = new ArrayList<>();

        for (int i=0; i<matrix.length; i++) {
            for(int j=0; j<matrix[i].length; j++) {
                if (matrix[i][j]>0) {
                    String[] detail = {routeList.get(i), routeList.get(j)};
                    connectedData.add(detail);
                }
            }
        }
        String[][] data = new String[connectedData.size()][2];
        for (int i=0; i<connectedData.size(); i++) {
            data[i][0] = connectedData.get(i)[0];
            data[i][1] = connectedData.get(i)[1];
        }
        int k=0;
        for (String[] datum : data) {
            g.addEdge(String.valueOf(k), datum[0], datum[1]);
            k++;
        }

        float[] dash = {10.0f};
        final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
        Transformer<String, Stroke> edgeStrokeTransformer =
                s -> edgeStroke;

        VisualizationImageServer<String, String> vs =
                new VisualizationImageServer<>(
                        new CircleLayout<>(g), new Dimension(500, 500));



        vs.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
        vs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        vs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<>());
        vs.getRenderer().getVertexLabelRenderer().setPosition(Position.N);
        JFrame frame = new JFrame();
        frame.getContentPane().add(vs);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}