package io.mahesh.quarkus.statsms.model;

public class Civilian {

    public String name;
    public int level;
    public String image;

    public static final Civilian FALLBACK;

    static {
        FALLBACK = new Civilian();
        FALLBACK.name = "Donatello (fallback)";
        FALLBACK.image = "https://www.superherodb.com/pictures2/portraits/10/050/10330.jpg";
        FALLBACK.level = 1;
    }


}
