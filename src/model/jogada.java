
package model;

import exceptions.OpcaoInvalida;
import exceptions.PosicaoInexistente;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author joaos
 */
public class jogada 
{
    private static int x_max = 10;
    private static int y_max = 8;
    
    public static void jogada(campo campos,int x, int y, String opcao) throws IOException, InterruptedException
    {
        opcao = opcao.toLowerCase();
        
        try{
            switch(opcao)
            {
                case "mark":
                    campos.salvaArq();
                    marcarBandeira(campos, x, y);
                    break;
                case "open":
                    campos.salvaArq();
                    abrir(campos, x, y);
                    break;
                default:
                    throw new OpcaoInvalida(opcao);
                
                
            }
        }
        catch(OpcaoInvalida e)
        {
            e.toString();
        }

            
        }
    
    public static void jogada(campo campos, String opcao) throws IOException, InterruptedException
    {
        opcao = opcao.toLowerCase();
        
        try{
            switch(opcao)
            {
                case "exit":
                    System.exit(0);
                    break;
                case "reset":
                    reset();
                    break;
                default:
                    throw new OpcaoInvalida(opcao);
            }
        }
        catch(OpcaoInvalida e)
        {
            e.toString();
        }

            
        }

    public static void marcarBandeira(campo campos,int x, int y)
    {
        try{
            if((x>=10) || (y>=8) || (x<0) || (y<0))
            {
                throw new PosicaoInexistente(); 
            }
            campos.setBandeira(x,y);
            
        }catch(PosicaoInexistente e)
            {
                e.toString();
            } 

    }
        
    public static void abrir(campo campos, int x, int y) throws IOException, InterruptedException
    {
        if (campos.checaCampo(x,y))
        {
            System.out.println("Campo já aberto!");
        }
        else if (campos.checaBomba(x, y))
        {
            perdeu(campos, x,y);
            
        }
        else
        {
            for (int k = -1; k < 2 ; k++){
                for (int l = -1; l < 2; l++){
                    if (((x+k) > -1) && ((y+l)> -1) && ((x+k) < x_max) && ((y+l) < y_max) && (!campos.checaBomba(x+k, y+l)))            
                    {
                        campos.setCampo(x+k, y+l);
                    }
                            
                }
            }                
        }
     }
    public static void perdeu(campo campos, int x, int y) throws IOException, InterruptedException
    {
        Scanner sc = new Scanner(System.in);
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        
        for (int i = 0; i < x_max; i++) {
            for (int j = 0; j < y_max; j++) {
                if (campos.checaBomba(i, j))
                {
                    campos.setCampo(i, j);
                }
            }
        }
        
        campos.mostracampo();
        System.out.println("Ah, não! Havia uma bomba na posiçao" + "(" + x + "," + y + ")");
        System.out.println("Fim de jogo!");
        System.out.println("Pressione ENTER para finalizar o jogo: ");
        sc.nextLine();
        File arquivo = new File("campominado.txt");
        arquivo.delete();
        System.exit(0);
    }
    
    
    public static void reset() throws IOException, InterruptedException 
    {

        File arquivo = new File("campominado.txt");
        arquivo.delete();
        String[] args = null;     
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        Main.main(args);
        
    }
    
}
    

