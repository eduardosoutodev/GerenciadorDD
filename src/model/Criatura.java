package model;

public class Criatura {
    private int id;
    private String nome;
    private String tipo;
    private String nd;
    private String tamanho;
    private int ca;
    private String pv;
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;
    private String habilidades;
    private String acoes;

    // Construtores
    public Criatura() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getNd() { return nd; }
    public void setNd(String nd) { this.nd = nd; }
    public String getTamanho() { return tamanho; }
    public void setTamanho(String tamanho) { this.tamanho = tamanho; }
    public int getCa() { return ca; }
    public void setCa(int ca) { this.ca = ca; }
    public String getPv() { return pv; }
    public void setPv(String pv) { this.pv = pv; }
    public int getForca() { return forca; }
    public void setForca(int forca) { this.forca = forca; }
    public int getDestreza() { return destreza; }
    public void setDestreza(int destreza) { this.destreza = destreza; }
    public int getConstituicao() { return constituicao; }
    public void setConstituicao(int constituicao) { this.constituicao = constituicao; }
    public int getInteligencia() { return inteligencia; }
    public void setInteligencia(int inteligencia) { this.inteligencia = inteligencia; }
    public int getSabedoria() { return sabedoria; }
    public void setSabedoria(int sabedoria) { this.sabedoria = sabedoria; }
    public int getCarisma() { return carisma; }
    public void setCarisma(int carisma) { this.carisma = carisma; }
    public String getHabilidades() { return habilidades; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }
    public String getAcoes() { return acoes; }
    public void setAcoes(String acoes) { this.acoes = acoes; }
}
