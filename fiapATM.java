import java.util.Scanner;   // Importa a classe Scanner para ler dados do teclado
import java.util.ArrayList; // Importa a classe ArrayList para armazenar o histórico de transações
import java.time.LocalDateTime; // Importa as classes para trabalhar com data e hora
import java.time.format.DateTimeFormatter;


public class fiapATM{   // Declaração da classe principal do programa
    
    // ==================== CLASSE INTERNA PARA TRANSAÇÕES ====================
    
    /**
     * Classe que representa uma transação bancária (depósito ou saque)
     * Esta classe armazena o tipo, valor, data e hora da transação
     */

    static class Transacao {    // Atributos da transação
        private String tipo;        // Tipo: "DEPOSITO" ou "SAQUE"
        private double valor;       // Valor da transação
        private LocalDateTime dataHora;  // Data e hora da transação
        
        public Transacao(String tipo, double valor) {   // Construtor da classe Transacao
            this.tipo = tipo;
            this.valor = valor;
            this.dataHora = LocalDateTime.now();    // Captura a data e hora atual no momento da transação
        }           
        
        
        public String getDataHoraFormatada() {  // Método para formatar a data e hora em um formato legível
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");   // Define o formato desejado: dia/mês/ano hora:minuto:segundo
            return dataHora.format(formatter);
        }
        
        public String toString() {  // Método para exibir a transação formatada
            String sinal = tipo.equals("DEPOSITO") ? "+" : "-"; // Seta o sinal do valor (+ para depósito, - para saque)
            return String.format("%-10s %s R$ %8.2f  %s",   // Retorna a string formatada com tipo, valor e data/hora
                tipo, sinal, valor, getDataHoraFormatada());
        }
        
        public String getTipo() { return tipo; }    // Métodos getters para acessar os atributos
        public double getValor() { return valor; }
        public LocalDateTime getDataHora() { return dataHora; }
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    /**
     * Método que exibe texto com efeito de digitação (PyFillet style)
     * @param texto O texto que será exibido
     * @param delay Tempo em milissegundos entre cada caractere
     * @throws InterruptedException Necessário para o Thread.sleep()
     */

    public static void digitar(String texto, int delay) throws InterruptedException {
        for (char c : texto.toCharArray()) {    // Converte a string em um array de caracteres e percorre cada um
            System.out.print(c);    // Imprime um caractere por vez
            Thread.sleep(delay);    // Aguarda o tempo definido antes de imprimir o próximo caractere
        }
        System.out.println();   // Após imprimir todos os caracteres, pula para a próxima linha
    }
    
    /**
     * Método que limpa o terminal (funciona em Windows, Linux e Mac)
     */

    public static void limparTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {    // Verifica qual sistema operacional está sendo usado
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();   // Se for Windows, executa o comando "cls" (clear screen)
            } else {
                System.out.print("\033[H\033[2J");  // Se for Linux ou Mac, usa código de escape ANSI para limpar
                System.out.flush(); // Força a execução imediata do comando
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {   // Se falhar (ex: IDE não suporta), imprime várias linhas em branco como fallback
                System.out.println();
            }
        }
    }   
    
    /**
     * Método que exibe o logo do ATM FIAP com efeito de digitação
     * @throws InterruptedException Necessário para o Thread.sleep()
     */

    public static void exibirLogo() throws InterruptedException {   // Array com cada linha do logo (arte ASCII)
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
        
        for (String linha : logo) { // Percorre cada linha do array de logo
            digitar(linha, 3);  // Chama o método digitar para cada linha com delay de 3ms entre caracteres
            Thread.sleep(30);   // Aguarda 30ms entre cada linha para dar efeito visual
        }
        Thread.sleep(800);  // Aguarda 800ms após mostrar todo o logo
    }
    
    /**
     * Método para exibir o histórico de transações
     * @param historico ArrayList contendo todas as transações
     * @param historico Arraylist contendo tods as transações 
     */

    public static void exibirSaldoComHistorico(double saldo, ArrayList <Transacao>historico) {
        System.out.println("\n=============================================================");
        System.out.println("                    CONSULTA DE SALDO                        ");
        System.out.println("=============================================================");

        System.out.printf("\n SALDO ATUAL: R$ %.2f%n",saldo);

        System.out.println("\n-------------------------------------------------------------");
        System.out.println("                    ULTIMAS MOVIMENTACOES                    ");
        System.out.println("-------------------------------------------------------------");
        
        if (historico.isEmpty()) {  // Verifica se o histórico está vazio
            System.out.println("\n  Nenhuma movimentacao realizada ainda.");
            System.out.println("  Realize depositos ou saques para ver o historico.");
        } else {
            // Exibe o cabeçalho da tabela
            System.out.println("\n  TIPO       VALOR          DATA E HORA");
            System.out.println("  -----------------------------------------");
            
            for (int i = 0; i < historico.size(); i++) {    // Percorre todas as transações do histórico
                Transacao t = historico.get(i);
                // Exibe cada transação formatada
                System.out.println("  " + t.toString());
            }
            
            System.out.println("\n  Total de movimentacoes: " + historico.size());  // Exibe o rodapé com o total de transações
        }
        
        System.out.println("\n=============================================================");
    }
    
