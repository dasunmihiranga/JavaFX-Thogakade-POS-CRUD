package edu.icet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Customer {
    private String id;
    private String title;
    private String name;
    private String address;
    private String number;
    private LocalDate dob;
}
