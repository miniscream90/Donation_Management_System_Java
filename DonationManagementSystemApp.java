import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

public class DonationManagementSystemApp {
    private final DonationSystem donationSystem;
    private JPanel cards;
    private Color colorBg;
    private Color colorSurface;
    private Color colorPrimary;
    private Color colorOnPrimary;
    private Color colorText;

    public DonationManagementSystemApp() {
        this.donationSystem = new DonationSystem();
    }

    public static void main(String[] args) {
        installNimbusTheme();
        SwingUtilities.invokeLater(() -> {
            DonationManagementSystemApp app = new DonationManagementSystemApp();
            if (app.showLoginDialog()) {
                app.showMainWindow();
            } else {
                System.exit(0);
            }
        });
    }

    private static void installNimbusTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.put("control", new Color(0xF5F7FA));
            UIManager.put("text", new Color(0x111827));
            UIManager.put("nimbusBase", new Color(0x1F2937));
            UIManager.put("nimbusBlueGrey", new Color(0xE5E7EB));
            UIManager.put("nimbusLightBackground", new Color(0xFFFFFF));
            UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
        } catch (Exception ignored) {}
    }

    private boolean showLoginDialog() {
        JPasswordField passwordField = new JPasswordField();
        JTextField usernameField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 1, 6, 6));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Donation Management System - Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String user = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());
            return user.equals("admin") && pass.equals("admin123");
        }
        return false;
    }

    private void showMainWindow() {
        colorBg = new Color(0xF8FAFC);
        colorSurface = new Color(0xFFFFFF);
        colorPrimary = new Color(0x3B82F6);
        colorOnPrimary = Color.WHITE;
        colorText = new Color(0x1F2937);

        JFrame frame = new JFrame("💝 Donation Management System");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(createAppIcon());

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(colorBg);

        JPanel appBar = new JPanel(new BorderLayout());
        appBar.setBorder(new EmptyBorder(15, 20, 15, 20));
        appBar.setBackground(colorSurface);
        appBar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0xE5E7EB), 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel title = new JLabel("💝 Donation Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(colorText);
        
        JLabel subtitle = new JLabel("Manage donations and track fund usage efficiently");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(0x6B7280));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(title, BorderLayout.NORTH);
        titlePanel.add(subtitle, BorderLayout.SOUTH);
        
        appBar.add(titlePanel, BorderLayout.WEST);
        root.add(appBar, BorderLayout.NORTH);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1, 0, 12));
        sidebar.setBorder(new EmptyBorder(20, 16, 20, 16));
        sidebar.setBackground(colorBg);
        sidebar.setPreferredSize(new Dimension(200, 0));

        cards = new JPanel(new CardLayout());
        cards.setBorder(new EmptyBorder(20, 20, 20, 20));
        cards.setBackground(colorBg);

        addNav(sidebar, "👤 Register Donor", "register");
        addNav(sidebar, "💰 Add Donation", "donate");
        addNav(sidebar, "📋 View Donations", "viewDonations");
        addNav(sidebar, "📝 Log Fund Usage", "logFund");
        addNav(sidebar, "📊 View Fund Usage", "viewFund");

        cards.add(wrapCard(createRegisterDonorPanel()), "register");
        cards.add(wrapCard(createAddDonationPanel()), "donate");
        cards.add(wrapCard(createViewDonationsPanel()), "viewDonations");
        cards.add(wrapCard(createLogFundUsagePanel()), "logFund");
        cards.add(wrapCard(createViewFundUsagePanel()), "viewFund");

        root.add(sidebar, BorderLayout.WEST);
        root.add(cards, BorderLayout.CENTER);
        frame.setContentPane(root);

        showCard("register");
        frame.setVisible(true);
    }

    private void addNav(JPanel sidebar, String text, String card) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(0xF1F5F9));
        btn.setForeground(colorText);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(new EmptyBorder(12, 16, 12, 16));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effects
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorPrimary);
                btn.setForeground(colorOnPrimary);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(0xF1F5F9));
                btn.setForeground(colorText);
            }
        });
        
        btn.addActionListener(e -> showCard(card));
        sidebar.add(btn);
    }

    private void showCard(String name) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, name);
    }

    private JPanel wrapCard(JComponent inner) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(colorBg);
        JPanel surface = new JPanel(new BorderLayout());
        surface.setBackground(colorSurface);
        surface.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(0xE5E7EB), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        surface.add(inner, BorderLayout.CENTER);
        card.add(surface, BorderLayout.CENTER);
        return card;
    }

    private JPanel createRegisterDonorPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("👤 Register New Donor");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(colorText);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0; 
        JLabel donorIdLabel = new JLabel("Donor ID:");
        donorIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(donorIdLabel, gbc);
        JTextField donorIdField = new JTextField(20);
        donorIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        donorIdField.setBorder(new LineBorder(new Color(0xD1D5DB), 1));
        donorIdField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1; gbc.gridy = 0; form.add(donorIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; 
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(nameLabel, gbc);
        JTextField nameField = new JTextField(20);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameField.setBorder(new LineBorder(new Color(0xD1D5DB), 1));
        nameField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1; gbc.gridy = 1; form.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; 
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(emailLabel, gbc);
        JTextField emailField = new JTextField(20);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(new LineBorder(new Color(0xD1D5DB), 1));
        emailField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1; gbc.gridy = 2; form.add(emailField, gbc);

        JButton registerBtn = new JButton("✅ Register Donor");
        registerBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerBtn.setBackground(colorPrimary);
        registerBtn.setForeground(colorOnPrimary);
        registerBtn.setBorderPainted(false);
        registerBtn.setOpaque(true);
        registerBtn.setBorder(new EmptyBorder(12, 24, 12, 24));
        registerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        registerBtn.addActionListener(e -> {
            String id = donorIdField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "⚠️ All fields are required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(panel, "⚠️ Please enter a valid email address.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean ok = donationSystem.registerDonor(id, name, email);
            if (ok) {
                JOptionPane.showMessageDialog(panel, "✅ Donor registered successfully!");
                donorIdField.setText("");
                nameField.setText("");
                emailField.setText("");
            } else {
                JOptionPane.showMessageDialog(panel, "❌ Donor ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(registerBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAddDonationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("💰 Add New Donation");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(colorText);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; 
        JLabel donorIdLabel = new JLabel("Donor ID:");
        donorIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(donorIdLabel, gbc);
        JTextField donorIdField = new JTextField(20);
        donorIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        donorIdField.setBorder(new LineBorder(new Color(0xD1D5DB), 1));
        donorIdField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1; gbc.gridy = 0; form.add(donorIdField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; 
        JLabel amountLabel = new JLabel("Amount (₹):");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(amountLabel, gbc);
        JSpinner amountSpinner = new JSpinner(new SpinnerNumberModel(100.0, 1.0, 1_000_000.0, 50.0));
        amountSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        amountSpinner.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1; gbc.gridy = 1; form.add(amountSpinner, gbc);

        JButton addBtn = new JButton("💳 Record Donation");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setBackground(colorPrimary);
        addBtn.setForeground(colorOnPrimary);
        addBtn.setBorderPainted(false);
        addBtn.setOpaque(true);
        addBtn.setBorder(new EmptyBorder(12, 24, 12, 24));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        addBtn.addActionListener(e -> {
            String id = donorIdField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "⚠️ Donor ID is required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Donor donor = donationSystem.findDonor(id);
            if (donor == null) {
                JOptionPane.showMessageDialog(panel, "❌ Donor not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double amount = ((Number) amountSpinner.getValue()).doubleValue();
            if (amount <= 0) {
                JOptionPane.showMessageDialog(panel, "⚠️ Amount must be positive.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            donor.addDonation(amount);
            donationSystem.updateDonor(donor);
            donationSystem.logDonation(id, amount);
            JOptionPane.showMessageDialog(panel, "✅ Donation recorded successfully!");
            donorIdField.setText("");
            amountSpinner.setValue(100.0);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createViewDonationsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("📋 View Donation History");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(colorText);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        
        JLabel donorIdLabel = new JLabel("Donor ID:");
        donorIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        top.add(donorIdLabel);
        
        JTextField donorIdField = new JTextField(20);
        donorIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        donorIdField.setBorder(new LineBorder(new Color(0xD1D5DB), 1));
        donorIdField.setPreferredSize(new Dimension(200, 35));
        top.add(donorIdField);
        
        JButton loadBtn = new JButton("🔍 Load History");
        loadBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loadBtn.setBackground(colorPrimary);
        loadBtn.setForeground(colorOnPrimary);
        loadBtn.setBorderPainted(false);
        loadBtn.setOpaque(true);
        loadBtn.setBorder(new EmptyBorder(8, 16, 8, 16));
        loadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        top.add(loadBtn);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        list.setBackground(new Color(0xFAFAFA));
        list.setBorder(new LineBorder(new Color(0xE5E7EB), 1));
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(new LineBorder(new Color(0xE5E7EB), 1));

        loadBtn.addActionListener(e -> {
            listModel.clear();
            String id = donorIdField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "⚠️ Please enter a Donor ID.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<String> donations = donationSystem.getDonations(id);
            if (donations.isEmpty() || donations.get(0).equals("No donations found.")) {
                listModel.addElement("No donations found for this donor.");
            } else {
                for (String d : donations) {
                    listModel.addElement(d);
                }
            }
        });

        panel.add(title, BorderLayout.NORTH);
        panel.add(top, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createLogFundUsagePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("📝 Log Fund Usage");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(colorText);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; 
        JLabel purposeLabel = new JLabel("Purpose:");
        purposeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(purposeLabel, gbc);
        JTextField purposeField = new JTextField(20);
        purposeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        purposeField.setBorder(new LineBorder(new Color(0xD1D5DB), 1));
        purposeField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1; gbc.gridy = 0; form.add(purposeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; 
        JLabel amountLabel = new JLabel("Amount (₹):");
        amountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        form.add(amountLabel, gbc);
        JSpinner amountSpinner = new JSpinner(new SpinnerNumberModel(100.0, 1.0, 1_000_000.0, 50.0));
        amountSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        amountSpinner.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1; gbc.gridy = 1; form.add(amountSpinner, gbc);

        JButton logBtn = new JButton("📊 Log Usage");
        logBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logBtn.setBackground(colorPrimary);
        logBtn.setForeground(colorOnPrimary);
        logBtn.setBorderPainted(false);
        logBtn.setOpaque(true);
        logBtn.setBorder(new EmptyBorder(12, 24, 12, 24));
        logBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        logBtn.addActionListener(e -> {
            String purpose = purposeField.getText().trim();
            double amount = ((Number) amountSpinner.getValue()).doubleValue();
            if (purpose.isEmpty() || amount <= 0) {
                JOptionPane.showMessageDialog(panel, "⚠️ Please enter valid purpose and amount.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            donationSystem.logFundUsage(purpose, amount);
            JOptionPane.showMessageDialog(panel, "✅ Fund usage recorded successfully!");
            purposeField.setText("");
            amountSpinner.setValue(100.0);
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(logBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createViewFundUsagePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel title = new JLabel("📊 Fund Usage Records");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(colorText);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        list.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        list.setBackground(new Color(0xFAFAFA));
        list.setBorder(new LineBorder(new Color(0xE5E7EB), 1));
        JScrollPane scroll = new JScrollPane(list);
        scroll.setBorder(new LineBorder(new Color(0xE5E7EB), 1));

        JButton refreshBtn = new JButton("🔄 Refresh Records");
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshBtn.setBackground(colorPrimary);
        refreshBtn.setForeground(colorOnPrimary);
        refreshBtn.setBorderPainted(false);
        refreshBtn.setOpaque(true);
        refreshBtn.setBorder(new EmptyBorder(8, 16, 8, 16));
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        refreshBtn.addActionListener(e -> {
            listModel.clear();
            List<String> usage = donationSystem.getFundUsage();
            if (usage.isEmpty() || usage.get(0).equals("No fund usage records found.")) {
                listModel.addElement("No fund usage records found.");
            } else {
                for (String u : usage) {
                    listModel.addElement(u);
                }
            }
        });

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.setOpaque(false);
        top.add(refreshBtn);

        panel.add(title, BorderLayout.NORTH);
        panel.add(top, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);
        return panel;
    }

    private Image createAppIcon() {
        // Create a simple icon using Graphics2D
        BufferedImage icon = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = icon.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw a heart shape
        g2d.setColor(colorPrimary);
        g2d.fillOval(4, 8, 8, 8);
        g2d.fillOval(20, 8, 8, 8);
        g2d.fillOval(8, 4, 16, 16);
        g2d.setColor(colorSurface);
        g2d.fillOval(10, 6, 12, 12);
        
        g2d.dispose();
        return icon;
    }
}


