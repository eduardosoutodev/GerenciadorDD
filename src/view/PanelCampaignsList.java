package view;

import model.Campanha;
import service.ServiceException;
import service.ServiceFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelCampaignsList extends JPanel {
    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel model;

    public PanelCampaignsList(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        refresh();
    }

    public void refresh() {
        removeAll();
        setupUI();
        carregarDados();
        revalidate();
        repaint();
    }

    private void setupUI() {
        setBackground(new Color(245, 235, 215));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(100, 30, 30));
        topBar.setPreferredSize(new Dimension(0, 60));

        JLabel titleLabel = new JLabel("Gerenciamento de Campanhas", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topBar.add(titleLabel, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columns = {"ID", "Nome", "Mestre", "Descrição"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);

        JButton btnNovo = new JButton("Nova Campanha");
        btnNovo.addActionListener(e -> new CadastroCampanhaVIEW(mainFrame).setVisible(true));

        JButton btnVoltar = new JButton("Voltar ao Painel");
        btnVoltar.addActionListener(e -> mainFrame.showScreen("Dashboard"));

        btnPanel.add(btnNovo);
        btnPanel.add(btnVoltar);
        centerPanel.add(btnPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void carregarDados() {
        try {
            List<Campanha> lista = ServiceFactory.getCampanhaService().listar();
            model.setRowCount(0);
            for (Campanha c : lista) {
                model.addRow(new Object[]{c.getId(), c.getNome(), c.getMestre(), c.getDescricao()});
            }
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
