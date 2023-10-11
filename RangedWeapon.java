public class RangedWeapon extends Weapon {
    private String triggers[];
    private Ammo ammo;
    private int magazine, maxAmmo;
    private double burstDps, reloadSpeed;
    
    public RangedWeapon(String n, Slot s, Type t, double bDmg, double tD, double cC, double cM, double bDps, double sD, double sC, String ts[], Ammo a, int m, int mA, double fR, double rS, Tier tier) {
        super(n, s, t, bDmg, tD, cC, cM, sD, sC, fR, tier);
        this.triggers = ts;
        this.ammo = a;
        this.magazine = m;
        this.maxAmmo = mA;
        this.reloadSpeed = rS;
        this.burstDps = bDps;
    }
    public RangedWeapon(String n) {
        super(n);
    }
}