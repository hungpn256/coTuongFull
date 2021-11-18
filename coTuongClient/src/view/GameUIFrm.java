/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.Timestamp;
import controller.ClientCtr;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Board;
import model.Friend;
import model.Match;
import model.ObjectWrapper;
import model.Paticipant;
import model.PaticipantMatch;
import model.PaticipantRoom;
import model.Piece;
import model.Room;
import model.RoomInvitation;

/**
 *
 * @author phamhung
 */
public class GameUIFrm extends javax.swing.JFrame {

    public static Color redColor = Color.RED;
    public static Color whiteColor = Color.WHITE;
    Room room;
    private ClientCtr mySocket;
    Paticipant paticipantLogin;
    PaticipantRoom paticipantRoom;
    List<Friend> friendOl;
    public int myColor = 0; // 0 = MÀU ĐỎ, 1 = MÀU TRẮNG
    /**
     * Creates new form GameUIFrm
     */
    Piece[][] chessPieces = new Piece[9][10];
    public BoardFrm board;

    public GameUIFrm(ClientCtr socket, Room r) {
        initComponents();
        friendOl = new ArrayList<>();
        mySocket = socket;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paticipantLogin = mySocket.getPaticipantLogin();
        List<PaticipantRoom> prs = r.getPaticipantRoom();
        paticipantRoom = prs.get(prs.size() - 1);
        room = r;
        idRoom.setText(r.getId() + "");
        jTextArea1.setText("");

        for (PaticipantRoom pr : prs) {
            jTextArea1.append(pr.getPaticipant().getNickName());
        }
        System.out.println(r.getId() + " id room in game");
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_ROOM, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_PATICIPANT_ROOM, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_INVITE_TO_ROOM, this));

        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_ALL_FRIEND));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                room.getPaticipantRoom().remove(paticipantRoom);
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.LEAVE_ROOM, room));
            }
        });
        this.setTitle("GAME CỜ TƯỚNG");

        board = new BoardFrm(mySocket, this);
        board.setLayout(null);
        board.setBounds(0, 0, 700, 700);
        board.setVisible(true);
        rightPanel.add(board);

        if (paticipantLogin.getId() != room.getCreatedBy().getId()) {
            btnStart.setVisible(false);
            jComboBox1.setVisible(false);
            btnInvite.setVisible(false);
            jLabel1.setVisible(false);
        }
        txtName.setText(paticipantLogin.getNickName());
        btnStart.setEnabled(false);
    }

    public void setEnable(boolean b) {
        btnStart.setEnabled(room.getPaticipantRoom().size() == 2 && b);
        btnInvite.setEnabled(room.getPaticipantRoom().size() < 2 && b);
        jComboBox1.setEditable(room.getPaticipantRoom().size() < 2 && b);
        txtTime.setText("10");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rightPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        btnStart = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        idRoom = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btnLeaveRoom = new javax.swing.JButton();
        btnInvite = new javax.swing.JButton();
        txtName = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTime = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 648, Short.MAX_VALUE)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        jLabel1.setText("Mời bạn:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        idRoom.setText("idRoom");

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setText("Đang trong phòng:");

        btnLeaveRoom.setText("Rời phòng");
        btnLeaveRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaveRoomActionPerformed(evt);
            }
        });

        btnInvite.setText("Mời");
        btnInvite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInviteActionPerformed(evt);
            }
        });

        txtName.setText("jLabel3");

        jLabel3.setText("Thời gian: ");

        txtTime.setText("10");

        jLabel4.setText("Mã phòng: ");

        jLabel5.setText("Tên người chơi:");

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTime)
                            .addComponent(idRoom)
                            .addComponent(txtName))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLeaveRoom)
                            .addComponent(btnStart)
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInvite)))
                        .addContainerGap(58, Short.MAX_VALUE))))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idRoom)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtName))
                .addGap(18, 18, 18)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTime))
                .addGap(27, 27, 27)
                .addComponent(btnStart)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInvite))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(btnLeaveRoom)
                .addContainerGap(249, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rightPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        // TODO add your handling code here:
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.START_GAME, room));

    }//GEN-LAST:event_btnStartActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnLeaveRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveRoomActionPerformed
        // TODO add your handling code here:
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.LEAVE_ROOM, room));


    }//GEN-LAST:event_btnLeaveRoomActionPerformed

    private void btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviteActionPerformed
        // TODO add your handling code here:
        int index = jComboBox1.getSelectedIndex();
        Paticipant p = friendOl.get(index).getFriend();
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.INVITE_TO_ROOM, new RoomInvitation(room, p)));
    }//GEN-LAST:event_btnInviteActionPerformed
    public void receivedAllFriendProcessing(ObjectWrapper data) {

        if (data.getData() instanceof List) {
            friendOl.clear();
            System.out.println("get friend success" + ((List<Friend>) data.getData()).size());
            paticipantLogin.setListFriend((List<Friend>) data.getData());
            for (Friend x : paticipantLogin.getListFriend()) {
                if (x.getFriend().getStatus().equals("online")) {
                    friendOl.add(x);
                }
            }
            setComboBox();
        } else {
            JOptionPane.showMessageDialog(this, "get friend fail");
        }
    }

    public void receivedLeaveRoomProcessing(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            if (board.match != null && board.autoMove == false) {
                System.out.println("quit");
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.QUIT_GAME, board.match));
            }
            if(board.timer != null ){
                board.timer.cancel();
            }
            JOptionPane.showMessageDialog(this, "Bạn đã rời khỏi phòng");
            HomeFrm home = new HomeFrm(mySocket);
            home.setVisible(true);

            mySocket.removeFunction(this);
            mySocket.removeFunction(board);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "out fail");
        }
    }

    public void receivedInviteRoRoomSuccessProcessing(ObjectWrapper data) {
        if (data.getData().equals("ok")) {
            JOptionPane.showMessageDialog(this, "Moi thanh cong");
        } else {
            JOptionPane.showMessageDialog(this, "get friend fail");
        }
    }

    public void setComboBox() {
        jComboBox1.removeAllItems();
        for (Friend x : friendOl) {
            jComboBox1.addItem(x.getFriend().getNickName() + " #" + x.getFriend().getId());
        }
    }

    public void receivedGetPaticipantRoomProcessing(ObjectWrapper data) {
        System.out.println("get PaticipantRoom");
        if (data.getData() instanceof List) {
            List<PaticipantRoom> lpr = (List<PaticipantRoom>) data.getData();
            room.setPaticipantRoom(lpr);
            jTextArea1.setText("");
            setEnable(true);
            for (PaticipantRoom pr : lpr) {
                jTextArea1.append("\n" + pr.getPaticipant().getNickName());
            }
        } else {
            JOptionPane.showMessageDialog(this, "get paticippant room fail");
        }
    }

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvite;
    private javax.swing.JButton btnLeaveRoom;
    private javax.swing.JButton btnStart;
    private javax.swing.JLabel idRoom;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JLabel txtName;
    public javax.swing.JLabel txtTime;
    // End of variables declaration//GEN-END:variables
}
