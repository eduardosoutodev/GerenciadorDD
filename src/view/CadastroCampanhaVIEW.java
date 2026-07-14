package view;

import service.ServiceException;
import service.ServiceFactory;
import javax.swing.*;
import java.awt.*;

/**
 * Responsabilidade única (após refatoração): apenas montar a tela e
 * repassar as ações do usuário para o CampanhaService. Validação de
 * campos e persistência não vivem mais aqui.
 */
public class CadastroCampanhaVIEW extends JFrame {
    private JTextField txtNome, txtMestre;
    private JTextArea txtDescricao;
    private MainFrame mainFrame;

    public CadastroCampanhaVIEW(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle("Nova Campanha");
        setSize(400, 350);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 235, 215));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nome da Campanha:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        panel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Mestre:"), gbc);
        gbc.gridx = 1;
        txtMestre = new JTextField(20);
        panel.add(txtMestre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        txtDescricao = new JTextArea(5, 20);
        panel.add(new JScrollPane(txtDescricao), gbc);

        JPanel btnPanel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        btnPanel.add(btnSalvar);
        btnPanel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void salvar() {
        try {
            ServiceFactory.getCampanhaService().criar(
                    txtNome.getText(), txtDescricao.getText(), txtMestre.getText());
            JOptionPane.showMessageDialog(this, "Campanha criada com sucesso!");
            mainFrame.showScreen("Dashboard");
            dispose();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
