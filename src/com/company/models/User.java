package com.company.models;

public class User {
    private int id;
    private String username;
    private int roleId;

    public User(int id, String username, int roleId) {
        this.id = id;
        this.username = username;
        this.roleId = roleId;
    }

    public int getRoleId() { return roleId; }
    public String getUsername() { return username; }
}