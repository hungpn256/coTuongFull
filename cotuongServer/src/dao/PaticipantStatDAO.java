/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.PaticipantStat;
import org.hibernate.query.Query;

/**
 *
 * @author phamhung
 */
public class PaticipantStatDAO extends DAO {

    public PaticipantStatDAO() {
        super();
    }
        public List<PaticipantStat> getRank(){
        List<PaticipantStat> res = new ArrayList<PaticipantStat>();
        PaticipantStat ps = new PaticipantStat();
        String sql = 
                "     SELECT paticipantID, COUNT(*) soTranThang ,RANK() OVER(ORDER BY soTranThang) rank_p "
                + "     FROM tblPaticipantMatch a "
                + "     WHERE a.result = 'win' "
                + "     GROUP BY paticipantID) b";
        try {
            javax.persistence.Query query = session.createNativeQuery(sql);
            List<Object[]> rs = query.getResultList();
                         
            for( Object[] lo : rs){
                PaticipantStat p = new PaticipantStat();
                p.setId(Integer.parseInt(lo[0].toString()));
                p.setRankWonGame(Integer.parseInt(lo[1].toString()));
            }           
        }catch(Exception e) {
            e.printStackTrace();
        }
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
        return ps;
    }
}
