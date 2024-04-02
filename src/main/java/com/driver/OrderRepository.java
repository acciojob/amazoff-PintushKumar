package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        if(order != null && !orderMap.containsKey(order.getId())){
            orderMap.put(order.getId() , order);
        }
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        if(!partnerMap.containsKey(partnerId)){
            partnerMap.put(partnerId , new DeliveryPartner(partnerId));
        }
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            Order order = orderMap.get(orderId);
            DeliveryPartner deliveryPartner = partnerMap.get(partnerId);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
            if(partnerToOrderMap.containsKey(partnerId)){
                partnerToOrderMap.get(partnerId).add(order.getId());
            }else{
                HashSet<String> hashSet =  new HashSet<>();
                hashSet.add(order.getId());
                partnerToOrderMap.put(partnerId , hashSet);
            }
            if(!orderToPartnerMap.containsKey(order.getId())){
                orderToPartnerMap.put(orderId , partnerId);
            }
        }
    }

    public Order findOrderById(String orderId){
        // your code here
       return orderMap.getOrDefault(orderId, null);
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return  partnerMap.getOrDefault(partnerId , null);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        return partnerToOrderMap.getOrDefault(partnerId , null).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        List<String>orders = new ArrayList<>();
        HashSet<String> hashSet = partnerToOrderMap.getOrDefault(partnerId , null);
        for(String s : hashSet){
            orders.add(s);
        }
        return orders;
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        List<String>orders = new ArrayList<>();
        for(String id : orderMap.keySet()){
            orders.add(orderMap.get(id).toString());
        }
        return orders;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        partnerMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        orderMap.remove(orderId);
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        return orderMap.size() - orderToPartnerMap.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int count =0;
        HashSet<String> hashSet = partnerToOrderMap.getOrDefault(partnerId , new HashSet<>());
        for(String id : hashSet){
            Order order = orderMap.getOrDefault(id , null);
            int time = order.changeDeliveryTimeIntoInteger(timeString);
            if(time < order.getDeliveryTime()){
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        int latestDeliveryTime = Integer.MIN_VALUE;

        HashSet<String> orders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());
        for (String orderId : orders) {
            Order order = orderMap.get(orderId);
            if (order != null && order.getDeliveryTime() > latestDeliveryTime) {
                latestDeliveryTime = order.getDeliveryTime();
            }
        }

        if (latestDeliveryTime != Integer.MIN_VALUE) {
            int hours = latestDeliveryTime / 60;
            int minutes = latestDeliveryTime % 60;
            return ""+hours+":"+minutes;
        } else {
            return null;
        }
    }
}