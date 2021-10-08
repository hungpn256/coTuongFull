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
public class PaticipantStat extends Paticipant implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private int rankWonRate;
    private int rankWonGame;
    private int rankTournament;

    public PaticipantStat() {
    }

    public PaticipantStat(int rankWonRate, int rankWonGame, int rankTournament) {
        this.rankWonRate = rankWonRate;
        this.rankWonGame = rankWonGame;
        this.rankTournament = rankTournament;
    }

    public int getRankWonRate() {
        return rankWonRate;
    }

    public void setRankWonRate(int rankWonRate) {
        this.rankWonRate = rankWonRate;
    }

    public int getRankWonGame() {
        return rankWonGame;
    }

    public void setRankWonGame(int rankWonGame) {
        this.rankWonGame = rankWonGame;
    }

    public int getRankTournament() {
        return rankTournament;
    }

    public void setRankTournament(int rankTournament) {
        this.rankTournament = rankTournament;
    }

    
}
