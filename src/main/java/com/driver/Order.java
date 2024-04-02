package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        this.deliveryTime = changeDeliveryTimeIntoInteger(deliveryTime);
    }

    public int changeDeliveryTimeIntoInteger(String deliveryTime){
        String [] parts = deliveryTime.split(":");
        if(parts.length !=2){
            throw  new IllegalCallerException("Invalid format of time "+deliveryTime);
        }

        try{
            int hours = Integer.parseInt(parts[0]);
            int min = Integer.parseInt(parts[1]);
            if(hours < 0 || hours >23 && min <0 || min >59){
                throw  new IllegalArgumentException("Invalid Format of Time "+deliveryTime);
            }
            return hours*60+min;
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid format of time "+ deliveryTime);
        }
    }


    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
