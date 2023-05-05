import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.util.Scanner;
import java.util.ArrayDeque;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = "";
        ArrayDeque<String> queue = new ArrayDeque<>();
        List<String> zipCodes;
        if (args[1].equals("AL")) {
            zipCodes = new ArrayList<String>();
        } 
        else {
            zipCodes = new LinkedList<String>();
        }
        System.out.println("Enter Command (or 'quit' to exit): ");
        String input = scanner.nextLine(); // User-Input: Zip 94108 Summary
        while (!input.toLowerCase().equals("quit")) {
            if (!input.equals("history")) {
                queue.add(input);
            }
            String[] inputArray = input.split(" "); // ["Zip", "94108", "Summary"]
            try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                br.readLine();
                List<String> businessTypes;
                List<String> neighborhoods;
                List<String> closedBusinesses;
                List<String> newBusinesses;
                List<String> naicsCodes;
                List<String> totalZipCodes;
                if (args[1].equals("AL")) {
                    businessTypes = new ArrayList<String>();
                    neighborhoods = new ArrayList<String>();
                    closedBusinesses = new ArrayList<String>();
                    newBusinesses = new ArrayList<String>();
                    naicsCodes = new ArrayList<String>();
                    totalZipCodes = new ArrayList<String>();

                }
                else {
                    businessTypes = new LinkedList<String>();
                    neighborhoods = new LinkedList<String>();
                    closedBusinesses = new LinkedList<String>();
                    newBusinesses = new LinkedList<String>();
                    naicsCodes = new LinkedList<String>();
                    totalZipCodes = new LinkedList<String>();

                }
                int zipTotalBusinesses = 0;
                int zipTotalBusinessTypes = 0;
                int zipTotalNeighborhoods = 0;
                int naicsNeighborhoods = 0;
                int naicsZipCodes = 0;
                int naicsTotalBusinesses = 0;
                int total = 0;
                int closed = 0;
                int openedLastYear = 0;
                while ((line = br.readLine()) != null) {
                    /* zip */
                    if (inputArray[0].toLowerCase().equals("zip")) {
                        String zipCode = inputArray[1].substring(0, 5); // 94108

                        String[] column = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                        String zip = column[7];
                        if (zip.equals("")) {
                            continue;
                        }
                        if (!zipCodes.contains(zip)) {
                            zipCodes.add(zip);
                        }
                        if (zip.equals(zipCode)) {
                            zipTotalBusinesses++;
                            String businessDescription = column[17];
                            if (!businessDescription.equals("")) {
                                /* add unique businessDescription */
                                if (!businessTypes.contains(businessDescription)) {
                                    businessTypes.add(businessDescription);
                                    zipTotalBusinessTypes++;
                                }
                            }
                            String neighborhood = column[23];
                            if (!neighborhood.equals("")) {
                                /* add unique neighborhoods */
                                if (!neighborhoods.contains(neighborhood)) {
                                    neighborhoods.add(neighborhood);
                                    zipTotalNeighborhoods++;
                                }
                            }
                        }
                    }
                    if (inputArray[0].toUpperCase().equals("NAICS")) {
                        String[] column = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                        String inputCode = inputArray[1];
                        int inputCodeInt = Integer.parseInt(inputCode);
                        String codes = column[16];
                        String type = column[17];

                        if (type.equals("")) {    
                            continue;
                        }
                        if (!businessTypes.contains(type)) {
                            businessTypes.add(type);
                        }
                        if (codes.equals("")) {
                            continue;
                        }
                        if (!naicsCodes.contains(codes)) {
                            naicsCodes.add(codes);
                        }
                        for (int i = 1; i <= naicsCodes.size(); i++) {
                            String[] splitCode = naicsCodes.get(i-1).split("[\\s-]+");
                            int lowerBound = Integer.parseInt(splitCode[0]);
                            int upperBound = Integer.parseInt(splitCode[1]);
                            if (inputCodeInt >= lowerBound && inputCodeInt <= upperBound) {
                                // String description = businessTypes.get(i);
                                //if description == whats at column 17 add naicstotalBuiss

                                naicsTotalBusinesses++;
                                String naicsZip = column[7];
                                if (!naicsZip.equals("")) {
                                    /* add unique businessDescription */
                                    if (!totalZipCodes.contains(naicsZip)) {
                                        totalZipCodes.add(naicsZip);
                                        naicsZipCodes++;
                                    }
                                    String neighborhood = column[23];
                                    if (!neighborhood.equals("")) {
                                    /* add unique neighborhoods */
                                        if (!neighborhoods.contains(neighborhood)) {
                                            neighborhoods.add(neighborhood);
                                            naicsNeighborhoods++;
                                        }
                                    }
                                }
                            }
                        }
                        
                    }
                    /* closed and new businesses */
                    if (inputArray[0].toLowerCase().equals("summary")) {
                        String[] column = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                        String endDate = column[9];
                        String startDate = column[8];
                        if (endDate != "") {
                            closedBusinesses.add(endDate); // do i need this?
                            closed++;
                        }
                        String year = startDate.substring(startDate.length() - 4);
                        if (year.equals("2022")) {
                            newBusinesses.add(startDate); //do i need this
                            openedLastYear++;
                        }
                    }

                    total++;
                }
                if (inputArray[0].toLowerCase().equals("zip") && inputArray[2].toLowerCase().equals("summary")) {
                    System.out.println(inputArray[1] + " Business Summary");
                    System.out.println("Total Businesses: " + zipTotalBusinesses);
                    System.out.println("Business Types: " + zipTotalBusinessTypes);
                    System.out.println("Neighborhood: " + zipTotalNeighborhoods);
                }
                if (inputArray[0].toUpperCase().equals("NAICS") && inputArray[2].toLowerCase().equals("summary")) {
                    System.out.println("Total Businesses: " + naicsTotalBusinesses);
                    System.out.println("Zip Codes: " + naicsZipCodes);
                    System.out.println("Neighborhood: " + naicsNeighborhoods);
                }
                if (inputArray[0].toLowerCase().equals("summary")) {
                    System.out.println("Total Businesses: " + total);
                    System.out.println("Closed Businesses: " + closed);
                    System.out.println("New Businesses: " + openedLastYear);
                }
                if (inputArray[0].toLowerCase().equals("history")) {
                    for (String command : queue) {
                        System.out.println();
                        System.out.println(command);
                        System.out.println();
                        
                    }
                }
                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Enter Command (or 'quit' to exit): ");
            input = scanner.nextLine();
        }
        scanner.close();
    }
}