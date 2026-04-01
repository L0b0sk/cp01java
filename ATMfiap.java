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
    }
}