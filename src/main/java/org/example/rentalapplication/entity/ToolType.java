package org.example.rentalapplication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToolType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private double dailyCharge;

    private boolean weekDayCharge;

    private boolean weekendCharge;

    private boolean holidayCharge;

    @OneToMany(mappedBy = "toolType", fetch = FetchType.EAGER)
    private Set<Tool> tools;
}
