package io.mahesh.quarkus.battlems.model;

public class Villain {

    public String name;
    public int level;
    public String image;

    public static final Villain FALLBACK;

    static {
        FALLBACK = new Villain();
        FALLBACK.name = "Mini T-X (fallback)";
        FALLBACK.image = "https://www.superherodb.com/pictures2/portraits/10/050/10412.jpg";
        FALLBACK.level = 1;
    }

}
