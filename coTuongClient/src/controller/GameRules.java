/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Piece;

/**
 *
 * @author phamhung
 */
public class GameRules {

    Piece[][] chessPieces;
    boolean canMove = false;

    public GameRules(Piece[][] pieces) {
        this.chessPieces = pieces;
    }
    
    public boolean canMove(int startX, int startY, int endX, int endY, String name) {
        int bigX;
        int smallX;
        int bigY;
        int smallY;
        canMove = true;

        if (startX >= endX) {
            bigX = startX;
            smallX = endX;
        } else {
            bigX = endX;
            smallX = startX;
        }
        if (startY >= endY) {
            bigY = startY;
            smallY = endY;
        } else {
            bigY = endY;
            smallY = startY;
        }

        if (name.equals("車")) {
            this.rook(bigX, smallX, bigY, smallY);
        } else if (name.equals("馬")) {
            this.knight(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        } else if (name.equals("相")) {
            this.bishop1(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        } else if (name.equals("象")) {
            this.bishop2(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        } else if (name.equals("士") || name.equals("仕")) {
            this.guard(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        } else if (name.equals("帥") || name.equals("將")) {
            this.king(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        } else if (name.equals("砲") || name.equals("炮")) {
            this.cannon(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        } else if (name.equals("兵")) {
            this.pawn1(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        } else if (name.equals("卒")) {
            this.pawn2(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
        }
        return canMove;
    }

    public void rook(int bigX, int smallX, int bigY, int smallY) {
        if (bigX == smallX) {
            for (int i = smallY + 1; i < bigY; ++i) {
                if (chessPieces[bigX][i] != null) {
                    canMove = false;
                    break;
                }
            }
        } else if (bigY == smallY) {
            for (int i = smallX + 1; i < bigX; ++i) {
                if (chessPieces[i][bigY] != null) {
                    canMove = false;
                    break;
                }
            }
        } else {
            canMove = false;
        }
    }

    public void knight(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {
        int a = bigX - smallX;
        int b = bigY - smallY;

        if ((a == 1) && (b == 2)) {
            if (startY > endY) {
                if (chessPieces[startX][startY - 1] != null) {
                    canMove = false;
                }
            } else {
                if (chessPieces[startX][startY + 1] != null) {
                    canMove = false;
                }
            }
        } else if ((a == 2) && (b == 1)) {
            if (startX > endX) {
                if (chessPieces[startX - 1][startY] != null) {
                    canMove = false;
                }
            } else {
                if (chessPieces[startX + 1][startY] != null) {
                    canMove = false;
                }
            }
        } else {
            canMove = false;
        }
    }

    public void bishop1(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {

        int a = bigX - smallX;
        int b = bigY - smallY;

        if ((a == 2) && (b == 2)) {
            if (endY > 4) {
                canMove = false;
            }
            if (chessPieces[(bigX + smallX) / 2][(bigY + smallY) / 2] != null) {// game rule
                canMove = false;
            }
        } else {
            canMove = false;
        }
    }

    public void bishop2(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {

        int a = bigX - smallX;
        int b = bigY - smallY;

        if ((a == 2) && (b == 2)) {
            if (endY < 5) {
                canMove = false;
            }
            if (chessPieces[(bigX + smallX) / 2][(bigY + smallY) / 2] != null) {// game rule
                canMove = false;
            }
        } else {
            canMove = false;
        }
    }

    public void guard(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {

        int a = bigX - smallX;
        int b = bigY - smallY;

        if ((a == 1) && (b == 1)) {
            if (startY > 4) {
                if (endY < 7) {
                    canMove = false;
                }
            } else {
                if (endY > 2) {
                    canMove = false;
                }
            }
            if (endX > 5 || endX < 3) {
                canMove = false;
            }
        } else {
            canMove = false;
        }
    }

    public void king(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {

        int a = bigX - smallX;
        int b = bigY - smallY;

        if (((a == 1) && (b == 0)) || ((a == 0) && (b == 1))) {
            if (startY > 4) {
                if (endY < 7) {
                    canMove = false;
                }
            } else {
                if (endY > 2) {
                    canMove = false;
                }
            }
            if (endX > 5 || endX < 3) {
                canMove = false;
            }
        } else {
            canMove = false;
        }
    }

    public void cannon(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {
        if (bigX == smallX) {
            if (chessPieces[endX][endY] != null) {
                int count = 0;
                for (int i = smallY + 1; i < bigY; ++i) {
                    if (chessPieces[smallX][i] != null) {
                        ++count;
                    }
                }
                if (count != 1) {
                    canMove = false;
                }
            } else if (chessPieces[endX][endY] == null) {

                for (int i = smallY + 1; i < bigY; ++i) {
                    if (chessPieces[smallX][i] != null) {
                        canMove = false;
                        break;
                    }
                }
            }
        } else if (bigY == smallY) {
            if (chessPieces[endX][endY] != null) {
                int count = 0;

                for (int i = smallX + 1; i < bigX; ++i) {
                    if (chessPieces[i][smallY] != null) {
                        ++count;
                    }
                }
                if (count != 1) {
                    canMove = false;
                }
            } else if (chessPieces[endX][endY] == null) {

                for (int i = smallX + 1; i < bigX; ++i) {
                    if (chessPieces[i][smallY] != null) {
                        canMove = false;
                        break;
                    }
                }
            }
        } else {
            canMove = false;
        }
    }

    public void pawn1(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {
        if (startY < 5) {
            if (startX != endX) {
                canMove = false;
            }
            if (endY - startY != 1) {
                canMove = false;
            }
        } else {
            if (startX == endX) {
                if (endY - startY != 1) {
                    canMove = false;
                }
            } else if (startY == endY) {
                if (bigX - smallX != 1) {
                    canMove = false;
                }
            } else {
                canMove = false;
            }
        }
    }

    public void pawn2(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {
        if (startY > 4) {
            if (startX != endX) {
                canMove = false;
            }
            if (endY - startY != -1) {
                canMove = false;
            }
        } else {
            if (startX == endX) {
                if (endY - startY != -1) {
                    canMove = false;
                }
            } else if (startY == endY) {
                if (bigX - smallX != 1) {
                    canMove = false;
                }
            } else {
                canMove = false;
            }
        }
    }
}
