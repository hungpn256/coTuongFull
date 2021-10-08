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

    public List<PaticipantStat> getRank() {
        List<PaticipantStat> res = new ArrayList<PaticipantStat>();
        String sql
                = "SELECT a.ID,a.`nickName`, b.soTranThang,b.rank_p,c.soTranDaChoi FROM"
                + " (SELECT paticipantID, COUNT(*) as soTranThang ,RANK() OVER(ORDER BY COUNT(*) desc) rank_p"
                + " FROM tblPaticipantMatch"
                + " WHERE result = 'win'"
                + " GROUP BY paticipantID"
                + " Order by count(*) desc) b"
                + " left join tblPaticipant a "
                + " on b.paticipantID = a.ID"
                + " left join  (SELECT paticipantID, COUNT(*) soTranDaChoi"
                + " FROM tblPaticipantMatch"
                + " GROUP BY paticipantID) c"
                + " on b.paticipantID = c.paticipantID";
        try {
            javax.persistence.Query query = session.createNativeQuery(sql);
            List<Object[]> rs = query.getResultList();

            for (Object[] lo : rs) {
                PaticipantStat p = new PaticipantStat();
                p.setId(Integer.parseInt(lo[0].toString()));
                p.setNickName(lo[1].toString());
                p.setWonGame(Integer.parseInt(lo[2].toString()));
                p.setRank(Integer.parseInt(lo[3].toString()));
                p.setPlayedGame(Integer.parseInt(lo[4].toString()));
                res.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
