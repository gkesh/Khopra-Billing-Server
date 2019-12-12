/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gkesh
 */
public class Order {
    private List<Item> items;
    private final Timestamp time;
    private String user, customer;
    
    public Order(List<Item> items, Timestamp time, String user) {
        this.items = new ArrayList<>();
        this.items = items;
        this.time = time;
        this.user = user;
    }

    public Order() {
        time = new Timestamp(System.currentTimeMillis());
        items = new ArrayList<>();
    }
    
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public void additem(Item item){
        this.items.add(item);
    }

    public Timestamp getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
