/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author phamhung
 */
import controller.ClientCtr;
import java.awt.event.*;

import controller.GameRules;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Board;
import model.Match;
import model.Movement;
import model.ObjectWrapper;
import model.PaticipantMatch;
import model.PaticipantRoom;
import model.Piece;

public class BoardFrm extends JPanel implements MouseListener {

    private int width;	// khoảng cách giữa các dòng
    boolean selected = false;
    int startX = -1;	// vị trí băt đầu
    int startY = -1;
    int endX = -1;		// vị trí kết thúc
    int endY = -1;
    public Piece[][] pieces = new Piece[9][10];

    GameUIFrm cchess = null;
    GameRules rules;
    public Match match;
    public PaticipantMatch paticipantMatch;
    public PaticipantMatch challenger;
    public Board board;
    ClientCtr mySocket;
    Timer timer;
    int interval = 10;
    boolean autoMove = false;

    // Cấu trúc bàn cờ
    public BoardFrm(ClientCtr socket, GameUIFrm chess) {
        mySocket = socket;
        this.cchess = chess;

        newGame();
        this.width = 100;
        rules = new GameRules(pieces);
        this.addMouseListener(this);
        this.setBounds(-100, 0, 700, 700);
        this.setLayout(null);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_START_GAME, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_MOVE, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_RESULT_MATCH, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_QUIT_GAME, this));
    }

    public void initialPieces() {
        for (int i = 0; i < 9; ++i) {// hết cờ trên bàn cờ
            for (int j = 0; j < 10; ++j) {
                this.pieces[i][j] = null;
            }
        }
        //Các quân cờ bên ĐỎ
        pieces[0][0] = new Piece(GameUIFrm.redColor, "車", 0, 0);
        pieces[1][0] = new Piece(GameUIFrm.redColor, "馬", 1, 0);
        pieces[2][0] = new Piece(GameUIFrm.redColor, "相", 2, 0);
        pieces[3][0] = new Piece(GameUIFrm.redColor, "仕", 3, 0);
        pieces[4][0] = new Piece(GameUIFrm.redColor, "帥", 4, 0);
        pieces[5][0] = new Piece(GameUIFrm.redColor, "仕", 5, 0);
        pieces[6][0] = new Piece(GameUIFrm.redColor, "相", 6, 0);
        pieces[7][0] = new Piece(GameUIFrm.redColor, "馬", 7, 0);
        pieces[8][0] = new Piece(GameUIFrm.redColor, "車", 8, 0);
        pieces[1][2] = new Piece(GameUIFrm.redColor, "炮", 1, 2);
        pieces[7][2] = new Piece(GameUIFrm.redColor, "炮", 7, 2);
        pieces[0][3] = new Piece(GameUIFrm.redColor, "兵", 0, 3);
        pieces[2][3] = new Piece(GameUIFrm.redColor, "兵", 2, 3);
        pieces[4][3] = new Piece(GameUIFrm.redColor, "兵", 4, 3);
        pieces[6][3] = new Piece(GameUIFrm.redColor, "兵", 6, 3);
        pieces[8][3] = new Piece(GameUIFrm.redColor, "兵", 8, 3);

        //Các quân cờ bên TRẮNG
        pieces[0][9] = new Piece(GameUIFrm.whiteColor, "車", 0, 9);
        pieces[1][9] = new Piece(GameUIFrm.whiteColor, "馬", 1, 9);
        pieces[2][9] = new Piece(GameUIFrm.whiteColor, "象", 2, 9);
        pieces[3][9] = new Piece(GameUIFrm.whiteColor, "士", 3, 9);
        pieces[4][9] = new Piece(GameUIFrm.whiteColor, "將", 4, 9);
        pieces[5][9] = new Piece(GameUIFrm.whiteColor, "士", 5, 9);
        pieces[6][9] = new Piece(GameUIFrm.whiteColor, "象", 6, 9);
        pieces[7][9] = new Piece(GameUIFrm.whiteColor, "馬", 7, 9);
        pieces[8][9] = new Piece(GameUIFrm.whiteColor, "車", 8, 9);
        pieces[1][7] = new Piece(GameUIFrm.whiteColor, "砲", 1, 7);
        pieces[7][7] = new Piece(GameUIFrm.whiteColor, "砲", 7, 7);
        pieces[0][6] = new Piece(GameUIFrm.whiteColor, "卒", 0, 6);
        pieces[2][6] = new Piece(GameUIFrm.whiteColor, "卒", 2, 6);
        pieces[4][6] = new Piece(GameUIFrm.whiteColor, "卒", 4, 6);
        pieces[6][6] = new Piece(GameUIFrm.whiteColor, "卒", 6, 6);
        pieces[8][6] = new Piece(GameUIFrm.whiteColor, "卒", 8, 6);
    }

    public void newGame() {
        initialPieces();
        startX = -1;	// vị trí băt đầu
        startY = -1;
        endX = -1;		// vị trí kết thúc
        endY = -1;

        selected = false;
        this.autoMove = false;
        this.cchess.setEnable(true);
        this.match = null;
        paticipantMatch = null;
        challenger = null;

        board = new Board(pieces);
        board.setKing1x(4);
        board.setKing1y(0);
        board.setKing2x(4);
        board.setKing2y(9);
        board.myTurn = false;
    }

    public void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
