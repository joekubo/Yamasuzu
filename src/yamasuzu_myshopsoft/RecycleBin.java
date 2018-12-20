
package yamasuzu_myshopsoft;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
public class RecycleBin extends javax.swing.JPanel {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private int userid;
    private int supplierid;
    private int paymentid;
    private int clientid;
    private int stockingid;
    private int categoryid;
    
    public RecycleBin() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
    }
    public void update_users(){
        try{
            String sql = "SELECT * FROM usertable WHERE status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableUsers.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"recyclebin.update_users");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_suppliers(){
         try{
            String sql = "SELECT * FROM supplierstable WHERE status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableSuppliers.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"recyclebin.update_suppliers");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_payments(){
         try{
            String sql = "SELECT * FROM paymenttable WHERE status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tablePayments.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.update_payments");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_client(){
         try{
            String sql = "SELECT * FROM clientstable WHERE status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableClients.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.update_client");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_stock(){
         try{
            String sql = "SELECT * FROM stockingtable WHERE status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableStocking.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.update_stock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_category(){
         try{
            String sql = "SELECT * FROM categorytable WHERE status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableCategories.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.update_category");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowusers(){
        try{
            int row = tableUsers.getSelectedRow();
            String table_click = tableUsers.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM usertable WHERE id = '"+table_click+"' AND status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    userid = rs.getInt("id");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.selectedrowusers");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowsuppliers(){
        try{
            int row = tableSuppliers.getSelectedRow();
            String table_click = tableSuppliers.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM supplierstable WHERE id = '"+table_click+"' AND status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    supplierid = rs.getInt("id");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.selectedrowsuppliers");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowpayments(){
        try{
            int row = tablePayments.getSelectedRow();
            String table_click = tablePayments.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM paymenttable WHERE paymentid = '"+table_click+"' AND status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    paymentid = rs.getInt("paymentid");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.selectedrowpayments");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowclients(){
        try{
            int row = tableClients.getSelectedRow();
            String table_click = tableClients.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM clientstable WHERE clientid = '"+table_click+"' AND status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    clientid = rs.getInt("clientid");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.selectedrowclients");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowstock(){
        try{
            int row = tableStocking.getSelectedRow();
            String table_click = tableStocking.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM stockingtable WHERE stockingid = '"+table_click+"' AND status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    stockingid = rs.getInt("stockingid");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.selectedrowstock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void selectedrowcategories(){
        try{
            int row = tableCategories.getSelectedRow();
            String table_click = tableCategories.getValueAt(row, 0).toString();
            
            String sql = "SELECT * FROM categorytable WHERE categoryid = '"+table_click+"' AND status = '0'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    categoryid = rs.getInt("categoryid");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.selectedrowcategories");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void restoreusers(){
        try{
            String sql = "UPDATE usertable SET status = '1'  WHERE status = '0' AND id = '"+userid+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Restored");
            update_users();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.restoreusers");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void restoresuppliers(){
        try{
            String sql = "UPDATE supplierstable SET status = '1' WHERE status = '0' AND id = '"+supplierid+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Restored");
            update_suppliers();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"recyclebin.restoresuppliers ");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void restorepayments(){
        try{
            String sql = "UPDATE paymenttable SET status = '1' WHERE status = '0' AND paymentid = '"+paymentid+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Restored");
            update_payments();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.restorepayments");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void restoreclients(){
        try{
            String sql = "UPDATE clientstable SET status = '1' WHERE status = '0' AND clientid = '"+clientid+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Restored");
            update_client();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.restoreclients");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void restorestock(){
        try{
            String sql = "UPDATE stockingtable SET status = '1' WHERE status = '0'  AND stockingid = '"+stockingid+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Restored");
            update_stock();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.restorestock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void restorecategories(){
        try{
            String sql = "UPDATE categorytable SET status = '1'WHERE status = '0'  AND categoryid = '"+categoryid+"'";
            pst = conn.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Restored");
            update_category();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" recyclebin.restorecategory");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel26 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        buttonRestoreUsers = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSuppliers = new javax.swing.JTable();
        buttonRestoreSuppliers = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePayments = new javax.swing.JTable();
        buttonRestorePayments = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableClients = new javax.swing.JTable();
        buttonRestoreClients = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableStocking = new javax.swing.JTable();
        buttonRestoreStocking = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableCategories = new javax.swing.JTable();
        buttonRestoreCategories = new javax.swing.JButton();

        setBackground(java.awt.Color.white);

        jLabel26.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel26.setText("Recycle Bin");

        jTabbedPane1.setBackground(java.awt.Color.white);
        jTabbedPane1.setBorder(new javax.swing.border.LineBorder(java.awt.Color.darkGray, 1, true));

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableUsers.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUsersMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableUsers);

        buttonRestoreUsers.setText("Restore");
        buttonRestoreUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestoreUsersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonRestoreUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRestoreUsers)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Users", jPanel1);

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

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
        jScrollPane2.setViewportView(tableSuppliers);

        buttonRestoreSuppliers.setText("Restore");
        buttonRestoreSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestoreSuppliersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonRestoreSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRestoreSuppliers)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Suppliers", jPanel2);

        jPanel3.setBackground(java.awt.Color.white);
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        tablePayments.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablePayments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePaymentsMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablePayments);

        buttonRestorePayments.setText("Restore");
        buttonRestorePayments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestorePaymentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonRestorePayments, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRestorePayments)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Supplier Payments", jPanel3);

        jPanel4.setBackground(java.awt.Color.white);
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        tableClients.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableClients.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableClientsMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableClients);

        buttonRestoreClients.setText("Restore");
        buttonRestoreClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestoreClientsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonRestoreClients, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRestoreClients)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Clients", jPanel4);

        jPanel5.setBackground(java.awt.Color.white);
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        tableStocking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableStocking.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableStockingMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tableStocking);

        buttonRestoreStocking.setText("Restore");
        buttonRestoreStocking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestoreStockingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonRestoreStocking, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRestoreStocking)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Stock", jPanel5);

        jPanel6.setBackground(java.awt.Color.white);
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        tableCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tableCategories.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCategoriesMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tableCategories);

        buttonRestoreCategories.setText("Restore");
        buttonRestoreCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRestoreCategoriesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 883, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonRestoreCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRestoreCategories)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Categories", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel26)
                .addGap(26, 26, 26)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonRestoreUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestoreUsersActionPerformed
       restoreusers();
    }//GEN-LAST:event_buttonRestoreUsersActionPerformed

    private void buttonRestoreSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestoreSuppliersActionPerformed
        restoresuppliers();
    }//GEN-LAST:event_buttonRestoreSuppliersActionPerformed

    private void buttonRestorePaymentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestorePaymentsActionPerformed
        restorepayments();
    }//GEN-LAST:event_buttonRestorePaymentsActionPerformed

    private void buttonRestoreClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestoreClientsActionPerformed
        restoreclients();
    }//GEN-LAST:event_buttonRestoreClientsActionPerformed

    private void buttonRestoreStockingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestoreStockingActionPerformed
        restorestock();
    }//GEN-LAST:event_buttonRestoreStockingActionPerformed

    private void buttonRestoreCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRestoreCategoriesActionPerformed
        restorecategories();
    }//GEN-LAST:event_buttonRestoreCategoriesActionPerformed

    private void tableUsersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUsersMouseClicked
        selectedrowusers();
    }//GEN-LAST:event_tableUsersMouseClicked

    private void tableSuppliersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableSuppliersMouseClicked
        selectedrowsuppliers();
    }//GEN-LAST:event_tableSuppliersMouseClicked

    private void tablePaymentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePaymentsMouseClicked
        selectedrowpayments();
    }//GEN-LAST:event_tablePaymentsMouseClicked

    private void tableClientsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableClientsMouseClicked
        selectedrowclients();
    }//GEN-LAST:event_tableClientsMouseClicked

    private void tableStockingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableStockingMouseClicked
        selectedrowstock();
    }//GEN-LAST:event_tableStockingMouseClicked

    private void tableCategoriesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCategoriesMouseClicked
        selectedrowcategories();
    }//GEN-LAST:event_tableCategoriesMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonRestoreCategories;
    private javax.swing.JButton buttonRestoreClients;
    private javax.swing.JButton buttonRestorePayments;
    private javax.swing.JButton buttonRestoreStocking;
    private javax.swing.JButton buttonRestoreSuppliers;
    private javax.swing.JButton buttonRestoreUsers;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tableCategories;
    private javax.swing.JTable tableClients;
    private javax.swing.JTable tablePayments;
    private javax.swing.JTable tableStocking;
    private javax.swing.JTable tableSuppliers;
    private javax.swing.JTable tableUsers;
    // End of variables declaration//GEN-END:variables
}
