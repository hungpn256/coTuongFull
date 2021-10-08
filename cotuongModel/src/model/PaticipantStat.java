/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author phamhung
 */
public class PaticipantStat implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private int id;
    private String nickName;
    private int rank;
    private int wonGame;
    private int playedGame;

    public PaticipantStat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getWonGame() {
        return wonGame;
    }

    public void setWonGame(int wonGame) {
        this.wonGame = wonGame;
    }

    public int getPlayedGame() {
        return playedGame;
    }

    public void setPlayedGame(int playedGame) {
        this.playedGame = playedGame;
    }


}
