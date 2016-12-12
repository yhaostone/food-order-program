/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Yan
 */
@Entity
@Table(name = "USER_ORDER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserOrder.findAll", query = "SELECT u FROM UserOrder u"),
    @NamedQuery(name = "UserOrder.findById", query = "SELECT u FROM UserOrder u WHERE u.id = :id"),
    @NamedQuery(name = "UserOrder.findByUserid", query = "SELECT u FROM UserOrder u WHERE u.userid = :userid"),
    @NamedQuery(name = "UserOrder.findByOrderfoods", query = "SELECT u FROM UserOrder u WHERE u.orderfoods = :orderfoods"),
    @NamedQuery(name = "UserOrder.findByTotalcost", query = "SELECT u FROM UserOrder u WHERE u.totalcost = :totalcost"),
    @NamedQuery(name = "UserOrder.findByOrdertime", query = "SELECT u FROM UserOrder u WHERE u.ordertime = :ordertime"),
    @NamedQuery(name = "UserOrder.findByTel", query = "SELECT u FROM UserOrder u WHERE u.tel = :tel"),
    @NamedQuery(name = "UserOrder.findByStatus", query = "SELECT u FROM UserOrder u WHERE u.status = :status"),
    @NamedQuery(name = "UserOrder.findByNotes", query = "SELECT u FROM UserOrder u WHERE u.notes = :notes")})
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private String id;
    @Column(name = "USERID")
    private String userid;
    @Column(name = "ORDERFOODS")
    private String orderfoods;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOTALCOST")
    private Double totalcost;
    @Column(name = "ORDERTIME")
    private String ordertime;
    @Column(name = "TEL")
    private String tel;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "NOTES")
    private String notes;

    public UserOrder() {
    }

    public UserOrder(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrderfoods() {
        return orderfoods;
    }

    public void setOrderfoods(String orderfoods) {
        this.orderfoods = orderfoods;
    }

    public Double getTotalcost() {
        return totalcost;
    }

    public void setTotalcost(Double totalcost) {
        this.totalcost = totalcost;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserOrder)) {
            return false;
        }
        UserOrder other = (UserOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UserOrder[ id=" + id + " ]";
    }
    
}
