package br.com.mochileirobot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "players")
public class Player {

    private String _id;

    @Field(name = "name")
    private String name;

    @Field(name = "items")
    private List<Item> items;

    @Field(name = "stats")
    List<Stat> stats;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Item {

        @Field(name = "name")
        private String name;

        @Field(name = "quantity")
        private Integer quantity;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Stat {

        @Field(name = "name")
        private String attribute;

        @Field(name = "quantity")
        private Integer value;
    }
}
