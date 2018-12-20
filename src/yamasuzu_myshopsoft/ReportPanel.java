
package yamasuzu_myshopsoft;

import java.util.Calendar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import net.proteanit.sql.DbUtils;
public class ReportPanel extends javax.swing.JPanel {
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    PreparedStatement pst1 = null;
    ResultSet rs1 = null;
    Calendar c = Calendar.getInstance();
    
    private double paidamount ;
    private double boughtamount;
    private int supplierid;
    private int clientid;
    
    public ReportPanel() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        c.add(Calendar.YEAR, 0);
        chooserFrom.getDateEditor().setDate(c.getTime());
        chooserTo.getDateEditor().setDate(c.getTime());
        comboSupplier.removeAllItems();
        comboSupplier.addItem("Select");
        fillcombosupplier();
    }

    public void update_tableSales_profits(){
        try{
            String From = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
            String To = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
            String sql = "SELECT salestable.productcode AS 'Product #',stocktable.type AS 'Product', stocktable.description AS 'Description'"
                    + ",COALESCE(SUM(salestable.qty), 0) AS 'Qty',COALESCE(SUM(salestable.profit), 0) AS 'Total Profit' FROM salestable,"
                    + "stocktable WHERE stocktable.productcode = salestable.productcode AND salestable.sold = 'yes' AND "
                    + "salestable.date BETWEEN '"+From+"' AND '"+To+"' GROUP BY salestable.productcode";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableSales_profits.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+(" reportpanel.update_tableSales_profits"));
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void fillcombosupplier(){
        try{
            String sql = "SELECT * FROM supplierstable WHERE status = '1' AND id > 1";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                comboSupplier.addItem(rs.getString("suppliername"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" fillcombosupplier");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void totalSales(){
        try{
            String From = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
            String To = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
            String sql = "SELECT COALESCE(SUM(profit),0),COALESCE(SUM(totalprice),0) FROM salestable WHERE "
                    + "sold = 'yes' AND date BETWEEN '"+From+"' AND '"+To+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next()){
                double totalprofit = (rs.getDouble("COALESCE(SUM(profit),0)"));
                double totalsellprice = (rs.getDouble("COALESCE(SUM(totalprice),0)"));
              
                txtTotalSales.setText(String.format("%.2f", totalsellprice ));
                txtProfits.setText(String.format("%.2f", totalprofit));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+(" reportpanel.totalSales"));
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    
     public void totalBalB_f_products(){
        try{
            String sql = "SELECT COALESCE(SUM(paidamount),0) FROM paymenttable";
            String sql1 = "SELECT COALESCE(SUM(total),0) FROM stockingtable WHERE status = '1' AND supplierid != '1'";
            pst = conn.prepareStatement(sql);
            pst1 = conn.prepareStatement(sql1);
            rs = pst.executeQuery();
            rs1 = pst1.executeQuery();
            
            if(rs.next()){
                paidamount = rs.getDouble("COALESCE(SUM(paidamount),0)");
                if(rs1.next()){
                 boughtamount= rs1.getDouble("COALESCE(SUM(total),0)");
                  }
                txtTotalBal_bf.setText(String.format("%.2f",(boughtamount) - paidamount));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" reportpanel.totalBalB_f_product");
        }finally{
            
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        
    }
     
    public void salesCash(){
         try{
             String From = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
             String To = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
             String sql = "SELECT COALESCE(SUM(totalprice),0) FROM salestable WHERE date BETWEEN '"+From+"' AND '"+To+"'AND"
                     + " means_of_payment = 'CASH'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             if(rs.next()){
                 txtCashSales.setText(String.format("%.2f", rs.getDouble("COALESCE(SUM(totalprice),0)")));
             }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e+" Error @ salesCash");
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
    public void salesTill(){
         try{
             String From = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
             String To = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
             String sql = "SELECT COALESCE(SUM(totalprice),0) FROM salestable WHERE date BETWEEN '"+From+"' AND '"+To+"'AND"
                     + " means_of_payment = 'TILL'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             if(rs.next()){
                 txtTillSales.setText(String.format("%.2f", rs.getDouble("COALESCE(SUM(totalprice),0)")));
             }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e+" Error @ salesTill");
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
    public void salesCheque(){
         try{
             String From = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
             String To = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
             String sql = "SELECT COALESCE(SUM(totalprice),0) FROM salestable WHERE date BETWEEN '"+From+"' AND '"+To+"'AND"
                     + " means_of_payment = 'CHEQUE'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
             if(rs.next()){
                 txtChequeSales.setText(String.format("%.2f", rs.getDouble("COALESCE(SUM(totalprice),0)")));
             }
         }catch(Exception e){
             JOptionPane.showMessageDialog(null, e+" Error @ salesCheque");
         }finally{
             try{
                 rs.close();
                 pst.close();
             }catch(Exception e){
                 
             }
         }
     }
    private void selectedrowsupplier(){
        try{
            int row = tableSuppliers.getSelectedRow();
            String table_click = tableSuppliers.getValueAt(row, 0).toString();
        }catch(Exception e){
            System.out.println(e+" selectedrowsupplier");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_tablesupplierpayment(){
        try{
            String from = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
            String to = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
            
            String sql = "SELECT datepay AS 'Payment Date',details AS 'Payment#',paidamount AS 'Paidamount',paymentmethod AS 'Pay Method',transactionid AS "
                    + "'Transaction #' FROM paymenttable WHERE status = '1' AND datepay BETWEEN  '"+from+"' AND '"+to+"' AND supplierid = '"+supplierid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableSuppliers.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" update_tablesupplierpayment");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void supplierpaymentsreport(){
        String from = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
        String to = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
        Manage manage = new Manage();
        String sql = "SELECT companytable.companyname,companytable.location,companytable.address,companytable.city,"
                    + "companytable.phoneno,companytable.email,companytable.dealerin,supplierstable.suppliername,paymenttable."
                    + "datepay,paymenttable.details,paymenttable.paidamount,paymenttable.totalamount,paymenttable.paymentmethod,"
                    + "paymenttable.transactionid FROM companytable,supplierstable,paymenttable WHERE paymenttable.supplierid = "
                    + "supplierstable.id AND supplierstable.status = '1' AND paymenttable.status = '1' AND paymenttable.supplierid"
                    + " = '"+supplierid+"' AND paymenttable.datepay BETWEEN '"+from+"' AND '"+to+"'";
        String path = "Reports/paymentreport.jrxml";
        manage.report(sql, path, panelReport);
        jTabbedPane2.setSelectedIndex(3);
    }
    private void salesreport(){
        Manage manage = new Manage();
        String from = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
        String to = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
        String sql = "SELECT companytable.companyname,companytable.location,companytable.address,companytable.city,"
                    + "companytable.phoneno,companytable.email,companytable.dealerin,salestable.date,salestable.details,salestable."
                    + "productcode,stocktable.type,stocktable.description,salestable.imei,salestable.qty,salestable.unitprice,"
                    + "salestable.totalprice,salestable.profit,salestable.means_of_payment,salestable.transactionid,usertable.name "
                    + "FROM companytable,usertable,stocktable,salestable WHERE companytable.companyid = usertable.companyid AND salestable."
                    + "userid = usertable.id AND salestable.productcode = stocktable.productcode AND usertable.status = '1' AND salestable."
                    + "status = '1'AND salestable.date BETWEEN '"+from+"' AND '"+to+"'";
        String path = "Reports/salesreport.jrxml";
        manage.report(sql, path, panelReport);
        jTabbedPane2.setSelectedIndex(3);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        chooserFrom = new com.toedter.calendar.JDateChooser();
        chooserTo = new com.toedter.calendar.JDateChooser();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtTillSales = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtCashSales = new javax.swing.JLabel();
        txtChequeSales = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotalSales = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtProfits = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        txtTotalBal_bf = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableAccessoriesStock = new javax.swing.JTable();
        printProduct = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableRemaining = new javax.swing.JTable();
        printremaining = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        comboSupplier = new javax.swing.JComboBox();
        buttonPrintReport1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableSuppliers = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSales_profits = new javax.swing.JTable();
        buttonSales_profits = new javax.swing.JButton();
        panelReport = new javax.swing.JPanel();

        setBackground(java.awt.Color.white);
        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.orange));

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        jLabel11.setText("Reports");

        jLabel1.setText("From:");

        jLabel2.setText("To:");

        chooserFrom.setDateFormatString("yyyy-MM-dd");
        chooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chooserFromPropertyChange(evt);
            }
        });

        chooserTo.setDateFormatString("yyyy-MM-dd");
        chooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                chooserToPropertyChange(evt);
            }
        });

        jPanel3.setBackground(java.awt.Color.white);

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        txtTillSales.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtTillSales.setText("  ");
        txtTillSales.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Cash :");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setText("Cheque:");

        txtCashSales.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtCashSales.setText("  ");
        txtCashSales.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        txtChequeSales.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtChequeSales.setText("  ");
        txtChequeSales.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel5.setText("Mpesa:");

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel8.setForeground(java.awt.Color.red);
        jLabel8.setText("Total Sales:");

        txtTotalSales.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtTotalSales.setText("  ");
        txtTotalSales.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel10.setForeground(java.awt.Color.red);
        jLabel10.setText("Total Profits:");

        txtProfits.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtProfits.setForeground(java.awt.Color.red);
        txtProfits.setText("  ");
        txtProfits.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jTabbedPane1.setBackground(java.awt.Color.white);
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        txtTotalBal_bf.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtTotalBal_bf.setText("  ");
        txtTotalBal_bf.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setText("Debtors");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalBal_bf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCashSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotalSales, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(txtTillSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(4, 4, 4)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtChequeSales, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(txtProfits, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTillSales, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtChequeSales, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCashSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtTotalBal_bf, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtProfits, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtTotalSales, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.setBackground(java.awt.Color.white);
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        jTabbedPane2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jPanel6.setBackground(java.awt.Color.white);
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        jTabbedPane3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        jTabbedPane3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        jPanel8.setBackground(java.awt.Color.white);
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        tableAccessoriesStock.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tableAccessoriesStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tableAccessoriesStock);

        printProduct.setText("Print");
        printProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printProductActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(printProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(printProduct)
                .addContainerGap())
        );

        jTabbedPane3.addTab("Products", jPanel8);

        jTabbedPane5.addTab("Stocking Records", jTabbedPane3);

        jPanel4.setBackground(java.awt.Color.white);
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        tableRemaining.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tableRemaining.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tableRemaining);

        printremaining.setText("Print");
        printremaining.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printremainingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(printremaining, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(printremaining))
        );

        jTabbedPane5.addTab("Remaining Stock", jPanel4);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Stock", jPanel6);

        jPanel1.setBackground(java.awt.Color.white);
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));

        comboSupplier.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        comboSupplier.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboSupplierPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        buttonPrintReport1.setText("Print Payments Report");
        buttonPrintReport1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPrintReport1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("Supplier:");

        tableSuppliers.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        jScrollPane3.setViewportView(tableSuppliers);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(231, 231, 231)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(324, 324, 324))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(buttonPrintReport1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonPrintReport1)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Suppliers", jPanel1);

        jPanel9.setBackground(java.awt.Color.white);
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray));

        tableSales_profits.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tableSales_profits.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableSales_profits);

        buttonSales_profits.setText("Print");
        buttonSales_profits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSales_profitsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(buttonSales_profits, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSales_profits)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Sales & Profits", jPanel9);

        javax.swing.GroupLayout panelReportLayout = new javax.swing.GroupLayout(panelReport);
        panelReport.setLayout(panelReportLayout);
        panelReportLayout.setHorizontalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 995, Short.MAX_VALUE)
        );
        panelReportLayout.setVerticalGroup(
            panelReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 402, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("Loading Report...", panelReport);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel2)
                        .addGap(2, 2, 2)
                        .addComponent(chooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(chooserFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void chooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chooserFromPropertyChange
       update_tableSales_profits();
       totalSales();
       totalBalB_f_products();
       salesCash();
       salesTill();
       salesCheque();
       comboSupplier.removeAllItems();
       comboSupplier.addItem("Select");
       fillcombosupplier();
       update_tablesupplierpayment();
       update_tablestock();
       remainingstock();
    }//GEN-LAST:event_chooserFromPropertyChange

    private void chooserToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_chooserToPropertyChange
        update_tableSales_profits();
        totalSales();
        totalBalB_f_products();
        salesCash();
        salesTill();
        salesCheque();
        comboSupplier.removeAllItems();
        comboSupplier.addItem("Select");
        fillcombosupplier();
        update_tablesupplierpayment();
        update_tablestock();
        remainingstock();
       // update_tableclients_statement();
    }//GEN-LAST:event_chooserToPropertyChange

    private void buttonPrintReport1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPrintReport1ActionPerformed
        
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
           @Override
           protected Void doInBackground() throws Exception {
                supplierpaymentsreport();
                return null;
        }
           
       };
       worker.execute();   
       
    }//GEN-LAST:event_buttonPrintReport1ActionPerformed

    private void comboSupplierPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboSupplierPopupMenuWillBecomeInvisible
        try{
            String supplier = (String)comboSupplier.getSelectedItem();
            String sql = "SELECT * FROM supplierstable WHERE status = '1' AND suppliername = ?";
            pst = conn.prepareStatement(sql);

            pst.setString(1, supplier);
            rs = pst.executeQuery();
            if(rs.next()){
                supplierid = rs.getInt("id");
                update_tablesupplierpayment();
            }else{
                supplierid = 0;
                update_tablesupplierpayment();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" reportpanel.comboSupplierPopupMenuWillBecomeInvisible");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){

            }
        }
    }//GEN-LAST:event_comboSupplierPopupMenuWillBecomeInvisible

    private void buttonSales_profitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSales_profitsActionPerformed
       
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
           @Override
           protected Void doInBackground() throws Exception {
                salesreport();
                return null;
        }
       };
       worker.execute(); 
    }//GEN-LAST:event_buttonSales_profitsActionPerformed

    
    private void stockingreport(){
                String from = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
                String to_ = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
                Manage manage = new Manage();
                String sql = "SELECT companytable.companyname,companytable.location,companytable.address,companytable.city,"
                    + "companytable.phoneno,companytable.email,companytable.dealerin,stockingtable.invoicedate,"
                    + "supplierstable.suppliername,stockingtable.invoiceno,stockingtable.productcode,CONCAT(stocktable.type,'-',"
                    + "stocktable.description,'-',stockingtable.imei),stockingtable.unitprice,stockingtable.qty,stockingtable.total,"
                    + "usertable.name FROM companytable,usertable,stockingtable,stocktable,supplierstable WHERE "
                    + "companytable.companyid = usertable.companyid AND stockingtable.userid = usertable.id AND stockingtable."
                    + "productcode = stocktable.productcode AND supplierstable.id = stockingtable.supplierid AND stockingtable.status "
                    + "= '1' AND stockingtable.invoicedate BETWEEN '"+from+"' AND '"+to_+"'";
                String path = "Reports/stockingreport.jrxml";
                manage.report(sql, path, panelReport);
                jTabbedPane2.setSelectedIndex(3);
    }
    
    private void printProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printProductActionPerformed
       
          SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
           @Override
           protected Void doInBackground() throws Exception {
                stockingreport();
               return null;
           }
           
       };
       worker.execute();                    
    }//GEN-LAST:event_printProductActionPerformed

    private void printremainingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printremainingActionPerformed
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
           @Override
           protected Void doInBackground() throws Exception {
                remainingreport();
                return null;
        }
           
       };
       worker.execute();    
    }//GEN-LAST:event_printremainingActionPerformed
    private void remainingreport(){
        Manage manage = new Manage();
        String sql = "SELECT companytable.companyname,companytable.location,companytable.address,companytable.city,"
                    + "companytable.phoneno,companytable.email,companytable.dealerin,stocktable.productcode,stocktable.type,"
                    + "stocktable.description,stocktable.qty,stocktable.currentunitprice,stocktable.lowestsellingprice,"
                    + "stocktable.highestsellingprice,stocktable.qty * stocktable.currentunitprice,stocktable.qty * "
                    + "stocktable.lowestsellingprice FROM companytable,stocktable";
        String path = "Reports/remainingproducts.jrxml";
        manage.report(sql, path, panelReport);
        jTabbedPane2.setSelectedIndex(3);
    }
    public void remainingstock(){
        try{
            String sql = "SELECT productcode AS 'P-CODE',type AS 'Product',description AS 'Description',currentunitprice AS 'Current B.P'"
                    + " ,qty AS 'Remaining Qty' FROM stocktable WHERE status = '1' ";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableRemaining.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" remainingstock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }

    public void update_tablestock(){
        try{
            String from = ((JTextField)chooserFrom.getDateEditor().getUiComponent()).getText();
            String to = ((JTextField)chooserTo.getDateEditor().getUiComponent()).getText();
            String sql = "SELECT stockingtable.invoiceno AS 'Suppliers Invoice#',stockingtable.productcode AS 'P-CODE',stocktable.type"
                    + " AS 'Product',stocktable.description AS 'Description',stockingtable.imei AS 'S/N or IMEI',stockingtable.qty AS "
                    + "'Qty',stockingtable.invoicedate AS 'Date' FROM stockingtable,stocktable WHERE stocktable.productcode = stockingtable."
                    + "productcode AND stockingtable.status = '1' AND stockingtable.invoicedate BETWEEN '"+from+"' AND '"+to+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            tableAccessoriesStock.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" update_tablestock");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPrintReport1;
    private javax.swing.JButton buttonSales_profits;
    private com.toedter.calendar.JDateChooser chooserFrom;
    private com.toedter.calendar.JDateChooser chooserTo;
    private javax.swing.JComboBox comboSupplier;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    public javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JPanel panelReport;
    private javax.swing.JButton printProduct;
    private javax.swing.JButton printremaining;
    private javax.swing.JTable tableAccessoriesStock;
    private javax.swing.JTable tableRemaining;
    private javax.swing.JTable tableSales_profits;
    private javax.swing.JTable tableSuppliers;
    private javax.swing.JLabel txtCashSales;
    private javax.swing.JLabel txtChequeSales;
    private javax.swing.JLabel txtProfits;
    private javax.swing.JLabel txtTillSales;
    private javax.swing.JLabel txtTotalBal_bf;
    private javax.swing.JLabel txtTotalSales;
    // End of variables declaration//GEN-END:variables
}
