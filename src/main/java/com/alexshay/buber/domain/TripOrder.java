package com.alexshay.buber.domain;

import com.alexshay.buber.dao.Identified;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TripOrder implements Identified<Integer> {
    private int id;
    private String from;
    private String to;
    private OrderStatus statusOrder;
    private float price;
    private Date dateCreate;
    private int clientId;
    private int driverId;
    private int bonusId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
