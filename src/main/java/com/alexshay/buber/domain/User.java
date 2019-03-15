package com.alexshay.buber.domain;

import com.alexshay.buber.dao.Identified;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class User implements Identified<Integer> {
    private int id;
    private String login;
    private String password;
    private String repasswordKey;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserStatus status;
    private String location;
    private Date registrationTime;
    private Date statusBan;
    private Role role;
    private List<Bonus> bonuses;


    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public void setId(Integer id) {
        this.id = id;
    }

}
