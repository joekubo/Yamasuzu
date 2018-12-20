
package yamasuzu_myshopsoft;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import org.json.JSONArray;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Calendar;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Sides;

public class Manage{
   Connection conn = null;
   PreparedStatement pst = null;
   PreparedStatement pst1 = null;
   PreparedStatement pst2 = null;
   PreparedStatement pst3 = null;
   
   ResultSet rs = null;
   ResultSet rs1 = null;
   ResultSet rs2 = null;
   ResultSet rs3 = null;
   public static String loggedid_number;
   public static String loggedusername;
   public  static String loggedinuserid;
   
    public Manage(){
        conn = Javaconnect.ConnecrDb();
    }
    
    //delete function
    public void delete(String table, Integer id){
        
            try{
                String sql = "DELETE FROM "+table+" WHERE id = "+id+"";
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }finally{
                try{
                    rs.close();
                    pst.close();
                }catch(Exception e){

                }
             }
        }
    public void delete_(String sql){
            try{
                pst = conn.prepareStatement(sql);
                pst.execute();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e+" delete_");
            }finally{
                try{
                    rs.close();
                    pst.close();
                }catch(Exception e){

                }
             }
        }
    public void update(String sql){
        try{
            pst = conn.prepareStatement(sql);
            pst.execute();
        }catch(Exception e){
            System.out.println(e+"manage.update");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void update_table(JTable table,String sql){
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn idColumn = table.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
        }catch(Exception e){
            System.out.println(e+" manage.update_table");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public String updatedate(String sql){
        try{
            pst = conn.prepareStatement(sql);
            pst.execute();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e+"manage.updatedate");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return "UPDATED";
    }
    public void fillcombo(String sql, JComboBox combobox,String value){
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                while(rs.next()){
                    combobox.addItem(rs.getString(""+value+""));
                }
        }catch(Exception e){
            System.out.println(e+" fillcombo");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void showdialog(JDialog dialog, JPanel panel, int x, int y){
        dialog.setVisible(true);
        dialog.setSize(x, y);
        dialog.setResizable(false);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(panel);
    }
    public void update_table(String sql, JTable table){
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs));
            TableColumn idColumn = table.getColumn("id");
            idColumn.setMaxWidth(0);
            idColumn.setMinWidth(0);
            idColumn.setPreferredWidth(0);
        }catch(Exception e){
            System.out.println(e+" update_table");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public void loggedmessagedelete(String name){
        try{
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String operation = ""+loggedid_number+"-"+loggedusername+" Deleted "+name+" details ON "+dateFormat.format(date.getTime())+" "
                                                                                                                + "AT "+timeFormat.format(date.getTime())+"";
            String sql = "INSERT INTO logstable(userid,name,date,time,operation)VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, loggedinuserid);
            pst.setString(2, loggedusername);
            pst.setString(3, dateFormat.format(date.getTime()));
            pst.setString(4, timeFormat.format(date.getTime()));
            pst.setString(5, operation);
            
            pst.execute();
        }catch(Exception e){
            System.out.println(e+" loggedmessagedelete");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
    }
    public Date expiry(){
        Date final_ = new Date();
         try{
            String sql = "SELECT final_date FROM renew_table WHERE s = '1'";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    final_ = rs.getDate("final_date");
                }
        }catch(Exception e){
           System.out.println(e+" manage.expiry");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
         return final_;
    }
    public void report(String sql,String path,JPanel panel){//panel report
        try {
            JasperDesign jd = JRXmlLoader.load(path);//Reports/invoice.jrxml
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn); 
            //JasperPrintManager.printReport(jp,true);
            panel.removeAll();
            panel.setLayout(new BorderLayout());
            panel.repaint();
            panel.add(new JRViewer(jp));
            panel.revalidate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }
    }
    
    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
    public void report_(String sql,String path,JPanel panel,String jasper_path){//panel report
        
        try {
            JasperDesign jd = JRXmlLoader.load(path);//Reports/invoice.jrxml
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            JasperReport jr = JasperCompileManager.compileReport(jd);
            //JRDataSource jrDataSource = new JREmptyDataSource();
            JasperPrint jp = JasperFillManager.fillReport(jr, null, conn); 
            //JasperExportManager.exportReportToPdfFile(jp,"generated_report.pdf");
           //JOptionPane.showMessageDialog(null, "Receipt Auto-Print ");
            //printing();
            //JasperPrintManager.printReport(jp,true)   ;
//                Desktop desktop = null;
//                    if(Desktop.isDesktopSupported()){
//                        desktop = Desktop.getDesktop();
//                    }
//                    desktop.print(new File("generated_report.pdf"));
//            PDDocument document = PDDocument.load(new File("generated_report.pdf"));
//
//            PrintService myPrintService = findPrintService("Hewlett-Packard-HP-LaserJet-M402dw");
//
//            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setPageable(new PDFPageable(document));
//            job.setPrintService(myPrintService);
//            job.print();
            panel.removeAll();
            panel.setLayout(new BorderLayout());
            panel.repaint();
            panel.add(new JRViewer(jp));
            panel.revalidate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e+" manage.report_");
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }
    }
   
    //validating jtextfield's length
    public  boolean length_check(String in,int len){
        return in.length() == len;
    }
    public void music(String path){
        try{
            String soundName = path;    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch(Exception e){
           System.out.println(e+" Sound Error!!!");
        }
    }
    public void poison(JPanel panel,String end_date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, 0);
            String setdate = end_date;//end_date is you set the date which you want the system to de-activate
            String currentdate = sdf.format(c.getTime());
                try{
                    Date set = sdf.parse(setdate);
                    Date now = sdf.parse(currentdate);
                    
                    if(now.after(set)){
                         panel.setVisible(false);//panel used here is desktop
                    }
                    
                }catch(Exception e){
                    
                }
            
    }
    public void sendmessage(String recipients,String message){
        // Specify your login credentials
        String username = "joetolah";
        String apiKey   = "24a67935198fcc0c86c5d093fa86ea8119b3548e6b3893284482b62660d07840";
        // Specify the numbers that you want to send to in a comma-separated list
        // Please ensure you include the country code (+254 for Kenya in this case)
        //String recipients = "+254723095840";--------------------was active
        // And of course we want our recipients to know what we really do
        //String message = "We are tolclin IT. We code all day and sleep all night";--------------was active
        // Create a new instance of our awesome gateway class
        AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
        /*************************************************************************************
            NOTE: If connecting to the sandbox:
            1. Use "sandbox" as the username
            2. Use the apiKey generated from your sandbox application
                https://account.africastalking.com/apps/sandbox/settings/key
            3. Add the "sandbox" flag to the constructor
            AfricasTalkingGateway gateway = new AfricasTalkingGateway(username, apiKey, "sandbox");
        **************************************************************************************/
        // Thats it, hit send and we'll take care of the rest. Any errors will
        // be captured in the Exception class below
        try {
            JSONArray results = gateway.sendMessage(recipients, message);
            //JOptionPane.showMessageDialog(null, "Message Sent Successfully...");
//            for( int i = 0; i < results.length(); ++i ) {
//                JSONObject result = results.getJSONObject(i);
//                //System.out.print(result.getString("status") + ","); // status is either "Success" or "error message"
//                System.out.print(result.getString("statusCode") + ",");
//                System.out.print(result.getString("number") + ",");
//                System.out.print(result.getString("messageId") + ",");
//                System.out.println(result.getString("cost"));
//                
//            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Encountered an error while sending. Check on your internet connection...");
        }
    }
   public void sendnotification_emailtome(String sender,String to,String pass,String subject_to_me, String message_to_me,JDialog dialog){
        final String username = sender;
        final String password = pass;

         Properties props = new Properties();
//         props.put("mail.smtp.auth", true);
//         props.put("mail.smtp.starttls.enable", true);
//         props.put("mail.smtp.host", "smtp.gmail.com");
//         props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

         Session session = Session.getInstance(props,
                 new javax.mail.Authenticator() {
                     protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                         return new javax.mail.PasswordAuthentication(username, password);
                     }
                 });

         try {

             Message message = new MimeMessage(session);
             message.setFrom(new InternetAddress("josephmwawasi29@gmail.com"));
             message.setRecipients(Message.RecipientType.TO,
                     InternetAddress.parse(to));
             message.setSubject(subject_to_me);//email subject
             message.setText(message_to_me);//email message
             
             System.out.println("Sending...");
             
             Transport.send(message);
             
             System.out.println("Done!");
         } catch (MessagingException e) {
             Yamasuzu_Myshopsoft main = new Yamasuzu_Myshopsoft();
             dialog.dispose();
             JOptionPane.showMessageDialog(null," Check on internet connection...Generated Code was unable to be sent...");
             main.showrenewdialog();
         }
    }
   public int checking(String sql){
        try{
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    return 1;
                }else{
                    return 0;
                }
        }catch(Exception e){
            System.out.println(e+" manage.checking");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return 2;
    }
   public int total_qty(String sql, String value_string){
       int total_qty = 0;
       try{
           pst = conn.prepareStatement(sql);
           rs = pst.executeQuery();
            if(rs.next()){
                total_qty = rs.getInt(""+value_string+"");
            }
       }catch(Exception e){
           System.out.println(e+" manage.total_qty");
       }finally{
           try{
               rs.close();
               pst.close();
           }catch(Exception e){
               
           }
       }
       return total_qty;
   }
   public void printing(){
    try{
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
        patts.add(Sides.DUPLEX);
        PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
            if (ps.length == 0) {
                throw new IllegalStateException("No Printer found");
            }
        System.out.println("Available printers: " + Arrays.asList(ps));
        PrintService myService = null;
            for (PrintService printService : ps) {
                if (printService.getName().equals("E-PoS_80mm_Thermal_Printer")) {
                    myService = printService;
                    break;
                }
            }
            if (myService == null) {
                throw new IllegalStateException("Printer not found");
            }
        FileInputStream fis = new FileInputStream("generated_report.pdf");
        Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        DocPrintJob printJob = myService.createPrintJob();
        printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
        fis.close();        
    }catch(Exception e){
        
    }
   }
   public int totalstock_qty(String product_code){
        int total_stockqty = 0;
        try{
           
            String sql = "SELECT COALESCE(SUM(qty), 0) FROM stockingtable WHERE status = '1'AND productcode = '"+product_code+"'";
            String sql1 = "SELECT COALESCE(SUM(qty), 0) FROM removetable WHERE status = '1' AND productcode = '"+product_code+"'";
            String sql2 = "SELECT COALESCE(SUM(qty), 0) FROM salestable WHERE status = '1' AND productcode = '"+product_code+"'";
            
            int purchaseqty;
            int removeqty = 0;
            int salesqty = 0;
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
                if(rs.next()){
                    purchaseqty = rs.getInt("COALESCE(SUM(qty), 0)");
                    pst1 = conn.prepareStatement(sql1);
                    rs1 = pst1.executeQuery();
                        if(rs1.next()){
                            removeqty = rs1.getInt("COALESCE(SUM(qty), 0)");
                            pst2 = conn.prepareStatement(sql2);
                            rs2 = pst2.executeQuery();
                                if(rs2.next()){
                                    salesqty = rs2.getInt("COALESCE(SUM(qty), 0)");
                                }
                        }
                        total_stockqty = purchaseqty - (removeqty + salesqty );
                        System.out.println(total_stockqty);
                }
        }catch(Exception e){
            System.out.println(e+" purchasespanel.totalstock_qty");
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(Exception e){
                
            }
        }
        return total_stockqty;
    }
}
