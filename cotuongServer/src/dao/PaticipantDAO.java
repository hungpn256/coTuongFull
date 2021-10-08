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
        ArrayList<Paticipant> result = (ArrayList<Paticipant>) session.createQuery("from Paticipant where name like '%" + key + "%'").list();
        return result;
    }
    
    public void update(Paticipant p) {
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(p);
        trans.commit();
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
    
//    public PaticipantStat getMyRank(){
//        PaticipantStat ps = new PaticipantStat();
//        String sql = 
//                "SELECT b.rank_p"
//                + "   FROM ("
//                + "     SELECT paticipantID, COUNT(*) soTranThang ,RANK() OVER(ORDER BY soTranThang) rank_p "
//                + "     FROM tblPaticipantMatch a "
//                + "     WHERE a.result = 'win' "
//                + "     GROUP BY paticipantID) b"
//                + "WHERE b.paticipantID = " + Navigator.getPaticipantLogin().getId();
//        Query query = session.createNativeQuery(sql);
//        List<Object[]> res = query.getResultList();
//        ps.setRankWonGame(Integer.parseInt(res.get(0)[0].toString()));
//        
//        sql = 
//               "SELECT (b.soTranThang/c.soTranDaChoi) rate,RANK() OVER(ORDER BY rate) rank_p "
//                + "   FROM ("
//                + "     SELECT paticipantID, COUNT(*) soTranThang"
//                + "     FROM tblPaticipantMatch a "
//                + "     WHERE a.result = 'win' "
//                + "     GROUP BY a.paticipantID) b , INNER JOIN ("
//                + "     SELECT paticipantID, COUNT(*) soTranDaChoi"
//                + "     FROM tblPaticipantMatch"
//                + "     GROUP BY paticipantID) c ON c.paticipantID = b.paticipantID)"
//                + "WHERE b.paticipantID = " + Navigator.getPaticipantLogin().getId();
//        query = session.createNativeQuery(sql);
//        res = query.getResultList();
//        ps.setRankWonRate(Integer.parseInt(res.get(0)[0].toString()));
//        return ps;
//    }
    
}
