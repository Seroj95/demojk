package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;
    @Column
    private  String name;
    @Column
    private String description;
   @Column
    private double price;
   @ManyToOne
    private Category category;
   @ManyToOne
   private Brand brand;
   @Column(name = "pic_url")
   private  String picUrl;
   @Column
    private String timestamp;

}
