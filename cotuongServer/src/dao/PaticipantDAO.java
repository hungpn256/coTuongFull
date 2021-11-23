/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Paticipant;
import model.PaticipantStat;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author phamhung
 */
public class PaticipantDAO extends DAO {
    public PaticipantDAO() {
        super();
    }
    @SuppressWarnings("unchecked")
    public ArrayList<Paticipant> searchPaticipant(String key) {
        ArrayList<Paticipant> result = (ArrayList<Paticipant>) session.createQuery("from Paticipant where nickName like '%" + key + "%'").list();
        return result;
    }
    public void update(Paticipant p) {
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(p);
        session.flush();
        return;
    }

    public void register(Paticipant p) {
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.save(p);
        trans.commit();
        return;
    }

    public Paticipant login(Paticipant p) {
        Paticipant res = null;
        try{
            res = (Paticipant) session.createQuery("from Paticipant where username = '" + p.getUsername() + "' and password = '"+p.getPassword()+"'").getSingleResult();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("TK MK khong dung");
        }
        if(res!=null){
            res.setStatus("online");
            Transaction trans = session.getTransaction();
            if (!trans.isActive()) {
                trans.begin();
            }
            session.clear();
            session.update(res);
            trans.commit();
        }
        return res;
    }
    
    public void logout(Paticipant p) {
        p.setStatus("offline");
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(p);
        trans.commit();
    }
    
}
