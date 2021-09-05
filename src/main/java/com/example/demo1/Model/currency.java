package com.example.demo1.Model;

import javax.persistence.*;

/**
 * @create 05.09.2021 17:12
 */

@Entity(name = "currency")
public class currency {

    @Id
    @SequenceGenerator(
            name = "currency_sequence",
            sequenceName = "currency_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "currency_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "date",
            columnDefinition = "TEXT"
    )
    private String date;

    @Column(
            name = "price_usd",
            columnDefinition = "TEXT"
    )
    private String price_USD;

    @Column(
            name = "price_eur",
            columnDefinition = "TEXT"
    )
    private String price_EUR;

    @Column(
            name = "price_rub",
            columnDefinition = "TEXT"
    )
    private String price_RUB;

    public currency(Long id, String date, String price_USD, String price_EUR, String price_RUB){
        this.id = id;
        this.date = date;
        this.price_USD = price_USD;
        this.price_EUR = price_EUR;
        this.price_RUB = price_RUB;
    }

    public currency() {

    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPrice_USD() {
        return price_USD;
    }

    public String getPrice_EUR() {
        return price_EUR;
    }

    public String getPrice_RUB() {
        return price_RUB;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice_USD(String price_USD) {
        this.price_USD = price_USD;
    }

    public void setPrice_EUR(String price_EUR) {
        this.price_EUR = price_EUR;
    }

    public void setPrice_RUB(String price_RUB) {
        this.price_RUB = price_RUB;
    }

    @Override
    public String toString() {
        return "currency{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", price_USD='" + price_USD + '\'' +
                ", price_EUR='" + price_EUR + '\'' +
                ", price_RUB='" + price_RUB + '\'' +
                '}';
    }
}