/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author phamhung
 */
@Entity
@Table(name = "tblFriendInvitation")
public class FriendInvitation implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "senderID")
    private Paticipant sender;
    
    @ManyToOne
    @JoinColumn(name = "accepterID")
    private Paticipant accepter;
    
    @Column(name="status")
    private String status;

    public FriendInvitation() {
    }

    public FriendInvitation(long id, Paticipant sender, Paticipant accepter, String status) {
        this.id = id;
        this.sender = sender;
        this.accepter = accepter;
        this.status = status;
    }

    public FriendInvitation(Paticipant sender, Paticipant accepter, String status) {
        this.sender = sender;
        this.accepter = accepter;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Paticipant getSender() {
        return sender;
    }

    public void setSender(Paticipant sender) {
        this.sender = sender;
    }

    public Paticipant getAccepter() {
        return accepter;
    }

    public void setAccepter(Paticipant accepter) {
        this.accepter = accepter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
