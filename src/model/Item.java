package model;

public class Item {
    private int id;
    private String nome;
    private int quantidade;
    private double peso;
    private int personagemId;

    public Item() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public int getPersonagemId() { return personagemId; }
    public void setPersonagemId(int personagemId) { this.personagemId = personagemId; }
}
