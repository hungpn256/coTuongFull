/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author phamhung
 */
public class Piece implements Serializable{
    // thuộc tính quân cờ
    private static final long serialVersionUID = 20210811004L;
    private Color color;
    private String name;
    private int x;
    private int y;
    private boolean selected = false;// click chọn quân cờ

    public Piece() {
    }

    public Piece(Color color, String name, int x, int y) {
        this.color = color;
        this.name = name;
        this.x = x;
        this.y = y;
        this.selected = false;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
