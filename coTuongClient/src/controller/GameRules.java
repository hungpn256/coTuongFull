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

	// wrapper method for whether a piece can move in general
	public boolean canMove(int startX, int startY, int endX, int endY, String name) {
		int bigX;// the bigger one out of the two Xs
		int smallX;// the smaller one out of the two Xs
		int bigY;// the bigger one out of the two Ys
		int smallY;// the smaller one out of the two Ys
		canMove = true;

		// assigning values
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
		} else if (name.equals("")) {
			this.bishop2(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
		} else if (name.equals("士") || name.equals("仕")) {
			this.guard(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
		} else if (name.equals("帥") || name.equals("將")) {
			this.king(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
		} else if (name.equals("砲") || name.equals("炮")) {
			this.cannon(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
		} else if (name.equals("兵")) {
			this.pawn1(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
		} else if (name.equals("兵")|| name.equals("卒")) {
			this.pawn2(bigX, smallX, bigY, smallY, startX, startY, endX, endY);
		}
		return canMove;
	}

	// individual methods for each piece
	public void rook(int bigX, int smallX, int bigY, int smallY) {

		if (bigX == smallX) {// if in a vertical line
			for (int i = smallY + 1; i < bigY; ++i) {
				if (chessPieces[bigX][i] != null) {
					canMove = false;
					break;
				}
			}
		} else if (bigY == smallY) {// in a horizontal line
			for (int i = smallY + 1; i < bigY; ++i) {
				if (chessPieces[i][bigY] != null) {
					canMove = false;
					break;
				}
			}
		} else {// not in a straight line
			canMove = false;
		}
	}

	public void knight(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {
		int a = bigX - smallX;
		int b = bigY - smallY;

		if ((a == 1) && (b == 2)) {// vertical rectangle
			// game rules
			if (startY > endY) {
				if (chessPieces[startX][startY - 1] != null) {
					canMove = false;
				}
			} else {
				if (chessPieces[startX][startY + 1] != null) {
					canMove = false;
				}
			}
		} else if ((a == 2) && (b == 1)) {// horizontal rectangle
			// game rules
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

		if ((a == 2) && (b == 2)) {// 2*2 square
			if (endY > 4) {// bishop cannot go across the river
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

		if ((a == 2) && (b == 2)) {// 2*2 square
			if (endY < 5) {// bishop cannot go across the river
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

		if ((a == 1) && (b == 1)) {// need to be 1 unit diagonal
			if (startY > 4) {// guard below
				if (endY < 7) {// can't exit the palace vertically
					canMove = false;
				}
			} else {// guard above
				if (endY > 2) {// can't exit the palace vertically
					canMove = false;
				}
			}
			if (endX > 5 || endX < 3) {// can't exit the palace horizontally
				canMove = false;
			}
		} else {
			canMove = false;
		}
	}

	public void king(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {

		int a = bigX - smallX;
		int b = bigY - smallY;

		if (((a == 1) && (b == 0)) || ((a == 0) && (b == 1))) {// 1 non-diagonal unit only
			if (startY > 4) {// king below
				if (endY < 7) {// can't exit the palace vertically
					canMove = false;
				}
			} else {// king above
				if (endY > 2) {// can't exit the palace vertically
					canMove = false;
				}
			}
			if (endX > 5 || endX < 3) {// can't exit the palace horizontally
				canMove = false;
			}
		} else {
			canMove = false;
		}
	}

	public void cannon(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {
		if (bigX == smallX) {// vertical line
			if (chessPieces[endX][endY] != null) {// hitting opponent's pieces
				int count = 0;
				for (int i = smallY + 1; i < bigY; ++i) {
					if (chessPieces[smallX][i] != null) {
						++count;
					}
				}
				if (count != 1) {// must have exactly one piece in between
					canMove = false;
				}
			} else if (chessPieces[endX][endY] == null) {// not hitting opponent's pieces

				for (int i = smallY + 1; i < bigY; ++i) {
					if (chessPieces[smallX][i] != null) {// cannot allow pieces in middle
						canMove = false;
						break;
					}
				}
			}
		} else if (bigY == smallY) {// horizontal line
			if (chessPieces[endX][endY] != null) {// hitting opponent's pieces
				int count = 0;

				for (int i = smallX + 1; i < bigX; ++i) {
					if (chessPieces[i][smallY] != null) {
						++count;
					}
				}
				if (count != 1) {// must have exactly one piece in between
					canMove = false;
				}
			} else if (chessPieces[endX][endY] == null) {// not hitting opponent's pieces

				for (int i = smallX + 1; i < bigX; ++i) {
					if (chessPieces[i][smallY] != null) {// cannot allow any pieces in between
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
		if (startY < 5) {// if not across the river yet
			if (startX != endX) {// cannot move horizontally
				canMove = false;
			}
			if (endY - startY != 1) {
				canMove = false;
			}
		} else {// crossed the river
			if (startX == endX) {// vertical line
				if (endY - startY != 1) {
					canMove = false;
				}
			} else if (startY == endY) {// horizontal line
				if (bigX - smallX != 1) {
					canMove = false;
				}
			} else {
				canMove = false;
			}
		}
	}

	public void pawn2(int bigX, int smallX, int bigY, int smallY, int startX, int startY, int endX, int endY) {
		if (startY > 4) {// if not across the river yet
			if (startX != endX) {// cannot move horizontally
				canMove = false;
			}
			if (endY - startY != -1) {
				canMove = false;
			}
		} else {// crossed the river
			if (startX == endX) {// vertical line
				if (endY - startY != -1) {
					canMove = false;
				}
			} else if (startY == endY) {// horizontal line
				if (bigX - smallX != 1) {
					canMove = false;
				}
			} else {
				canMove = false;
			}
		}
	}
}
