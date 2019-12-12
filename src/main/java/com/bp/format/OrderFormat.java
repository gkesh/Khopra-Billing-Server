/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bp.format;

import com.bp.models.Item;
import com.bp.models.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author gkesh
 */
public class OrderFormat {
    
     public static Order mapOrder(JSONObject object) throws JSONException {
        Order order = new Order();
        order.setUser(object.getString("user"));
        order.setCustomer(object.getString("customer"));
        if (object.getString("user").equals("")) {
            order.setUser("Guest");
        } else {
            order.setUser(object.getString("user"));
        }
        
        JSONArray items = object.getJSONArray("items");
        for(Object item: items) {
            JSONObject temp = (JSONObject) item;
            order.additem(new Item(Integer.parseInt(temp.getString("id")),
                    temp.getString("name"),
                    Double.parseDouble(temp.getString("price")),
                    Integer.parseInt(temp.getString("quantity"))));
        }
        return order;
    }
    
}
