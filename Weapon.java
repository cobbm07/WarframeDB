import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.util.List;
import java.io.Serializable;

public class Weapon implements Serializable {
    protected String name;
    protected Slot slot;
    protected Type type;
    protected double baseDmg, totDmg;
    protected double critChance, critMult, statusChance, fireRate, susDps;
    protected Tier tier;
    public Weapon(String n, Slot s, Type t, double bDmg, double tD, double cC, double cM, double sD, double sC, double fR, Tier tier) {
        this.name = n;
        this.slot = s;
        this.type = t;
        this.baseDmg = bDmg;
        this.totDmg = tD;
        this.critChance = cC;
        this.critMult = cM;
        this.statusChance = sC;
        this.fireRate = fR;
        this.susDps = sD;
        this.tier = tier;
    }
    public Weapon(String n) {
        this.name = "None";
    }
    
    @Override
    public String toString() {
        String spacing[] = {"\t\t\t\t", "\t\t", "\t\t\t", "\t\t", "\t\t", "\t\t", "\t\t\t", "\t\t", "\t\t", "\t\t"};
        Object info[] = {this.name, this.slot, this.type, this.baseDmg, this.totDmg, this.critChance, this.critMult, this.statusChance, this.fireRate, this.susDps, this.tier};
        String data = "";
        for (int i = 0; i < info.length; i++) {
            if (i >= 3 && i != 10) {
                info[i] = String.format("%.2f", info[i]);
            }
            if (i == 5 || i == 7) {
                info[i] = String.format("%.1f%%",Double.parseDouble((String)info[i]) * 100);
            }
            if (i != info.length - 1) {
                if (info[i] instanceof String || info[i] instanceof Slot || info[i] instanceof Type) {
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
    
    public static void weaponMenu(BufferedReader reader, List<Weapon> weapons) {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1) Print the list");
        System.out.println("2) Sort the list");
        System.out.println("3) Search for a weapon");
        System.out.println("4) Show only Prime items");
        int choice = Database.validChoice(1,4,reader);
        switch(choice) {
            case 1:
                printWeapons(weapons);
                break;
            case 2:
                weaponSort(reader, weapons);
                break;
            case 3:
                weaponSearch(reader, weapons);
                break;
            case 4:
                printWeapons(weapons.stream()
                    .filter(x -> x.getName()
                    .contains("Prime"))
                    .collect(Collectors.toList()));
                break;
            default:
                System.out.println("Error with user choice!");
        }
        return;
    }
    public static void weaponSort(BufferedReader reader, List<Weapon> weapons) {
        System.out.println("What would you like to sort by?");
        System.out.println("1) Weapon slot");
        System.out.println("2) Weapon type");
        System.out.println("3) Weapon name");
        System.out.println("4) Weapon Total Damage");
        System.out.println("5) Weapon DPS");
        System.out.println("6) Weapon Status Chance");
        System.out.println("7) Weapon Critical Chance");
        System.out.println("8) Weapon Critical Multiplier");
        System.out.println("9) Weapon Fire Rate");
        System.out.println("10) Weapon Tier");
        int choice = Database.validChoice(1,10,reader);
        switch(choice) {
            case 1:
                weapons.sort((x, y) -> x.getSlot().compareTo(y.getSlot()));
                System.out.println("List of weapons, sorted by Slot");
                printWeapons(weapons);
                break;
            case 2:
                weapons.sort((x, y) -> x.getType().compareTo(y.getType()));
                System.out.println("List of weapons, sorted by Type");
                printWeapons(weapons);
                break;
            case 3:
                weapons.sort((x, y) -> x.getName().compareTo(y.getName()));
                System.out.println("List of weapons, sorted by Name");
                printWeapons(weapons);
                break;
            case 4:
                weapons.sort((x, y) -> Double.compare(x.getTotDmg(), y.getTotDmg()));
                System.out.println("List of weapons, sorted by Total Damage");
                printWeapons(weapons);
                break;
            case 5:
                weapons.sort((x, y) -> Double.compare(x.getSusDps(), y.getSusDps()));
                System.out.println("List of weapons, sorted by DPS");
                printWeapons(weapons);
                break;
            case 6:
                weapons.sort((x, y) -> Double.compare(x.getStatusChance(), y.getStatusChance()));
                System.out.println("List of weapons, sorted by Status Chance");
                printWeapons(weapons);
                break;
            case 7:
                weapons.sort((x, y) -> Double.compare(x.getCritChance(), y.getCritChance()));
                System.out.println("List of weapons, sorted by Critical Chance");
                printWeapons(weapons);
                break;
            case 8:
                weapons.sort((x, y) -> Double.compare(x.getCritMult(), y.getCritMult()));
                System.out.println("List of weapons, sorted by Critical Multiplier");
                printWeapons(weapons);
                break;
            case 9:
                weapons.sort((x, y) -> Double.compare(x.getFireRate(), y.getFireRate()));
                System.out.println("List of weapons, sorted by Fire Rate");
                printWeapons(weapons);
                break;
            case 10:
                weapons.sort((x, y) -> x.getTier().compareTo(y.getTier()));
                printWeapons(weapons);
                break;
            default:
                System.out.println("Error with user choice!");
        }
    }
    public static void weaponSearch(BufferedReader reader, List<Weapon> weapons) {
        System.out.println("What would you like to search by?");
        System.out.println("1) Weapon Slot");
        System.out.println("2) Weapon Type");
        System.out.println("3) Weapon Name");
        System.out.println("4) Weapon Minimum Total Damage");
        System.out.println("5) Weapon Minimum DPS");
        System.out.println("6) Weapon Minimum Status Chance");
        System.out.println("7) Weapon Minimum Critical Chance");
        System.out.println("8) Weapon Minimum Critical Multiplier");
        System.out.println("9) Weapon Minimum Fire Rate");
        System.out.println("10) Weapon Tier");
        int choice = Database.validChoice(1,10,reader);
        boolean valid = false;
        while (!valid) {
            try {
                switch(choice) {
                    case 1:
                        System.out.println("Enter the weapon slot to search for: ");
                        String slot = reader.readLine();
                        Slot slots[] = Slot.values();
                        boolean validSlot = false;
                        for (Slot s : slots) {
                            if (slot.toUpperCase().equals(s.toString().toUpperCase())) {
                                validSlot = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getSlot().equals(s))
                                    .collect(Collectors.toList()));
                                valid = true;
                                break;
                            }
                        }
                        if (!validSlot) {
                            System.out.println("Invalid slot choice! Try again: ");
                        }
                        break;
                    case 2:
                        System.out.println("Enter the weapon type to search for: ");
                        String type = reader.readLine();
                        Type types[] = Type.values();
                        validSlot = false;
                        for (Type t : types) {
                            if (type.toUpperCase().equals(t.toString().toUpperCase())) {
                                validSlot = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getSlot().equals(t))
                                    .collect(Collectors.toList()));
                                valid = true;
                                break;
                            }
                        }
                        if (!validSlot) {
                            System.out.println("Invalid type choice! Try again: ");
                        }
                        break;
                    case 3:
                        System.out.println("Enter the weapon name to search for: ");
                        String name = reader.readLine();
                        Stream weaponStream = weapons.stream()
                            .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()));
                        if (weaponStream.count() == 0) {
                            System.out.println("No items match this search term! Try another search: ");
                        }
                        else {
                            valid = true;
                            printWeapons(weapons.stream()
                                .filter(x -> x.getName().toUpperCase().contains(name.toUpperCase()))
                                .collect(Collectors.toList()));
                        }
                        break;
                    case 4:
                        System.out.println("Enter the minimum Total Damage to filter by: ");
                        try {
                            double totDmg = Double.parseDouble(reader.readLine());
                            weaponStream = weapons.stream()
                                .filter(x -> x.getTotDmg() >= totDmg);
                            if (weaponStream.count() == 0) {
                                System.out.println("No items with that Total Damage or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getTotDmg() >= totDmg)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Total Damage! Try again: ");
                        }
                        break;
                    case 5:
                        System.out.println("Enter the minimum DPS to filter by: ");
                        try {
                            double dps = Double.parseDouble(reader.readLine());
                            weaponStream = weapons.stream()
                                .filter(x -> x.getSusDps() >= dps);
                            if (weaponStream.count() == 0) {
                                System.out.println("No items with that DPS or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getSusDps() >= dps)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid DPS! Try again: ");
                        }
                        break;
                    case 6:
                        System.out.println("Enter the minimum Status Chance to filter by (Ex: 12 for 12%): ");
                        try {
                            double statusChance = (Double.parseDouble(reader.readLine())) * 0.01;
                            weaponStream = weapons.stream()
                                .filter(x -> x.getStatusChance() >= statusChance);
                            if (weaponStream.count() == 0) {
                                System.out.println("No items with that Status Chance or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getStatusChance() >= statusChance)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Status Chance! Try again: ");
                        }
                        break;
                    case 7:
                        System.out.println("Enter the minimum Critical Chance to filter by (Ex: 12 for 12%): ");
                        try {
                            double critChance = Double.parseDouble(reader.readLine());
                            weaponStream = weapons.stream()
                                .filter(x -> x.getCritChance() >= critChance);
                            if (weaponStream.count() == 0) {
                                System.out.println("No items with that Critical Chance or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getCritChance() >= critChance)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Critical Chance! Try again: ");
                        }
                        break;
                    case 8:
                        System.out.println("Enter the minimum Critical Multiplier to filter by: ");
                        try {
                            double critMult = Double.parseDouble(reader.readLine());
                            weaponStream = weapons.stream()
                                .filter(x -> x.getCritMult() >= critMult);
                            if (weaponStream.count() == 0) {
                                System.out.println("No items with that Critical Multiplier or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getCritMult() >= critMult)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Critical Multiplier! Try again: ");
                        }
                        break;
                    case 9:
                        System.out.println("Enter the minimum Fire Rate to filter by: ");
                        try {
                            double fireRate = Double.parseDouble(reader.readLine());
                            weaponStream = weapons.stream()
                                .filter(x -> x.getFireRate() >= fireRate);
                            if (weaponStream.count() == 0) {
                                System.out.println("No items with that Fire Rate or higher! Try another search: ");
                            }
                            else {
                                valid = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getFireRate() >= fireRate)
                                    .collect(Collectors.toList()));
                            }
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Invalid Fire Rate! Try again: ");
                        }
                        break;
                    case 10:
                        System.out.println("Enter the weapon Tier to search for: ");
                        String tier = reader.readLine();
                        Tier tiers[] = Tier.values();
                        validSlot = false;
                        for (Tier t : tiers) {
                            if (tier.toUpperCase().equals(t.toString().toUpperCase())) {
                                validSlot = true;
                                printWeapons(weapons.stream()
                                    .filter(x -> x.getTier().equals(t))
                                    .collect(Collectors.toList()));
                                valid = true;
                                break;
                            }
                        }
                        if (!validSlot) {
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
    public static void printWeapons(List<Weapon> weapons) {
        System.out.println("Name\t\t\t\tSlot\t\tType\t\t\tBase Damage\tTotal Damage\tCritical Chance\tCritical Multiplier\tStatus Chance\tFire Rate\tSustained DPS\tTier");
        weapons.forEach(System.out::println);
        System.out.println("Name\t\t\t\tSlot\t\tType\t\t\tBase Damage\tTotal Damage\tCritical Chance\tCritical Multiplier\tStatus Chance\tFire Rate\tSustained DPS\tTier");
    }
    
    public String getName() { return this.name; }
    public Slot getSlot() { return this.slot; }
    public Type getType() { return this.type; }
    public double getBaseDmg() { return this.baseDmg; }
    public double getTotDmg() { return this.totDmg; }
    public double getCritChance() { return this.critChance; }
    public double getCritMult() { return this.critMult; }
    public double getSusDps() { return this.susDps; }
    public double getStatusChance() { return this.statusChance; }
    public double getFireRate() { return this.fireRate; }
    public Tier getTier() { return this.tier; }
}