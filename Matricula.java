public class Matricula {

    private String codigoDisciplina;
    private double nota;
    public Matricula(String codigoDisciplina, double nota) {
        setCodigoDisciplina(codigoDisciplina);
        setNota(nota);
    }
    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }
    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = (codigoDisciplina == null) ? "" : codigoDisciplina.trim();
    }
    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }
    @Override public String toString() {
        return codigoDisciplina + "(" + String.format("%.1f", nota) + ")";
    }
}