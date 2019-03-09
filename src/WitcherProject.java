import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

public class WitcherProject {

    /**
     * Define some class variables here...
     **/

    private static final String fileName = "Witcher Inventory.csv";
    private static String[][] dataFromCSV;
    private static boolean hasHeader = true;

    public static void main(String[] args) throws IOException {
        /**
         * Psuedocode
         *
         * import csv
         * if csv loads correctly, load csv into 2d array
         * separate header from rest of text
         * DO NOT INCLUDE HEADER IN 2D ARRAY
         *
         * once loaded into 2d array...
         *
         * cycle through array
         * find max of each category
         * add each new elemt to a new 2d array (5x5)
         *
         * if total value of all is over num crowns,
         * do again
         * else print
         * ---
         *
         * some problems -
         * can't store multiple different values into the same array;
         *  circumvent this by storing each column in it's own "2d" array, of 1 column, x rows
         *
         * some solutions -
         * cycle through, set type of armor to one data type
         * now do loop as defined between "once loaded into 2d array..." and "---"
         * when numbers are found that work however - search through the array and find the row that has all
         * the values that work - use the values that worked and find the name of the item, print out the
         * best solution
         *
         * OR
         *
         * avoid all of this
         * and store into SINGLE 2d array of Strings
         * convert all data besides words into String forms using .toString();
         * then just do parseInt throughout the array
         * do  loop
         *
         *
         */
        loadData();
        for (int i = 0; i < dataFromCSV.length; i++) {
            for (int j = 0; j < dataFromCSV[i].length; j++) {
                System.out.print(dataFromCSV[i][j] + " ");
            }
            System.out.println();
        }

    }

    public static void loadData() throws IOException {

        File file = new File(fileName);
        dataFromCSV = new String[countRows(file)][4];

        System.out.println();
        System.out.println("Number of Rows : "+dataFromCSV.length);
        System.out.println("Number of Columns : " + dataFromCSV[0].length);
        System.out.println();
        int row = 0;

        //read each line of text file
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader(fileName)));
            while (s.hasNextLine()) {
                String nextLine = s.nextLine();
                String tempArray[] = nextLine.split(",");


                for (int i = 0; i < tempArray.length; i++) {
                    dataFromCSV[row][i] = tempArray[i];
                }
                row++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int countRows(File fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName.getAbsoluteFile()));
        String input;
        int numRows; // -1 for header, 0 if no header in file

        if (hasHeader) {
            numRows = -1;
        } else {
            numRows = 0;
        }

        while ((input = br.readLine()) != null) {
            numRows++;
        }

        if (hasHeader) {
            return numRows + 1;
        } else {
            return numRows;
        }
    }
}
