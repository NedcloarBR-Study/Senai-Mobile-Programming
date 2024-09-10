package Atividades.Atividade01;

public class ComputadorMain {
    public static void main(String[] args) {
        Computador computador = new Computador("Pedro", 1200);
        
        computador.setNome("Pedro");
        computador.setClock(1200);

        System.out.println("Nome: " + computador.getNome());
        System.out.println("Clock: " + computador.getClock() + " MHz");
    }
}
