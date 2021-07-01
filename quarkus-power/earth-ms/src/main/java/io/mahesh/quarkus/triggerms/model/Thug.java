package io.mahesh.quarkus.triggerms.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import io.smallrye.mutiny.Uni;

import java.util.Random;

@MongoEntity(collection = "thugs")
public class Thug extends ReactivePanacheMongoEntity {

    public String name;
    public int level;
    public String image;

    public static Uni<Thug> findRandom() {
        Random random = new Random();
        return Thug.count()
                .onItem().transform(l -> random.nextInt(l.intValue()))
                .onItem().transformToUni(index -> {
                    return Thug.findAll().page(index, 1).firstResult();
                });
    }


}
