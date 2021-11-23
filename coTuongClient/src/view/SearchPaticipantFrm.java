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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
 
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import model.Club;
import model.ClubInvitation;
import model.ObjectWrapper;
import model.Paticipant;

public class SearchPaticipantFrm extends JFrame implements ActionListener{
    private List<Paticipant> listPaticipant;
    private List<JButton> listAdd;
    private JTextField txtKey;
    private JButton btnSearch;
    private JTable tblResult;
    private ClientCtr mySocket;
    public SearchPaticipantFrm(ClientCtr mySocket){
        super("Search Paticipant");        
        this.mySocket = mySocket;
        listPaticipant = new ArrayList<>();
        listAdd = new ArrayList<>();
        JPanel pnMain = new JPanel();
        pnMain.setSize(this.getSize().width-5, this.getSize().height-20);        
        pnMain.setLayout(new BoxLayout(pnMain,BoxLayout.Y_AXIS));
        JPanel pn1 = new JPanel();
        pn1.setLayout(new BoxLayout(pn1,BoxLayout.X_AXIS));
        pn1.setSize(this.getSize().width-5, 20);
        pn1.add(new JLabel("Keyword: "));
        txtKey = new JTextField();
        pn1.add(txtKey);
        btnSearch = new JButton("Search");
        btnSearch.addActionListener(this);
        pn1.add(btnSearch);
        pnMain.add(pn1);
 
        JPanel pn2 = new JPanel();
        pn2.setLayout(new BoxLayout(pn2,BoxLayout.Y_AXIS));
        tblResult = new JTable(new ClientTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
        });
        JScrollPane scrollPane= new  JScrollPane(tblResult);
        tblResult.setFillsViewportHeight(false); 
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 250));
 
        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        tblResult.getColumn("Thêm").setCellRenderer(buttonRenderer);
        tblResult.addMouseListener(new JTableButtonMouseListener(tblResult));
        pn2.add(scrollPane);
        pnMain.add(pn2);    
        this.add(pnMain);
        this.setSize(400,200);
        pnMain.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_SEARCH_PATICIPANT, this));
        mySocket.getActiveFunction().add(new ObjectWrapper(ObjectWrapper.REPLY_INVITE_TO_CLUB, this));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("create club close");
                mySocket.removeFunction(this);
            }
        });
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton btnClicked = (JButton)e.getSource();
        if(btnClicked.equals(btnSearch)){
            btnSearchClick();
            return;
        }
        for(int i=0; i<listAdd.size(); i++)
            if(btnClicked.equals(listAdd.get(i))){
                btnAddClick(i);
                return;
            }
    }
 
    /**
     * processing the event that the Search button is clicked
     */
    private void btnSearchClick(){
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.SEARCH_PATICIPANT,txtKey.getText()));
    }
 
    /**
     * processing the event that the @index Edit button is clicked
     * @param index
     */
    private void btnAddClick(int index){
        mySocket.sendData(new ObjectWrapper(ObjectWrapper.INVITE_TO_CLUB, new ClubInvitation(listPaticipant.get(index), mySocket.getPaticipantLogin().getClub(), "pending")));
    }
    class ClientTableModel extends DefaultTableModel {
        private String[] columnNames = {"Id", "Tên", "Thêm"};
        private final Class<?>[] columnTypes = new  Class<?>[] {Integer.class, String.class, JButton.class};
 
        @Override public int getColumnCount() {
            return columnNames.length;
        }
 
        @Override public int getRowCount() {
            return listPaticipant.size();
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
                    return listPaticipant.get(rowIndex).getId();
                case 1: 
                    return listPaticipant.get(rowIndex).getNickName();
                case 2: 
                    return listAdd.get(rowIndex);
                default: return "Error";
            }
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
 
    class JTableButtonRenderer implements TableCellRenderer {        
        @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                boolean hasFocus, int row, int column) {
            JButton button = (JButton)value;
            return button;  
        }
    }
    
    public void receivedSearchPaticipantProcessing(ObjectWrapper data){
        
        if(data.getData() instanceof List){
            listPaticipant  = (List<Paticipant>)data.getData();
            for(Paticipant x:listPaticipant){
                JButton addP = new JButton("Mời");
                addP.addActionListener(this);
                listAdd.add(addP);
            }
            System.out.println("search paticipant" + listPaticipant.size());
            ((DefaultTableModel)tblResult.getModel()).fireTableDataChanged();
        }
        else{
            JOptionPane.showMessageDialog(this, "get paticipant fail");
        }
        
    }
    
    public void receivedInviteToClubProcessing(ObjectWrapper data) {        
        String res = (String)data.getData();
        System.out.println(res+"        res");
        if(res.equals("ok")){
            JOptionPane.showMessageDialog(this, "Mời thành công!!");
            mySocket.removeFunction(this);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(this, "Mời thất bại");
        }
    }
 
    /**
     * @param args
     */
}