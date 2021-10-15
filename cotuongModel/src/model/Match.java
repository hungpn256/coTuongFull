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
import javax.persistence.Transient;
import javax.swing.Timer;

/**
 *
 * @author phamhung
 */
@Entity
@Table(name = "tblMatch")
public class Match implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;
    
    @Column(name="createdAt")
    private Timestamp createAt;
    
    @Column(name="endAt")
    private Timestamp endAt;
    
    @ManyToOne
    @JoinColumn(name = "gameTypeID")
    private GameType gameType;
    
    @OneToMany(mappedBy = "match",cascade = CascadeType.PERSIST)
    private List<PaticipantMatch> listPaticipantMatch = new ArrayList<>();
    
    @Transient
    private Board board;

    public Match() {
    }

    public Match(long id, Room room, Timestamp createAt, Timestamp endAt, GameType gameType, Board board) {
        this.id = id;
        this.room = room;
        this.createAt = createAt;
        this.endAt = endAt;
        this.gameType = gameType;
        this.board = board;
    }

    public Match(Room room, Timestamp createAt, Timestamp endAt, GameType gameType, Board board) {
        this.room = room;
        this.createAt = createAt;
        this.endAt = endAt;
        this.gameType = gameType;
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public List<PaticipantMatch> getListPaticipantMatch() {
        return listPaticipantMatch;
    }

    public void setListPaticipantMatch(List<PaticipantMatch> listPaticipantMatch) {
        this.listPaticipantMatch = listPaticipantMatch;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
    
    
}
