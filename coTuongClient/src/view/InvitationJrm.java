/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import controller.ClientCtr;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import model.ClubInvitation;
import model.FriendInvitation;
import model.ObjectWrapper;
import model.Paticipant;

/**
 *
 * @author phamhung
 */
public class InvitationJrm extends javax.swing.JFrame implements ActionListener {
private ClientCtr mySocket;
    /**
     * Creates new form Friend
     */
    
    AddFriendTableModel defaultTableModel;
    PendingFriendTableModel pendingFriendTableModel;
    PendingClubTableModel pendingClubTableModel;
    List<JButton> listBtnAcceptClub;
    List<JButton> listBtnDenyClub;
    List<Paticipant> listPaticipantAddFriend;
    List<FriendInvitation> listPaticipantPendingFriend;
    List<ClubInvitation> listClubInvitation;
    List<JButton> listBtnAdd;
    List<JButton> listBtnAccept;
    List<JButton> listBtnRemove;
    
    
    Paticipant paticipant;
    public InvitationJrm(ClientCtr socket) {
        listPaticipantAddFriend = new ArrayList<>();
        listPaticipantPendingFriend = new ArrayList<>();
        listClubInvitation = new ArrayList<>();
        listBtnAdd = new ArrayList<>();
        listBtnAccept = new ArrayList<>();
        listBtnRemove = new ArrayList<>();
        listBtnAcceptClub = new ArrayList<>();
        listBtnDenyClub = new ArrayList<>();
        initComponents();
        this.setName("FriendJrm");
        defaultTableModel = new AddFriendTableModel();
        jTableAddFriend.setModel(defaultTableModel);
        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        jTableAddFriend.getColumn("Addfriend").setCellRenderer(buttonRenderer);
        jTableAddFriend.addMouseListener(new JTableButtonMouseListener(jTableAddFriend));
        
        pendingFriendTableModel = new PendingFriendTableModel();
        tablePendingFriend.setModel(pendingFriendTableModel);
        tablePendingFriend.getColumn("Accept").setCellRenderer(buttonRenderer);
        tablePendingFriend.getColumn("Remove").setCellRenderer(buttonRenderer);
        tablePendingFriend.addMouseListener(new JTableButtonMouseListener(tablePendingFriend));
        
        pendingClubTableModel = new PendingClubTableModel();
        tablePendingClub.setModel(pendingClubTableModel);
        tablePendingClub.getColumn("Accept").setCellRenderer(buttonRenderer);
        tablePendingClub.getColumn("Remove").setCellRenderer(buttonRenderer);
        tablePendingClub.addMouseListener(new JTableButtonMouseListener(tablePendingClub));
        
        mySocket = socket;
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_ADD_FRIEND, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_REQUEST_ADD_FRIEND, this));
        this.addWindowListener( new WindowAdapter(){
           public void windowClosing(WindowEvent e) {
               System.out.println("tat");
               mySocket.removeFunction(this);
            }
        });
        
        //send data 
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_PENDING_FRIEND));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_PENDING_FRIEND, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_FRIEND, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DENY_FRIEND, this));
        
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_PENDING_INVITE_TO_CLUB));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_GET_PENDING_INVITE_TO_CLUB, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_ACCEPT_INVITE_TO_CLUB, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_DENY_INVITE_TO_CLUB, this));
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btnClicked = (JButton)e.getSource();
        for(int i=0; i<listBtnAdd.size(); i++)
            if(btnClicked.equals(listBtnAdd.get(i))){
                btnAddClick(i);
                return;
        }
        for(int i=0; i<listBtnAccept.size(); i++)
            if(btnClicked.equals(listBtnAccept.get(i))){
                btnAcceptClick(i);
                return;
        }
        for(int i=0; i<listBtnRemove.size(); i++)
            if(btnClicked.equals(listBtnRemove.get(i))){
                btnRemoveClick(i);
                return;
        }
        for(int i=0; i<listBtnAcceptClub.size(); i++)
            if(btnClicked.equals(listBtnAcceptClub.get(i))){
                btnAcceptClubClick(i);
                return;
        }
        for(int i=0; i<listBtnDenyClub.size(); i++)
            if(btnClicked.equals(listBtnDenyClub.get(i))){
                btnDenyClubClick(i);
                return;
        }
    }
    public void btnAcceptClubClick(int i){
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.ACCEPT_INVITE_TO_CLUB, listClubInvitation.get(i)));
    }
    
    public void btnDenyClubClick(int i){
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.DENY_INVITE_TO_CLUB, listClubInvitation.get(i)));
    }
    
    public void btnAcceptClick(int i){
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.ACCEPT_FRIEND, listPaticipantPendingFriend.get(i)));
    }
    
    public void btnRemoveClick(int i){
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.DENY_FRIEND, listPaticipantPendingFriend.get(i)));
    }
    
    public void btnAddClick(int i){
        FriendInvitation fi = new FriendInvitation(mySocket.getPaticipantLogin(), listPaticipantAddFriend.get(i), "pending");
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.REQUEST_ADD_FRIEND, fi));
    }
    class AddFriendTableModel extends DefaultTableModel {
        private String[] columnNames = {"Id", "Name", "Addfriend"};
        private final Class<?>[] columnTypes = new  Class<?>[] {Long.class, String.class, JButton.class};
 
        @Override public int getColumnCount() {
            return columnNames.length;
        }
 
        @Override public int getRowCount() {
            return listPaticipantAddFriend.size();
        }
 
        @Override public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
 
        @Override public Class<?> getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
        }
 
        @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
                /*Adding components*/
            switch (columnIndex) {
                case 0: 
                    return listPaticipantAddFriend.get(rowIndex).getId();
                case 1: 
                    return listPaticipantAddFriend.get(rowIndex).getNickName();
                case 2: 
                    return listBtnAdd.get(rowIndex);                    
                default: return "Error";
            }
        } 
    }
    
    class PendingFriendTableModel extends DefaultTableModel {
        private String[] columnNames = {"Id", "Name", "Accept","Remove"};
        private final Class<?>[] columnTypes = new  Class<?>[] {Long.class, String.class, JButton.class,JButton.class};
 
        @Override public int getColumnCount() {
            return columnNames.length;
        }
 
        @Override public int getRowCount() {
            return listPaticipantPendingFriend.size();
        }
 
        @Override public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
 
        @Override public Class<?> getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
        }
 
        @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
                /*Adding components*/
            switch (columnIndex) {
                case 0: 
                    return listPaticipantPendingFriend.get(rowIndex).getSender().getId();
                case 1: 
                    return listPaticipantPendingFriend.get(rowIndex).getSender().getNickName();
                case 2: 
                    return listBtnAccept.get(rowIndex);
                case 3: 
                    return listBtnRemove.get(rowIndex);
                default: return "Error";
            }
        } 
    }
    
    class PendingClubTableModel extends DefaultTableModel {
        private String[] columnNames = {"Id", "Tên Club", "Accept","Remove"};
        private final Class<?>[] columnTypes = new  Class<?>[] {Long.class, String.class, JButton.class,JButton.class};
 
        @Override public int getColumnCount() {
            return columnNames.length;
        }
 
        @Override public int getRowCount() {
            return listClubInvitation.size();
        }
 
        @Override public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
 
        @Override public Class<?> getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
        }
 
        @Override public Object getValueAt(final int rowIndex, final int columnIndex) {
                /*Adding components*/
            switch (columnIndex) {
                case 0: 
                    return listClubInvitation.get(rowIndex).getClub().getId();
                case 1: 
                    return listClubInvitation.get(rowIndex).getClub().getName();
                case 2: 
                    return listBtnAcceptClub.get(rowIndex);
                case 3: 
                    return listBtnDenyClub.get(rowIndex);
                default: return "Error";
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePendingFriend = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableAddFriend = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePendingClub = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablePendingFriend.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Tên", "Đồng ý", "Từ chối"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablePendingFriend);
        if (tablePendingFriend.getColumnModel().getColumnCount() > 0) {
            tablePendingFriend.getColumnModel().getColumn(1).setResizable(false);
            tablePendingFriend.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lời mời kết bạn", jPanel2);

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jTableAddFriend.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "id", "name", "action"
            }
        ));
        jScrollPane3.setViewportView(jTableAddFriend);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnSearch)))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Kết bạn", jPanel3);

        tablePendingClub.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Tên Club", "Đồng ý", "Từ chối"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tablePendingClub);
        if (tablePendingClub.getColumnModel().getColumnCount() > 0) {
            tablePendingClub.getColumnModel().getColumn(1).setResizable(false);
            tablePendingClub.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Lời mời club", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        System.out.println(mySocket.getPaticipantLogin().getId()+"id paticipant");
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_ADD_FRIEND,txtSearch.getText()));
        //        defaultTableModel.setRowCount(0);
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    class JTableButtonRenderer implements TableCellRenderer {        
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            return button;  
        }
    }
    
    
    class JTableButtonMouseListener extends MouseAdapter {
        private final JTable table;
 
        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }
 
        public void mouseClicked(MouseEvent e) {
            int column = table.getColumnModel().getColumnIndexAtX(e.getX()); // get the coloum of the button
            int row    = e.getY()/table.getRowHeight(); //get the row of the button
 
                    //*Checking the row or column is valid or not
            if (row < table.getRowCount() && row >= 0  && column < table.getColumnCount() && column >= 0)  {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    //perform a click event
                    ((JButton)value).doClick();
                }
            }
        }
    }
    
    public void receivedSearchFriendProcessing(ObjectWrapper data) {        
        listPaticipantAddFriend = (List<Paticipant>)data.getData();
        listBtnAdd.clear();
        for (Paticipant paticipant : listPaticipantAddFriend) {
            JButton btn = new JButton("add friend");
            btn.addActionListener(this);
            listBtnAdd.add(btn);
            //            defaultTableModel.addRow(new Object[]{paticipant.getId(),paticipant.getNickName()});
            System.out.println(paticipant.getId()+"id paticipant add friend");
        }
        defaultTableModel.fireTableDataChanged();
 
    }
    
    public void receivedAddFriendProcessing(ObjectWrapper data) {        
        String res = (String)data.getData();
        System.out.println(res+"        res");
        if(res.equals("ok")){
            this.listPaticipantAddFriend.clear();
            this.listBtnAdd.clear();
            JOptionPane.showMessageDialog(this, "Kết bạn thành công!!");
            defaultTableModel.fireTableDataChanged();
        }
        else{
            JOptionPane.showMessageDialog(this, "Kết bạn thất bại");
        }
        
 
    }
    
    public void receivedPendingFriendProcessing(ObjectWrapper data) {        
        listPaticipantPendingFriend = (List<FriendInvitation>)data.getData();
        listBtnAccept.clear();
        listBtnRemove.clear();
        for (FriendInvitation friendInvitation : listPaticipantPendingFriend) {
            JButton btn = new JButton("accept");
            btn.addActionListener(this);
            listBtnAccept.add(btn);
            JButton btnRemove = new JButton("deny");
            btnRemove.addActionListener(this);
            listBtnRemove.add(btnRemove);
            //            defaultTableModel.addRow(new Object[]{paticipant.getId(),paticipant.getNickName()});
        }
        ((DefaultTableModel)tablePendingFriend.getModel()).fireTableDataChanged(); 
    }
    public void receivedAcceptFriendProcessing(ObjectWrapper data) {        
        if (data.getData().equals("ok")) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_PENDING_FRIEND));
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_ALL_FRIEND));
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect Acc");
        }
    }
    
    public void receivedDenyFriendProcessing(ObjectWrapper data) {        
        if (data.getData().equals("ok")) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_PENDING_FRIEND));
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect Deny Friend");
        }
    }
    
    public void receivedPendingInvitationClubProcessing(ObjectWrapper data) {
        System.out.println("get data list invitation club");        
        listClubInvitation = (List<ClubInvitation>)data.getData();
        listBtnAcceptClub.clear();
        listBtnDenyClub.clear();
        for (ClubInvitation clubInvitation : listClubInvitation) {
            JButton btn = new JButton("accept");
            btn.addActionListener(this);
            listBtnAcceptClub.add(btn);
            
            JButton btnDeny = new JButton("deny");
            btnDeny.addActionListener(this);
            listBtnDenyClub.add(btnDeny);
        }
        pendingClubTableModel.fireTableDataChanged(); 
    }
    public void receivedAcceptInvitationClubProcessing(ObjectWrapper data) {        
        if (data.getData() instanceof ClubInvitation) {
            ClubInvitation ci = (ClubInvitation)data.getData();
            mySocket.getPaticipantLogin().setClub(ci.getClub());
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_PENDING_INVITE_TO_CLUB));
            JOptionPane.showMessageDialog(this, "Tham gia thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Tham gia thất bạt");
        }
    }
    
    public void receivedDenyInvitationClubProcessing(ObjectWrapper data) {        
        if (data.getData().equals("ok")) {
            mySocket.sendData(new ObjectWrapper(ObjectWrapper.GET_PENDING_INVITE_TO_CLUB));
            JOptionPane.showMessageDialog(this, "Từ chối thành công");
        } else {
            JOptionPane.showMessageDialog(this, "tu chối thất bại");
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableAddFriend;
    private javax.swing.JTable tablePendingClub;
    private javax.swing.JTable tablePendingFriend;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
