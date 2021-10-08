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
public class RoomInvitation implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    Room room;
    Paticipant acceptor;

    public RoomInvitation() {
    }

    public RoomInvitation(Room room, Paticipant acceptor) {
        this.room = room;
        this.acceptor = acceptor;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Paticipant getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(Paticipant acceptor) {
        this.acceptor = acceptor;
    }
}
