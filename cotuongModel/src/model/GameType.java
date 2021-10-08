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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author phamhung
 */
@Entity
@Table(name = "tblGameType")
public class GameType implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "score")
    private long score;
    
    @OneToMany(mappedBy = "gameType",cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Match> listMatch = new ArrayList<>();;

    public GameType() {
    }

    public GameType(long id, String name, long score, List<Match> listMatch) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.listMatch = listMatch;
    }

    public GameType(String name, long score, List<Match> listMatch) {
        this.name = name;
        this.score = score;
        this.listMatch = listMatch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public List<Match> getListMatch() {
        return listMatch;
    }

    public void setListMatch(List<Match> listMatch) {
        this.listMatch = listMatch;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
