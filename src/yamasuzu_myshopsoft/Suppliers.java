package yamasuzu_myshopsoft;

import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;

public class Suppliers extends javax.swing.JPanel {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    public String loggeduserid;
    public String loggedusername;
    public String loggedpriviledge;
    private int supplierid;
    Manage manage = new Manage();
    public Suppliers() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
    }
    private void loginmessageupdate(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            String Operation = "Update Supplier details for '"+txtSupplierName.getText()+"'";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Error @ loginmessageupdate");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void loginmessagedelete(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            String Operation = "Delete Supplier details for '"+txtSupplierName.getText()+"'";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Error @ loginmessagedelete");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void reset(){
        txtSupplierName.setText("");
        txtPhoneNo.setText("");
        txtSupplierName.requestFocus();
        buttonSave.setEnabled(true);
        buttonUpdate.setEnabled(false);
        buttonDelete.setEnabled(false);
        supplierid = 0;
        txtSupplierName.setEnabled(true);
        txtPhoneNo.setEnabled(true);
        update_table();
    }
    public void update_table(){
        String sql = "SELECT id AS 'id',suppliername AS 'Supplier',phoneNo AS 'Phone No' FROM supplierstable WHERE status = '1'";
        manage.update_table(tableSuppliers,sql);
    }
    private void selectedrow(){
        try{
            int row = tableSuppliers.getSelectedRow();
            String table_click = tableSuppliers.getValueAt(row, 0).toString();
            String sql = "SELECT * FROM supplierstable WHERE id = '"+table_click+"' AND status = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            if(rs.next()){
                supplierid = rs.getInt("id");
                    if(supplierid == 1){
                        txtSupplierName.setEnabled(false);
                        txtPhoneNo.setEnabled(false);
                    }else{
                        txtSupplierName.setEnabled(true);
                        txtPhoneNo.setEnabled(true);
                    }
                String Suppliername = rs.getString("suppliername");
                txtSupplierName.setText(Suppliername);
                
                String Phone = rs.getString("phoneNo");
                txtPhoneNo.setText(Phone);
            }
        }catch(Exception e){
            System.out.println(e+" suppliers.selectedrow");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void save(){
        try{
            String Name = txtSupplierName.getText();
            String Phone = txtPhoneNo.getText();
            String Status = "1";
            
            if(Name.equals("")){
                JOptionPane.showMessageDialog(null, "Enter Name");
                txtSupplierName.requestFocus();
            }else if(Phone.equals("")){
                JOptionPane.showMessageDialog(null, "Enter Phone");
                 txtPhoneNo.requestFocus();
            }else{
                String sql = "INSERT INTO supplierstable(suppliername,phoneno,status)VALUES(?,?,?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, Name);
                pst.setString(2, Phone);
                pst.setString(3, Status);
                
                pst.execute();
                JOptionPane.showMessageDialog(null, "Saved");
                reset();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error @ save");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        update_table();
    }
    private void update(){
        
            String Name = txtSupplierName.getText();
            String Phone = txtPhoneNo.getText();
            String sql = "UPDATE supplierstable SET suppliername = '"+Name+"',phoneNo = '"+Phone+"' WHERE id = '"+supplierid+"' AND status = '1'";
            manage.update(sql);
            update_table();
    }
    private void delete(){
        if(supplierid == 1){
            JOptionPane.showMessageDialog(null, ""+txtSupplierName.getText()+" cannot be Deleted...");
            reset();
        }else{
            String sql = "UPDATE supplierstable SET status = '0' WHERE status = '1' AND id = '"+supplierid+"'";
            manage.update(sql);
            loginmessagedelete();
            JOptionPane.showMessageDialog(null, "Supplier Deleted Successfully...");
            reset();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtSupplierName = new javax.swing.JTextField();
        txtPhoneNo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSuppliers = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        buttonUpdate = new javax.swing.JButton();
        buttonDelete = new javax.swing.JButton();
        buttonReset = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();

        setBackground(java.awt.Color.white);
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel1.setText("Suppliers");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel2.setText("Name/company:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel3.setText("Phone No:");

        txtSupplierName.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtSupplierName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSupplierNameKeyReleased(evt);
            }
        });

        txtPhoneNo.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        txtPhoneNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneNoKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneNoKeyReleased(evt);
            }
        });

        tableSuppliers.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tableSuppliers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableSuppliers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableSuppliersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableSuppliers);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        buttonUpdate.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonUpdate.setText("Update");
        buttonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUpdateActionPerformed(evt);
            }
        });

        buttonDelete.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonDelete.setText("Delete");
        buttonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDeleteActionPerformed(evt);
            }
        });

        buttonReset.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonReset.setText("Reset");
        buttonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonResetActionPerformed(evt);
            }
        });

        buttonSave.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSave.setText("Save");
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });

        buttonExit.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonExit.setText("Exit");
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(buttonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonReset)
                    .addComponent(buttonSave)
                    .addComponent(buttonUpdate)
                    .addComponent(buttonDelete)
                    .addComponent(buttonExit))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSupplierName, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                            .addComponent(txtPhoneNo))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtSupplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhoneNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void tableSuppliersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSuppliersMouseClicked
       selectedrow();
       buttonSave.setEnabled(false);
       buttonUpdate.setEnabled(true);
       buttonDelete.setEnabled(true);
    }//GEN-LAST:event_tableSuppliersMouseClicked

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
       
        save();
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetActionPerformed
        reset();
    }//GEN-LAST:event_buttonResetActionPerformed

    private void txtPhoneNoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneNoKeyReleased

    private void txtPhoneNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneNoKeyTyped
          char vchar = evt.getKeyChar();
      if((!(Character.isDigit(vchar)))
          ||(vchar == KeyEvent.VK_BACK_SPACE)
          
          || (vchar == KeyEvent.VK_DELETE)){
        evt.consume();
    }
    }//GEN-LAST:event_txtPhoneNoKeyTyped

    private void txtSupplierNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSupplierNameKeyReleased
        txtSupplierName.setText(txtSupplierName.getText().toUpperCase());
    }//GEN-LAST:event_txtSupplierNameKeyReleased

    private void buttonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUpdateActionPerformed
       update();
    }//GEN-LAST:event_buttonUpdateActionPerformed

    private void buttonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteActionPerformed
      
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION){
        }else if (response == JOptionPane.YES_OPTION){
            delete();
        }else if(response == JOptionPane.CLOSED_OPTION){ 
            
        }     
    }//GEN-LAST:event_buttonDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton buttonDelete;
    public javax.swing.JButton buttonExit;
    public javax.swing.JButton buttonReset;
    public javax.swing.JButton buttonSave;
    public javax.swing.JButton buttonUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableSuppliers;
    private javax.swing.JTextField txtPhoneNo;
    private javax.swing.JTextField txtSupplierName;
    // End of variables declaration//GEN-END:variables
}
