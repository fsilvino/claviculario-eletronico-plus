package br.ufsc.ine5605.clavicularioeletronico.validacoes;

/**
 *
 * @author Gabriel
 */
public class ValidacaoDadosFuncionario {

    private ValidacaoDadosFuncionario() {
    }
    
    public static boolean validaMatricula(String matricula) {
        if (!matricula.matches("^[1-9][0-9]*")) {
            System.out.println("A matricula deve ser um numero positivo!");
            return false;
        }
        return true;
    }
    
    public static boolean validaNome(String nome) {
        if (!nome.matches("[a-zA-Z ]+")) {
            System.out.println("Você deve digitar um nome!");
            return false;
        }
        return true;
    }
    
    public static boolean validaDataNascimento(String dataNascimento) {
        if (!dataNascimento.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("Você deve digitar uma data no formato dd/mm/aaaa");
            return false;
        }
        return true;
    }
    
    public static boolean validaTelefone(String telefone) {
        if (!telefone.matches("\\d{8,}+")) {
            System.out.println("Você deve informar seu telefone contendo no mínimo 8 digitos!");
            return false;
        }
        return true;
    }
    
    public static boolean validaCargo(String cargo) {
        if (!cargo.matches("1|2")) {
            System.out.println("Você deve escolher o cargo 1 ou 2!");
            return false;
        }
        return true;
    }
}
