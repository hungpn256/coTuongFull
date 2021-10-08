/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import javax.swing.*;
import java.util.List;
import model.Piece;

/**
 *
 * @author phamhung
 */
public class Board implements Serializable{
    private static final long serialVersionUID = 20210811004L;
    boolean selected = false;
    int king1x = 4;
    int king1y = 0;
    int king2x = 4;
    int king2y = 9;
    List<Movement> movement;
    public Piece pieces[][];

    // Cấu trúc bàn cờ
    public Board(Piece pieces[][]) {
        this.pieces = pieces;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getKing1x() {
        return king1x;
    }

    public void setKing1x(int king1x) {
        this.king1x = king1x;
    }

    public int getKing1y() {
        return king1y;
    }

    public void setKing1y(int king1y) {
        this.king1y = king1y;
    }

    public int getKing2x() {
        return king2x;
    }

    public void setKing2x(int king2x) {
        this.king2x = king2x;
    }

    public int getKing2y() {
        return king2y;
    }

    public void setKing2y(int king2y) {
        this.king2y = king2y;
    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public List<Movement> getMovement() {
        return movement;
    }

    public void setMovement(List<Movement> movement) {
        this.movement = movement;
    }
    

}
