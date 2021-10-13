/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ClientCtr;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Board;
import model.Friend;
import model.ObjectWrapper;
import model.Paticipant;
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
    /**
     * Creates new form GameUIFrm
     */
    Piece[][] chessPieces = new Piece[9][10];
    public GameUIFrm(ClientCtr socket,Room r) {
        friendOl = new ArrayList<>();
        mySocket = socket;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        paticipantLogin = mySocket.getPaticipantLogin();
        paticipantRoom = r.getPaticipantRoom().get(r.getPaticipantRoom().size()-1);
        initComponents();
        initialPieces();
        room = r;
        System.out.println(r.getId()+" id room in game");
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LEAVE_ROOM, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, this));
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_ALL_FRIEND));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mySocket.removeFunction(this);
                room.getPaticipantRoom().remove(paticipantRoom);
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.LEAVE_ROOM, room));
            }
        });
    }
    public void initialPieces() {
        //Các quân cờ bên ĐỎ
        chessPieces[0][0] = new Piece(redColor, "車", 0, 0);
        chessPieces[1][0] = new Piece(redColor, "馬", 1, 0);
        chessPieces[2][0] = new Piece(redColor, "相", 2, 0);
        chessPieces[3][0] = new Piece(redColor, "仕", 3, 0);
        chessPieces[4][0] = new Piece(redColor, "帥", 4, 0);
        chessPieces[5][0] = new Piece(redColor, "仕", 5, 0);
        chessPieces[6][0] = new Piece(redColor, "相", 6, 0);
        chessPieces[7][0] = new Piece(redColor, "馬", 7, 0);
        chessPieces[8][0] = new Piece(redColor, "車", 8, 0);
        chessPieces[1][2] = new Piece(redColor, "炮", 1, 2);
        chessPieces[7][2] = new Piece(redColor, "炮", 7, 2);
        chessPieces[0][3] = new Piece(redColor, "兵", 0, 3);
        chessPieces[2][3] = new Piece(redColor, "兵", 2, 3);
        chessPieces[4][3] = new Piece(redColor, "兵", 4, 3);
        chessPieces[6][3] = new Piece(redColor, "兵", 6, 3);
        chessPieces[8][3] = new Piece(redColor, "兵", 8, 3);

        //Các quân cờ bên TRẮNG
        chessPieces[0][9] = new Piece(whiteColor, "車", 0, 9);
        chessPieces[1][9] = new Piece(whiteColor, "馬", 1, 9);
        chessPieces[2][9] = new Piece(whiteColor, "象", 2, 9);
        chessPieces[3][9] = new Piece(whiteColor, "士", 3, 9);
        chessPieces[4][9] = new Piece(whiteColor, "將", 4, 9);
        chessPieces[5][9] = new Piece(whiteColor, "士", 5, 9);
        chessPieces[6][9] = new Piece(whiteColor, "象", 6, 9);
        chessPieces[7][9] = new Piece(whiteColor, "馬", 7, 9);
        chessPieces[8][9] = new Piece(whiteColor, "車", 8, 9);
        chessPieces[1][7] = new Piece(whiteColor, "砲", 1, 7);
        chessPieces[7][7] = new Piece(whiteColor, "砲", 7, 7);
        chessPieces[0][6] = new Piece(whiteColor, "卒", 0, 6);
        chessPieces[2][6] = new Piece(whiteColor, "卒", 2, 6);
        chessPieces[4][6] = new Piece(whiteColor, "卒", 4, 6);
        chessPieces[6][6] = new Piece(whiteColor, "卒", 6, 6);
        chessPieces[8][6] = new Piece(whiteColor, "卒", 8, 6);
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
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        idRoom = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btnLeaveRoom = new javax.swing.JButton();
        btnInvite = new javax.swing.JButton();

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

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLeaveRoom)
                    .addComponent(idRoom)
                    .addComponent(jButton1)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(leftPanelLayout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnInvite))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(idRoom)
                .addGap(79, 79, 79)
                .addComponent(jButton1)
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
                .addContainerGap(284, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(rightPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(leftPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(rightPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void btnLeaveRoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaveRoomActionPerformed
        // TODO add your handling code here:
        room.getPaticipantRoom().remove(paticipantRoom);
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.LEAVE_ROOM, room));
    }//GEN-LAST:event_btnLeaveRoomActionPerformed

    private void btnInviteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInviteActionPerformed
        // TODO add your handling code here:
        int index = jComboBox1.getSelectedIndex();
        Paticipant p = friendOl.get(index).getFriend();
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.INVITE_TO_ROOM,new RoomInvitation(room,p)));
    }//GEN-LAST:event_btnInviteActionPerformed
    public void receivedAllFriendProcessing(ObjectWrapper data) {
        
        if (data.getData() instanceof List) {
            friendOl.clear();
            System.out.println("get friend success"+ ((List<Friend>)data.getData()).size());
            paticipantLogin.setListFriend((List<Friend>)data.getData());
            for(Friend x:paticipantLogin.getListFriend()){
                if(x.getFriend().getStatus().equals("online")){
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
            HomeFrm home = new HomeFrm(mySocket);
            home.setVisible(true);
            mySocket.removeFunction(this);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "get friend fail");
        }
    }
    
    public void setComboBox(){
        jComboBox1.removeAllItems();
        for(Friend x:friendOl){
            jComboBox1.addItem(x.getFriend().getNickName() + " #"+x.getFriend().getId());
        }
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInvite;
    private javax.swing.JButton btnLeaveRoom;
    private javax.swing.JLabel idRoom;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JPanel rightPanel;
    // End of variables declaration//GEN-END:variables
}
