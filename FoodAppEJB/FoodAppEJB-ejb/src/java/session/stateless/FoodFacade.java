/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.Food;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import pojo.FoodDTO;

/**
 *
 * @author Yan
 */
@Stateless
public class FoodFacade extends AbstractFacade<Food> implements FoodFacadeRemote {
    
    @PersistenceContext(unitName = "FoodAppEJB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FoodFacade() {
        super(Food.class);
    }

    @Override
    public ArrayList<FoodDTO> getFoodsByLocation(String location) {    
        ArrayList<FoodDTO> foodList = new ArrayList<FoodDTO>();
        Query query = em.createNamedQuery("Food.findByLocation");
        query.setParameter("location", location); 
        List<Food> list = query.getResultList();
        for(Food f:list){
            FoodDTO dto = new FoodDTO();
            dto.setId(f.getId());
            dto.setName(f.getName());
            dto.setPrice(f.getPrice());
            dto.setQty(f.getQuantity());
            dto.setLocation(f.getLocation());
            dto.setRestaurant(f.getRestaurant());
            foodList.add(dto);
        } 
        return foodList;
    }

    @Override
    public FoodDTO getFoodById(String id) {
        FoodDTO dto = new FoodDTO();
        Food food = new Food();
        try{
            food = em.find(Food.class, id);
            dto.setId(food.getId());
            dto.setName(food.getName());
            dto.setPrice(food.getPrice());
            dto.setQty(food.getQuantity());
            dto.setLocation(food.getLocation());
            dto.setRestaurant(food.getRestaurant());
            return dto;
        }catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        return dto;
    }
    
}
