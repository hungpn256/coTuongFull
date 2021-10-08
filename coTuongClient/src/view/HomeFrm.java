/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ClientCtr;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Friend;
import model.ObjectWrapper;
import model.Paticipant;
import model.PaticipantRoom;
import model.PaticipantStat;
import model.Room;
import model.RoomInvitation;

/**
 *
 * @author phamhung
 */
public class HomeFrm extends javax.swing.JFrame {

    private ClientCtr mySocket;
    Paticipant paticipantLogin;

    /**
     * Creates new form HomeFrm
     */
    public HomeFrm(ClientCtr socket) {
        initComponents();
        mySocket = socket;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_ALL_FRIEND, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_LOG_OUT, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_CREATE_ROOM, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_INVITE_TO_ROOM, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_RANK, this));
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_ALL_FRIEND));
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_RANK));
        paticipantLogin = mySocket.getPaticipantLogin();
        txtName.setText(paticipantLogin.getNickName()+"(chua co rank)");
        setTableFriend();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                mySocket.sendData(new ObjectWrapper(ObjectWrapper.LOG_OUT));
                mySocket.removeFunction(this);
            }
        });
        System.out.println("home");

    }

    public void setTableFriend() {
        List<Friend> friends = paticipantLogin.getListFriend();
        DefaultTableModel dtm = (DefaultTableModel) tableFriend.getModel();
        dtm.setRowCount(0);
        for (Friend f : friends) {
            if (f.getFriend().getStatus().equals("online")) {
                dtm.addRow(new Object[]{f.getFriend().getId(), f.getFriend().getNickName(), f.getFriend().getStatus()});
            }

        }
        for (Friend f : friends) {
            if (f.getFriend().getStatus().equals("offline")) {
                dtm.addRow(new Object[]{f.getFriend().getId(), f.getFriend().getNickName(), f.getFriend().getStatus()});

            }
        }

    }

    public void receivedAllFriendProcessing(ObjectWrapper data) {
        
        if (data.getData() instanceof List) {
            System.out.println("get friend success"+ data.getData());
            paticipantLogin.setListFriend((List<Friend>)data.getData());
            System.out.println("friend current"+ mySocket.getPaticipantLogin().getListFriend().size());
            setTableFriend();
        } else {
            JOptionPane.showMessageDialog(this, "get friend fail");
        }
    }
    public void receivedCreateRoomProcessing(ObjectWrapper data) {
        
        if (data.getData() instanceof Room) {
            Room r = (Room)data.getData();
            GameUIFrm gameView = new GameUIFrm(mySocket,r);
            gameView.setVisible(true);
            mySocket.removeFunction(this);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Tạo phòng thất bại");
        }
    }
    public void receivedInviteToRoomProcessing(ObjectWrapper data) {
        
        if (data.getData() instanceof RoomInvitation) {
            RoomInvitation ri = (RoomInvitation)data.getData();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (this, "bạn có muốn tham gia phòng "+ ri.getRoom().getId(), "Mời", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION){
                System.out.println("tham gia");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tham gia room that bai");
        }
    }
    public void receivedGetRankProcessing(ObjectWrapper data) {
        if(data.getData() instanceof List){
            DefaultTableModel mdr = (DefaultTableModel)tableRanking.getModel();
            mdr.setRowCount(0);
            List<PaticipantStat> listRank = (List<PaticipantStat>)data.getData();
            for(PaticipantStat x:listRank){
                if(x.getId()==paticipantLogin.getId()){
                    txtName.setText(paticipantLogin.getNickName()+"("+x.getRank()+")");
                }
                mdr.addRow(new Object[]{x.getId(),x.getNickName(),x.getWonGame(),x.getPlayedGame(),x.getRank()});
            }
        }
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNamePaticipant = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtName = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableRanking = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableFriend = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtNamePaticipant.setText("Tên:");

        jButton1.setText("Invitation");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtName.setText("Name");

        tableRanking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "id", "name", "win", "played", "rank"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableRanking);
        if (tableRanking.getColumnModel().getColumnCount() > 0) {
            tableRanking.getColumnModel().getColumn(0).setResizable(false);
            tableRanking.getColumnModel().getColumn(1).setResizable(false);
            tableRanking.getColumnModel().getColumn(2).setResizable(false);
            tableRanking.getColumnModel().getColumn(3).setResizable(false);
            tableRanking.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 528, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Ranking", jPanel1);

        jButton2.setText("Tạo bàn");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Chơi ngẫu nhiên");

        jButton4.setText("Vào phòng");

        jButton5.setText("Đăng xuất");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tableFriend.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "id", "name", "status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableFriend);
        if (tableFriend.getColumnModel().getColumnCount() > 0) {
            tableFriend.getColumnModel().getColumn(0).setResizable(false);
            tableFriend.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableFriend.getColumnModel().getColumn(1).setResizable(false);
            tableFriend.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableFriend.getColumnModel().getColumn(2).setResizable(false);
            tableFriend.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        jButton6.setText("Hội nhóm");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNamePaticipant, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtName)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNamePaticipant)
                                    .addComponent(txtName))
                                .addGap(175, 175, 175)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton6)
                                .addGap(12, 12, 12)
                                .addComponent(jButton5))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FriendJrm friendJrm = new FriendJrm(mySocket);
        friendJrm.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.LOG_OUT));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Room r = new Room();
        PaticipantRoom pr = new PaticipantRoom();
        pr.setPaticipant(mySocket.getPaticipantLogin());
        pr.setRoom(r);
        Date date = new Date();
        r.setCreatedAt(new Timestamp(date.getTime()));
        r.setCreatedBy(mySocket.getPaticipantLogin());
        r.getPaticipantRoom().add(pr);
        r.setStatus("pending");
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.CREATE_ROOM,r));
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tableFriend;
    private javax.swing.JTable tableRanking;
    private javax.swing.JLabel txtName;
    private javax.swing.JLabel txtNamePaticipant;
    // End of variables declaration//GEN-END:variables
}
