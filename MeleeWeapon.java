public class MeleeWeapon extends Weapon {
    private double range;
    public MeleeWeapon(String n, Slot s, Type t, double bDmg, double tD, double cC, double cM, double sD, double sC, double fR, double r, Tier tier) {
        super(n, s, t, bDmg, tD, cC, cM, sD, sC, fR, tier);
        this.range = r;
    }
    public MeleeWeapon(String n) {
        super(n);
    }
}