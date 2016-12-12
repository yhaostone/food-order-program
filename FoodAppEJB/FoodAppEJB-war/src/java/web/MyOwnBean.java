/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.Serializable;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import pojo.FoodDTO;
import session.stateful.ShoppingCartRemote;
import session.stateless.FoodFacadeRemote;

/**
 *
 * @author Yan
 */
@Named(value = "myOwnBean")
@SessionScoped
public class MyOwnBean implements Serializable {
    @EJB
    private ShoppingCartRemote remote;
    @EJB
    private FoodFacadeRemote foodFacade;
    
    private String inputLocation;
    private ArrayList<FoodDTO> foodByLocation;

    public MyOwnBean() {
        
    } 
    
    public String getInputLocation() {
        return inputLocation;
    }

    public void setInputLocation(String inputLocation) {
        this.inputLocation = inputLocation;
    }
    
    public ArrayList<FoodDTO> foodByLocation(){
        System.out.println("foodByLocation!!!!!!!!!!!!!!!!!!!!");
        return foodFacade.getFoodsByLocation(inputLocation);
    }
    
    
    
    
}
