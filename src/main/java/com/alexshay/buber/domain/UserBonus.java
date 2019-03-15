package com.alexshay.buber.domain;

import com.alexshay.buber.dao.Identified;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserBonus implements Identified<Integer> {
    private int id;
    private int bonusId;
    private int userId;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

}
