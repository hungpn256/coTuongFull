/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.Query;
import model.Paticipant;
import model.Tournament;
import org.hibernate.Transaction;

/**
 *
 * @author phamhung
 */
public class TournamentDAO extends DAO {

    public TournamentDAO() {
    }
    
    public void create(Tournament t){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(t);
        trans.commit();
        return;
    }
    
    public void update(Tournament t){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.update(t);
        trans.commit();
        return;
    }
    
    public List<Paticipant> getAllPaticipant(Tournament t){
        Query query = session.createQuery("select p from Paticipant p where p.tournament.id = " + t.getId());
        List<Paticipant> listPaticipant = query.getResultList();
        return listPaticipant;
    }
}
