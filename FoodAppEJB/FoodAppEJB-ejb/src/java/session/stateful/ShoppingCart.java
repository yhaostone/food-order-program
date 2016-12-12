/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateful;

import java.util.ArrayList;
import javax.ejb.Stateful;
import pojo.FoodDTO;

/**
 *
 * @author Yan
 */
@Stateful
public class ShoppingCart implements ShoppingCartRemote {

    private ArrayList<FoodDTO> selection;
    
    public ShoppingCart() {
        selection = new ArrayList<FoodDTO>();
    }

    @Override
    public boolean add(FoodDTO cartItem) {
        String id = cartItem.getId();
        int existingQuantity = 0;
        boolean contain = false;
        for(FoodDTO p:selection){
            if(id.equals(p.getId())){
                contain=true;
                existingQuantity=p.getQty();
                removeItem(p);
                break;
            }
        }
        if(contain==true){
            int newQty = existingQuantity + cartItem.getQty();
            cartItem.setQty(newQty);
            return selection.add(cartItem);   
        }else{
            return selection.add(cartItem);   
        }
    }

    @Override
    public void delete() {
        selection.clear();
    }

    @Override
    public boolean removeItem(FoodDTO food) {
        return selection.remove(food);
    }

    @Override
    public boolean editItem(FoodDTO food) {
        int newQty = food.getQty();
        for(FoodDTO f: selection){
            if(f.getId().equals(food.getId())){
                FoodDTO editItem = new FoodDTO();
                editItem.setId(food.getId());
                editItem.setName(food.getName());
                editItem.setQty(newQty);
                editItem.setPrice(food.getPrice());
                editItem.setLocation(food.getLocation());
                editItem.setRestaurant(food.getRestaurant());
                selection.remove(f);
                selection.add(editItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<FoodDTO> getAllCartItems() {
        return selection;
    }
}
