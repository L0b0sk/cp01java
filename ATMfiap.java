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
        public String toString(){   //metodo para exibir a transaçao formatada
            String sinal = tipo.equals("DEPOSITO") ?"+" : "-";  //setta o sinal de valor (+ para deposito e - para saque)
            return String.format("%-10s %s R$ %8.2f %s",    //retorna as strings formatadas co tipo, volr e data/hora 
                tipo, sinal, valor, getDataHotaFormatada());
        }
        // metodo getter para acessar os atributos
        public String getTipo(){return  tipo;}
        public double getValor(){return valor;}
        public LocalDateTime getDataHora(){return dataHora;}
    }
}