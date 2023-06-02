package ru.job4j.cinema.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class File {
    private int id;
    private String name;
    private String path;

    public File(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
