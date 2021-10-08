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
    private String chessName;
    private String chessColor;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public Movement() {
    }

    public Movement(String chessName, String chessColor, int startX, int startY, int endX, int endY) {
        this.chessName = chessName;
        this.chessColor = chessColor;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public String getChessName() {
        return chessName;
    }

    public void setChessName(String chessName) {
        this.chessName = chessName;
    }

    public String getChessColor() {
        return chessColor;
    }

    public void setChessColor(String chessColor) {
        this.chessColor = chessColor;
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
    

}
