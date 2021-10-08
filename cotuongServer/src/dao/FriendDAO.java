/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import javax.swing.table.TableCellRenderer;
import model.Friend;
import model.FriendInvitation;
import model.Paticipant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author phamhung
 */
public class FriendDAO extends DAO {

    public FriendDAO() {
    }
    
    public ArrayList<Paticipant> searchAddFriend(Paticipant paticipantLogin,String key) {
        Query query = session.createQuery("from Paticipant p where p.id != "+ paticipantLogin.getId()
                + " and (p.username like '%" + key+"%'"+" or p.nickName like '%" + key+"%') "
                + " and p.id NOT IN (SELECT f.paticipant.id from Friend f where f.friend.id = "+ paticipantLogin.getId()+" )");
        ArrayList<Paticipant> result = (ArrayList<Paticipant>) query.getResultList();
        return result;
    }
    
    public void requestAddFriend(Paticipant sender,Paticipant accepter) {
        FriendInvitation fi = new FriendInvitation();
        fi.setSender(sender);
        fi.setAccepter(accepter);
        fi.setStatus("pending");
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(fi);
        trans.commit();
        return;
    }
    
    public void acceptFriend(FriendInvitation fi) {
        Friend fr = new Friend();
        fr.setPaticipant(fi.getAccepter());
        fr.setFriend(fi.getSender());
        
        Friend fr2 = new Friend();
        fr2.setPaticipant(fi.getSender());
        fr2.setFriend(fi.getAccepter());
        
        
        fi.setStatus("accepted");
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.save(fr);
        session.save(fr2);
        session.update(fi);
        trans.commit();
        
        return;
    }
    
    public void denyFriend(FriendInvitation fi) {
        fi.setStatus("denied");
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(fi);
        trans.commit();
    }
    
    public ArrayList<FriendInvitation> getAllPendingFriend(Paticipant paticipantLogin) {
        Query query = session.createQuery("from FriendInvitation fi where fi.accepter.id =" 
                + paticipantLogin.getId()
                + " and status = 'pending'");
        ArrayList<FriendInvitation> result = (ArrayList<FriendInvitation>)query.getResultList();
        System.out.println(result.size()+"size friend");
        return result;
    }
    
    public ArrayList<Friend> getAllFriend(Paticipant paticipantLogin) {
        Query query = session.createQuery("from Friend f where f.paticipant.id = " + paticipantLogin.getId());
        ArrayList<Friend> result = (ArrayList<Friend>) query.getResultList();
        System.out.println(result.size()+"size all friend");
        return result;
    }
    
    public void removeFriend(Paticipant paticipantLogin,Paticipant p) {
        Query query = session.createQuery("from Friend f where (f.paticipant.id = " 
                + paticipantLogin.getId() 
                +" and f.friend.id = "+ p.getId()
                +") or ( f.paticipant.id = " +p.getId() + " and f.friend.id = "+ paticipantLogin.getId() +")" );
        ArrayList<Friend> result = (ArrayList<Friend>) query.getResultList();
        
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        for (Friend friend : result) {
            session.clear();
            session.delete(friend);
        }
        trans.commit();
        return;
    }
    
}
