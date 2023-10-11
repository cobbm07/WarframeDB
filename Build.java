import java.io.Serializable;
import java.io.BufferedReader;
import java.util.List;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

public class Build implements Serializable {
    private Warframe frame;
    private Weapon weaponA;
    private Weapon weaponB;
    private Weapon weaponC;
    private String name;
    
    public void createBuild(BufferedReader reader, List<Weapon> weapons, List<Warframe> frames) {
        boolean valid = false;
        while (!valid) {
            try {
                System.out.print("Enter the warframe you would like to use for this build: ");
                String frame = reader.readLine();
                List<Warframe> choices = frames.stream()
                    .filter(x -> x.getName().toUpperCase().contains(frame.toUpperCase()))
                    .collect(Collectors.toList());
                if (choices.size() == 0) {
                    System.out.println("No matching Warframes! Try another search!");
                }
                else {
                    System.out.println("Select the Waframe from the following:");
                    List<String> frameNames = choices.stream()
                        .map(x -> x.getName())
                        .collect(Collectors.toList());
                    for (int i = 0; i < choices.size(); i++) {
                        System.out.println((i+1) + ") " + frameNames.get(i));
                    }
                    int choice = (Database.validChoice(1, choices.size(), reader)) - 1;
                    this.frame = choices.get(choice);
                    valid = true;
                }
            }
            catch (IOException e) {
                System.out.println("Invalid Warframe!");
            }
        }
        int noneCount = 0;
        for (int i = 0; i < 3; i++) {
            valid = false;
            while (!valid) {
                String currWeapon = "";
                switch(i) {
                    case 0: 
                        currWeapon = "Primary";
                        break;
                    case 1: 
                        currWeapon = "Secondary";
                        break;
                    case 2: 
                        currWeapon = "Melee";
                        break;
                }
                try {
                    System.out.printf("Enter the %s weapon you would like to use for this build, or enter NONE for no %s weapon: ", currWeapon, currWeapon);
                    String weapon = reader.readLine();
                    if (weapon.equalsIgnoreCase("NONE") && noneCount != 2) {
                        switch(i) {
                            case 0: 
                                this.weaponA = new Weapon("None");
                                break;
                            case 1: 
                                this.weaponB = new Weapon("None");
                                break;
                            case 2: 
                                this.weaponC = new Weapon("None");
                                break;
                        }
                        noneCount++;
                        break;
                    }
                    final String lambdaW = currWeapon;
                    List<Weapon> choices = weapons.stream()
                        .filter(x -> x.getSlot().equals(Slot.valueOf(lambdaW)))
                        .filter(x -> x.getName().toUpperCase().contains(weapon.toUpperCase()))
                        .collect(Collectors.toList());
                    if (weapon.equalsIgnoreCase("NONE") && noneCount == 2) {
                        System.out.println("You must have at least one weapon!");
                    }
                    else if (choices.size() == 0) {
                        System.out.printf("No matching %s weapons! Try another search!%n", currWeapon);
                    }
                    else {
                        System.out.println("Select the Weapon from the following:");
                        List<String> weaponNames = choices.stream()
                        .map(x -> x.getName())
                        .collect(Collectors.toList());
                        for (int j = 0; j < choices.size(); j++) {
                            System.out.println((j+1) + ") " + weaponNames.get(j));
                        }
                        int choice = (Database.validChoice(1, choices.size(), reader)) - 1;
                        switch(i) {
                            case 0: 
                                this.weaponA = choices.get(choice);
                                break;
                            case 1: 
                                this.weaponB = choices.get(choice);
                                break;
                            case 2: 
                                this.weaponC = choices.get(choice);
                                break;
                        }
                        valid = true;
                    }
                }
                catch (IOException e) {
                    System.out.println("Invalid Weapon!");
                }
            }
        }
        valid = false;
        while (!valid) {
            System.out.println("What would you like to name this build: ");
            try {
                this.name = reader.readLine();
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("Data/Builds/" + this.name + ".build"));
                output.writeObject(this);
                valid = true;
                System.out.println(this.toString());
            }
            catch (IOException e) {
                System.out.println("Invalid Name! Try again!");
            }
        }
    }
    
    @Override
    public String toString() {
        return String.format("---%s---%n%nWarframe: %s%nPrimary Weapon: %s%nSecondary Weapon: %s%nMelee Weapon: %s%n", this.name, this.frame.getName(), this.weaponA.getName(), this.weaponB.getName(), this.weaponC.getName());
    }
}