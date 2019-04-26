package nl.rug.oop.introduction;

public abstract class Location {

    private final String DEFAULT_DESCRIPTION = "A nondescript location...";

    protected String description = DEFAULT_DESCRIPTION;

    public String getDescription() {
        return description;
    }

}
