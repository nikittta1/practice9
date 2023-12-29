package org.example;

import java.time.LocalDate;
import java.util.Date;

@Table(title = "RABBIT")
public class Rabbit {

    public enum Color {
        BROWN, WHITE, GRAY;
    }
    public Rabbit(String name, int age, String maxRunDistance, Color color, Date DateOfBirth) {
        this.name = name;
        this.age = age;
        this.maxRunDistance = maxRunDistance;
        this.color = color;
        this.DateOfBirth = DateOfBirth;
    }

    @Column
    private String name;

    @Column
    private int age;

    @Column
    private String maxRunDistance;

    @Column
    private Color color;
    @Column
    private Date DateOfBirth;


}

