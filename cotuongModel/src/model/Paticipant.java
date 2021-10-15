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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author phamhung
 */
@Entity
@Table(name = "tblPaticipant")
public class Paticipant implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickName")
    private String nickName;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "clubID", nullable = true)
    private Club club;
    
    @ManyToOne
    @JoinColumn(name = "tounamentID", nullable = true)
    private Tournament tournament;
    
    @OneToMany(mappedBy = "paticipant",cascade = CascadeType.PERSIST)
    private List<Friend> listFriend = new ArrayList<>();
    
    @OneToMany(mappedBy = "accepter", cascade = CascadeType.PERSIST)
    private List<FriendInvitation> listPendingFriend = new ArrayList<>();
    
    @OneToMany(mappedBy = "paticipant",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<ClubInvitation> listPendingClub = new ArrayList<>();
    
    @OneToMany(mappedBy = "paticipant",cascade = CascadeType.PERSIST)
    private List<PaticipantRoom> listPaticipantRoom = new ArrayList<>();
    
    @OneToMany(mappedBy = "paticipant",cascade = CascadeType.PERSIST)
    private List<PaticipantMatch> listPaticipantMatch = new ArrayList<>();
    
    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.PERSIST)
    private List<Room> listRoom = new ArrayList<>();
    
    @OneToOne(mappedBy = "createdBy")
    private Club myClub;

    public Paticipant() {
        // TODO Auto-generated constructor stub
    }

    public Paticipant(String username, String password, String nickName) {
        super();
        this.username = username;
        this.password = password;
        this.nickName = nickName;

    }

    public Paticipant(long id, String username, String password, String nickName, String status, Club club, Tournament tournament, Club myClub) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.status = status;
        this.club = club;
        this.tournament = tournament;
        this.myClub = myClub;
    }

    public Paticipant(String username, String password, String nickName, String status, Club club, Tournament tournament, Club myClub) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.status = status;
        this.club = club;
        this.tournament = tournament;
        this.myClub = myClub;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
    public List<Friend> getListFriend() {
        return listFriend;
    }

    public void setListFriend(List<Friend> listFriend) {
        this.listFriend = listFriend;
    }

    public Club getMyClub() {
        return myClub;
    }

    public void setMyClub(Club myClub) {
        this.myClub = myClub;
    }

    public List<FriendInvitation> getListPendingFriend() {
        return listPendingFriend;
    }

    public void setListPendingFriend(List<FriendInvitation> listPendingFriend) {
        this.listPendingFriend = listPendingFriend;
    }

    public List<ClubInvitation> getListPendingClub() {
        return listPendingClub;
    }

    public void setListPendingClub(List<ClubInvitation> listPendingClub) {
        this.listPendingClub = listPendingClub;
    }

    public List<PaticipantRoom> getListPaticipantRoom() {
        return listPaticipantRoom;
    }

    public void setListPaticipantRoom(List<PaticipantRoom> listPaticipantRoom) {
        this.listPaticipantRoom = listPaticipantRoom;
    }

    public List<PaticipantMatch> getListPaticipantMatch() {
        return listPaticipantMatch;
    }

    public void setListPaticipantMatch(List<PaticipantMatch> listPaticipantMatch) {
        this.listPaticipantMatch = listPaticipantMatch;
    }

    public List<Room> getListRoom() {
        return listRoom;
    }

    public void setListRoom(List<Room> listRoom) {
        this.listRoom = listRoom;
    }
}
