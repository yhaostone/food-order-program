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
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import pojo.FoodDTO;
import session.stateful.ShoppingCartRemote;
import session.stateless.FoodFacadeRemote;
import session.stateless.UserOrderFacadeRemote;

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
    @EJB
    private UserOrderFacadeRemote orderFacade;
    
    private String inputLocation;
    private ArrayList<FoodDTO> foodByLocation;
    private String addId;
    private int addQty;
    private int editQty;
    private String orderResponse;
    private ArrayList<FoodDTO> shoppingList;
    private String card;
    private String username;
    private String address;
    private String tel;
    private String notes;

    public MyOwnBean() {
        
    } 

    public int getEditQty() {
        return editQty;
    }

    public void setEditQty(int editQty) {
        this.editQty = editQty;
    }
    
    public String getInputLocation() {
        return inputLocation;
    }

    public void setInputLocation(String inputLocation) {
        this.inputLocation = inputLocation;
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public int getAddQty() {
        return addQty;
    }

    public void setAddQty(int addQty) {
        this.addQty = addQty;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public ArrayList<FoodDTO> getFoodByLocation(){
        foodByLocation=foodFacade.getFoodsByLocation(inputLocation);
        return foodByLocation;
    }
    
    public void addFood(){
        FoodDTO dto=foodFacade.getFoodById(addId);
        dto.setQty(addQty);
        remote.add(dto);
    }
    
    public ArrayList<FoodDTO> getShoppingList(){
        shoppingList=remote.getAllCartItems();
        return shoppingList;
    }
    
    public void setEditQty(ValueChangeEvent evt){
        int newValue = (int) evt.getNewValue();
        this.editQty=newValue;
    }
    
    public void removeItem(FoodDTO food){
        remote.removeItem(food);
    }
    
    public void editQty(FoodDTO item){
        if(editQty!=0){
            item.setQty(editQty);
        }
        remote.editItem(item);
    }
    
    public double getTotalCost(){
        double cost=0.0;
        ArrayList<FoodDTO> cartItems = remote.getAllCartItems();
        for(FoodDTO item:cartItems){
            cost+=item.getQty()*item.getPrice();
        }
        return cost;
    }
    
    public String getOrderResponse(){
        orderResponse = orderFacade.makeOrder(card,username,tel,address,notes, shoppingList);
        return orderResponse;
    }
}
