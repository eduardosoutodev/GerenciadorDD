package model;

public class Campanha {
    private int id;
    private String nome;
    private String descricao;
    private String mestre;

    public Campanha() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getMestre() { return mestre; }
    public void setMestre(String mestre) { this.mestre = mestre; }
}
