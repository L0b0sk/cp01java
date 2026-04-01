import java.util.Scanner;       // Importa a classe Scanner para ler dados do teclado
import java.util.ArrayList;     // Importa a classe ArrayList para armazenar o histórico de transações
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

        /**
     * Método que exibe o logo do ATM FIAP com efeito de digitação
     * @throws InterruptedException Necessário para o Thread.sleep()
     */

    public static void exibitLogo() throws InterruptedException{    // Array com cada linha do logo (arte ASCII)
        String[] logo = {
           "",
            "    █████╗ ████████╗███╗   ███╗    ███████╗██╗ █████╗ ██████╗ ",
            "   ██╔══██╗╚══██╔══╝████╗ ████║    ██╔════╝██║██╔══██╗██╔══██╗",
            "   ███████║   ██║   ██╔████╔██║    █████╗  ██║███████║██████╔╝",
            "   ██╔══██║   ██║   ██║╚██╔╝██║    ██╔══╝  ██║██╔══██║██╔═══╝ ",
            "   ██║  ██║   ██║   ██║ ╚═╝ ██║    ██║     ██║██║  ██║██║     ",
            "   ╚═╝  ╚═╝   ╚═╝   ╚═╝     ╚═╝    ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝     ",
            "",
            "   =======================================================",
            "   ============ SISTEMA DE AUTOMACAO BANCARIA ============",
            "   =======================================================",
            ""
        };
        for (String linha : logo) {  // Percorre cada linha do array de logo
            digitar(linha, 3);      // Chama o método digitar para cada linha com delay de 3ms entre caracteres
            Thread.sleep(30);   // Aguarda 30ms entre cada linha para dar efeito visual
        }
        Thread.sleep(800);  // Aguarda 800ms após mostrar todo o logo
    }

       /**
     * Método para exibir o histórico de transações
     * @param historico ArrayList contendo todas as transações
     */
    public static void exibirHistorico(ArrayList<transacao> historico) {
        System.out.println("\n=============================================================");
        System.out.println("                    HISTORICO DE MOVIMENTACOES                 ");
        System.out.println("=============================================================");
        
     
        if (historico.isEmpty()) {     // Verifica se o histórico está vazio
            System.out.println("\n  Nenhuma movimentacao realizada ainda.");
            System.out.println("  Realize depositos ou saques para ver o historico.");
        } else {
            // Exibe o cabeçalho da tabela
            System.out.println("\n  TIPO       VALOR          DATA E HORA");
            System.out.println("  -----------------------------------------");
            
            // Percorre todas as transações do histórico
            for (int i = 0; i < historico.size(); i++) {
                transacao t = historico.get(i);
                // Exibe cada transação formatada
                System.out.println("  " + t.toString());
            }
            
            // Exibe o rodapé com o total de transações
            System.out.println("\n  Total de movimentacoes: " + historico.size());
        }
        
        System.out.println("\n=============================================================");
    }
}