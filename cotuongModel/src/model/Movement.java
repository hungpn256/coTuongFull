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
public class Movement implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private PaticipantMatch sender;
    private PaticipantMatch accepter;

    public Movement() {
    }

    public Movement(int startX, int startY, int endX, int endY, PaticipantMatch sender, PaticipantMatch accepter) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.sender = sender;
        this.accepter = accepter;
    }
    
    

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public PaticipantMatch getSender() {
        return sender;
    }

    public void setSender(PaticipantMatch sender) {
        this.sender = sender;
    }

    public PaticipantMatch getAccepter() {
        return accepter;
    }

    public void setAccepter(PaticipantMatch accepter) {
        this.accepter = accepter;
    }
    

}
