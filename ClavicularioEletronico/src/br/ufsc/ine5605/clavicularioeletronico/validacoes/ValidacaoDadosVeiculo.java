package br.ufsc.ine5605.clavicularioeletronico.validacoes;

/**
 *
 * @author Gabriel
 */
public class ValidacaoDadosVeiculo {
    
    private ValidacaoDadosVeiculo() {
        
    }
    
    public static boolean validaPlaca(String placa) {
        if (!placa.matches("[A-Z]{3}-{1}\\d{4}")) {
            System.out.println("A placa deve seguir o seguinte formato: (AAA-9999)");
            return false;
        }
        return true;
    }
    
    public static boolean validaModelo(String modelo) {
        if (!modelo.matches("^[^ ]*[a-zA-Z0-9 ]+")) {
            System.out.println("Você deve digitar o modelo do carro.");
            return false;
        }
        return true;
    }
    
    public static boolean validaMarca(String marca) {
        if (!marca.matches("^[^ ]*[a-zA-Z0-9 ]+")) {
            System.out.println("Você deve digitar a marca do carro.");
            return false;
        }
        return true;
    }
    
    public static boolean validaAno(String ano) {
        if (!ano.matches("\\d{4}")) {
            System.out.println("Você deve informar um ano com 4 digitos.");
            return false;
        }
        return true;
    }
    
    public static boolean validaQuilometragem(String quilometragem) {
        if (!quilometragem.matches("[0-9]+")) {
            System.out.println("Você deve informar a quilometragem do carro com 1 ou mais digitos.");
            return false;
        }
        return true;
    }
}
