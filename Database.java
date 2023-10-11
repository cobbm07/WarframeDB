import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

public class Database {
    public static void main(String args[]) {
        List<Weapon> weapons = new ArrayList<>();
        weapons.addAll(DataReader.readRangedData());
        weapons.addAll(DataReader.readMeleeData());
        
        List<Warframe> frames = new ArrayList<>();
        frames.addAll(DataReader.readFrameData());
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        while(running) {
            running = mainMenu(reader, weapons, frames);
        }
        try
        {
            reader.close();
        }
        catch (IOException e)
        {
            System.out.println("Could not close reader!");
        }
    }
    
    public static boolean mainMenu(BufferedReader reader, List<Weapon> weapons, List<Warframe> frames) {
        System.out.println("Interact with Weapons, Warframes, or Both?");
        System.out.println("1) Weapons");
        System.out.println("2) Warframes");
        System.out.println("3) Both");
        System.out.println("4) Quit");
        int choice = validChoice(1,4,reader);
        switch(choice) {
            case 1:
                Weapon.weaponMenu(reader, weapons);
                break;
            case 2:
                Warframe.warframeMenu(reader, frames);
                break;
            case 3:
                allMenu(reader, weapons, frames);
                break;
            case 4:
                System.out.println("Okay, goodbye!");
                return false;
            default:
                System.out.println("Error with user choice!");
        }
        return true;
    }
    public static void allMenu(BufferedReader reader, List<Weapon> weapons, List<Warframe> frames) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Print the list");
        System.out.println("2) Sort the list by name");
        System.out.println("3) Sort the list by tier");
        System.out.println("4) Search for an item by name");
        System.out.println("5) Search for an item by tier");
        System.out.println("6) Show only Prime items");
        System.out.println("7) Create a new Build");
        System.out.println("8) Open a previous Build");
        System.out.println("9) Look up an item on the wiki page");
        int choice = validChoice(1,9,reader);
        boolean valid = false;
        while (!valid) {
            try {
                switch(choice) {
                    case 1:
                        printAll(weapons, frames);
                        valid = true;
                        break;
                    case 2:
                        weapons.sort((x, y) -> x.getName().compareTo(y.getName()));
                        frames.sort((x, y) -> x.getName().compareTo(y.getName()));
                        printAll(weapons, frames);
                        valid = true;
                        break;
                    case 3:
                        weapons.sort((x, y) -> x.getTier().compareTo(y.getTier()));
                        frames.sort((x, y) -> x.getTier().compareTo(y.getTier()));
                        valid = true;
                        printAll(weapons, frames);
                        break;
                    case 4:
                        System.out.println("Enter the item name to search for: ");
                        String name = reader.readLine();
                        Stream weaponStream = weapons.stream()
                            .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()));
                        Stream frameStream = frames.stream()
                            .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()));
                        if (weaponStream.count() == 0 && frameStream.count() == 0) {
                            System.out.println("No items match this search term! Try another search: ");
                        }
                        else {
                            valid = true;
                            printAll(weapons.stream()
                                .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()))
                                .collect(Collectors.toList()),
                            frames.stream()
                                .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()))
                                .collect(Collectors.toList()));
                        }
                        break;
                    case 5:
                        System.out.println("Enter the item tier to search for: ");
                        String tier = reader.readLine();
                        weaponStream = weapons.stream()
                            .filter(x -> x.getTier().name().equalsIgnoreCase(tier));
                        frameStream = frames.stream()
                            .filter(x -> x.getTier().name().equalsIgnoreCase(tier));
                        if (weaponStream.count() == 0 && frameStream.count() == 0) {
                            System.out.println("No items match this search term! Try another search: ");
                        }
                        else {
                            valid = true;
                            printAll(
                            weapons.stream()
                                .filter(x -> x.getTier().name().equalsIgnoreCase(tier))
                                .collect(Collectors.toList()), 
                            frames.stream()
                                .filter(x -> x.getTier().name().equalsIgnoreCase(tier))
                                .collect(Collectors.toList()));
                        }
                        break;
                    case 6:
                        printAll(
                        weapons.stream()
                            .filter(x -> x.getName()
                            .contains("Prime"))
                            .collect(Collectors.toList()),
                        frames.stream()
                            .filter(x -> x.getName()
                            .contains("Prime"))
                            .collect(Collectors.toList()));
                        valid = true;
                        break;
                    case 7:
                        Build newBuild = new Build();
                        newBuild.createBuild(reader, weapons, frames);
                        valid = true;
                        break;
                    case 8:
                        System.out.println("Search for the build by its name: ");
                        try {
                            String buildName = reader.readLine();
                            ObjectInputStream input = new ObjectInputStream(new FileInputStream("Data/Builds/" + buildName + ".build"));
                            System.out.println(((Build)input.readObject()).toString());
                            valid = true;
                        }
                        catch (FileNotFoundException e) {
                            System.out.println("The given build does not exist!");
                            valid = true;
                        }
                        catch (ClassNotFoundException e) {
                            System.out.println("Class not found!");
                        }
                        catch (IOException e) {
                            System.out.println("Error reading from file!");
                        }
                        break;
                    case 9:
                        System.out.println("What is the name of the item you would like to look up: ");
                        name = reader.readLine();
                        weaponStream = weapons.stream()
                            .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()));
                        frameStream = frames.stream()
                            .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()));
                        if (weaponStream.count() == 0 && frameStream.count() == 0) {
                            System.out.println("No items match this search term! Try another search: ");
                        }
                        else {
                            valid = true;
                            List<String> weaponNames = weapons.stream()
                                .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()))
                                .map(x -> x.getName())
                                .collect(Collectors.toList());
                            List<String> frameNames = frames.stream()
                                .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()))
                                .map(x -> x.getName())
                                .collect(Collectors.toList());
                            List<String> names = Stream.concat(weaponNames.stream(), frameNames.stream())
                                .collect(Collectors.toList());
                            for (int j = 0; j < names.size(); j++) {
                                System.out.println((j+1) + ") " + names.get(j));
                            }
                            choice = (Database.validChoice(1, names.size(), reader)) - 1;
                            lookUpWord(names.get(choice));
                        }
                        break;
                    default:
                        System.out.println("Error with user choice!");
                }
            }
            catch (IOException e) {
                System.out.println("Invalid user choice!");
            }
        }
    }
    
    public static int validChoice(int min, int max, BufferedReader reader) {
        int choice = -1;
        boolean valid = false;
        while (!valid) {
            try {
                choice = Integer.parseInt(reader.readLine());
                if (choice < min || choice > max) {
                    System.out.print("Please enter a valid choice: ");
                }
                else valid = true;
            }
            catch (IOException e) {
                System.out.print("Please enter a valid choice: ");
            }
            catch (NumberFormatException e) {
                System.out.print("Please enter a valid choice: ");
            }
        }
        return choice;
    }
    
    public static void printAll(List<Weapon> weapons, List<Warframe> frames) {
        System.out.println("List of weapons:");
        Weapon.printWeapons(weapons);
        System.out.println("\n\nList of Warframes:");
        Warframe.printFrames(frames);
    }
    
    public static void lookUpWord(String word) {
        Desktop d = Desktop.getDesktop();
        try {
            word = word.replace(' ', '_');
            d.browse(new URI("https://warframe.fandom.com/wiki/" + word));
        }
        catch (IOException e) {
            System.out.println("Error finding webpage");
        }
        catch (URISyntaxException e) {
            System.out.println("Error with webpage format");
        }
    }
}