    // ==================== MÉTODO PRINCIPAL ====================
    
    /**
     * Método principal onde o programa começa a executar
     * @param args Argumentos da linha de comando (não usado)
     * @throws InterruptedException Necessário para os métodos com Thread.sleep()
     */
    public static void main(String[] args) throws InterruptedException {
        
        // ========== PARTE 1: EFEITO PYFILET E LOGO ==========
        
        limparTerminal();   // Limpa o terminal antes de começar a exibir qualquer coisa
        
        digitar("=============================================================", 20);   // Exibe uma borda superior com efeito de digitação
        digitar("                   BANCO FIAP DIGITAL                      ", 20); // Exibe o título do banco com efeito de digitação
        digitar("=============================================================", 20);
        
        Thread.sleep(500);  // Aguarda 500ms antes de continuar
        
        System.out.println();   // Pula uma linha
        
        digitar("Inicializando sistema", 40);   // Exibe texto de inicialização com efeito de digitação
        digitar(".", 200);  // Exibe pontos com delay para simular carregamento
        digitar(".", 200);
        digitar(".", 200);
        
        
        System.out.println();   // Pula uma linha
        
        Thread.sleep(500);  // Aguarda 500ms
        
        exibirLogo();   // Chama o método que exibe o logo completo
        
        Thread.sleep(500);  // Aguarda 500ms após o logo
        
        // ========== PARTE 2: CADASTRO DO USUÁRIO ==========
        
        Scanner scanner = new Scanner(System.in);   // Cria um objeto Scanner para ler dados do teclado
        
        
        System.out.print("\nDigite seu nome completo: ");   // Solicita o nome completo do usuário
        String nomeCompleto = scanner.nextLine().trim();    // Lê a linha inteira, remove espaços extras no início e fim
        String primeiroNome = nomeCompleto.split(" ")[0];   // Pega o primeiro nome (divide por espaços e pega a primeira posição)
        
        System.out.println();   // Pula uma linha
        
        digitar("Bem-Vindo " + primeiroNome + "!", 50); // Exibe mensagem de boas-vindas personalizada com efeito de digitação
        
        Thread.sleep(500);  // Aguarda 500ms
        
        // ========== PARTE 3: CADASTRO DE SENHA ==========
        
        // Expressão regular para validar senha forte
        // ^ = início da string
        // (?=.*[0-9]) = deve conter pelo menos um número
        // (?=.*[A-Z]) = deve conter pelo menos uma letra maiúscula
        // (?=.*[!@#$%¨&*()\\-_+=?><]) = deve conter pelo menos um caractere especial
        // .{8,} = mínimo de 8 caracteres
        // $ = fim da string
        String regexSenha = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%¨&*()\\-_+=?><]).{8,}$";
        
        String senha;   // Variável para armazenar a senha
        
        // Exibe os requisitos da senha
        System.out.println("\n-------------------------------------------------------------");
        System.out.println("           REQUISITOS PARA SENHA FORTE:               ");
        System.out.println("  - Minimo de 8 caracteres                            ");
        System.out.println("  - Pelo menos 1 numero                               ");
        System.out.println("  - Pelo menos 1 letra MAIUSCULA                      ");
        System.out.println("  - Pelo menos 1 caractere especial (!@#$%¨&* etc)   ");
        System.out.println("-------------------------------------------------------------");
        
        while(true) {   // Loop infinito até a senha ser válida
            System.out.print("\nCadastre uma senha forte: ");   // Solicita a senha (visível durante o cadastro)
            senha = scanner.nextLine(); // Lê a senha digitada
            
            if (senha.matches(regexSenha)) {    // Verifica se a senha atende aos requisitos da regex
                digitar("Senha cadastrada com sucesso!", 40);   // Se for válida, mostra mensagem de sucesso
                break;
            } else {
                System.out.println("Senha invalida! Tente novamente."); // Se for inválida, mostra mensagem de erro e repete
                System.out.println("   Lembre-se: 8+ caracteres, 1 numero, 1 maiuscula, 1 caractere especial\n");
            }
        }
        
        Thread.sleep(1000); // Aguarda 1 segundo
        
        // ========== PARTE 4: LOGIN ==========
        
        int tentativas = 0; // Variável para contar as tentativas de login
        boolean autenticado = false;    // Variável para controlar se o usuário foi autenticado
        
        System.out.println("\nREALIZE O LOGIN PARA ACESSAR O SISTEMA"); // Exibe mensagem de login
        
        while (tentativas < 3) {    // Loop que permite no máximo 3 tentativas
            System.out.print("Digite sua senha para login: ");  // Solicita a senha para login (visível)
            String tentativa = scanner.nextLine();  // Lê a tentativa de senha
            
            if (tentativa.equals(senha)) {  // Compara a senha digitada com a senha cadastrada
                autenticado = true; // Se acertou, marca como autenticado e sai do loop
                break;
            } else {
                tentativas++;   // Se errou, incrementa o contador de tentativas
                System.out.println("Senha incorreta! Tentativas restantes: " + (3 - tentativas));    // Mostra mensagem de erro com tentativas restantes
            }
        }
        
        if (!autenticado) { // Verifica se após as tentativas o usuário não foi autenticado
            System.out.println("\n=============================================================");
            System.out.println("                    ACESSO BLOQUEADO!                     ");
            System.out.println("          Numero maximo de tentativas excedido.          ");
            System.out.println("=============================================================");
            scanner.close();    // Fecha o scanner para liberar recursos
            return;
        }
        
        // ========== PARTE 5: LIMPA O TERMINAL APÓS LOGIN ==========
        
        
        limparTerminal();   // Chama o método que limpa o terminal
        
        System.out.println();    // Exibe mensagem de sucesso no login com efeito de digitação
        digitar("=============================================================", 30);
        digitar("                     LOGIN REALIZADO!                     ", 30);
        digitar("              BEM-VINDO AO SEU BANCO DIGITAL              ", 30);
        digitar("=============================================================", 30);
        
        Thread.sleep(1000); // Aguarda 1 segundo
        
        exibirLogo();   // Exibe o logo novamente para dar abertura
        
        System.out.println();   // Mensagem de saudação personalizada
        digitar("Ola " + primeiroNome + "! Seu acesso foi liberado com sucesso.", 45);
        digitar("Como podemos ajudar voce hoje?", 45);
        
        
        Thread.sleep(1000); // Aguarda 1 segundo
        
        // ========== PARTE 6: MENU PRINCIPAL COM HISTÓRICO ==========
        
        double saldo = 0.0; // Variável para armazenar o saldo (inicialmente zero)
        int opcao;  // Variável para armazenar a opção escolhida pelo usuário
        
        ArrayList<Transacao> historico = new ArrayList<Transacao>();    // Cria um ArrayList para armazenar o histórico de transações
        
        do {
            System.out.println("\n-------------------------------------------------------------");
            System.out.println("                      MENU PRINCIPAL                      ");
            System.out.println("-------------------------------------------------------------");
            System.out.println("  [1]  CONSULTAR SALDO E HISTORICO                                   ");
            System.out.println("  [2]  FAZER DEPOSITO                                    ");
            System.out.println("  [3]  FAZER SAQUE                                       ");
            System.out.println("  [4]  SAIR                                              ");
            System.out.println("-------------------------------------------------------------");
            System.out.print("Escolha uma opcao: ");
            
            opcao = scanner.nextInt();  // Lê a opção escolhida pelo usuário
            
            switch (opcao) {    // Switch para executar a ação conforme a opção escolhida
                case 1: // Consultar Saldo
                    exibirSaldoComHistorico(saldo, historico);
                    break;
                
                case 2: // Fazer Depósito
                    System.out.print("\nDigite o valor do deposito: R$ ");  // Solicita o valor do depósito
                    double deposito = scanner.nextDouble();
                    
                    if (deposito > 0) {
                        saldo += deposito;
                        
                        Transacao depositoTransacao = new Transacao("DEPOSITO", deposito);  // Cria uma nova transação do tipo DEPOSITO
                        historico.add(depositoTransacao);   // Adiciona a transação ao histórico
                        
                        System.out.printf("Deposito de R$ %.2f realizado com sucesso!%n", deposito);    // Exibe mensagem de sucesso
                        System.out.printf("Novo saldo: R$ %.2f%n", saldo);
                    } else {
                        System.out.println("Valor invalido! O deposito deve ser maior que zero.");  // Mensagem de erro para valor inválido
                    }
                    break;
                
                case 3: // Fazer Saque
                    System.out.print("\nDigite o valor para saque: R$ ");
                    double saque = scanner.nextDouble();
                    
                    if (saque <= 0) {
                        System.out.println("Valor invalido! O saque deve ser maior que zero.");
                    } 
                    else if (saque > saldo) {   // Verifica se tem saldo suficiente
                        System.out.println("Saldo insuficiente!");
                        System.out.printf("Saldo disponivel: R$ %.2f%n", saldo);
                    } 
                    else {
                        saldo -= saque;
                        
                        Transacao saqueTransacao = new Transacao("SAQUE", saque);   // Cria uma nova transação do tipo SAQUE
                        historico.add(saqueTransacao);  
                        
                        System.out.printf("Saque de R$ %.2f realizado com sucesso!%n", saque);
                        System.out.printf("Novo saldo: R$ %.2f%n", saldo);
                    }
                    break;
                case 4: // Sair
                    System.out.println("\n-------------------------------------------------------------");
                    System.out.println("    O Banco FIAP agradece sua preferencia!                ");
                    System.out.println("              Volte sempre!                                ");
                    System.out.println("-------------------------------------------------------------");
                    break;
                
                default: // Opção inválida
                    System.out.println("Opcao invalida! Por favor, escolha uma opcao entre 1 e 5.");
            }
            
        } while (opcao != 4); // Continua enquanto o usuário não escolher sair (opção 5)
        
        scanner.close();    // Fecha o scanner para liberar recursos
        
        System.out.println("\nSistema encerrado. Ate mais!");
    }
}