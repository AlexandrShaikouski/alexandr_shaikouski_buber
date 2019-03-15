package com.alexshay.buber.domain;

import com.alexshay.buber.dao.Identified;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bonus implements Identified<Integer> {
    private int id;
    private String name;
    private float factor;

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

}
