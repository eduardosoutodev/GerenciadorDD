package model;

public class Personagem {
    private int id;
    private String nome;
    private String classe;
    private int nivel;
    private String raca;
    private String alinhamento;
    private int forca;
    private int destreza;
    private int constituicao;
    private int inteligencia;
    private int sabedoria;
    private int carisma;
    private int pvMax;
    private int pvAtual;
    private int ca;
    private String notas;
    private int campanhaId;

    // Construtores
    public Personagem() {}

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public int getNivel() { return nivel; }
    public void setNivel(int nivel) { this.nivel = nivel; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public String getAlinhamento() { return alinhamento; }
    public void setAlinhamento(String alinhamento) { this.alinhamento = alinhamento; }
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
    public int getPvMax() { return pvMax; }
    public void setPvMax(int pvMax) { this.pvMax = pvMax; }
    public int getPvAtual() { return pvAtual; }
    public void setPvAtual(int pvAtual) { this.pvAtual = pvAtual; }
    public int getCa() { return ca; }
    public void setCa(int ca) { this.ca = ca; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public int getCampanhaId() { return campanhaId; }
    public void setCampanhaId(int campanhaId) { this.campanhaId = campanhaId; }
}
