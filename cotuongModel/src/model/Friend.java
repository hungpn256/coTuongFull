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
@Table(name = "tblFriend")
public class Friend implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "paticipantID")
    private Paticipant paticipant;
    
    @ManyToOne
    @JoinColumn(name = "friendID")
    private Paticipant friend;

    public Friend() {
    }

    public Friend(long id, Paticipant paticipant, Paticipant friend) {
        this.id = id;
        this.paticipant = paticipant;
        this.friend = friend;
    }

    public Friend(Paticipant paticipant, Paticipant friend) {
        this.paticipant = paticipant;
        this.friend = friend;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Paticipant getPaticipant() {
        return paticipant;
    }

    public void setPaticipant(Paticipant paticipant) {
        this.paticipant = paticipant;
    }

    public Paticipant getFriend() {
        return friend;
    }

    public void setFriend(Paticipant friend) {
        this.friend = friend;
    }
}
