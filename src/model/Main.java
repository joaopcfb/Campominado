package model;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

/**
 * @author Joao
 * Universidade Anhanguera de Niterói
 * DATA: 27/05/2021
 * Professor: Gabriel
 * Disciplina: Programação orientada à objetos 1
 * Alunos: Joao P. F. ; Gabriel R.
 * 
 */
public class Main {
    public static int x_max = 10;
    public static int y_max = 8;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("========================");
        System.out.println("Campo minado simples em java");
        System.out.println("Professor: Gabriel");
        System.out.println("Alunos: Joao P. F. ; Gabriel R.");
        System.out.println("========================");
        System.out.println("Aperte enter para iniciar: ");
        
        sc.nextLine();
        
        /* Apaga console */
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        
        
        campo campos;
        campos = learquivo();
        
        /* Lê a quantidade de bombas, antes checa se foi campos foi instanciado */ 
        
        if (campos == null)
        {
            int qtdbomba;
            while (true) {
                System.out.println("Digite a quantidade de bombas (de 1 até 79)");
                qtdbomba = Integer.parseInt(sc.nextLine());
                if ((qtdbomba >= 80) || (qtdbomba <= 0)) {
                    System.out.println("Quantidade invalida! digite novamente:");
                } else {
                    break;
                }
            }
            campos = new campo(qtdbomba);           
        }
            
        
        /* Looping que o usuario coloca os comandos e mostra o campo toda vez que uma jogada for realizada*/
        while(true){
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            campos.mostracampo();
            System.out.print("Legenda: " + (char)215 + " -Campo não foi aberto; " + (char)160 + " -Campo Vazio; " + (char)187 + " -Bandeira marcada; " + (char)164 + " -Bomba\n");
            System.out.print("Comandos: mark <x> <x> - marcar bandeira; open <x> <y> - abrir campo; exit - sair do programa; reset - resetar o programa\n");
            System.out.print("Digite o comando: ");
            String entrada = sc.nextLine();
            String [] argseparado  = Separaargs(entrada);
            String opcao = argseparado[0];
            if (argseparado.length > 2){
                int x = Integer.parseInt(argseparado[1]);
                int y = Integer.parseInt(argseparado[2]);
                jogada.jogada(campos, x, y, opcao);
            }
            else
            {
                jogada.jogada(campos, opcao);
            }
               
        }
           
    }
    
    
    /* Separa o comando das posicoes x e y */
    public static String[] Separaargs(String entrada)
    {
        String [] argseparado;
        entrada = entrada.replace(">", "");
        argseparado = entrada.split("<");
        for (int i=0; i<argseparado.length; i++){
            argseparado[i] = argseparado[i].replaceAll(" ", "");
        }
        return argseparado;
    
    }
    
    /*Funcao que verifica se tem um arquivo. Caso verdadeiro, pergunta se o usuario deseja retornar com o jogo salvo*/
    public static campo learquivo() throws IOException, InterruptedException
    {
        Scanner sc = new Scanner(System.in);
        boolean [][] bandeiras = new boolean[x_max][y_max];
        boolean [][] campo = new boolean[x_max][y_max];
        boolean [][] tembomba = new boolean[x_max][y_max];
        
        File arquivo = new File("campominado.txt");
        try
        {
            Scanner leitor;
            leitor = new Scanner(arquivo);
            System.out.println("Existe Jogo salvo! Deseja gerar o jogo salvo novamente? (y-sim, n-nao)");
            String escolha = sc.nextLine();
            if ("y".equals(escolha)){
            for (int i = 0; i < x_max; i++) {
                for (int j = 0; j < y_max; j++) {
                    campo[i][j] = leitor.nextBoolean();
                }    
            }
            for (int i = 0; i < x_max; i++) {
                for (int j = 0; j < y_max; j++) {
                    bandeiras[i][j] = leitor.nextBoolean();
                }    
            }
            for (int i = 0; i < x_max; i++) {
                for (int j = 0; j < y_max; j++) {
                    tembomba[i][j] = leitor.nextBoolean();
                }    
            }
           campo campos = new campo(campo, bandeiras, tembomba);
           return campos;
           }else
            {
                throw new FileNotFoundException(); 
            }
            
            
        }catch(FileNotFoundException ex)
        {
            System.out.println("Gerando novo jogo...");
            System.out.println("Pressione ENTER para continuar");
            sc.nextLine();
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            return null;
        }
        
    }
    
}
