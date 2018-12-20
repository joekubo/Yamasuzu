
package yamasuzu_myshopsoft;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import org.mindrot.jbcrypt.BCrypt;
public class Yamasuzu_Myshopsoft extends javax.swing.JFrame {
    Connection conn = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    
    ResultSet rs = null;
    ResultSet rs1 = null;
    Toolkit tk = Toolkit.getDefaultToolkit();
    public String loggeduserid;
    public String loggedusername;
    public String loggedpriviledge;
    public String companyid;
    public String companyname;
    public String company_phoneno;
    public String email_address;
    
    
    Manage manage = new Manage();
    GeneratedCode generated = new GeneratedCode();
    public Yamasuzu_Myshopsoft() {
        initComponents();
        conn = Javaconnect.ConnecrDb();
        loadCompanyname();
        this.setTitle("MyshopSOFT v-1.0.1 --"+companyname+"--");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(this.getSize());
        changeuser.setEnabled(false);
        alwayscheckuser();
        buttonYes.setVisible(false);
        buttonNo.setVisible(false);
        buttonactivate_app.setVisible(false);
    }
    private void loadCompanyname(){
        try{
            String sql = "SELECT companyname,phoneno FROM companytable WHERE companyid = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    companyname = rs.getString("companyname");
                    company_phoneno = rs.getString("phoneno");
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" Mainswitch.loadCompnanyname");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void showLoginform(){
      LoginForm.setVisible(true);
      LoginForm.setSize(554, 237);
      LoginForm.setResizable(false);
      LoginForm.setAlwaysOnTop(true);
      LoginForm.setLocationRelativeTo(this);
      LoginForm.setTitle("User Authentication ***");
      reset();
    }
    private void loginmessage(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String Operation = "Successfully logged in";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            String sql1 = "UPDATE usertable SET logged = ? WHERE id = '"+loggeduserid+"'";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst1 = conn.prepareStatement(sql1);
            
            pst1.setString(1, "1");
            
            pst.execute();
            pst1.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" mainswitch.loginmessage");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void logoutmessage(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            
            String Operation = "Successfully logged out";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            String sql1 = "UPDATE usertable SET logged = ? WHERE id = '"+loggeduserid+"'";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst1 = conn.prepareStatement(sql1);
            
            pst1.setString(1, "0");
            
            pst.execute();
            pst1.execute();
        }catch(Exception e){
            //JOptionPane.showMessageDialog(null, e+" Error @ logoutmessage");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void checkuser(){
        try{
            String sql = "SELECT * FROM usertable WHERE id = '"+loggeduserid+"'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    if(rs.getInt("status") == 0){
                        desktop.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Your have been disabled by the Administrator. Soorry!!!");
                        loginmessage_disabledbyadmin();
                        System.exit(0);
                    }
                }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" mainswitch.checkuser");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void loginmessage_disabledbyadmin(){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String Operation = "User '"+loggedusername+"' has been disabled from the system";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggeduserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, Operation);
            
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+" mainswitch.loginmessage");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void alwayscheckuser(){
         Timer t = new Timer(10000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                checkuser();
            }
        });
        t.start();
     }
     private void login(){
        try{
            String Username = txtUsername.getText();
            String Password = txtPassword.getText();
            MainInternal internalframe = new MainInternal();
            
            
            String sql = "SELECT * FROM usertable WHERE username = ? AND status = '1'";
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, Username);
            
            rs = pst.executeQuery();
            if(rs.next()){
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String today = (dateFormat.format(date));
                String hashed = rs.getString("password");
                if(BCrypt.checkpw(Password, hashed)){
                    String loggedin = rs.getString("id");
                    String loggedname = rs.getString("name");
                    companyid = rs.getString("companyid");
                    txtloggedname.setText(loggedname+" is logged in...");
                    loggeduserid = loggedin;
                    loggedusername = loggedname;
                    loggedpriviledge = rs.getString("usertype");
                    
                    manage.updatedate("UPDATE renewtable SET today_date = '"+today+"' WHERE id = '1'");//update date today
                    
//                    LoginForm.dispose();
//                    desktop.add(internalframe);
//                    desktop.setVisible(true);
//                    internalframe.setVisible(true);
//                    internalframe.setSize(desktop.getSize());
//                    internalframe.setLocation(0, 0);
                    
                    desktop.add(internalframe);
                    internalframe.setSize(desktop.getWidth(), desktop.getHeight());
                    internalframe.setVisible(true);
                    LoginForm.dispose();
                    loginmessage();
//                 ________________________________________________________________________---checking on date for re-activation---
                    
                    compare_date();
//                 ________________________________________________________________________---end of checking on date for re-activation---

                        
                    internalframe.manage.loggedid_number = loggeduserid;
                    internalframe.manage.loggedusername = loggedusername;
                    internalframe.manage.loggedinuserid = loggeduserid;
                    internalframe.users.loggeduserid = loggeduserid;
                    internalframe.users.loggedusername = loggedusername;
                    internalframe.users.loggedpriviledge = loggedpriviledge;
                    internalframe.users.companyid = companyid;
                    internalframe.suppliers.loggeduserid = loggeduserid;
                    internalframe.suppliers.loggedusername = loggedusername;
                    internalframe.suppliers.loggedpriviledge = loggedpriviledge;
                    internalframe.stock.loggeduserid = loggeduserid;
                    internalframe.stock.loggedusername = loggedusername;
                    internalframe.stock.loggedpriviledge = loggedpriviledge;
                    internalframe.viewsales.loggeduserid = loggeduserid;
                    internalframe.viewsales.loggedusername = loggedusername;
                    internalframe.viewsales.companyid = companyid;
                    internalframe.viewsales.loggedpriviledge = loggedpriviledge;
                    internalframe.sales.loggedinuserid = loggeduserid;
                    internalframe.sales.loggedpriviledge = loggedpriviledge;
                    internalframe.sales.companyid = companyid;
                    internalframe.shop.loggeduserid = loggeduserid;
                    internalframe.shop.loggedusername = loggedusername;
                    internalframe.client.loggeduserid = loggeduserid;
                    internalframe.client.loggedusername = loggedusername;
                    internalframe.client.loggedpriviledge = loggedpriviledge;
                    internalframe.quotation.loggeduserid = loggeduserid;
                    internalframe.invoice.loggeduserid = loggeduserid;
                    internalframe.loggedpriviledge = loggedpriviledge;
                    internalframe.allbuttonsdefaultcolor();

                        if(loggedpriviledge.equals("Manager")){
                            internalframe.buttonUsers.setVisible(false);
                            //internalframe.buttonClients.setVisible(false);
                            internalframe.buttonLogs.setVisible(false);
                            internalframe.buttonRecyclebin.setVisible(false);
                            internalframe.suppliers.buttonDelete.setVisible(false);
                            internalframe.stock.buttonRemove.setVisible(true);
                            internalframe.client.buttonDelete.setVisible(false);
                            internalframe.viewsales.buttonDelete.setVisible(false);
                            internalframe.stock.buttonDeleteCategory.setVisible(false);
                            internalframe.buttonShop.setVisible(false);
                        }else if(loggedpriviledge.equals("Admin")){
                            internalframe.buttonRecyclebin.setVisible(false);
                            //internalframe.buttonClients.setVisible(false);
                            
                        }else if(loggedpriviledge.equals("User")){
                            internalframe.buttonUsers.setVisible(false);
                            internalframe.buttonLogs.setVisible(false);
                            internalframe.buttonClients.setVisible(false);
                            internalframe.buttonRecyclebin.setVisible(false);
                            internalframe.buttonShop.setVisible(false);
                            internalframe.buttonSuppliers.setVisible(false);
                            internalframe.buttonStocking.setVisible(false);
                            internalframe.buttonReports.setVisible(false);
                            internalframe.stock.buttonRemove.setVisible(false);
                            //internalframe.client.buttonDelete.setVisible(false);
                            internalframe.viewsales.buttonDelete.setVisible(false);
                            internalframe.stock.buttonDeleteCategory.setVisible(false);
                        }
                            
                    }
                }else{
                    
                    lbl1.setText("Wrong Username or Password...");
                    txtUsername.setText("");
                    txtPassword.setText("");
                    txtUsername.requestFocus();
                }
        }catch(Exception e){
            System.out.println(e+" (Mainswitch.login)");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    private void reset(){
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocus();
    }
//    _____________________________renew code_____________________________________________
    
    public void showrenewdialog(){
        DialogRenew.setVisible(true);
        DialogRenew.setSize(669, 336);
        DialogRenew.setTitle("Pay to renew subscription...");
        DialogRenew.setAlwaysOnTop(true);
        DialogRenew.setLocationRelativeTo(this);
        DialogRenew.setResizable(false);
        txtCode.setText("");
        txtCode.requestFocus();
        
        jLabel6.setVisible(false);
        txtCode.setVisible(false);
        buttonActivate.setVisible(false);
        buttonSendCode.setEnabled(false);
    }
//    _________________________________________________________end renew code_______________
    
    //            ___________________generate time for renew_______________________
     public void renewingtime(){//activate immediately the code has accepted.
            String amount = comboAmount.getSelectedItem().toString();
            amount = amount.trim();
            int renewal_amount = Integer.parseInt(amount);
            int days = renewal_amount/50 ;
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, days);
            String output = sdf.format(c.getTime());
            manage.updatedate("UPDATE renewtable SET final_date = '"+output+"' WHERE id = '1'");
    } 
     private void compare_date(){
         
             try{
             String sql = "SELECT final_date,today_date FROM renewtable WHERE id = '1'";
             pst = conn.prepareStatement(sql);
             rs = pst.executeQuery();
                if(rs.next()){
                    Date final_date = rs.getDate("final_date");
                    Date today_date = rs.getDate("today_date");
                    
                    if(today_date.after(final_date)){
                        this.setEnabled(false);
                        showrenewdialog();
                    }else if(today_date.equals(final_date)){
                        buttonactivate_app.setVisible(true);
                        String path = "nazi.wav";
                        manage.music(path);
                    }else{
                    }
                   
                }
         }catch(Exception e){
             System.out.println(e+" mainswitch.compare_date");
         }finally{
             try{
                rs.close();
                pst.close();
             }catch(Exception e){
                 
             }
         }
     }
     public void nowdate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
