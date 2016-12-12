package session.stateful;

import java.util.ArrayList;
import javax.ejb.Remote;
import pojo.FoodDTO;

/**
 *
 * @author Yan
 */
@Remote
public interface ShoppingCartRemote {
    
    boolean add(FoodDTO food);

    void delete();
    
    boolean removeItem(FoodDTO food);
    
    boolean editItem(FoodDTO food);
    
    ArrayList<FoodDTO> getAllCartItems();
    
}
