package io.mahesh.quarkus.triggerms.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;

import java.util.Random;

@MongoEntity(collection = "heroes")
public class Civilian extends ReactivePanacheMongoEntity {

    public String name;
    public int level;
    public String image;

    public static Uni<Civilian> findRandom() {
        Random random = new Random();
        return Civilian.count()
                .map(l -> random.nextInt(l.intValue()))
                .flatMap(index -> {
                    return Civilian.findAll().page(index, 1).firstResult();
                });
    }
}
