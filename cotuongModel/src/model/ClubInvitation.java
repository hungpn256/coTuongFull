/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "tblClubInvitation")
public class ClubInvitation implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "paticipantID")
    private Paticipant paticipant;
    
    @ManyToOne
    @JoinColumn(name = "clubID")
    private Club club;
    
    @Column(name="status")
    private String status;

    public ClubInvitation() {
    }

    public ClubInvitation(long id, Paticipant paticipant, Club club, String status) {
        this.id = id;
        this.paticipant = paticipant;
        this.club = club;
        this.status = status;
    }

    public ClubInvitation(Paticipant paticipant, Club club, String status) {
        this.paticipant = paticipant;
        this.club = club;
        this.status = status;
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

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
