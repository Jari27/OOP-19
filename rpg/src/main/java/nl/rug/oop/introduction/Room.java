package nl.rug.oop.introduction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room extends Location implements Inspectable{

    String description;

    List<Object> contents = new ArrayList<>();

    public Room(String description, Object... contents) {
        this.description = description;
        this.contents.addAll(Arrays.asList(contents));
    }

    @Override
    public void inspect() {

    }
}
