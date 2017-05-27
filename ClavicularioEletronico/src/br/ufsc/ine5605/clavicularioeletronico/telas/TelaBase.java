package br.ufsc.ine5605.clavicularioeletronico.telas;

import br.ufsc.ine5605.clavicularioeletronico.enums.Cargo;
import br.ufsc.ine5605.clavicularioeletronico.validacoes.ValidacaoDadosFuncionario;
import br.ufsc.ine5605.clavicularioeletronico.validacoes.ValidacaoDadosVeiculo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Tela base para funções básicas que todas as telas usam
 * @author Flávio
 */
public abstract class TelaBase {
    
    protected Scanner teclado;
    
    public TelaBase() {
        teclado = new Scanner(System.in);
    }
    
    public void solicitaEnterParaContinuar() {
        System.out.println("Aperte ENTER para continuar...");
        this.teclado.nextLine();
    }
    
    public int inputOpcao() {
        System.out.println("-------------------------------------------------");
        System.out.print("Opção escolhida: ");
        int opcao;
        try {
            opcao = Integer.parseInt(this.teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("A opcao precisa ser um numero!");
            opcao = -1;
        }
        return opcao;
    }

    public int inputMatricula() throws Exception {       
        String input;
        int tentativa = 0;
        int matricula = -1;
        while (tentativa < 3) {
            System.out.println("Digite a matricula: ");
            input = this.teclado.nextLine().trim();
            if (ValidacaoDadosFuncionario.validaMatricula(input)) {
                matricula = Integer.parseInt(input);
                return matricula;
            }
            tentativa++;
        }
        throw new Exception("Numero de tentativas excedido!");
    }
    
    public String inputNome() throws Exception {
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite o nome do funcionario: ");
            String nome = this.teclado.nextLine().trim();
            if (ValidacaoDadosFuncionario.validaNome(nome)) {
                return nome;
            }
            tentativa++;
        }
        throw new Exception("Numero de tentativas excedido!");
    }
    
    public Date inputDataNascimento() throws Exception {
        int tentativa = 0;
        Date nascimento = null;
        while (tentativa < 3) {
            System.out.println("Digite a data de nascimento do funcionário (dd/mm/aaaa): ");
            String data = this.teclado.nextLine().trim();
            if (ValidacaoDadosFuncionario.validaDataNascimento(data)) {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    nascimento = formato.parse(data);
                } catch (ParseException e) {            
                }
                return nascimento;
            }
            tentativa++;  
        }        
        throw new Exception("Numero de tentativas excedido!");
    }
    
    public String inputTelefone() throws Exception {
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite o telefone do funcionário: ");
            String telefone = this.teclado.nextLine().trim();
            if (ValidacaoDadosFuncionario.validaTelefone(telefone)) {
                return telefone;    
            }
            tentativa++;            
        }
        throw new Exception("Numero de tentativas excedido!");     
    }
    
    public Cargo inputCargo() throws Exception{
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite o cargo do funcionário");
            System.out.println("1 - Diretoria");       
            System.out.println("2 - Motorista:");
            String input = this.teclado.nextLine().trim();
            int opcao = 0;
            if (ValidacaoDadosFuncionario.validaCargo(input)) {
                opcao = Integer.parseInt(input);
                switch (opcao) {
                    case 1: return Cargo.DIRETORIA;

                    case 2: return Cargo.MOTORISTA;
                }   
                return null;
            }
            tentativa++;
        }
        throw new Exception("Numero de tentativas excedido!"); 
    }

    public String inputPlaca() throws Exception{
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite a placa do veiculo. Ex: (AAA-9999): ");
            String placa = this.teclado.nextLine().trim();
            if (ValidacaoDadosVeiculo.validaPlaca(placa)) {
                return placa;
            }
            tentativa++;
        }
        throw new Exception("Numero de tentativas excedido!"); 
    }
    
    public String inputModelo() throws Exception{
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite o modelo do veiculo: ");
            String modelo = this.teclado.nextLine().trim();
            if (ValidacaoDadosVeiculo.validaModelo(modelo)) {
                return modelo;    
            }
            tentativa++;                      
        }
        throw new Exception("Numero de tentativas excedido!");
    }
    
    public String inputMarca() throws Exception{
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite a marca do veiculo: ");
            String marca = this.teclado.nextLine().trim();
            if (ValidacaoDadosVeiculo.validaMarca(marca)) {
            return marca;    
            }
            tentativa++;            
        }
        throw new Exception("Numero de tentativas excedido!");
    }
    
    public int inputAno() throws Exception{
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite o ano do veiculo: ");
            String input = this.teclado.nextLine().trim();
            if (ValidacaoDadosVeiculo.validaAno(input)) { 
                return Integer.parseInt(input);
            }
            tentativa++;
        }
        throw new Exception("Numero de tentativas excedido!");
    }
    
    public int inputQuilometragemAtual() throws Exception{
        int tentativa = 0;
        while (tentativa < 3) {
            System.out.println("Digite a quilometragem atual do veiculo: ");
            String input = this.teclado.nextLine().trim();
            if (ValidacaoDadosVeiculo.validaQuilometragem(input)) {
                return Integer.parseInt(input);
            }
            tentativa++;
        }
        throw new Exception("Numero de tentativas excedido!");
    }
    
    public String inputConfirmacao() {
        System.out.println("(S/N):");
        return this.teclado.nextLine();
    }
    
    protected boolean pedeConfirmacao() {
        String opcao = inputConfirmacao().toLowerCase();
        return opcao.matches("s|sim|y|yes");
    }
    
}
