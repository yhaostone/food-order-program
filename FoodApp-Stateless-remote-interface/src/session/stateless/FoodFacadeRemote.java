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
public interface FoodFacadeRemote {

    
    ArrayList<FoodDTO> getFoodsByLocation(String location);
    
    FoodDTO getFoodById(String id);
       
}
