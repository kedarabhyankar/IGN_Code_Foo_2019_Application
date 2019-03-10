import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * IGN Code Foo 2019 - Witcher Inventory
 * <p>
 * This program finds the maximum armor value of each of the given categories. If the cost of all of these exceeds the
 * maximum budget allowed, then the program runs again, until the total cost is under the given budget.
 *
 * @author Kedar Abhyankar
 * @version March 9th, 2019
 */
public class WitcherProject {

    /**
     * Definition of some class variables here...
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
    private static ArrayList<Integer> helmetValuesToSkip = new ArrayList<>();
    private static ArrayList<Integer> chestValuesToSkip = new ArrayList<>();
    private static ArrayList<Integer> leggingsValuesToSkip = new ArrayList<>();
    private static ArrayList<Integer> bootsValuesToSkip = new ArrayList<>();
    private static boolean firstTimeThrough = true;
    private static int maxHelmet = 0;
    private static int maxChest = 0;
    private static int maxLeggings = 0;
    private static int maxBoots = 0;
    private static int maxHelmetCost = 1000;
    private static int maxChestCost = 1000;
    private static int maxLeggingsCost = 1000;
    private static int maxBootsCost = 1000;

    /**
     * Psuedocode
     * <p>
     * import csv
     * if csv loads correctly, load csv into 2d array
     * separate header from rest of text
     * DO NOT INCLUDE HEADER IN 2D ARRAY
     * <p>
     * once loaded into 2d array...
     * <p>
     * cycle through array
     * find max of each category
     * add each new elemt to a new 2d array (5x5)
     * <p>
     * if total value of all is over num crowns,
     * do again
     * else print
     * ---
     * <p>
     * some problems -
     * can't store multiple different values into the same array;
     * circumvent this by storing each column in it's own "2d" array, of 1 column, x rows
     * <p>
     * some solutions -
     * cycle through, set type of armor to one data type
     * now do loop as defined between "once loaded into 2d array..." and "---"
     * when numbers are found that work however - search through the array and find the row that has all
     * the values that work - use the values that worked and find the name of the item, print out the
     * best solution
     * <p>
     * OR
     * <p>
     * avoid all of this
     * and store into SINGLE 2d array of Strings
     * convert all data besides words into String forms using .toString();
     * then just do parseInt throughout the array
     * do  loop
     */
    public static void main(String[] args) throws IOException, NotInDataSetException {
        loadData();
        prettyPrintData("DATA FROM CSV FILE LOADED", "DATA IS DISPLAYED ABOVE");
        convertArmorTypeToIndex();
        System.out.println();
        do {
            indexOfMaxValHelmet = findMaxValueOfType(4, helmetValuesToSkip);
            indexOfMaxValChest = findMaxValueOfType(3, chestValuesToSkip);
            indexOfMaxValLeggings = findMaxValueOfType(2, leggingsValuesToSkip);
            indexOfMaxValBoots = findMaxValueOfType(1, bootsValuesToSkip);
            sumOfItemCost = Integer.parseInt(dataFromCSV[indexOfMaxValHelmet[0] - 1][indexOfMaxValHelmet[1] - 1]) +
                    Integer.parseInt(dataFromCSV[indexOfMaxValChest[0] - 1][indexOfMaxValChest[1] - 1]) +
                    Integer.parseInt(dataFromCSV[indexOfMaxValLeggings[0] - 1][indexOfMaxValLeggings[1] - 1]) +
                    Integer.parseInt(dataFromCSV[indexOfMaxValBoots[0] - 1][indexOfMaxValBoots[1] - 1]);
            //prettyPrintData("=DATA IN ARMOR INDEX FORM", "DATA IS DISPLAYED ABOVE");
            firstTimeThrough = numCrowns - sumOfItemCost < 0;
            if (firstTimeThrough) {
                System.out.println("Not enough crowns. Retrying.");
            }
            helmetValuesToSkip.add(Integer.parseInt(dataFromCSV[indexOfMaxValHelmet[0] - 1][indexOfMaxValHelmet[1]]));
            chestValuesToSkip.add(Integer.parseInt(dataFromCSV[indexOfMaxValChest[0] - 1][indexOfMaxValChest[1]]));
            leggingsValuesToSkip.add(Integer.parseInt(dataFromCSV[indexOfMaxValLeggings[0] - 1][indexOfMaxValLeggings[1]]));
            bootsValuesToSkip.add(Integer.parseInt(dataFromCSV[indexOfMaxValBoots[0] - 1][indexOfMaxValBoots[1]]));
            System.out.println();
        } while ((numCrowns - sumOfItemCost) < 0);
        System.out.println("The maximum helmet value is " + dataFromCSV[indexOfMaxValHelmet[0] - 1][indexOfMaxValHelmet[1]] + " on line " + indexOfMaxValHelmet[0] + ", column " + indexOfMaxValHelmet[1] + " in the CSV file. This item is called " + dataFromCSV[indexOfMaxValHelmet[0] - 1][indexOfMaxValHelmet[1] - 2] + ", and costs " + dataFromCSV[indexOfMaxValHelmet[0] - 1][indexOfMaxValHelmet[1] - 1] + " crowns.");
        System.out.println("The maximum chest value is " + dataFromCSV[indexOfMaxValChest[0] - 1][indexOfMaxValChest[1]] + " on line " + indexOfMaxValChest[0] + ", column " + indexOfMaxValChest[1] + " in the CSV file. This item is called " + dataFromCSV[indexOfMaxValChest[0] - 1][indexOfMaxValChest[1] - 2] + ", and costs " + dataFromCSV[indexOfMaxValChest[0] - 1][indexOfMaxValChest[1] - 1] + " crowns.");
        System.out.println("The maximum leggings value is " + dataFromCSV[indexOfMaxValLeggings[0] - 1][indexOfMaxValLeggings[1]] + " on line " + indexOfMaxValLeggings[0] + ", column " + indexOfMaxValLeggings[1] + " in the CSV file. This item is called " + dataFromCSV[indexOfMaxValLeggings[0] - 1][indexOfMaxValLeggings[1] - 2] + ", and costs " + dataFromCSV[indexOfMaxValLeggings[0] - 1][indexOfMaxValLeggings[1] - 1] + " crowns.");
        System.out.println("The maximum boots value is " + dataFromCSV[indexOfMaxValBoots[0] - 1][indexOfMaxValBoots[1]] + " on line " + (indexOfMaxValBoots[0] + 1) + ", column " + indexOfMaxValBoots[1] + " in the CSV file. This item is called " + dataFromCSV[indexOfMaxValBoots[0]][indexOfMaxValBoots[1] - 2] + ", and costs " + dataFromCSV[indexOfMaxValBoots[0] - 1][indexOfMaxValBoots[1] - 1] + " crowns.");
        System.out.println("The total cost of all of the chosen items is " + sumOfItemCost + " crowns.");
        System.out.println("Should you choose to purchase this armor set, you will have " + (numCrowns - sumOfItemCost) + " crowns left.");

    }

