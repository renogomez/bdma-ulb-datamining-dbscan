package bdma.ulb.datamining;

import bdma.ulb.datamining.algo.DBScan;
import bdma.ulb.datamining.model.Cluster;
import bdma.ulb.datamining.util.Util;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;




public class Test {

    private static void printClusters(final List<Cluster> clusters) throws IOException {
        final CSVWriter writer = new CSVWriter(new FileWriter("/Users/reno/bdma/datamining/bdma-ulb-datamining-assignment-2/resultSimple.csv"),
                CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);
        final List<String[]> headers = new ArrayList<>();
        headers.add(new String[]{"id", "clusterId", "x", "y"});
        System.out.println("HEEEEEYYYYY " + clusters.size());
        writer.writeAll(headers);
        int index = 0;
        int complexIndex = 0;
        final List<String[]> output = new ArrayList<>();
        for(Cluster c : clusters) {
            //System.out.println("HEEEEEYYYYY Complex Formed with Grid with ids " + c.());

            for(final double[] point : c.getDataPoints()) {
                output.add(new String[]{String.valueOf(index), String.valueOf(complexIndex), String.valueOf(point[0]), String.valueOf(point[1])});
                index = index + 1;

            }
            writer.writeAll(output);
            complexIndex++;
        }



        writer.close();
    }

    public static void main(String[] args) throws IOException {
        String fileLocation = "/Users/reno/bdma/datamining/bdma-ulb-datamining-assignment-2/ex_Aggregation.csv";
        List<double[]> dataSet = Files.readAllLines(Paths.get(fileLocation))
                                      .stream()
                                      .map(string -> string.split(",")) // Each line is a string, we break it based on delimiter ',' . This gives us an array
                                      .skip(1) //Skip the header
                                      .map(array -> new double[]{Double.valueOf(array[1]), Double.valueOf(array[2])}) // The 2nd and 3rd column in the csv file
                                      .collect(Collectors.toList());
        double epsilon = 1.8;
        int minPts = 1550;

        DBScan dbScan = new DBScan(dataSet, epsilon, minPts);
        List<Cluster> clusters = dbScan.compute();


        printClusters(clusters);
        for(Cluster cluster : clusters) {
            //This had to be done because the default toString representation of double[] is just the hash code
            System.out.println(Util.stringRepresentation(cluster.getDataPoints()));
            System.out.println(cluster.getSize());
        }


    }


}
