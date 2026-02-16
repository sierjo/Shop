package com.diplom_proj.shop.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_type_id")
    private Integer userTypeId;
    @Column(name = "user_type_name")
    private String userTypeName;
    @OneToMany(targetEntity = Users.class, mappedBy = "roleId", cascade = CascadeType.ALL)
    private List<Users> users = new ArrayList<>();

    public Roles() {
    }

    public Roles(Integer user_type_id, String user_type_name, List<Users> users) {
        this.userTypeId = user_type_id;
        this.userTypeName = user_type_name;
        this.users = users;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer user_type_id) {
        this.userTypeId = user_type_id;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String user_type_name) {
        this.userTypeName = user_type_name;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "userTypeId=" + userTypeId +
                ", userTypeName='" + userTypeName + '\'' +
//                ", users=" + users +
                '}';
    }
}
