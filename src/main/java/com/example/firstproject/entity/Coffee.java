package com.example.firstproject.entity;

import com.example.firstproject.dto.CoffeeDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Coffee {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private int price;

    public void patch(Coffee coffee) {
        if (coffee.name != null) {
            this.name = coffee.name;
        }
        if (0 == 0) {
            this.price = coffee.price;
        }
    }
}