//        System.out.println(dateFormat.format(date));
        JOptionPane.showMessageDialog(null,(dateFormat.format(date)));
     }
     
    
//            ___________________________end renew time generation___________________
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoginForm = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        txtUsername = new javax.swing.JTextField();
        buttonLogin = new javax.swing.JButton();
        buttonExit = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        DialogRenew = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        buttonActivate = new javax.swing.JButton();
        txtCode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        buttonSendCode = new javax.swing.JButton();
        comboAmount = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lbl_exception = new javax.swing.JLabel();
        desktop = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        buttonactivate_app = new javax.swing.JButton();
        buttonNo = new javax.swing.JButton();
        changeuser = new javax.swing.JButton();
        buttonYes = new javax.swing.JButton();
        txtloggedname = new javax.swing.JLabel();
        logoutbutton = new javax.swing.JButton();
        lbl1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        LoginForm.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        LoginForm.setIconImage(tk.getImage(getClass().getResource("icons/inventory_icon.png")));
        LoginForm.setUndecorated(true);
        LoginForm.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                LoginFormWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 248, 199));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.blue));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Password:");

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Username:");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPasswordKeyPressed(evt);
            }
        });

        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
        });

        buttonLogin.setText("Login");
        buttonLogin.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoginActionPerformed(evt);
            }
        });
        buttonLogin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                buttonLoginKeyPressed(evt);
            }
        });

        buttonExit.setText("Exit");
        buttonExit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        buttonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(buttonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsername)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonExit, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/myshopsoft.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout LoginFormLayout = new javax.swing.GroupLayout(LoginForm.getContentPane());
        LoginForm.getContentPane().setLayout(LoginFormLayout);
        LoginFormLayout.setHorizontalGroup(
            LoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        LoginFormLayout.setVerticalGroup(
            LoginFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        DialogRenew.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DialogRenew.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                DialogRenewWindowClosing(evt);
            }
        });

        jPanel4.setBackground(java.awt.Color.white);
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        jLabel6.setText("Enter Code Here:");

        buttonActivate.setText("Activate");
        buttonActivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActivateActionPerformed(evt);
            }
        });

        txtCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodeKeyReleased(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        jLabel8.setForeground(java.awt.Color.blue);
        jLabel8.setText("Enter Amount and click 'Send Code' button for code to be sent to you after verification.");

        jLabel9.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        jLabel9.setForeground(java.awt.Color.blue);
        jLabel9.setText("Ensure there is internet connection. This will not take a lot of time...");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 204)));

        jLabel7.setText("Enter Amount:");

        buttonSendCode.setText("Send Code");
        buttonSendCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSendCodeActionPerformed(evt);
            }
        });

        comboAmount.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Amount", "1500", "3000", "4500", "6000", "7500", "9000", "10500", "12000", "13500", "15000", "16500", "18000" }));
        comboAmount.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                comboAmountPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel10.setText("Enter Phone #:");

        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPhoneKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPhone))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonSendCode, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(buttonSendCode)
                    .addComponent(comboAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 0));
        jLabel11.setText("NOTE: Please use the same phone# you used to do the payment");

        lbl_exception.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        lbl_exception.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(lbl_exception, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonActivate, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtCode))
                                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(buttonActivate)
                        .addGap(3, 3, 3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_exception, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout DialogRenewLayout = new javax.swing.GroupLayout(DialogRenew.getContentPane());
        DialogRenew.getContentPane().setLayout(DialogRenewLayout);
        DialogRenewLayout.setHorizontalGroup(
            DialogRenewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        DialogRenewLayout.setVerticalGroup(
            DialogRenewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconImage(tk.getImage(getClass().getResource("icons/inventory_icon.png")));
        setPreferredSize(new java.awt.Dimension(1366, 768));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        desktop.setBackground(new java.awt.Color(51, 51, 255));
        desktop.setPreferredSize(new java.awt.Dimension(1366, 725));

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1369, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 747, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        buttonactivate_app.setText("Activate");
        buttonactivate_app.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonactivate_appActionPerformed(evt);
            }
        });

        buttonNo.setBackground(java.awt.Color.green);
        buttonNo.setText("No");
        buttonNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNoActionPerformed(evt);
            }
        });

        changeuser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/changeuser.png"))); // NOI18N
        changeuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeuserActionPerformed(evt);
            }
        });

        buttonYes.setBackground(new java.awt.Color(204, 0, 0));
        buttonYes.setForeground(new java.awt.Color(255, 255, 255));
        buttonYes.setText("Yes");
        buttonYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonYesActionPerformed(evt);
            }
        });

        txtloggedname.setFont(new java.awt.Font("SansSerif", 2, 15)); // NOI18N
        txtloggedname.setForeground(java.awt.Color.blue);

        logoutbutton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/shutdown.png"))); // NOI18N
        logoutbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutbuttonActionPerformed(evt);
            }
        });

        lbl1.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        lbl1.setForeground(java.awt.Color.red);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(changeuser, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonactivate_app, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtloggedname, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(buttonYes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonNo)
                .addGap(28, 28, 28)
                .addComponent(logoutbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(logoutbutton)
                            .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(buttonYes)
                                .addComponent(buttonNo))))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(changeuser)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtloggedname, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonactivate_app))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 1369, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 747, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        showLoginform();
    }//GEN-LAST:event_formWindowOpened

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void buttonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonExitActionPerformed
       
        lbl1.setText("Are you sure you want to close your application?");
        buttonYes.setVisible(true);
        buttonNo.setVisible(true);
    }//GEN-LAST:event_buttonExitActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void LoginFormWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_LoginFormWindowClosing
        LoginForm.dispose();
        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Do you want to Exit Application?", "Confirm",
                                                                                                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION){
            showLoginform();
        }else if (response == JOptionPane.YES_OPTION){
            System.exit(0);
            logoutmessage();
        }else if(response == JOptionPane.CLOSED_OPTION){
            JOptionPane.showMessageDialog(null, "Please Enter the correct username and password");
            showLoginform();
        }   
    }//GEN-LAST:event_LoginFormWindowClosing

    private void buttonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoginActionPerformed
        login();
    }//GEN-LAST:event_buttonLoginActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      
        lbl1.setText("Are you sure you want to close your application?");
        buttonYes.setVisible(true);
        buttonNo.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void logoutbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutbuttonActionPerformed
       
        lbl1.setText("Are you sure you want to close your application?");
        buttonYes.setVisible(true);
        buttonNo.setVisible(true);
    }//GEN-LAST:event_logoutbuttonActionPerformed

    private void changeuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeuserActionPerformed

        JDialog.setDefaultLookAndFeelDecorated(true);
        int response = JOptionPane.showConfirmDialog(null, "Are you SURE you want to Change Users?", "Confirm",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.NO_OPTION){

        }else if (response == JOptionPane.YES_OPTION){
           try{
                    Runtime.getRuntime().exec("java -jar MyshopSOFT.jar");
                    logoutmessage();
                    System.exit(0);
              }catch (IOException e) {
                 
            }
        }else if(response == JOptionPane.CLOSED_OPTION){

        }
    }//GEN-LAST:event_changeuserActionPerformed

    private void txtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            login();
        }
    }//GEN-LAST:event_txtPasswordKeyPressed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        lbl1.setText("");
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            login();
        }
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void buttonYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonYesActionPerformed
        System.exit(0);
    }//GEN-LAST:event_buttonYesActionPerformed

    private void buttonNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNoActionPerformed
        lbl1.setText("");
        buttonYes.setVisible(false);
        buttonNo.setVisible(false);
    }//GEN-LAST:event_buttonNoActionPerformed

    private void buttonActivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActivateActionPerformed

        try{
            String sql = "SELECT * FROM renewtable WHERE code = ? AND id = '1' AND s = '1'";
            pst = conn.prepareStatement(sql);

            pst.setString(1, txtCode.getText());
            rs = pst.executeQuery();
            if(rs.next()){
                DialogRenew.dispose();
                renewingtime();
                this.setEnabled(true);
                buttonactivate_app.setVisible(false);
                String path = "thankyou.wav";
                manage.music(path);
            }
        }catch(Exception e){
            DialogRenew.dispose();
            JOptionPane.showMessageDialog(null, e+" Mainswitch.buttonActivateActionPerformed");
            this.setEnabled(false);
            showrenewdialog();
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){

            }
        }
    }//GEN-LAST:event_buttonActivateActionPerformed

    private void buttonSendCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSendCodeActionPerformed
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>(){
            @Override
            protected Void doInBackground() throws Exception {
                String code_ = (String)comboAmount.getSelectedItem();
                if(code_.equals("Select Amount") || txtPhone.getText().equals("")){
                    lbl_exception.setText("Please Enter All the details required...");
                    txtPhone.requestFocus();
                }else{
                    buttonSendCode.setEnabled(false);
                    DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    String today = (dateformat.format(date));

                    GeneratedCode generated = new GeneratedCode();
                    String subject_to_me = ""+companyname+" Generated Code";
                    String codegen = generated.generation();

                    String query = "UPDATE renewtable SET code = '"+codegen+"' WHERE s = '1' AND id = '1'";
                    manage.update(query);

                    String message_to_me = "Company Name: "+companyname+" Phone #: "+company_phoneno+" AND Email Address "+email_address+" Generated Code:"
                    + " "+codegen+" AS "+today+" AMOUNT Kshs."+(String)comboAmount.getSelectedItem()+"";
                    String message = ""+companyname+",Phone#:"+txtPhone.getText()+",Code:"+codegen+",Amount:"+(String)comboAmount.getSelectedItem()+"";

                    manage.sendmessage("0723095840",message);
                    manage.sendnotification_emailtome("josephmwawasi29@gmail.com","tolclin.it@gmail.com","J35u5Christ",subject_to_me,message_to_me,DialogRenew);
                    comboAmount.setEnabled(false);
                    txtPhone.setEnabled(false);
                    jLabel6.setVisible(true);
                    txtCode.setVisible(true);
                    buttonActivate.setVisible(true);
                    buttonSendCode.setEnabled(false);
                }
                return null;
            }

        };
        worker.execute();
    }//GEN-LAST:event_buttonSendCodeActionPerformed

    private void comboAmountPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_comboAmountPopupMenuWillBecomeInvisible
        
        txtPhone.requestFocus();
        lbl_exception.setText("");
    }//GEN-LAST:event_comboAmountPopupMenuWillBecomeInvisible

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        char vchar = evt.getKeyChar();
        if((!(Character.isDigit(vchar)))
            ||(vchar == KeyEvent.VK_BACK_SPACE)

            || (vchar == KeyEvent.VK_DELETE)){
            evt.consume();
        }
    }//GEN-LAST:event_txtPhoneKeyTyped

    private void txtPhoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyPressed
        buttonSendCode.setEnabled(true);
        lbl_exception.setText("");
    }//GEN-LAST:event_txtPhoneKeyPressed

    private void DialogRenewWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_DialogRenewWindowClosing
        if(buttonactivate_app.isVisible()){
            DialogRenew.dispose();
        }else{
            System.exit(0);
        }

    }//GEN-LAST:event_DialogRenewWindowClosing

    private void buttonactivate_appActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonactivate_appActionPerformed
        showrenewdialog();
    }//GEN-LAST:event_buttonactivate_appActionPerformed

    private void txtCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodeKeyReleased

    private void txtPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyReleased
        if(txtPhone.getText().equals("")){
            buttonSendCode.setEnabled(false);
        }else{
            buttonSendCode.setEnabled(true);
        }
    }//GEN-LAST:event_txtPhoneKeyReleased

    private void buttonLoginKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_buttonLoginKeyPressed
        lbl1.setText("");
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            login();
        }
    }//GEN-LAST:event_buttonLoginKeyPressed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Yamasuzu_Myshopsoft.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Yamasuzu_Myshopsoft.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Yamasuzu_Myshopsoft.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Yamasuzu_Myshopsoft.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Yamasuzu_Myshopsoft().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JDialog DialogRenew;
    public javax.swing.JDialog LoginForm;
    private javax.swing.JButton buttonActivate;
    private javax.swing.JButton buttonExit;
    private javax.swing.JButton buttonLogin;
    private javax.swing.JButton buttonNo;
    private javax.swing.JButton buttonSendCode;
    private javax.swing.JButton buttonYes;
    private javax.swing.JButton buttonactivate_app;
    private javax.swing.JButton changeuser;
    private javax.swing.JComboBox<String> comboAmount;
    private javax.swing.JPanel desktop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl_exception;
    private javax.swing.JButton logoutbutton;
    private javax.swing.JTextField txtCode;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JLabel txtloggedname;
    // End of variables declaration//GEN-END:variables

}
