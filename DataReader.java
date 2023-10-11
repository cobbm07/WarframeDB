import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataReader {
    public static Set<Weapon> readRangedData() {
        File file = new File("Data/P_S_Info.csv");
        Set<Weapon> ranged = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                String[] info = line.split(",");
                
                String name = info[0];
                Slot slot = Slot.valueOf(info[1]);
                
                if (info[2].contains(" / ")) {
                    info[2] = info[2].replace(" / ", "_");
                }
                info[2] = info[2].replace('-', '_');
                info[2] = info[2].replace(' ', '_');
                Type type = Type.valueOf(info[2]);
                
                double baseDmg = Double.parseDouble(info[3]);
                double totDmg = Double.parseDouble(info[4]);
                double critChance = Double.parseDouble(info[5]);
                double critMult = Double.parseDouble(info[6]);
                double burstDps = Double.parseDouble(info[7]);
                double susDps;
                if (info[8].equals("-nan")) {
                    //No susDps
                    susDps = Double.NEGATIVE_INFINITY;
                }
                else susDps = Double.parseDouble(info[8]);
                double statusChance = Double.parseDouble(info[9]);
                String triggers[] = {info[10]};
                Ammo ammo = Ammo.valueOf(info[11]);
                int magazine = Integer.parseInt(info[12]);
                int maxAmmo;
                if (info[13].equals("inf")) {
                    maxAmmo = Integer.MAX_VALUE;
                }
                else maxAmmo = Integer.parseInt(info[13]);
                double fireRate;
                if (info[14].equals("inf")) {
                    fireRate = Double.POSITIVE_INFINITY;
                }
                else fireRate = Double.parseDouble(info[14]);
                double reload = Double.parseDouble(info[15]);
                Tier tier = Tier.valueOf(info[16]);
                
                RangedWeapon newWeapon = new RangedWeapon(name, slot, type, baseDmg, totDmg, critChance, critMult, burstDps, susDps, statusChance, triggers, ammo, magazine, maxAmmo, fireRate, reload, tier);
                ranged.add(newWeapon);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Error reading from file!");
        }
        return ranged;
    }
    public static Set<Weapon> readMeleeData() {
        File file = new File("Data/M_Info.csv");
        Set<Weapon> melee = new HashSet<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                String[] info = line.split(",");
                
                String name = info[0];
                Slot slot = Slot.valueOf(info[1]);
                
                if (info[2].contains(" / ")) {
                    info[2] = info[2].replace(" / ", "_");
                }
                info[2] = info[2].replace('-', '_');
                info[2] = info[2].replace(' ', '_');
                Type type = Type.valueOf(info[2]);
                
                double baseDmg = Double.parseDouble(info[3]);
                double totDmg = Double.parseDouble(info[4]);
                double critChance = Double.parseDouble(info[5]);
                double critMult = Double.parseDouble(info[6]);
                double statusChance = Double.parseDouble(info[7]);
                double fireRate;
                if (info[8].equals("inf")) {
                    fireRate = Double.POSITIVE_INFINITY;
                }
                else fireRate = Double.parseDouble(info[8]);
                double range = Double.parseDouble(info[9]);
                Tier tier = Tier.valueOf(info[10]);
                
                MeleeWeapon newWeapon = new MeleeWeapon(name, slot, type, baseDmg, totDmg, critChance, critMult, (fireRate * totDmg), statusChance, fireRate, range, tier);
                melee.add(newWeapon);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Error reading from file!");
        }
        return melee;
    }
    public static Set<Warframe> readFrameData() {
        File file = new File("Data/W_Info.csv");
        Set<Warframe> frames = new HashSet<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                String[] info = line.split(",");
                
                String name = info[0];
                int energy = Integer.parseInt(info[1]);
                int health = Integer.parseInt(info[2]);
                int shield = Integer.parseInt(info[3]);
                double sprintSpeed = Double.parseDouble(info[4]);
                int armor = Integer.parseInt(info[5]);
                String abilities[] = {info[6], info[7], info[8], info[9]};
                Tier tier = Tier.valueOf(info[10]);
                
                Warframe newFrame = new Warframe(name, abilities, energy, health, shield, armor, sprintSpeed, tier);
                frames.add(newFrame);
                line = reader.readLine();
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
            System.exit(1);
        }
        catch (IOException e) {
            System.out.println("Error reading from file!");
        }
        
        return frames;
    }
}