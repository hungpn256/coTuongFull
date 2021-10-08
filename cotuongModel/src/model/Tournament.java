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

/**
 *
 * @author phamhung
 */
@Entity
@Table(name = "tblTournament")
public class Tournament implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "clubID")
    private Club club;
    
    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "endAt")
    private Timestamp endAt;
    
    @Column(name="totalPlayer")
    private int totalPlayer;
    
    @OneToMany(mappedBy = "tournament",cascade = CascadeType.PERSIST)
    private List<Room> listRoom = new ArrayList<>();
    
    @OneToMany(mappedBy = "tournament",cascade = CascadeType.PERSIST)
    private List<Paticipant> listPaticipant = new ArrayList<>();

    public Tournament() {
    }

    public Tournament(long id, Club club, String name, Timestamp createdAt, Timestamp endAt, int totalPlayer, List<Room> listRoom, List<Paticipant> listPaticipant) {
        this.id = id;
        this.club = club;
        this.name = name;
        this.createdAt = createdAt;
        this.endAt = endAt;
        this.totalPlayer = totalPlayer;
        this.listRoom = listRoom;
        this.listPaticipant = listPaticipant;
    }

    public Tournament(Club club, String name, Timestamp createdAt, Timestamp endAt, int totalPlayer, List<Room> listRoom, List<Paticipant> listPaticipant) {
        this.club = club;
        this.name = name;
        this.createdAt = createdAt;
        this.endAt = endAt;
        this.totalPlayer = totalPlayer;
        this.listRoom = listRoom;
        this.listPaticipant = listPaticipant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public int getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(int totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public List<Room> getListRoom() {
        return listRoom;
    }

    public void setListRoom(List<Room> listRoom) {
        this.listRoom = listRoom;
    }

    public List<Paticipant> getListPaticipant() {
        return listPaticipant;
    }

    public void setListPaticipant(List<Paticipant> listPaticipant) {
        this.listPaticipant = listPaticipant;
    }
}
