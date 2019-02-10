package com.practice.demo.persistance.modle;

import com.practice.demo.utils.DateFormats;
import com.practice.demo.utils.RichDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @Column
    @DateTimeFormat(pattern = DateFormats.DOB_DATE_FORMAT)
    private LocalDate dob;

    private final int getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    public final int getId() {
        return id;
    }

    public final void setId(int id) {
        this.id = id;
    }

    public final String getName() {
        return name;
    }

    public final void setName(String name) {
        this.name = name;
    }

    public final LocalDate getDob() {
        return dob;
    }

    public final void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
