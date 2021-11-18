/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author phamhung
 */
@Entity
@Table(name = "tblRoom")
public class Room implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Paticipant createdBy;
    
    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "status")
    private String status;
    
    
    @OneToMany(mappedBy = "room",cascade = CascadeType.PERSIST)
    private List<Match> Match = new ArrayList<>();
    
    @OneToMany(mappedBy = "room",cascade = CascadeType.PERSIST)
    private List<PaticipantRoom> paticipantRoom = new ArrayList<>();
    
    public Room() {
    }

    public Room(long id, Paticipant createdBy, Timestamp createdAt, String status, List<Match> Match, List<PaticipantRoom> paticipantRoom) {
        this.id = id;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.status = status;
        this.Match = Match;
        this.paticipantRoom = paticipantRoom;
    }

    public Room(Paticipant createdBy, Timestamp createdAt, String status, List<Match> Match,List<PaticipantRoom> paticipantRoom) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.status = status;
        this.Match = Match;
        this.paticipantRoom = paticipantRoom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Paticipant getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Paticipant createdBy) {
        this.createdBy = createdBy;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Match> getMatch() {
        return Match;
    }

    public void setMatch(List<Match> Match) {
        this.Match = Match;
    }

    public List<PaticipantRoom> getPaticipantRoom() {
        return paticipantRoom;
    }

    public void setPaticipantRoom(List<PaticipantRoom> paticipantRoom) {
        this.paticipantRoom = paticipantRoom;
    }
}
