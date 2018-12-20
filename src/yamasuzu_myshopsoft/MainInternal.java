
package yamasuzu_myshopsoft;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;

public class MainInternal extends javax.swing.JPanel {
        GridBagLayout layout = new GridBagLayout();
        StockingIn stock;
        Suppliers suppliers;
        SalesPanel sales;
        UsersPanel users;
        ViewSales viewsales;
        ReportPanel reports;
        QuotationPanel quotation;
        InvoicePanel invoice;
        LogsPanel logs;
        ShopPanel shop;
        ClientPanel client;
        RecycleBin bin;
        Manage manage;
        public String loggedpriviledge;
    public MainInternal() {
        initComponents();
        stock = new StockingIn();
        suppliers = new Suppliers();
        sales = new SalesPanel();
        users = new UsersPanel();
        viewsales = new ViewSales();
        reports = new ReportPanel();
        quotation = new QuotationPanel();
        invoice = new InvoicePanel();
        logs = new LogsPanel();
        shop = new ShopPanel();
        client = new ClientPanel();
        bin = new RecycleBin();
        manage = new Manage();
        
        DynamicPanel.setLayout(layout);
        GridBagConstraints c = new  GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        DynamicPanel.add(stock,c);
        DynamicPanel.add(suppliers,c);
        DynamicPanel.add(sales,c);
        DynamicPanel.add(users,c);
        DynamicPanel.add(viewsales,c);
        DynamicPanel.add(logs,c);
        DynamicPanel.add(reports,c);
        DynamicPanel.add(quotation,c);
        DynamicPanel.add(invoice,c);
        DynamicPanel.add(shop, c);
        DynamicPanel.add(client, c);
        DynamicPanel.add(bin, c);
        
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        viewsales.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
        buttonInvoice.setVisible(false);
        manage.poison(panelButtons, "2019-03-06");
        manage.poison(DynamicPanel, "2019-03-06");
    }
    private void disposealldialogs(){
        Yamasuzu_Myshopsoft main = new Yamasuzu_Myshopsoft();
        main.LoginForm.dispose();
        sales.DialogInvoice.dispose();
        stock.DialogSuppliers.dispose();
        stock.DialogPayments.dispose();
        stock.DialogCategory.dispose();
        stock.DialogCat.dispose();
        stock.DialogAllproducts.dispose();
        stock.DialogRemove.dispose();
        quotation.DialogEditQuote.dispose();
        quotation.DialogQuotations.dispose();
        invoice.DialogEditInvoice.dispose();
        invoice.DialogInvoice.dispose();
        shop.DialogShop.dispose();
    }
    public void allbuttonsdefaultcolor(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
    }
    private void shop(){
        buttonShop.setBackground(Color.blue);
        buttonShop.setForeground(Color.white);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(true);
        shop.update_table();
        client.setVisible(false);
        bin.setVisible(false);
        disposealldialogs();
    }
    private void users(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.blue);
        buttonUsers.setForeground(Color.white);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(true);
        users.fillcomboCompany();
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
        users.update_table();
        users.reset();
        disposealldialogs();
    }
    private void suppliers(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.blue);
        buttonSuppliers.setForeground(Color.white);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(true);
        sales.setVisible(false);
        users.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        client.setVisible(false);
        suppliers.reset();
        viewsales.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        bin.setVisible(false);
        disposealldialogs();
    }
    private void stock(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.blue);
        buttonStocking.setForeground(Color.white);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(true);
        suppliers.setVisible(false);
        sales.setVisible(false);
        stock.findProduct();
        stock.reset();
        stock.enablePanel();
        users.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
        disposealldialogs();
    }
    private void clients(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.blue);
        buttonClients.setForeground(Color.white);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(true);
        bin.setVisible(false);
        client.findclient();
        client.reset();
        users.update_table();
        disposealldialogs();
    }
    private void sales(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.blue);
        buttonSales.setForeground(Color.white);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(true);
        sales.reset();
        sales.findProduct();
        sales.txtGrandTotal.setText("0.00");
        sales.buttonReceipt.setEnabled(false);
        sales.jTabbedPane2.setSelectedIndex(0);
        users.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
        
        disposealldialogs();
    }
    private void viewsales(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.blue);
        buttonViewSales.setForeground(Color.white);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(true);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
        viewsales.findviewsales();
        viewsales.buttonDelete.setEnabled(false);
        viewsales.buttonReprintReceipt.setEnabled(false);
        viewsales.buttonEditInvoice.setVisible(false);
        viewsales.update_tableremove();
        viewsales.reset();
        viewsales.jTabbedPane1.setSelectedIndex(0);
        disposealldialogs();
    }
    private void reports(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.blue);
        buttonReports.setForeground(Color.white);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        reports.setVisible(true);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
        reports.update_tableSales_profits();
        reports. totalSales();
        reports.totalBalB_f_products();
        reports.salesCash();
        reports.salesTill();
        reports.salesCheque();
        reports.update_tablesupplierpayment();
        reports.update_tablestock();
        reports.remainingstock();
        reports.jTabbedPane2.setSelectedIndex(0);
        disposealldialogs();
    }
    private void quotation(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.blue);
        buttonQuotations.setForeground(Color.white);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        viewsales.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(true);
        quotation.findQuotation();
        quotation.generateQuotation_no();
        quotation.jTabbedPane1.setSelectedIndex(0);
        invoice.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
    }
    private void invoice(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.lightGray);
        buttonLogs.setForeground(Color.BLACK);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.blue);
        buttonInvoice.setForeground(Color.white);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        viewsales.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(true);
        invoice.findQuotation();
        invoice.generateQuotation_no();
        
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(false);
    }
    private void logs(){
        buttonShop.setBackground(Color.lightGray);
        buttonShop.setForeground(Color.BLACK);
        buttonUsers.setBackground(Color.lightGray);
        buttonUsers.setForeground(Color.BLACK);
        buttonSuppliers.setBackground(Color.lightGray);
        buttonSuppliers.setForeground(Color.BLACK);
        buttonStocking.setBackground(Color.lightGray);
        buttonStocking.setForeground(Color.BLACK);
        buttonClients.setBackground(Color.lightGray);
        buttonClients.setForeground(Color.BLACK);
        buttonSales.setBackground(Color.lightGray);
        buttonSales.setForeground(Color.BLACK);
        buttonViewSales.setBackground(Color.lightGray);
        buttonViewSales.setForeground(Color.BLACK);
        buttonReports.setBackground(Color.lightGray);
        buttonReports.setForeground(Color.BLACK);
        buttonLogs.setBackground(Color.blue);
        buttonLogs.setForeground(Color.white);
        buttonQuotations.setBackground(Color.lightGray);
        buttonQuotations.setForeground(Color.BLACK);
        buttonInvoice.setBackground(Color.lightGray);
        buttonInvoice.setForeground(Color.BLACK);
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        invoice.setVisible(false);
        viewsales.setVisible(false);
        shop.setVisible(false);
        logs.setVisible(true);
        client.setVisible(false);
        bin.setVisible(false);
        logs.update_table();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, 0);
        logs.chooserDate.getDateEditor().setDate(c.getTime());
        disposealldialogs();
    }
    private void recyclebin(){
        stock.setVisible(false);
        suppliers.setVisible(false);
        sales.setVisible(false);
        users.setVisible(false);
        viewsales.setVisible(false);
        reports.setVisible(false);
        quotation.setVisible(false);
        logs.setVisible(false);
        shop.setVisible(false);
        client.setVisible(false);
        bin.setVisible(true);
        bin.update_stock();
        bin.update_category();
        bin.update_client();
        bin.update_payments();
        bin.update_suppliers();
        bin.update_users();
        disposealldialogs();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelButtons = new javax.swing.JPanel();
        buttonStocking = new javax.swing.JButton();
        buttonSuppliers = new javax.swing.JButton();
        buttonSales = new javax.swing.JButton();
        buttonUsers = new javax.swing.JButton();
        buttonViewSales = new javax.swing.JButton();
        buttonReports = new javax.swing.JButton();
        buttonLogs = new javax.swing.JButton();
        buttonShop = new javax.swing.JButton();
        buttonClients = new javax.swing.JButton();
        buttonRecyclebin = new javax.swing.JButton();
        buttonQuotations = new javax.swing.JButton();
        buttonInvoice = new javax.swing.JButton();
        DynamicPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        setPreferredSize(new java.awt.Dimension(1479, 880));

        panelButtons.setBackground(java.awt.Color.white);
        panelButtons.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("CheckBoxMenuItem.selectionBackground")));

        buttonStocking.setBackground(java.awt.Color.gray);
        buttonStocking.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonStocking.setForeground(java.awt.Color.white);
        buttonStocking.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/stock.png"))); // NOI18N
        buttonStocking.setText("       Stock");
        buttonStocking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonStockingActionPerformed(evt);
            }
        });

        buttonSuppliers.setBackground(java.awt.Color.gray);
        buttonSuppliers.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSuppliers.setForeground(java.awt.Color.white);
        buttonSuppliers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/suppliers.png"))); // NOI18N
        buttonSuppliers.setText("   Suppliers");
        buttonSuppliers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSuppliersActionPerformed(evt);
            }
        });

        buttonSales.setBackground(java.awt.Color.gray);
        buttonSales.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonSales.setForeground(java.awt.Color.white);
        buttonSales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/sales.png"))); // NOI18N
        buttonSales.setText("       Sales");
        buttonSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSalesActionPerformed(evt);
            }
        });

        buttonUsers.setBackground(java.awt.Color.gray);
        buttonUsers.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonUsers.setForeground(java.awt.Color.white);
        buttonUsers.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/users.png"))); // NOI18N
        buttonUsers.setText("        Users");
        buttonUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonUsersActionPerformed(evt);
            }
        });

        buttonViewSales.setBackground(java.awt.Color.gray);
        buttonViewSales.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonViewSales.setForeground(java.awt.Color.white);
        buttonViewSales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/views.png"))); // NOI18N
        buttonViewSales.setText("View Sales");
        buttonViewSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonViewSalesActionPerformed(evt);
            }
        });

        buttonReports.setBackground(java.awt.Color.gray);
        buttonReports.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonReports.setForeground(java.awt.Color.white);
        buttonReports.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/report.png"))); // NOI18N
        buttonReports.setText("     Reports");
        buttonReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReportsActionPerformed(evt);
            }
        });

        buttonLogs.setBackground(java.awt.Color.gray);
        buttonLogs.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonLogs.setForeground(java.awt.Color.white);
        buttonLogs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/logs.png"))); // NOI18N
        buttonLogs.setText("        Logs");
        buttonLogs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLogsActionPerformed(evt);
            }
        });

        buttonShop.setBackground(java.awt.Color.gray);
        buttonShop.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonShop.setForeground(java.awt.Color.white);
        buttonShop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/company.png"))); // NOI18N
        buttonShop.setText("   Company");
        buttonShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonShopActionPerformed(evt);
            }
        });

        buttonClients.setBackground(java.awt.Color.gray);
        buttonClients.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonClients.setForeground(java.awt.Color.white);
        buttonClients.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/client.png"))); // NOI18N
        buttonClients.setText("      Clients");
        buttonClients.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClientsActionPerformed(evt);
            }
        });

        buttonRecyclebin.setBackground(java.awt.Color.gray);
        buttonRecyclebin.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonRecyclebin.setForeground(java.awt.Color.white);
        buttonRecyclebin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/trash.png"))); // NOI18N
        buttonRecyclebin.setText("Recycle Bin");
        buttonRecyclebin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRecyclebinActionPerformed(evt);
            }
        });

        buttonQuotations.setBackground(java.awt.Color.gray);
        buttonQuotations.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        buttonQuotations.setForeground(java.awt.Color.white);
        buttonQuotations.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/quotation.png"))); // NOI18N
        buttonQuotations.setText("Pro forma Invoice");
        buttonQuotations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonQuotationsActionPerformed(evt);
            }
        });

        buttonInvoice.setBackground(java.awt.Color.gray);
        buttonInvoice.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        buttonInvoice.setForeground(java.awt.Color.white);
        buttonInvoice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/yamasuzu_myshopsoft/icons/invoice.png"))); // NOI18N
        buttonInvoice.setText("       Invoice");
        buttonInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonsLayout = new javax.swing.GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonStocking, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonViewSales, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonReports, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSuppliers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonUsers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonLogs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelButtonsLayout.createSequentialGroup()
                        .addComponent(buttonShop, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(buttonClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonRecyclebin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonQuotations, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonInvoice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buttonShop, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonUsers, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSuppliers, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonStocking, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonClients, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSales, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonViewSales, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonReports, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonQuotations, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonInvoice, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonLogs, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonRecyclebin, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(257, Short.MAX_VALUE))
        );

        DynamicPanel.setBackground(new java.awt.Color(51, 51, 255));
        DynamicPanel.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("CheckBoxMenuItem.selectionBackground")));
        DynamicPanel.setPreferredSize(new java.awt.Dimension(900, 725));

        javax.swing.GroupLayout DynamicPanelLayout = new javax.swing.GroupLayout(DynamicPanel);
        DynamicPanel.setLayout(DynamicPanelLayout);
        DynamicPanelLayout.setHorizontalGroup(
            DynamicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1189, Short.MAX_VALUE)
        );
        DynamicPanelLayout.setVerticalGroup(
            DynamicPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 627, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DynamicPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DynamicPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buttonStockingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonStockingActionPerformed
        stock();
    }//GEN-LAST:event_buttonStockingActionPerformed

    private void buttonSuppliersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSuppliersActionPerformed
        suppliers();
    }//GEN-LAST:event_buttonSuppliersActionPerformed

    private void buttonSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSalesActionPerformed
        sales();
        
    }//GEN-LAST:event_buttonSalesActionPerformed

    private void buttonUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonUsersActionPerformed
        users();
    }//GEN-LAST:event_buttonUsersActionPerformed

    private void buttonViewSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonViewSalesActionPerformed
        viewsales();
    }//GEN-LAST:event_buttonViewSalesActionPerformed

    private void buttonReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReportsActionPerformed
        reports();
    }//GEN-LAST:event_buttonReportsActionPerformed

    private void buttonLogsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLogsActionPerformed
        logs();
    }//GEN-LAST:event_buttonLogsActionPerformed

    private void buttonShopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonShopActionPerformed
        shop();
    }//GEN-LAST:event_buttonShopActionPerformed

    private void buttonClientsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClientsActionPerformed
        clients();
    }//GEN-LAST:event_buttonClientsActionPerformed

    private void buttonRecyclebinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRecyclebinActionPerformed
        recyclebin();
    }//GEN-LAST:event_buttonRecyclebinActionPerformed

    private void buttonQuotationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonQuotationsActionPerformed
        quotation();
    }//GEN-LAST:event_buttonQuotationsActionPerformed

    private void buttonInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInvoiceActionPerformed
        invoice();
    }//GEN-LAST:event_buttonInvoiceActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DynamicPanel;
    public javax.swing.JButton buttonClients;
    public javax.swing.JButton buttonInvoice;
    public javax.swing.JButton buttonLogs;
    public javax.swing.JButton buttonQuotations;
    public javax.swing.JButton buttonRecyclebin;
    public javax.swing.JButton buttonReports;
    public javax.swing.JButton buttonSales;
    public javax.swing.JButton buttonShop;
    public javax.swing.JButton buttonStocking;
    public javax.swing.JButton buttonSuppliers;
    public javax.swing.JButton buttonUsers;
    public javax.swing.JButton buttonViewSales;
    public javax.swing.JPanel panelButtons;
    // End of variables declaration//GEN-END:variables
}
