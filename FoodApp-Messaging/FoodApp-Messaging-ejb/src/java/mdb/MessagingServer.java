/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author Yan
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/queue9"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessagingServer implements MessageListener {
    
    @Resource
    private MessageDrivenContext msgDrvnCtx;
    
    public MessagingServer() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
                MapMessage mm = (MapMessage) message;
                String username = mm.getString("username");
                String tel = mm.getString("tel");
                String address = mm.getString("address");
                String notes = mm.getString("notes");
                String orderfoods = mm.getString("orderfoods");
                double totalCost = mm.getDouble("totalCost");
                String orderTimeString = mm.getString("orderTimeString");
                if (orderfoods == null) {
                    System.err.println("Error: Invalid food information - MapMessage needs a \"orderfoods\" entry");
                    msgDrvnCtx.setRollbackOnly();
                } else {
                    System.out.println("=================New Order=================="); 
                    System.out.println("Foods: "+orderfoods); 
                    System.out.println("Notes: "+notes); 
                    System.out.println("User Name: "+username); 
                    System.out.println("Tel: "+tel); 
                    System.out.println("Address: "+address); 
                    System.out.println("Total Cost (AUD$): "+totalCost); 
                    System.out.println("Order Time: "+orderTimeString); 
                    System.out.println("============================================"); 
                }
            } catch (JMSException e) {
                System.err.println("Error : Invalid message format - need a MapMessage");
                msgDrvnCtx.setRollbackOnly();
            }
    }
}