    /**
     * The loadData method loads the data from the CSV file into a 2D array. The 2D array is manipulated throughout the
     * program. Should the 2D array throw an IOException, the program will not continue. Because this is a CSV, and it
     * contains two different types of data types - String and int - the program will instead store everything
     * in one 2D array of Strings. When an individual value has to be accessed, the use of the data types wrapper
     * class and parseInt() must be used to obtain the value.
     *
     * @throws IOException
     * @author Kedar Abhyankar
     * @version March 9th, 2019
     */
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
            throw new IOException("The file had issues with I/O. Perhaps the filename is incorrect. " +
                    "See if something is wrong with the file.");
        }
    }

    /**
     * This program does one simple task - it counts the number rows in a file, where the filename is passed
     * into the method. Should the file not exist, a catch statement will catch the IOException, and alert
     * the user of the problem. It does account for whether or not there is a header to the file or not.
     * If there is no header, the number of rows is just that - the number rows in the file. Otherwise, if
     * a header exists, the rowCount is incremented by one.
     *
     * @param fileName
     * @return number of rows in parameterized file.
     * @throws IOException
     * @author Kedar Abhyankar
     * @version March 9th, 2019
     */
    public static int countRows(File fileName) throws IOException {
        try {
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
        } catch (IOException e) {
            throw new IOException("The file had issues with I/O. Perhaps the file name is incorrect. " +
                    "See if something is wrong with the file.");
        }
    }

    /**
     * This method "pretty prints" the values in the CSV. This way, the user can see the values in the CSV file without
     * opening up the file in their IDE or an auxiliary program like Microsoft Excel. Many of the values are fine
     * tuned such that the headers are centered and that the data in the actual CSV file is printed correctly.
     * <p>
     * In addition, it takes two parameters - as defined below - these parameters display the text centered between the
     * "=" borders. Should you choose to change the text here, the entire line should be 176 characters - this way, the
     * "pretty printed" table and the ends of the table all align.
     *
     * @param topLine
     * @param bottomLine
     * @author Kedar Abhyankar
     * @version March 9th, 2019
     */
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

    /**
     * This method simply squashes the categories of items into integers. This way, it's easier to know from top
     * to bottom of the hero which piece of armor we're working with. In this program, it starts at 1 and ends at 4.
     * The helmet has a value of 4, chestpiece 3, leggings 2, and boots 1.
     *
     * @author Kedar Abhyankar
     * @version March 9th, 2019.
     */
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

    /**
     * This is the most complicated part of the program - it finds the maximum value under many different constraints, in
     * each column of the dataset. This method has three constraints - the first, is the category. It uses the
     * parameterized value to find the category, post data squashing. The second, is the List of values not allowed.
     * Should the program run multiple times, we want to make sure that the program avoids the maximums from the time before.
     * This is because that item may be causing the price to go over the maximum number of crowns allowed. The third constraint is
     * the cost. Because items can have the same cost, we want to be cost efficient -  if two items both have an armor value of 50,
     * we'd want the one that costs less, right? This is what the third parameter handles - it makes sure that the cost of the
     * item doesn't force the budget to be exceeded.
     * <p>
     * Once the item is found, it sets the field 'maxHelmet' to armor value. It also sets the cost equal to the same item's cost. The
     * final step is to return the index at which the maximum value exists, stored in a 1D array of type int.
     *
     * @param type
     * @param listOfValuesToSkip
     * @return a 1D integer array detailing the coordinates of the maximum armor type of a category.
     */
    public static int[] findMaxValueOfType(int type, ArrayList<Integer> listOfValuesToSkip) throws NotInDataSetException {
        try {
            int[] indicesOfMax = new int[2];
            if (type == 4) {
                for (int i = 1; i < dataFromCSV.length; i++) {
                    if (Integer.parseInt(dataFromCSV[i][0]) == 4) {
                        if (Integer.parseInt(dataFromCSV[i][3]) > maxHelmet &&
                                !listOfValuesToSkip.contains(Integer.parseInt(dataFromCSV[i][3])) &&
                                Integer.parseInt(dataFromCSV[i][2]) < maxHelmetCost) {
                            maxHelmet = Integer.parseInt(dataFromCSV[i][3]);
                            maxHelmetCost = Integer.parseInt(dataFromCSV[i][2]);
                            indicesOfMax[0] = i;
                            indicesOfMax[1] = 3;
                        }
                    }
                }
            } else if (type == 3) {
                for (int i = 1; i < dataFromCSV.length; i++) {
                    if (Integer.parseInt(dataFromCSV[i][0]) == 3) {
                        if (Integer.parseInt(dataFromCSV[i][3]) > maxChest &&
                                !listOfValuesToSkip.contains(Integer.parseInt(dataFromCSV[i][3])) &&
                                Integer.parseInt(dataFromCSV[i][2]) < maxChestCost) {
                            maxChest = Integer.parseInt(dataFromCSV[i][3]);
                            maxChestCost = Integer.parseInt(dataFromCSV[i][2]);
                            indicesOfMax[0] = i;
                            indicesOfMax[1] = 3;
                        }
                    }
                }
            } else if (type == 2) {
                for (int i = 1; i < dataFromCSV.length; i++) {
                    if (Integer.parseInt(dataFromCSV[i][0]) == 2) {
                        if (Integer.parseInt(dataFromCSV[i][3]) > maxLeggings &&
                                !listOfValuesToSkip.contains(Integer.parseInt(dataFromCSV[i][3])) &&
                                Integer.parseInt(dataFromCSV[i][2]) < maxLeggingsCost) {
                            maxLeggings = Integer.parseInt(dataFromCSV[i][3]);
                            maxLeggingsCost = Integer.parseInt(dataFromCSV[i][2]);
                            indicesOfMax[0] = i;
                            indicesOfMax[1] = 3;
                        }
                    }
                }
            } else if (type == 1) {
                for (int i = 1; i < dataFromCSV.length; i++) {
                    if (Integer.parseInt(dataFromCSV[i][0]) == 1) {
                        if (Integer.parseInt(dataFromCSV[i][3]) > maxBoots &&
                                !listOfValuesToSkip.contains(Integer.parseInt(dataFromCSV[i][3])) &&
                                Integer.parseInt(dataFromCSV[i][2]) < maxBootsCost) {
                            maxBoots = Integer.parseInt(dataFromCSV[i][3]);
                            maxBootsCost = Integer.parseInt(dataFromCSV[i][2]);
                            indicesOfMax[0] = i;
                            indicesOfMax[1] = 3;
                        }
                    }
                }
            } else {
                throw new NotInDataSetException("The value was something not in the dataset. Check to  make sure that" +
                        "you didn't misspell a category such that a value was not squashed to -1.");
            }
            return indicesOfMax;

        } catch (NotInDataSetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
