/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author joaos
 */
public class OpcaoInvalida extends Exception{
    private String opcao;
    
    public OpcaoInvalida(String opcao)
    {
        super();
        this.opcao = opcao;
    }
    
    @Override
    public String toString()
    {
        return opcao + " não é uma opcao válida!";
    }       
    
}
