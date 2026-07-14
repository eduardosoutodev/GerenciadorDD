package view;

import model.Campanha;
import service.ServiceException;
import service.ServiceFactory;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CadastroPersonagemVIEW extends JFrame {
    private JTextField txtNome, txtClasse, txtNivel, txtRaca, txtAlinhamento;
    private JTextField txtFor, txtDes, txtCon, txtInt, txtSab, txtCar;
    private JTextField txtPvMax, txtCa;
    private JComboBox<String> cbCampanha;
    private List<Campanha> campanhas;
    private MainFrame mainFrame;

    public CadastroPersonagemVIEW(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setTitle("Novo Personagem");
        setSize(500, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 235, 215));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; txtNome = new JTextField(15); panel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Classe:"), gbc);
        gbc.gridx = 1; txtClasse = new JTextField(15); panel.add(txtClasse, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Nível:"), gbc);
        gbc.gridx = 1; txtNivel = new JTextField("1", 5); panel.add(txtNivel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Raça:"), gbc);
        gbc.gridx = 1; txtRaca = new JTextField(15); panel.add(txtRaca, gbc);

        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Alinhamento:"), gbc);
        gbc.gridx = 1; txtAlinhamento = new JTextField(15); panel.add(txtAlinhamento, gbc);

        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("FOR:"), gbc);
        gbc.gridx = 1; txtFor = new JTextField("10", 5); panel.add(txtFor, gbc);

        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel("DES:"), gbc);
        gbc.gridx = 1; txtDes = new JTextField("10", 5); panel.add(txtDes, gbc);

        gbc.gridx = 0; gbc.gridy = 7; panel.add(new JLabel("CON:"), gbc);
        gbc.gridx = 1; txtCon = new JTextField("10", 5); panel.add(txtCon, gbc);

        gbc.gridx = 0; gbc.gridy = 8; panel.add(new JLabel("INT:"), gbc);
        gbc.gridx = 1; txtInt = new JTextField("10", 5); panel.add(txtInt, gbc);

        gbc.gridx = 0; gbc.gridy = 9; panel.add(new JLabel("SAB:"), gbc);
        gbc.gridx = 1; txtSab = new JTextField("10", 5); panel.add(txtSab, gbc);

        gbc.gridx = 0; gbc.gridy = 10; panel.add(new JLabel("CAR:"), gbc);
        gbc.gridx = 1; txtCar = new JTextField("10", 5); panel.add(txtCar, gbc);

        gbc.gridx = 0; gbc.gridy = 11; panel.add(new JLabel("PV Máx:"), gbc);
        gbc.gridx = 1; txtPvMax = new JTextField("10", 5); panel.add(txtPvMax, gbc);

        gbc.gridx = 0; gbc.gridy = 12; panel.add(new JLabel("CA:"), gbc);
        gbc.gridx = 1; txtCa = new JTextField("10", 5); panel.add(txtCa, gbc);

        gbc.gridx = 0; gbc.gridy = 13; panel.add(new JLabel("Campanha:"), gbc);
        gbc.gridx = 1; cbCampanha = new JComboBox<>();
        carregarCampanhas();
        panel.add(cbCampanha, gbc);

        JPanel btnPanel = new JPanel();
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        btnPanel.add(btnSalvar);
        btnPanel.add(btnCancelar);

        add(new JScrollPane(panel), BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void carregarCampanhas() {
        try {
            campanhas = ServiceFactory.getCampanhaService().listar();
            cbCampanha.addItem("Nenhuma");
            for (Campanha c : campanhas) {
                cbCampanha.addItem(c.getNome());
            }
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void salvar() {
        Integer campanhaId = null;
        int idx = cbCampanha.getSelectedIndex();
        if (idx > 0) {
            campanhaId = campanhas.get(idx - 1).getId();
        }

        try {
            ServiceFactory.getPersonagemService().criar(
                    txtNome.getText(), txtClasse.getText(), txtNivel.getText(),
                    txtRaca.getText(), txtAlinhamento.getText(),
                    txtFor.getText(), txtDes.getText(), txtCon.getText(),
                    txtInt.getText(), txtSab.getText(), txtCar.getText(),
                    txtPvMax.getText(), txtCa.getText(), campanhaId);
            JOptionPane.showMessageDialog(this, "Personagem criado com sucesso!");
            mainFrame.showScreen("CharacterSheet");
            dispose();
        } catch (ServiceException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
