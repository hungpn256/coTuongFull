/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
@Table(name = "tblPaticipantMatch")
public class PaticipantMatch implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "paticipantID")
    private Paticipant paticipant;
    
    @Column(name="color")
    private String color;
    
    @ManyToOne
    @JoinColumn(name = "matchID")
    private Match match;
    
    @Column(name="result")
    private String result;
   

    public PaticipantMatch() {
    }

    public PaticipantMatch(long id, Paticipant paticipant, String color, Match match, String result) {
        this.id = id;
        this.paticipant = paticipant;
        this.color = color;
        this.match = match;
        this.result = result;
    }

    public PaticipantMatch(Paticipant paticipant, String color, Match match, String result) {
        this.paticipant = paticipant;
        this.color = color;
        this.match = match;
        this.result = result;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
    
}
