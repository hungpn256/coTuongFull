/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import model.Paticipant;
import model.PaticipantRoom;
import model.Room;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author phamhung
 */
public class RoomDAO extends DAO{

    public RoomDAO() {
    }
    
    public Room createRoom(Room r){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(r);
        trans.commit();
        return r;
    }
    
    public List<PaticipantRoom> getPaticipantRoom(long id){
        Query query = session.createQuery("from PaticipantRoom pr where pr.room.id = "+ id);
        List<PaticipantRoom> prs = (List<PaticipantRoom>)query.getResultList();
        return prs;
    }
    
    public Room findRoomById(long id){
        Query query = session.createQuery("from Room r where r.id = "+ id);
        Room room  = (Room)query.getSingleResult();
        return room;
    }
    
    public Room joinRoomById(Paticipant paticipantLogin,long id){
        session.clear();
        Room room  = this.findRoomById(id);
        PaticipantRoom pr = new PaticipantRoom();
        pr.setRoom(room);
        pr.setPaticipant(paticipantLogin);
        this.createParticipantRoom(pr);
        room.getPaticipantRoom().add(pr);
        this.updateRoom(room);
        return room;
    }
    public void leaveRoom(Room room){
        System.out.println(room.getPaticipantRoom().size()+" size paticipant room");
        if(room.getPaticipantRoom().isEmpty()){
            room.setStatus("closed");
        }
        this.updateRoom(room);
    }
    
    public void createParticipantRoom(PaticipantRoom pr){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.save(pr);
        trans.commit();
        return;
    }
    
    public Room findAndJoinPendingRoom(Paticipant paticipantLogin){
        Room room = null;
        Query query = session.createQuery("from Room r where r.status = 'waiting' and size(r.paticipantRoom) < 2");
        query.setFirstResult(0);
        try{
            room = (Room)query.getSingleResult();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        if(room == null){
            room = new Room();
            room.setCreatedBy(paticipantLogin);
            Date date = new Date();
            room.setCreatedAt(new Timestamp(date.getTime()));
            room.setStatus("waiting");
            this.createRoom(room);
        }
        PaticipantRoom pr = new PaticipantRoom();
        pr.setRoom(room);
        pr.setPaticipant(paticipantLogin);
        this.createParticipantRoom(pr);
        room.getPaticipantRoom().add(pr);
        this.updateRoom(room);
        return room;
    }
    
    public void updateRoom(Room room){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(room);
        trans.commit();
        return;
    }
    public void updateParticipantRoom(PaticipantRoom pr){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.update(pr);
        trans.commit();
        return;
    }
    public void deleteParticipantRoom(PaticipantRoom pr){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        session.clear();
        session.delete(pr);
        trans.commit();
        return;
    }
    
    public void deleteRoom(Room room){
        Transaction trans = session.getTransaction();
        if (!trans.isActive()) {
            trans.begin();
        }
        
        session.clear();
        session.delete(room);
        trans.commit();
        return;
    }
}
