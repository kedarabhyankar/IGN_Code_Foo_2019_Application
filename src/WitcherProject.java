import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class WitcherProject {

    /**
     * Define some class variables here...
     **/

    private static final String fileName = "Witcher Inventory.csv";
    private static String[][] dataFromCSV;
    private static boolean hasHeader = false;
    private static int[] indexOfMaxValHelmet;
    private static int[] indexOfMaxValChest;
    private static int[] indexOfMaxValLeggings;
    private static int[] indexOfMaxValBoots;
    private static final int numCrowns = 300;
    private static int sumOfItemCost = 0;

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
        prettyPrintData("DATA FROM CSV FILE LOADED", "DATA IS DISPLAYED ABOVE");
        convertArmorTypeToIndex();
        System.out.println();
        do {
            indexOfMaxValHelmet = findMaxValueOfType(4);
            indexOfMaxValChest = findMaxValueOfType(3);
            indexOfMaxValLeggings = findMaxValueOfType(2);
            indexOfMaxValBoots = findMaxValueOfType(1);
            sumOfItemCost = Integer.parseInt(dataFromCSV[indexOfMaxValHelmet[0]][indexOfMaxValHelmet[1] - 1]) +
                    Integer.parseInt(dataFromCSV[indexOfMaxValChest[0]][indexOfMaxValChest[1] - 1]) +
                    Integer.parseInt(dataFromCSV[indexOfMaxValLeggings[0]][indexOfMaxValLeggings[1] - 1]) +
                    Integer.parseInt(dataFromCSV[indexOfMaxValBoots[0]][indexOfMaxValBoots[1] - 1]);
            prettyPrintData("=DATA IN ARMOR INDEX FORM", "DATA IS DISPLAYED ABOVE");
            System.out.println("The maximum helmet value is: " + dataFromCSV[indexOfMaxValHelmet[0]][indexOfMaxValHelmet[1]] + " at position " + indexOfMaxValHelmet[0] + "," + indexOfMaxValHelmet[1] + " in the CSV file.");
            System.out.println("The maximum chest value is: " + dataFromCSV[indexOfMaxValChest[0]][indexOfMaxValChest[1]] + " at position " + indexOfMaxValChest[0] + "," + indexOfMaxValChest[1] + " in the CSV file.");
            System.out.println("The maximum leggings value is: " + dataFromCSV[indexOfMaxValLeggings[0]][indexOfMaxValLeggings[1]] + " at position " + indexOfMaxValLeggings[0] + "," + indexOfMaxValLeggings[1] + " in the CSV file.");
            System.out.println("The maximum boots value is: " + dataFromCSV[indexOfMaxValBoots[0]][indexOfMaxValBoots[1]] + " at position " + indexOfMaxValBoots[0] + "," + indexOfMaxValBoots[1] + " in the CSV file.");
            System.out.println("The total cost of all of the chosen items is: " + sumOfItemCost);
            System.out.println();
        } while ((numCrowns - sumOfItemCost) < 0);

    }

    public static void loadData() throws IOException {

        File file = new File(fileName);
        dataFromCSV = new String[countRows(file)][4];

        System.out.println();
        System.out.println("Number of Rows : " + dataFromCSV.length);
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
        } catch (IOException e) {
            throw new IOException("The file had issues with I/O. See if something is wrong with the file.");
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

    public static void prettyPrintData(String topLine, String bottomLine) {
        System.out.println("===========================================================================" + topLine + "============================================================================");
        String[] fileHeaders = {"Item Category", "Item Name", "Item Cost (in crowns)", "Armor Value"};
        for (String s : fileHeaders) {
            System.out.printf("               %-27s |", s);
        }
        System.out.println();
        for (int i = 0; i < 176; i++) {
            System.out.print("_");
        }
        System.out.println();
        for (int i = 1; i < dataFromCSV.length; i++) { //1 if skipping header, else 0
            for (int j = 0; j < dataFromCSV[i].length; j++) {
                System.out.printf("| %-40s |", dataFromCSV[i][j]);
            }
            System.out.println();
        }
        System.out.println("============================================================================" + bottomLine + "=============================================================================");
    }

    public static void convertArmorTypeToIndex() {
        /**
         * Helmet - 4
         * Chest - 3
         * Leggings - 2
         * Boots - 1
         */
        for (int i = 0; i < dataFromCSV.length; i++) {
            if (dataFromCSV[i][0].trim().equalsIgnoreCase("Helmet")) {
                dataFromCSV[i][0] = Integer.toString(4);
            } else if (dataFromCSV[i][0].trim().equalsIgnoreCase("Chest")) {
                dataFromCSV[i][0] = Integer.toString(3);
            } else if (dataFromCSV[i][0].trim().equalsIgnoreCase("Leggings")) {
                dataFromCSV[i][0] = Integer.toString(2);
            } else if (dataFromCSV[i][0].trim().equalsIgnoreCase("Boots")) {
                dataFromCSV[i][0] = Integer.toString(1);
            } else {
                dataFromCSV[i][0] = Integer.toString(-1);
            }
        }
    }

    public static int[] findMaxValueOfType(int type) {
        int maxHelmet = 0;
        int maxChest = 0;
        int maxLeggings = 0;
        int maxBoots = 0;
        int num = 0;
        int[] indicesOfMax = new int[2];
        if (type == 4) {
            for (int i = 1; i < dataFromCSV.length; i++) {
                if (Integer.parseInt(dataFromCSV[i][0]) == 4) {
                    if (Integer.parseInt(dataFromCSV[i][3]) > maxHelmet) {
                        maxHelmet = Integer.parseInt(dataFromCSV[i][3]);
                        indicesOfMax[0] = i;
                        indicesOfMax[1] = 3;
                    }
                }
            }
        } else if (type == 3) {
            for (int i = 1; i < dataFromCSV.length; i++) {
                if (Integer.parseInt(dataFromCSV[i][0]) == 3) {
                    if (Integer.parseInt(dataFromCSV[i][3]) > maxChest) {
                        maxChest = Integer.parseInt(dataFromCSV[i][3]);
                        indicesOfMax[0] = i;
                        indicesOfMax[1] = 3;
                    }
                }
            }
        } else if (type == 2) {
            for (int i = 1; i < dataFromCSV.length; i++) {
                if (Integer.parseInt(dataFromCSV[i][0]) == 2) {
                    if (Integer.parseInt(dataFromCSV[i][3]) > maxLeggings) {
                        maxLeggings = Integer.parseInt(dataFromCSV[i][3]);
                        indicesOfMax[0] = i;
                        indicesOfMax[1] = 3;
                    }
                }
            }
        } else if (type == 1) {
            for (int i = 1; i < dataFromCSV.length; i++) {
                if (Integer.parseInt(dataFromCSV[i][0]) == 1) {
                    if (Integer.parseInt(dataFromCSV[i][3]) > maxBoots) {
                        maxBoots = Integer.parseInt(dataFromCSV[i][3]);
                        indicesOfMax[0] = i;
                        indicesOfMax[1] = 3;
                    }
                }
            }
        } else {
            System.out.println("Oops");
            indicesOfMax[0] = -1;
            indicesOfMax[1] = -1;
        }

        return indicesOfMax;
    }


}
