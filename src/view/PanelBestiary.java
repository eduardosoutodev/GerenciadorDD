package view;

import model.Criatura;
import service.ServiceException;
import service.ServiceFactory;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelBestiary extends JPanel {
    private MainFrame mainFrame;
    private JPanel creatureListPanel;
    private JPanel detailsPanel;
    private List<Criatura> todasCriaturas;
    private JTextField searchField;

    public PanelBestiary(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        JPanel topBar = createTopBar();
        add(topBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(245, 235, 215));

        JPanel filterPanel = createFilterPanel();
        centerPanel.add(filterPanel, BorderLayout.WEST);

        creatureListPanel = new JPanel();
        creatureListPanel.setLayout(new BoxLayout(creatureListPanel, BoxLayout.Y_AXIS));
        creatureListPanel.setBackground(new Color(245, 235, 215));
        creatureListPanel.setBorder(BorderFactory.createTitledBorder("Lista de Criaturas"));

        JScrollPane scrollPane = new JScrollPane(creatureListPanel);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(new Color(245, 235, 215));
        detailsPanel.setPreferredSize(new Dimension(300, 0));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalhes da Criatura"));
        centerPanel.add(detailsPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);

        carregarCriaturas();
    }

    private void carregarCriaturas() {
        try {
            todasCriaturas = ServiceFactory.getCriaturaService().listar();
            atualizarLista(todasCriaturas);
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void atualizarLista(List<Criatura> lista) {
        creatureListPanel.removeAll();
        for (Criatura c : lista) {
            JPanel card = new JPanel(new BorderLayout());
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            card.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel("  " + c.getNome() + " (ND " + c.getNd() + ")");
            card.add(nameLabel, BorderLayout.WEST);

            JButton btnDetails = new JButton("Ver Detalhes");
            btnDetails.addActionListener(e -> exibirDetalhes(c));
            card.add(btnDetails, BorderLayout.EAST);

            creatureListPanel.add(card);
            creatureListPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        creatureListPanel.revalidate();
        creatureListPanel.repaint();
    }

    // Antes: filtro estava aqui mesmo, misturando string.toLowerCase()/contains
    // com atualização da lista Swing. Agora delega o "o quê" filtrar ao
    // CriaturaService, e só cuida do "como" atualizar a tela.
    private void filtrarCriaturas() {
        List<Criatura> filtradas = ServiceFactory.getCriaturaService().filtrar(todasCriaturas, searchField.getText());
        atualizarLista(filtradas);
    }

    private void exibirDetalhes(Criatura c) {
        detailsPanel.removeAll();
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Detalhes: " + c.getNome()));

        detailsPanel.add(new JLabel("Tipo: " + c.getTipo()));
        detailsPanel.add(new JLabel("Tamanho: " + c.getTamanho()));
        detailsPanel.add(new JLabel("Classe de Armadura: " + c.getCa()));
        detailsPanel.add(new JLabel("Pontos de Vida: " + c.getPv()));
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        detailsPanel.add(new JLabel("Atributos:"));
        String[] columns = {"FOR", "DES", "CON", "INT", "SAB", "CAR"};
        Object[][] data = {{c.getForca(), c.getDestreza(), c.getConstituicao(), c.getInteligencia(), c.getSabedoria(), c.getCarisma()}};
        JTable attrTable = new JTable(data, columns);
        attrTable.setEnabled(false);
        JScrollPane sp = new JScrollPane(attrTable);
        sp.setPreferredSize(new Dimension(280, 40));
        sp.setMaximumSize(new Dimension(280, 40));
        detailsPanel.add(sp);

        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(new JLabel("Habilidades:"));
        detailsPanel.add(new JLabel("<html><body style='width: 200px'>" + c.getHabilidades().replace("\n", "<br>") + "</body></html>"));

        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(new JLabel("Ações:"));
        detailsPanel.add(new JLabel("<html><body style='width: 200px'>" + c.getAcoes().replace("\n", "<br>") + "</body></html>"));

        detailsPanel.add(Box.createVerticalGlue());
        detailsPanel.revalidate();
        detailsPanel.repaint();
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(100, 30, 30));
        topBar.setPreferredSize(new Dimension(0, 60));

        JButton backBtn = new JButton("Voltar");
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(new Color(120, 40, 40));
        backBtn.addActionListener(e -> mainFrame.showScreen("Dashboard"));
        topBar.add(backBtn, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Biblioteca de Referência - Bestiário", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topBar.add(titleLabel, BorderLayout.CENTER);

        searchField = new JTextField("Pesquisar...", 15);
        searchField.addActionListener(e -> filtrarCriaturas());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setOpaque(false);
        searchPanel.add(searchField);
        topBar.add(searchPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        filterPanel.setBackground(new Color(245, 235, 215));
        filterPanel.setPreferredSize(new Dimension(200, 0));
        filterPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));

        filterPanel.add(new JLabel("Tipo de Criatura:"));
        filterPanel.add(new JCheckBox("Bestas"));
        filterPanel.add(new JCheckBox("Dragões"));
        filterPanel.add(new JCheckBox("Mortos-vivos"));

        JButton btnApply = new JButton("Aplicar Filtros");
        btnApply.addActionListener(e -> filtrarCriaturas());
        filterPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        filterPanel.add(btnApply);

        return filterPanel;
    }
}
