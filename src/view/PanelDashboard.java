package view;

import model.Campanha;
import service.ServiceException;
import service.ServiceFactory;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelDashboard extends JPanel {
    private MainFrame mainFrame;
    private JPanel campaignList;

    public PanelDashboard(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        refresh();
    }

    public void refresh() {
        removeAll();
        JPanel sideMenu = createSideMenu();
        add(sideMenu, BorderLayout.WEST);

        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);

        carregarCampanhas();
        revalidate();
        repaint();
    }

    private JPanel createSideMenu() {
        JPanel sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.Y_AXIS));
        sideMenu.setBackground(new Color(100, 30, 30));
        sideMenu.setPreferredSize(new Dimension(200, 0));

        JLabel logoLabel = new JLabel("Gerenciador D&D");
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));
        sideMenu.add(logoLabel);

        String[] menuItems = {"Painel", "Campanhas", "Personagens", "Referência", "Configurações"};
        for (String item : menuItems) {
            JButton menuBtn = new JButton(item);
            menuBtn.setForeground(Color.WHITE);
            menuBtn.setBackground(new Color(120, 40, 40));
            menuBtn.setFocusPainted(false);
            menuBtn.setMaximumSize(new Dimension(200, 40));
            menuBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            menuBtn.addActionListener(e -> {
                switch (item) {
                    case "Painel": mainFrame.showScreen("Dashboard"); break;
                    case "Campanhas": mainFrame.showScreen("CampaignsList"); break;
                    case "Personagens": mainFrame.showScreen("CharactersList"); break;
                    case "Referência": mainFrame.showScreen("Bestiary"); break;
                }
            });
            sideMenu.add(menuBtn);
            sideMenu.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return sideMenu;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 235, 215));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(100, 30, 30));
        topBar.setPreferredSize(new Dimension(0, 60));

        JLabel welcomeLabel = new JLabel("Bem-vindo, Aventureiro!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        topBar.add(welcomeLabel, BorderLayout.WEST);

        contentPanel.add(topBar, BorderLayout.NORTH);

        JPanel dashboard = new JPanel(new GridBagLayout());
        dashboard.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton btnNewCampaign = new JButton("Nova Campanha");
        btnNewCampaign.setPreferredSize(new Dimension(200, 50));
        btnNewCampaign.addActionListener(e -> new CadastroCampanhaVIEW(mainFrame).setVisible(true));
        gbc.gridx = 0; gbc.gridy = 0;
        dashboard.add(btnNewCampaign, gbc);

        JButton btnNewCharacter = new JButton("Novo Personagem");
        btnNewCharacter.setPreferredSize(new Dimension(200, 50));
        btnNewCharacter.addActionListener(e -> new CadastroPersonagemVIEW(mainFrame).setVisible(true));
        gbc.gridx = 1; gbc.gridy = 0;
        dashboard.add(btnNewCharacter, gbc);

        JLabel lblRecent = new JLabel("Campanhas Ativas");
        lblRecent.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        dashboard.add(lblRecent, gbc);

        campaignList = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        campaignList.setOpaque(false);
        gbc.gridy = 2;
        dashboard.add(campaignList, gbc);

        contentPanel.add(new JScrollPane(dashboard), BorderLayout.CENTER);

        return contentPanel;
    }

    private void carregarCampanhas() {
        try {
            List<Campanha> lista = ServiceFactory.getCampanhaService().listar();
            campaignList.removeAll();
            for (Campanha camp : lista) {
                JPanel campCard = new JPanel(new BorderLayout());
                campCard.setPreferredSize(new Dimension(180, 120));
                campCard.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                campCard.setBackground(Color.WHITE);

                JLabel nameLabel = new JLabel(camp.getNome(), SwingConstants.CENTER);
                nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                campCard.add(nameLabel, BorderLayout.CENTER);

                JLabel mestreLabel = new JLabel("Mestre: " + camp.getMestre(), SwingConstants.CENTER);
                mestreLabel.setFont(new Font("Arial", Font.ITALIC, 10));
                campCard.add(mestreLabel, BorderLayout.NORTH);

                JButton btnDetails = new JButton("Abrir");
                campCard.add(btnDetails, BorderLayout.SOUTH);

                campaignList.add(campCard);
            }
            campaignList.revalidate();
            campaignList.repaint();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
