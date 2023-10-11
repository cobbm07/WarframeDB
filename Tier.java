import java.util.Comparator;

public enum Tier{
    S(1),
    A(2),
    B(3),
    C(4),
    D(5),
    NA(6);

    private int rank;

    Tier(int r) {
        this.rank = r;
    }

    public int getRank() {
        return this.rank;
    }
    public static Comparator<Tier> rankComparator = new Comparator<Tier>() {
        public int compare(Tier t1, Tier t2) {
            return Integer.compare(t1.getRank(), t2.getRank());
        }
    };
}