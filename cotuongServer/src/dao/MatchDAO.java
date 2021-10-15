/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.GameType;
import model.Match;
import model.Movement;
import model.Paticipant;
import model.PaticipantMatch;
import org.hibernate.Transaction;

/**
 *
 * @author phamhung
 */
public class MatchDAO extends DAO{

    public MatchDAO() {
        
    }
    
    public void createMatch(Match match){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(match);
        trans.commit();
        return;
    }
    
    public GameType getGameTypeById(long id){
        GameType gameType = (GameType)session.createQuery("from GameType where id = "+id).getSingleResult();
        return gameType;
    }
    
    public List<GameType> getAllGameType(){
        List<GameType> listGameType = (List<GameType>)session.createQuery("from GameType").getResultList();
        return listGameType;
    }
    
    public void createPaticipantMatch(PaticipantMatch pm){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(pm);
        trans.commit();
        return;
    }
    
    public void createMovement(Movement m){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(m);
        trans.commit();
        return;
    }
    
    public void updateMovement(Movement m){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.update(m);
        trans.commit();
        return;
    }
    
    public void updatePaticipantMatch(PaticipantMatch pm){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(pm);
        trans.commit();
    }
    
    public void updateMatch(Match match){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(match);
        trans.commit();
        return;
    }
}
