package com.example.bookmyshow_first.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.List;

@Data
@Entity(name = "theatres")
public class Theatre extends BaseModel{
    private String name;
    @OneToMany
    private List<Screen> screens;
}
