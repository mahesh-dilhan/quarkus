package io.mahesh.quarkus.statsms.model;

public class Thug {

    public String name;
    public int level;
    public String image;

    public static final Thug FALLBACK;


    static {
        FALLBACK = new Thug();
        FALLBACK.name = "T-X (fallback)";
        FALLBACK.image = "https://www.superherodb.com/pictures2/portraits/10/050/10412.jpg";
        FALLBACK.level = 42;
    }

}
