package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private PanelDashboard dashboard;
    private PanelBestiary bestiary;
    private PanelCharacterSheet characterSheet;
    private PanelCampaignsList campaignsList;
    private PanelCharactersList charactersList;

    public MainFrame() {
        setTitle("Gerenciador D&D");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        dashboard = new PanelDashboard(this);
        bestiary = new PanelBestiary(this);
        characterSheet = new PanelCharacterSheet(this);
        campaignsList = new PanelCampaignsList(this);
        charactersList = new PanelCharactersList(this);

        mainPanel.add(dashboard, "Dashboard");
        mainPanel.add(bestiary, "Bestiary");
        mainPanel.add(characterSheet, "CharacterSheet");
        mainPanel.add(campaignsList, "CampaignsList");
        mainPanel.add(charactersList, "CharactersList");

        add(mainPanel);
        showScreen("Dashboard");
    }

    public void showScreen(String screenName) {
        if (screenName.equals("Dashboard")) dashboard.refresh();
        if (screenName.equals("CharacterSheet")) characterSheet.refresh();
        if (screenName.equals("CampaignsList")) campaignsList.refresh();
        if (screenName.equals("CharactersList")) charactersList.refresh();
        cardLayout.show(mainPanel, screenName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
