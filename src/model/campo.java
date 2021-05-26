
package model;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author joaos
 */
public class campo {
    /* quatro matrizes booleanas: matriz campo diz se o campo já foi aberto. a tembomba diz se existem bombas. a de bandeiras diz se existem bandeiras. bombperto diz o numero de bombas perto de um campo*/
    private final boolean [][] campo;
    private final boolean [][] tembomba;
    private boolean [][] bandeiras;
    private int [][] bombperto;
    private int x_max = 10;
    private int y_max = 8;
    private int num_bandeiras = 0;
    private int qtd_bombas = 0;
    
    /*construtor de um novo jogo*/
    public campo(int qtdbomba) 
    {
        this.bombperto = new int[x_max][y_max];
        this.bandeiras = new boolean[x_max][y_max];
        this.campo = new boolean[x_max][y_max];
        this.tembomba = new boolean[x_max][y_max];
        this.qtd_bombas = qtdbomba;
        gerabombas(qtdbomba);
        geraBombperto();
        this.bandeiras = new boolean[x_max][y_max];

    
    }
    
    /* construtor de um jogo que estava salvo*/
    public campo(boolean [][] campo, boolean [][] bandeiras, boolean [][] tembomba) 
    {
        this.bandeiras = bandeiras;
        this.campo = campo;
        this.tembomba = tembomba;
        this.bombperto = new int[x_max][y_max];
        geraBombperto();

    
    }
    
    public boolean checaBomba(int x, int y)
    {
        return (tembomba[x][y]);
    }
    
    public boolean checaCampo(int x, int y)
    {
        return (campo[x][y]);
    }
    /* coloca bandeiras. quando o numero de bandeiras for igual ao de bombas, ele checa se o usuario venceu */
    public void setBandeira(int x, int y)
    {
        if (!(bandeiras[x][y]))
        {
            (bandeiras[x][y]) = true;
            num_bandeiras++;
        }
        else
        {
            (bandeiras[x][y]) = false;
            num_bandeiras--;
        }
        if (num_bandeiras == qtd_bombas)
        {
            checavenceu();
        }
    }
    
    public void setCampo(int x, int y)
    {
        campo[x][y] = true;
    }
            
    /* funcao que gera bombas */
    public void gerabombas(int num_bomb)
    {
        Random local_bomb = new Random();
        
        for (int i = 0; i<num_bomb; i++){
            /*Gera coordenadas e checa pra ver se já nao existe bomba no local gerado*/
            while(true)
            {
                int localbombx = local_bomb.nextInt(10);
                int localbomby = local_bomb.nextInt(8);
                if (tembomba[localbombx][localbomby] == false)
                {
                    tembomba[localbombx][localbomby] = true;
                    break;
                }
            }
                               
        }
    
    }
    
    /* funcao que vai verificar se existe bomba perto de um campo, se sim, ele conta a quantidade de bombas e coloca na matriz */
    public void geraBombperto()
    {
        for (int i = 0; i < 10; i++) 
        {
            for (int j = 0; j < 8; j++) 
            {
                int num_bomb_perto = 0;
                    for (int k = -1; k < 2 ; k++) 
                    {
                        for (int l = -1; l < 2; l++) 
                        {
                            if (((i-1) > -1) && ((j-1)> -1) && ((i+1) < x_max) && ((j+1) < y_max))
                            {
                                if (tembomba[i+k][j+l])
                                {
                                    num_bomb_perto++;
                                }
                            }
                            
                        }
                    }
                    bombperto[i][j] = num_bomb_perto; 
                
                
            }
        
        }
    }
    
    public void checavenceu()
    {
        Scanner sc = new Scanner(System.in);
        boolean venceu = true;
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++) {
                if (!(bandeiras[i][j] == tembomba[i][j]));
                    venceu = false;
            }
        }
        if (venceu)
        {
            System.out.println("Parabéns! você venceu!");
            sc.nextLine();
            System.exit(0);
        }else
            System.out.println("Opss! Parece que uma bandeira está errada");
        
    }
    
    public void mostracampo()
    {
        System.out.println("======Campo======");
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < 10; i++)
        {
            System.out.print(i + " ");
            for (int j = 0; j < 8; j++) {
                if ((campo[i][j] == true) && (bombperto[i][j] == 0) && (!(tembomba[i][j])))
                {
                    System.out.print((char)160 + " ");
                }else 
                    if((campo[i][j] == true) && (!(tembomba[i][j])))
                    {
                        System.out.print(bombperto[i][j] + " ");
                    }
                
                    else 
                    if(bandeiras[i][j] == true)
                    {
                        System.out.print((char)187 + " ");
                    }
                    else if((campo[i][j]) && (tembomba[i][j]))
                    {
                        System.out.print((char)164 + " ");
                    }
                    else
                    {
                        System.out.print((char)215 + " ");
                    }
            }
            System.out.println("");
        }
        System.out.println("");
        
        
        
        /* Codígo que gera os locais com bombas, só para teste*/
//        System.out.println("  0 1 2 3 4 5 6 7");
//        for (int i = 0; i < 10; i++)
//        {
//            System.out.print(i + " ");
//            for (int j = 0; j < 8; j++) {
//
//                    if(tembomba[i][j])
//                    {
//
//                        System.out.print("B ");
//                    }
//                    else 
//                    {
//                        System.out.print((char)187 + " ");
//                    }
//
//            }
//            System.out.println("");
//        }
//            
//        
//            
    }   
    
      public void salvaArq()
    {
        File arquivo = new File("campominado.txt");
        try(PrintWriter fw = new PrintWriter(arquivo)) {
            for (int i = 0; i < x_max; i++) {
                    for (int j = 0; j < y_max; j++) {
                        fw.print(campo[i][j] + " ");
                    }
                    fw.print("\n");
                }
            for (int i = 0; i < x_max; i++) {
                for (int j = 0; j < y_max; j++) {
                    fw.print(bandeiras[i][j] + " ");
                }
                fw.print("\n");
            }
            for (int i = 0; i < x_max; i++) {
                for (int j = 0; j < y_max; j++) {
                    fw.print(tembomba[i][j] + " ");
                }
                fw.print("\n");
            }
        }catch (IOException ex){
            System.out.println("Nao foi possivel salvar o arquivo");
        }
        
    }
    }  
    


    

