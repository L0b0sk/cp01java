import java.util.Scanner;       // Importa a classe Scanner para ler dados do teclado
import java.util.ArrayList; // Importa a classe ArrayList para armazenar o histórico de transações
import java.time.LocalDateTime; // Importa as classes para trabalhar com data e hora
import java.time.format.DateTimeFormatter;

//declarando classe que vou trabalhar 
public class ATMfiap {

    /* 
    * Classe que representa uma transação bancária (depósito ou saque)
    * Esta classe armazena o tipo, valor, data e hora da transação 
    */

    static class transacao{
        private String tipo;    //tipo de transacao (DEPOSITO ou SAQUE)
        private double valor;   //valor da transacao 
        private LocalDateTime dataHora; //data e hora da transacao 

        //construtor da classe transacao
        public transacao(String tipo, double valor) {
            this.tipo = tipo;
            this.valor = valor;
            this.dataHora = LocalDateTime.now();   //captura a data e a hora da transacao 
        }
        
        public String getDataHotaFormatada(){   // formatando data e hora  em formato legivel
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return dataHora.format(formatter);
        }
         // Métodos getters para acessar os atributos
        public String getTipo() { return tipo; }
        public double getValor() { return valor; }
        public LocalDateTime getDataHora() { return dataHora; }
    }

    /**
     * Método que exibe texto com efeito de digitação (PyFillet style)
     * @param texto O texto que será exibido
     * @param delay Tempo em milissegundos entre cada caractere
     * @throws InterruptedException Necessário para o Thread.sleep()
     */

    public static void digitar(String texto, int delay) throws InterruptedException{    //converte a string em um array e percorre caractere por caractere
        for(char c : texto.toCharArray()) {
            System.out.print(c);    //imprime um caractere por vez
            Thread.sleep(delay);    //espera um tem para imprimir o proximo caractere
        }
        System.out.println();
    }
    /**
     * metodo para limpaar terminal tanto windows, mac, quanto no linux
     */
  public static void limparTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {    // Verifica qual sistema operacional está sendo usado
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();   // Se for Windows, executa o comando "cls" (clear screen)
            } else {    // Se for Linux ou Mac, usa código de escape ANSI para limpar
                System.out.print("\033[H\033[2J");
                System.out.flush(); // Força a execução imediata do comando
            }
        } catch (Exception e) { // Se falhar (ex: IDE não suporta), imprime várias linhas em branco como fallback
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }
}