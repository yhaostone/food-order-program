/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import java.util.ArrayList;
import javax.ejb.Remote;
import pojo.FoodDTO;

/**
 *
 * @author Yan
 */
@Remote
public interface UserOrderFacadeRemote {

    String makeOrder(String card, String username, String tel, String address, String notes, ArrayList<FoodDTO> shoppingList);
    
}
