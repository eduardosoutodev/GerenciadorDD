package view;

import model.Personagem;
import model.Item;
import service.AtributoService;
import service.ServiceException;
import service.ServiceFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelCharacterSheet extends JPanel {
    private MainFrame mainFrame;
    private Personagem personagem;
    private final AtributoService atributoService = ServiceFactory.getAtributoService();
    private JTable invTable;
    private DefaultTableModel invModel;

    public PanelCharacterSheet(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        refresh();
    }

    public void refresh() {
        carregarDados();
        removeAll();
        setupUI();
        revalidate();
        repaint();
    }

    private void setupUI() {
        JPanel topBar = createTopBar();
        add(topBar, BorderLayout.NORTH);

        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 235, 215));

        JPanel leftPanel = createLeftPanel();
        mainContent.add(leftPanel, BorderLayout.WEST);

        JTabbedPane tabbedPane = createTabbedPane();
        mainContent.add(tabbedPane, BorderLayout.CENTER);

        JPanel rightPanel = createRightPanel();
        mainContent.add(rightPanel, BorderLayout.EAST);

        add(mainContent, BorderLayout.CENTER);

        carregarInventario();
    }

    // Antes chamava PersonagemDAO diretamente e criava um "personagem padrão"
    // aqui mesmo dentro da view; agora essa decisão é do PersonagemService.
    private void carregarDados() {
        try {
            this.personagem = ServiceFactory.getPersonagemService().obterPersonagemAtualOuPadrao();
        } catch (ServiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            this.personagem = new Personagem();
        }
    }

    private void carregarInventario() {
        if (personagem.getId() > 0) {
            try {
                List<Item> itens = ServiceFactory.getItemService().listarPorPersonagem(personagem.getId());
                invModel.setRowCount(0);
                for (Item i : itens) {
                    invModel.addRow(new Object[]{i.getNome(), i.getQuantidade(), i.getPeso() + " kg"});
                }
            } catch (ServiceException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
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

        String titulo = personagem.getNome() + " - " + personagem.getClasse() + " Nível " + personagem.getNivel();
        JLabel titleLabel = new JLabel(titulo, SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topBar.add(titleLabel, BorderLayout.CENTER);

        return topBar;
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(245, 235, 215));
        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel imgPlaceholder = new JPanel();
        imgPlaceholder.setPreferredSize(new Dimension(200, 200));
        imgPlaceholder.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imgPlaceholder.add(new JLabel("IMAGEM DO PERSONAGEM", SwingConstants.CENTER));
        leftPanel.add(imgPlaceholder);

        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(new JLabel("Raça: " + personagem.getRaca()));
        leftPanel.add(new JLabel("Alinhamento: " + personagem.getAlinhamento()));

        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        String[][] attrData = {
            {"FOR", String.valueOf(personagem.getForca())},
            {"DES", String.valueOf(personagem.getDestreza())},
            {"CON", String.valueOf(personagem.getConstituicao())},
            {"INT", String.valueOf(personagem.getInteligencia())},
            {"SAB", String.valueOf(personagem.getSabedoria())},
            {"CAR", String.valueOf(personagem.getCarisma())}
        };
        for (String[] attr : attrData) {
            JPanel attrRow = new JPanel(new BorderLayout());
            attrRow.setBackground(new Color(120, 40, 40));
            attrRow.setMaximumSize(new Dimension(200, 30));
            JLabel lblName = new JLabel(attr[0]);
            lblName.setForeground(Color.WHITE);
            lblName.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            JLabel lblVal = new JLabel(attr[1], SwingConstants.CENTER);
            lblVal.setForeground(Color.BLACK);
            lblVal.setOpaque(true);
            lblVal.setBackground(Color.WHITE);
            lblVal.setPreferredSize(new Dimension(50, 0));
            attrRow.add(lblName, BorderLayout.WEST);
            attrRow.add(lblVal, BorderLayout.EAST);
            leftPanel.add(attrRow);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        return leftPanel;
    }

    private JTabbedPane createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel skillsPanel = new JPanel();
        skillsPanel.setLayout(new BoxLayout(skillsPanel, BoxLayout.Y_AXIS));
        skillsPanel.setBackground(new Color(245, 235, 215));

        // Antes: strings fixas ("BÔNUS DE ATAQUE: +5"). Agora calculado de
        // verdade a partir dos atributos do personagem via AtributoService.
        int bonusAtaque = atributoService.calcularBonusAtaque(personagem);
        int bonusAtletismo = atributoService.calcularBonusPericia(personagem, "forca");
        int bonusFurtividade = atributoService.calcularBonusPericia(personagem, "destreza");

        skillsPanel.add(new JLabel("BÔNUS DE ATAQUE: " + atributoService.formatarModificador(bonusAtaque)));
        skillsPanel.add(new JSeparator());
        skillsPanel.add(new JLabel("TESTES DE PERÍCIA: Atletismo "
                + atributoService.formatarModificador(bonusAtletismo) + ", Furtividade "
                + atributoService.formatarModificador(bonusFurtividade)));
        skillsPanel.add(new JSeparator());
        skillsPanel.add(new JLabel("TRAÇOS & TALENTOS: Sentido Aguçado, Inimigo Favorito"));
        tabbedPane.addTab("Habilidades", skillsPanel);

        JPanel inventoryPanel = createInventoryPanel();
        tabbedPane.addTab("Inventário", inventoryPanel);

        JPanel notesPanel = new JPanel(new BorderLayout());
        JTextArea txtNotas = new JTextArea(personagem.getNotas());
        notesPanel.add(new JScrollPane(txtNotas), BorderLayout.CENTER);

        JButton btnSalvarNotas = new JButton("Salvar Notas");
        btnSalvarNotas.addActionListener(e -> {
            try {
                ServiceFactory.getPersonagemService().atualizarNotas(personagem, txtNotas.getText());
                JOptionPane.showMessageDialog(this, "Notas salvas com sucesso!");
            } catch (ServiceException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
        notesPanel.add(btnSalvarNotas, BorderLayout.SOUTH);

        tabbedPane.addTab("Notas", notesPanel);

        return tabbedPane;
    }

    private JPanel createInventoryPanel() {
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(new Color(245, 235, 215));

        String[] columns = {"Item", "Qtd", "Peso"};
        invModel = new DefaultTableModel(columns, 0);
        invTable = new JTable(invModel);
        inventoryPanel.add(new JScrollPane(invTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Adicionar Item");
        btnAdd.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome do Item:");
            if (nome != null && !nome.isEmpty()) {
                try {
                    // Valores padrão (quantidade=1, peso=1.0) agora são regra
                    // de negócio no ItemService, não mais "mágicos" na view.
                    ServiceFactory.getItemService().adicionarItemPadrao(nome, personagem.getId());
                    carregarInventario();
                } catch (ServiceException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });
        btnPanel.add(btnAdd);
        inventoryPanel.add(btnPanel, BorderLayout.SOUTH);

        return inventoryPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(245, 235, 215));
        rightPanel.setPreferredSize(new Dimension(200, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        rightPanel.add(new JLabel("Pontos de Vida: " + personagem.getPvAtual() + " / " + personagem.getPvMax()));
        JProgressBar hpBar = new JProgressBar(0, personagem.getPvMax());
        hpBar.setValue(personagem.getPvAtual());
        hpBar.setForeground(Color.RED);
        rightPanel.add(hpBar);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        JPanel acPanel = new JPanel(new BorderLayout());
        acPanel.setBackground(new Color(100, 30, 30));
        JLabel lblAc = new JLabel("Classe de Armadura", SwingConstants.CENTER);
        lblAc.setForeground(Color.WHITE);
        JLabel lblAcVal = new JLabel(String.valueOf(personagem.getCa()), SwingConstants.CENTER);
        lblAcVal.setFont(new Font("Arial", Font.BOLD, 24));
        lblAcVal.setForeground(Color.WHITE);
        acPanel.add(lblAc, BorderLayout.NORTH);
        acPanel.add(lblAcVal, BorderLayout.CENTER);
        acPanel.setMaximumSize(new Dimension(150, 80));
        rightPanel.add(acPanel);

        return rightPanel;
    }
}