//		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color c = g.getColor();
        g.setColor(new Color(28, 242, 242));
        g.fill3DRect(70, 30, 580, 630, false);
        g.setColor(Color.black);

        //BÀN CỜ
        for (int i = 80; i <= 620; i = i + 60) {
            g.drawLine(110, i, 590, i);
        }
        g.drawLine(110, 80, 110, 620);
        g.drawLine(590, 80, 590, 620);
        for (int i = 170; i <= 530; i = i + 60) {
            g.drawLine(i, 80, i, 320);
            g.drawLine(i, 380, i, 620);
        }
        g.drawLine(290, 80, 410, 200);
        g.drawLine(290, 200, 410, 80);
        g.drawLine(290, 500, 410, 620);
        g.drawLine(290, 620, 410, 500);

        g.setColor(Color.black);
        Font font1 = new Font(Font.DIALOG, Font.BOLD, 50);
        g.setFont(font1);
        g.drawString("LT.MẠNG 2021", 170, 365);
        Font font2 = new Font(Font.DIALOG, Font.BOLD, 30);
        g.setFont(font2);

        //QUÂN CỜ
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (pieces[i][j] != null) {
                    if (this.pieces[i][j].getSelected() != false) {
                        g.setColor(new Color(242, 242, 242));
                        g.fillOval(110 + i * 60 - 25, 80 + j * 60 - 25, 50, 50);
                        g.setColor(new Color(96, 95, 91));
                    } else {
                        g.fillOval(110 + i * 60 - 25, 80 + j * 60 - 25, 50, 50);
                        g.setColor(pieces[i][j].getColor());
                    }
                    g.drawString(pieces[i][j].getName(), 110 + i * 60 - 15, 80 + j * 60 + 10);
                    g.setColor(Color.black);
                }
            }
        }
        g.setColor(c); //reset về màu gốc
    }

    public void mouseClicked(MouseEvent e) {
        if (this.board.myTurn == true) {	// nếu đúng đến lượt người chơi

            int i = -1;
            int j = -1;
            int[] pos = getPos(e);
            i = pos[0];
            j = pos[1];
            System.out.println("click " + i + " " + j);
            if (i >= 0 && i <= 8 && j >= 0 && j <= 9) {	// nếu đang ở trong bàn cờ
                if (selected == false) {// không có quân cờ nào được chọn
                    this.noFocus(i, j);
                } else {	// nếu quân cờ đã được chọn trước
                    if (pieces[i][j] != null) {	// quân cờ chọn ở hiện tại
                        if (pieces[i][j].getColor() == pieces[startX][startY].getColor()) {// my piece
                            // lựa chọn
                            System.out.println("lua chon dung");
                            pieces[startX][startY].setSelected(false);
                            pieces[i][j].setSelected(true);
                            startX = i;
                            startY = j;
                        } else {	// quân cờ của đối thủ
                            System.out.println("lua chon sai");
                            endX = i;
                            endY = j;
                            String name = pieces[startX][startY].getName();
                            // nếu nước đi hợp lệ
                            boolean canMove = rules.canMove(startX, startY, endX, endY, name);
                            if (canMove) {
                                try {
                                    interval = 10;
                                    if (!autoMove) {
                                        Movement m = new Movement(startX, startY, endX, endY, paticipantMatch, challenger);
                                        mySocket.sendData(new ObjectWrapper(ObjectWrapper.MOVE, m));
                                    }
                                    this.board.myTurn = !this.board.myTurn;
                                    if (pieces[endX][endY].getName().equals("帥")
                                            || pieces[endX][endY].getName().equals("將")) {
                                        this.win();
                                    } else {
                                        this.gameNotEnd();
                                    }
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                            }
                        }
                    } else {	// nếu ko có quân cờ nào ở vị trí này
                        endX = i;
                        endY = j;
                        String name = pieces[startX][startY].getName();
                        boolean canMove = rules.canMove(startX, startY, endX, endY, name);
                        if (canMove) {
                            this.noPieces();
                        }
                    }
                }
            }

            this.repaint();
        }
    }

    public int[] getPos(MouseEvent e) {
        int[] pos = new int[2];
        pos[0] = -1;
        pos[1] = -1;
        Point p = e.getPoint();
        double x = p.getX();
        double y = p.getY();
        if (Math.abs((x - 110) / 1 % 60) <= 25) {
            pos[0] = Math.round((float) (x - 110)) / 60;
        } else if (Math.abs((x - 110) / 1 % 60) >= 35) {
            pos[0] = Math.round((float) (x - 110)) / 60 + 1;
        }
        if (Math.abs((y - 80) / 1 % 60) <= 25) {
            pos[1] = Math.round((float) (y - 80)) / 60;
        } else if (Math.abs((y - 80) / 1 % 60) >= 35) {
            pos[1] = Math.round((float) (y - 80)) / 60 + 1;
        }
        return pos;
    }

    public void noFocus(int i, int j) {
        if (this.pieces[i][j] != null) {
            if (this.paticipantMatch.getColor().equals("red")) {
                if (this.pieces[i][j].getColor().equals(GameUIFrm.redColor)) {
                    this.pieces[i][j].setSelected(true);
                    selected = true;
                    startX = i;
                    startY = j;
                }
            } else {
                if (this.pieces[i][j].getColor().equals(GameUIFrm.whiteColor)) {
                    this.pieces[i][j].setSelected(true);
                    selected = true;
                    startX = i;
                    startY = j;
                }
            }
        }
    }

    public void win() {
        pieces[endX][endY] = pieces[startX][startY];	//tướng bị ăn
        pieces[startX][startY] = null;
        this.repaint();// paint the final move
        if (timer != null) {
            timer.cancel();
        }
        if (!autoMove) {
            paticipantMatch.setResult("win");
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_PATICIPANT_MATCH, paticipantMatch));
        }

        mySocket.getPaticipantLogin().setStatus("online");
        JOptionPane.showMessageDialog(this, "Chúc mừng, bạn thắng!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_STATUS_PATICIPANT, mySocket.getPaticipantLogin()));
        this.newGame();
        this.repaint();
    }

    public void lose() {
        if (timer != null) {
            timer.cancel();
        }
        if (!autoMove) {
            paticipantMatch.setResult("lose");
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_PATICIPANT_MATCH, paticipantMatch));
        }

        mySocket.getPaticipantLogin().setStatus("online");
        JOptionPane.showMessageDialog(this, "Bạn đã thua!", "THÔNG BÁO", JOptionPane.INFORMATION_MESSAGE);
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_STATUS_PATICIPANT, mySocket.getPaticipantLogin()));
        // thiết lập cho ván chơi mới
        this.newGame();
        this.repaint();
    }

    public void gameNotEnd() {
        pieces[endX][endY] = pieces[startX][startY];
        pieces[startX][startY] = null;
        pieces[endX][endY].setSelected(false);
        this.cchess.repaint(); // tô màu bước đi
        // cập nhật vị trí tướng
        if (pieces[endX][endY].getName().equals("帥")) {
            this.board.setKing1x(endX);
            this.board.setKing1y(endY);
        } else if (pieces[endX][endY].getName().equals("將")) {
            this.board.setKing2x(endX);
            this.board.setKing2y(endY);
        }
        if (this.board.getKing1x() == this.board.getKing2x()) {	// luật
            int count = 0;
            for (int n = this.board.getKing1y() + 1; n < this.board.getKing2y(); ++n) {
                if (pieces[this.board.getKing1x()][n] != null) {
                    count++;
                    break;
                }
            }
            if (count == 0) {// luật
                lose();
            }
        }
        startX = -1;
        startY = -1;
        endX = -1;
        endY = -1;
        selected = false;
    }

    public void noPieces() {
        try {
            interval = 10;
            if (!autoMove) {
                Movement m = new Movement(startX, startY, endX, endY, paticipantMatch, challenger);
                System.out.println(m.getAccepter().getPaticipant().getId());
                this.board.setLastMovement(m);
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.MOVE, this.board));
            }

            this.board.myTurn = !this.board.myTurn;
            pieces[endX][endY] = pieces[startX][startY];
            pieces[startX][startY] = null;
            pieces[endX][endY].setSelected(false);
            this.cchess.repaint();// tô màu bước đi

            int king1x = this.board.getKing1x();
            int king1y = this.board.getKing1y();
            int king2y = this.board.getKing2y();
            int king2x = this.board.getKing2x();
            // cập nhật vị trí tướng
            if (pieces[endX][endY].getName().equals("帥")) {
                king1x = endX;
                king1y = endY;
            } else if (pieces[endX][endY].getName().equals("將")) {
                king2x = endX;
                king2y = endY;
            }
            if (king1x == king2x)// điều kiện thua
            {
                int count = 0;
                for (int n = king1y + 1; n < king2y; ++n) {
                    if (pieces[king1x][n] != null) {
                        count++;
                        break;
                    }
                }
                if (count == 0) {// thua cuộc
                    lose();
                }

            }
            startX = -1;
            startY = -1;
            endX = -1;
            endY = -1;
            selected = false;

        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void move(int startI, int startJ, int endX, int endY) {
        this.board.myTurn = !this.board.myTurn;
        if (pieces[endX][endY] != null
                && (pieces[endX][endY].getName().equals("帥") || pieces[endX][endY].getName().equals("將"))) {// tướng bị ăn

            pieces[endX][endY] = pieces[startI][startJ];
            pieces[startI][startJ] = null;
            this.repaint();// tô màu nước đi
            lose();
            // thiết lập cho ván chơi mới
        } else {// nếu tướng chưa bị ăn
            if (pieces[endX][endY] != null) {
                pieces[endX][endY] = null;
            }
            pieces[endX][endY] = pieces[startI][startJ];
            pieces[startI][startJ] = null;// di chuyển quân cờ
            this.repaint();

            // nếu tướng đã di chuyển
            if (pieces[endX][endY].getName().equals("帥")) {
                this.board.setKing1x(endX);
                this.board.setKing1y(endY);
            } else if (pieces[endX][endY].getName().equals("將")) {
                this.board.setKing2x(endX);
                this.board.setKing2y(endY);
            }

            int king1x = this.board.getKing1x();
            int king1y = this.board.getKing1y();
            int king2y = this.board.getKing2y();
            int king2x = this.board.getKing2x();
            if (king1x == king2x) {// 2 tướng trên 1 line
                int count = 0;
                for (int n = king1y + 1; n < king2y; ++n) {
                    if (pieces[king1x][n] != null) {
                        count++;
                        break;
                    }
                }
                if (count == 0) {
                    win();
                }
            }
        }
        this.repaint();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void receivedStartGameProcessing(ObjectWrapper data) {
        System.out.println("get PaticipantRoom");
        String str = "Bat dau tran dau";
        if (data.getData() instanceof Match) {
            this.match = (Match) data.getData();
            this.match.setBoard(this.board);
            List<PaticipantMatch> pms = match.getListPaticipantMatch();
            for (int i = 0; i < pms.size(); i++) {
                PaticipantMatch p = pms.get(i);
                if (p.getPaticipant().getId() == mySocket.getPaticipantLogin().getId()) {
                    mySocket.getPaticipantLogin().setStatus("busy");
//                    mySocket.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_STATUS_PATICIPANT,mySocket.getPaticipantLogin()));
                    this.paticipantMatch = p;
                    System.out.println(p.getId() + " me");
                    if (i == 0) {
                        this.board.myTurn = true;
                        System.out.println("luot t");
                        str += "\nBan di truoc";
                    }
                } else {
                    System.out.println(p.getId() + " challenger");
                    challenger = p;
                }
            }
            System.out.println("paticipant Math " + paticipantMatch.getPaticipant().getId());
            this.cchess.setEnable(false);
            scheduleMove();
            JOptionPane.showMessageDialog(this, str);
        } else {
            JOptionPane.showMessageDialog(this, "start match fail");
        }
    }

    public void receivedMovementProcessing(ObjectWrapper data) {
        if (data.getData() instanceof Movement) {
            Movement m = (Movement) data.getData();
            this.move(m.getStartX(), m.getStartY(), m.getEndX(), m.getEndY());
            interval = 10;
        }
    }

    public void receivedChallengerQuitGameProcessing(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            autoMove = true;
            paticipantMatch.setResult("win");
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_PATICIPANT_MATCH, paticipantMatch));
            challenger.setResult("lose");
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.UPDATE_PATICIPANT_MATCH, challenger));
            JOptionPane.showMessageDialog(null, "Đối thủ đã thoát, Bạn đã được xử thắng, bạn sẽ đánh với máy");
        }
    }

    public void scheduleMove() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println(waitingMove());
                cchess.txtTime.setText(interval + "");
            }
        }, 1000, 1000);
    }

    public int waitingMove() {
        System.out.println("before");
        if (interval <= 1) {
//            timer.cancel();
            if (this.board.myTurn) {
                try {
                    System.out.println(this.paticipantMatch.getColor());
                    do {
                        startX = (int) Math.round(Math.random() * 8);
                        startY = (int) Math.round(Math.random() * 9);
                        if (this.pieces[startX][startY] != null) {
                            if (this.paticipantMatch.getColor().equals("red")) {
                                if (this.pieces[startX][startY].getColor().equals(GameUIFrm.redColor)) {
                                    break;
                                }
                            } else {
                                if (this.pieces[startX][startY].getColor().equals(GameUIFrm.whiteColor)) {
                                    break;
                                }
                            }
                        }

                    } while (true);
                    System.out.println(this.pieces[startX][startY].getColor().toString() + startX + " " + startY + " " + endX + " " + endY);
                    while (true) {
                        endX = (int) Math.round(Math.random() * 8);
                        endY = (int) Math.round(Math.random() * 9);
                        if (rules.canMove(startX, startY, endX, endY, pieces[startX][startY].getName())) {
                            System.out.println(startX + " " + startY + " " + endX + " " + endY + "11111111111");
                            if (this.paticipantMatch.getColor().equals("red")) {
                                if (this.pieces[endX][endY] == null) {
                                    System.out.println(startX + " " + startY + " " + endX + " " + endY + "22222222222");
                                    break;
                                } else {
                                    if (!this.pieces[endX][endY].getColor().equals(GameUIFrm.redColor)) {
                                        break;
                                    }
                                }
                            } else {
                                if (this.pieces[endX][endY] == null) {
                                    System.out.println(startX + " " + startY + " " + endX + " " + endY + "33333333333");
                                    break;
                                } else {
                                    if (!this.pieces[endX][endY].getColor().equals(GameUIFrm.whiteColor)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(startX + " " + startY + " " + endX + " " + endY);
                    noPieces();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                if (autoMove) {
                    System.out.println("aotomove");
                    try {
                        System.out.println(this.paticipantMatch.getColor());
                        do {
                            startX = (int) Math.round(Math.random() * 8);
                            startY = (int) Math.round(Math.random() * 9);
                            if (this.pieces[startX][startY] != null) {
                                if (this.paticipantMatch.getColor().equals("red")) {
                                    if (this.pieces[startX][startY].getColor().equals(GameUIFrm.whiteColor)) {
                                        break;
                                    }
                                } else {
                                    if (this.pieces[startX][startY].getColor().equals(GameUIFrm.redColor)) {
                                        break;
                                    }
                                }
                            }

                        } while (true);
                        System.out.println(this.pieces[startX][startY].getColor().toString() + startX + " " + startY + " " + endX + " " + endY);
                        while (true) {
                            endX = (int) Math.round(Math.random() * 8);
                            endY = (int) Math.round(Math.random() * 9);
                            if (rules.canMove(startX, startY, endX, endY, pieces[startX][startY].getName())) {
                                System.out.println(startX + " " + startY + " " + endX + " " + endY + "11111111111");
                                if (this.paticipantMatch.getColor().equals("red")) {
                                    if (this.pieces[endX][endY] == null) {
                                        System.out.println(startX + " " + startY + " " + endX + " " + endY + "22222222222");
                                        break;
                                    } else {
                                        if (!this.pieces[endX][endY].getColor().equals(GameUIFrm.redColor)) {
                                            break;
                                        }
                                    }
                                } else {
                                    if (this.pieces[endX][endY] == null) {
                                        System.out.println(startX + " " + startY + " " + endX + " " + endY + "33333333333");
                                        break;
                                    } else {
                                        if (!this.pieces[endX][endY].getColor().equals(GameUIFrm.whiteColor)) {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        System.out.println(startX + " " + startY + " " + endX + " " + endY);
                        noPieces();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        if (interval == 0) {
            return 0;
        }
        return --interval;
    }

}
