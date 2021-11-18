/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
@Table(name = "tblClub")
public class Club implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private Paticipant createdBy;

    @OneToMany(mappedBy = "club",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<Paticipant> listPaticipant = new ArrayList<>();;

    @OneToMany(mappedBy = "club",cascade = CascadeType.PERSIST)
    private List<ClubInvitation> listClubInvitation = new ArrayList<>();
    
    
    public Club() {
        // TODO Auto-generated constructor stub
    }

    public Club(long id, String name, Timestamp createdAt, Paticipant createdBy, List<Paticipant> listPaticipant) {
        super();
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.listPaticipant = listPaticipant;
    }

    public Club(String name, Timestamp createdAt, Paticipant createdBy, List<Paticipant> listPaticipant) {
        super();
        this.name = name;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.listPaticipant = listPaticipant;
    }

    public List<Paticipant> getListPaticipant() {
        return listPaticipant;
    }

    public void setListPaticipant(List<Paticipant> listPaticipant) {
        this.listPaticipant = listPaticipant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Paticipant getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Paticipant createdBy) {
        this.createdBy = createdBy;
    }
}
