package org.example;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws SQLException {
        Rabbit [] rabbits = {
                new Rabbit("Тиша", 1, "300", Rabbit.Color.BROWN, new Date()),
                new Rabbit("Емеля", 2, "400", Rabbit.Color.WHITE, new Date()),
                new Rabbit("Стеша", 1, "200", Rabbit.Color.GRAY, new Date()),
        };
        Requests.createTable(rabbits[0]);
        for (Rabbit rabbit: rabbits) {
            Requests.insertIntoTable(rabbit);
        }

    }
}
