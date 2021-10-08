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
@Table(name = "tblPaticipantRoom")
public class PaticipantRoom implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "roomID")
    private Room room;
    
    @ManyToOne
    @JoinColumn(name = "paticipantID")
    private Paticipant paticipant;

    public PaticipantRoom() {
    }

    public PaticipantRoom(long id, Room room, Paticipant paticipant) {
        this.id = id;
        this.room = room;
        this.paticipant = paticipant;
    }

    public PaticipantRoom(Room room, Paticipant paticipant) {
        this.room = room;
        this.paticipant = paticipant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Paticipant getPaticipant() {
        return paticipant;
    }

    public void setPaticipant(Paticipant paticipant) {
        this.paticipant = paticipant;
    }
    
    
}
