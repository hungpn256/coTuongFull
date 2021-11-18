/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Club;
import model.ClubInvitation;
import model.FriendInvitation;
import model.Paticipant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author phamhung
 */
public class ClubDAO extends DAO{

    public ClubDAO() {
    }
    
    public void createClub(Club club){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(club);
        trans.commit();
        return;
    } 
    
    public Club getClubById(long id){
        Club club = (Club)session.createQuery("from Club c where c.id = " + id).getSingleResult();
        return club;
    }
    
    public void updatedClub(Club club){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(club);
        trans.commit();
        return;
    }
    
    public void inviteJoinClub(ClubInvitation ci){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(ci);
        trans.commit();
        return;
    }
    
    public ArrayList<ClubInvitation> getAllPendingClubInvitation(Paticipant paticipantLogin){
        Query query = session.createQuery("from ClubInvitation ci where ci.paticipant.id =" 
                + paticipantLogin.getId()
                + " and status = 'pending'");
        ArrayList<ClubInvitation> result = (ArrayList<ClubInvitation>)query.getResultList();
        return result;
    }
    
    public List<Paticipant> getAllPaticipantClub(Club club){
        Query query = session.createQuery("from Paticipant p where p.club.id = " + club.getId());
        List<Paticipant> result = (List<Paticipant>)query.getResultList();
        return result;
    }
    
    public void acceptJoinClub(ClubInvitation ci){
       Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        ci.getPaticipant().setClub(ci.getClub());
        session.update(ci.getPaticipant());
        session.delete(ci);
        trans.commit();
    }
    
    public void denyJoinClub(ClubInvitation ci){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.delete(ci);
        trans.commit();
        return;
    }
    
    public void deleteClub(Club c){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.delete(c);
        trans.commit();
        return;
    }
    
}
