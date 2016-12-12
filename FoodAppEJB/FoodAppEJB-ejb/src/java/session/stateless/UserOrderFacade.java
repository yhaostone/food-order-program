/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session.stateless;

import entity.UserOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pojo.FoodDTO;

/**
 *
 * @author Yan
 */
@Stateless
public class UserOrderFacade extends AbstractFacade<UserOrder> implements session.stateless.UserOrderFacadeRemote {

    @Resource(mappedName = "jms/queue9")
    private Queue queue;

    @Resource(mappedName = "jms/queue9Factory")
    private ConnectionFactory queueFactory;
    
    @PersistenceContext(unitName = "FoodAppEJB-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserOrderFacade() {
        super(UserOrder.class);
    }

    @Override
    public String makeOrder(String card, String username, String tel, String address, String notes, ArrayList<FoodDTO> shoppingList){
        // boolean cardIsValid=true;   // at this moment no bank service can be used to check credit, so assume card is valid
        if(card!=null&&card!=""){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            String orderTimeString =formatter.format(new Date());
            String orderfoods="";
            double totalCost = 0.0;
            for(FoodDTO food: shoppingList){
                orderfoods+="Food id:"+food.getId()+";name:"+food.getName()+";qty:"+food.getQty()+";price:"
                        +food.getPrice()+":restaurant:"+food.getRestaurant()+", card: "+card+"...";
                totalCost+=food.getPrice()*food.getQty();
            }
            UserOrder order = new UserOrder();
            order.setId(orderTimeString+username);
            order.setUserid(username);
            order.setOrderfoods(orderfoods);
            order.setOrdertime(orderTimeString);
            order.setTel(tel);
            order.setNotes(notes);
            order.setTotalcost(totalCost);
            order.setStatus("new order");
            em.persist(order);
            System.out.println("Order added to database");
            try {
                sendMessage(username,tel,address,notes,orderfoods,totalCost,orderTimeString);   // send message to the restaurant
            } catch (JMSException ex) {
                Logger.getLogger(FoodFacade.class.getName()).log(Level.SEVERE, null, ex);
                return "Exception got while sending message to restaurant";
            }
            return "Order successfully";
        }else{
            return "Please enter a valid card number for payment";
        }
    }
    
    private void sendMessage(String username, String tel, String address, String notes, String orderfoods, double totalCost, 
            String orderTimeString) throws JMSException{
        Connection connection = null;
        Session session = null;
        try {
            connection = queueFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = session.createProducer(queue);
            
            MapMessage mm = session.createMapMessage();
            mm.setString("username", username);
            mm.setString("tel", tel);
            mm.setString("address", address);
            mm.setString("notes", notes);
            mm.setString("orderfoods", orderfoods);
            mm.setDouble("totalCost", totalCost);
            mm.setString("orderTimeString", orderTimeString);
            messageProducer.send(mm);
            
            System.out.println("An Order Message has been sent! user name="+username+", tel="+tel+", address="+address+", orderfoods="
                    +orderfoods +", totalCost="+totalCost+", order time="+orderTimeString);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
