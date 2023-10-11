import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.Serializable;

public class Warframe implements Serializable {
    private String name, abilities[];
    private int energy, health, shield, armor;
    private double sprintSpeed;
    private Tier tier;
    
    public Warframe(String n, String ab[], int e, int h, int s, int ar, double sS, Tier tier) {
        this.name = n;
        this.abilities = ab;
        this.energy = e;
        this.health = h;
        this.shield = s;
        this.armor = ar;
        this.sprintSpeed = sS;
        this.tier = tier;
    }
    @Override
    public String toString() {
        String spacing[] = {"\t\t", "\t", "\t", "\t", "\t", "\t\t", "\t\t\t", "\t\t\t", "\t\t\t", "\t\t\t"};
        Object info[] = {this.name, this.health, this.shield, this.armor, this.energy, this.sprintSpeed, this.abilities[0], this.abilities[1], this.abilities[2], this.abilities[3], this.tier};
        String data = "";
        for (int i = 0; i < info.length; i++) {
            if (i == 5) {
                info[i] = String.format("%.2f", info[i]);
            }
            if (i != info.length - 1) {
                if (info[i] instanceof String) {
                    String infoStr = String.valueOf(info[i]);
                    if (infoStr.length() >= 8) {
                        spacing[i] = spacing[i].substring(1, spacing[i].length());
                    }
                    if (infoStr.length() >= 16) {
                        spacing[i] = spacing[i].substring(1, spacing[i].length());
                    }
                    if (infoStr.length() >= 24) {
                        spacing[i] = spacing[i].substring(1, spacing[i].length());
                    }
                    if (infoStr.length() >= 32) {
                        spacing[i] = spacing[i].substring(1, spacing[i].length());
                    }
                }
                data += info[i];
                data += spacing[i];
            }
            else {
                data += info[i];
            }
        }
        return data;
    }
    public static void warframeMenu(BufferedReader reader, List<Warframe> frames) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Print the list");
        System.out.println("2) Sort the list");
        System.out.println("3) Search for a Warframe");
        System.out.println("4) Show only Prime items");
        int choice = Database.validChoice(1,4,reader);
        switch(choice) {
            case 1:
                printFrames(frames);
                break;
            case 2:
                framesSort(reader, frames);
                break;
            case 3:
                framesSearch(reader, frames);
                break;
            case 4:
                printFrames(frames.stream()
                    .filter(x -> x.getName()
                    .contains("Prime"))
                    .collect(Collectors.toList()));
                break;
            default:
                System.out.println("Error with user choice!");
        }
        return;
    }
    public static void framesSort(BufferedReader reader, List<Warframe> frames) {
        System.out.println("What would you like to sort by?");
        System.out.println("1) Warframe Name");
        System.out.println("2) Warframe Health");
        System.out.println("3) Warframe Shield");
        System.out.println("4) Warframe Armor");
        System.out.println("5) Warframe Energy");
        System.out.println("6) Warframe Sprint Speed");
        System.out.println("7) Warframe Tier");
        
        int choice = Database.validChoice(1,7,reader);
        switch(choice) {
            case 1:
                frames.sort((x, y) -> x.getName().compareTo(y.getName()));
                System.out.println("List of Warframes, sorted by Name");
                printFrames(frames);
                break;
            case 2:
                frames.sort((x, y) -> Integer.compare(x.getHealth(), y.getHealth()));
                System.out.println("List of Warframes, sorted by Health");
                printFrames(frames);
                break;
            case 3:
                frames.sort((x, y) -> Integer.compare(x.getShield(), y.getShield()));
                System.out.println("List of Warframes, sorted by Shield");
                printFrames(frames);
                break;
            case 4:
                frames.sort((x, y) -> Integer.compare(x.getArmor(), y.getArmor()));
                System.out.println("List of Warframes, sorted by Armor");
                printFrames(frames);
                break;
            case 5:
                frames.sort((x, y) -> Integer.compare(x.getEnergy(), y.getEnergy()));
                System.out.println("List of Warframes, sorted by Energy");
                printFrames(frames);
                break;
            case 6:
                frames.sort((x, y) -> Double.compare(x.getSprintSpeed(), y.getSprintSpeed()));
                System.out.println("List of Warframes, sorted by Sprint Speed");
                printFrames(frames);
                break;
            case 7:
                frames.sort((x, y) -> x.getTier().compareTo(y.getTier()));
                printFrames(frames);
                break;
            default:
                System.out.println("Error with user choice!");
        }
    }
    public static void framesSearch(BufferedReader reader, List<Warframe> frames) {
        System.out.println("What would you like to search by?");
        System.out.println("1) Warframe Name");
        System.out.println("2) Warframe Minimum Health");
        System.out.println("3) Warframe Minimum Shield");
        System.out.println("4) Warframe Minimum Armor");
        System.out.println("5) Warframe Minimum Energy");
        System.out.println("6) Warframe Minimum Sprint Speed");
        System.out.println("7) Warframe Tier");
        int choice = Database.validChoice(1,7,reader);
        boolean valid = false;
        while (!valid) {
            try {
                switch(choice) {
                    case 1:
                        System.out.println("Enter the Warframe name to search for: ");
                        String name = reader.readLine();
                        Stream frameStream = frames.stream()
                            .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()));
                        if (frameStream.count() == 0) {
                            System.out.println("No items match this search term! Try another search: ");
                        }
                        else {
                            valid = true;
                            printFrames(frames.stream()
                                .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()))
                                .collect(Collectors.toList()));
                        }
                        break;
                    case 2:
                        System.out.println("Enter the minimum Health to search for: ");
                        try {
                            int minHealth = Integer.parseInt(reader.readLine());
                            frameStream = frames.stream()
                                .filter(x -> x.getHealth() >= minHealth);
                            if (frameStream.count() == 0) {
                                System.out.println("No items with that Health or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printFrames(frames.stream()
                                    .filter(x -> x.getHealth() >= minHealth)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Minimum Health! Try again: ");
                        }
                        break;
                    case 3:
                        System.out.println("Enter the minimum Shield to search for: ");
                        try {
                            int minShield = Integer.parseInt(reader.readLine());
                            frameStream = frames.stream()
                                .filter(x -> x.getShield() >= minShield);
                            if (frameStream.count() == 0) {
                                System.out.println("No items with that Shield or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printFrames(frames.stream()
                                    .filter(x -> x.getShield() >= minShield)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Minimum Shield! Try again: ");
                        }
                        break;
                    case 4:
                        System.out.println("Enter the minimum Armor to search for: ");
                        try {
                            int minArmor = Integer.parseInt(reader.readLine());
                            frameStream = frames.stream()
                                .filter(x -> x.getArmor() >= minArmor);
                            if (frameStream.count() == 0) {
                                System.out.println("No items with that Armor or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printFrames(frames.stream()
                                    .filter(x -> x.getArmor() >= minArmor)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Minimum Armor! Try again: ");
                        }
                        break;
                    case 5:
                        System.out.println("Enter the minimum Energy to search for: ");
                        try {
                            int minEnergy = Integer.parseInt(reader.readLine());
                            frameStream = frames.stream()
                                .filter(x -> x.getEnergy() >= minEnergy);
                            if (frameStream.count() == 0) {
                                System.out.println("No items with that Energy or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printFrames(frames.stream()
                                    .filter(x -> x.getEnergy() >= minEnergy)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Minimum Energy! Try again: ");
                        }
                        break;
                    case 6:
                        System.out.println("Enter the minimum Sprint Speed to search for: ");
                        try {
                            double minSpeed = Double.parseDouble(reader.readLine());
                            frameStream = frames.stream()
                                .filter(x -> x.getSprintSpeed() >= minSpeed);
                            if (frameStream.count() == 0) {
                                System.out.println("No items with that Sprint Speed or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printFrames(frames.stream()
                                    .filter(x -> x.getSprintSpeed() >= minSpeed)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Minimum Sprint Speed! Try again: ");
                        }
                        break;
                    case 7:
                        System.out.println("Enter the warframe Tier to search for: ");
                        String tier = reader.readLine();
                        Tier tiers[] = Tier.values();
                        boolean validTier = false;
                        for (Tier t : tiers) {
                            if (tier.toUpperCase().equals(t.toString().toUpperCase())) {
                                validTier = true;
                                printFrames(frames.stream()
                                    .filter(x -> x.getTier().equals(t))
                                    .collect(Collectors.toList()));
                                valid = true;
                                break;
                            }
                        }
                        if (!validTier) {
                            System.out.println("Invalid tier choice! Try again: ");
                        }
                        break;
                    default:
                        System.out.println("Error with user choice!");
                }
            }
            catch (IOException e) {
                System.out.println("Error with user choice!");
            }
            catch (NumberFormatException e) {
                System.out.print("Please enter a valid choice: ");
            }
        }
    }
    public static void printFrames(List<Warframe> frames) {
        System.out.println("Name\t\tHealth\tShield\tArmor\tEnergy\tSprint Speed\tAbilities\t\t\t\t\t\t\t\t\t\t\tTier");
        frames.forEach(System.out::println);
        System.out.println("Name\t\tHealth\tShield\tArmor\tEnergy\tSprint Speed\tAbilities\t\t\t\t\t\t\t\t\t\t\tTier");
    }
    public String getName() { return this.name; }
    public String[] getAbilities() { return this.abilities; }
    public int getEnergy() { return this.energy; }
    public int getHealth() { return this.health; }
    public int getShield() { return this.shield; }
    public int getArmor() { return this.armor; }
    public double getSprintSpeed() { return this.sprintSpeed; }
    public Tier getTier() { return this.tier; }
}