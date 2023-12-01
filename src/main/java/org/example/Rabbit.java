package org.example;

@Table(title = "RABBIT")
public class Rabbit {
    public enum Color {
        BROWN, WHITE, GRAY;
    }
    public Rabbit(String name, int age, String maxRunDistance, Color color) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.color = color;
    }
    @Column
    private final String name;

    @Column
    private final int age;

    @Column
    private final String maxRunDistance;

    @Column
    private final Color color;
}

