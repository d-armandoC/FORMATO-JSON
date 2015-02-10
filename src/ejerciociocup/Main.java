/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciociocup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego C
 */
public class Main {

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//        String path="D:/Veronica C/Desktop/EjerciocioCupJson/ejemplo.lex";
//        generarLexer(path);
//        //Ya no necesitamos en código de arriba porque
////        try{
////            //probamos nuestra clase Lexer.java
////            probarLexerFile();
////        }
////        catch(IOException ex){
////            System.out.println(ex.getMessage());
////        }
//    }
    public static void generarLexer(String path) {
        File file = new File(path);
        JFlex.Main.generate(file);
    }

//    public static void probarLexerFile() throws IOException{
//        Lexer lexer = new Lexer(System.in);
//        while(true){
//            Token token = lexer.yylex();
//            if(token==null){
//                System.out.println("EOF");
//                return;
//            }
//            switch(token){
//                case ID: case INT:
//                    System.out.println("TOKEN: "+token+" "+lexer.lexeme);
//                break;
//                default:
//                    System.out.println("TOKEN: "+token);
//            }
//        }
//    }
    public final static int GENERAR = 1;
    public final static int EJECUTAR = 2;
    public final static int SALIR = 3;

    /**
     * Es un menu para elegir entre generar el analizador lexico y sintactico, o
     * ejecutarlos sobre un archivo de pruebas.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.util.Scanner in = new Scanner(System.in);
        int valor = 0;
        do {
            System.out.println("Elija una opcion: ");
            System.out.println("1) Generar");
            System.out.println("2) Ejecutar");
            System.out.println("3) Salir");
            System.out.print("Opcion: ");
            try {
                valor = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Se ha cancelado la ejecución porque has ingresado Letras"
                        + " Vuelve a Ejecutar");
                valor=3;
            }
           
              switch (valor) {

                case GENERAR: {
                    System.out.println("\n*** Generando ***\n");
                    String archLexico = "";
                    String archSintactico = "";
                    if (args.length > 0) {
                        System.out.println("\n*** Procesando archivos custom ***\n");
                        archLexico = args[0];
                        archSintactico = args[1];
                    } else {
                        System.out.println("\n*** Procesando archivo default ***\n");
                        archLexico = "ejemplo.lex";
                        archSintactico = "sintactico.cup";
                    }
                    String[] alexico = {archLexico};
                    String[] asintactico = {"-parser", "AnalizadorSintactico", archSintactico};
                    jflex.Main.main(alexico);
                    try {
                        java_cup.Main.main(asintactico);
                    } catch (Exception ex) {
                        Logger.getLogger(EjerciocioCup.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //movemos los archivos generados
                    boolean mvAL = moverArch("Lexer.java");
                    boolean mvAS = moverArch("AnalizadorSintactico.java");
                    boolean mvSym = moverArch("sym.java");
                    if (mvAL && mvAS && mvSym) {
                        System.exit(0);
                    }
                    System.out.println("Generado!");
                    break;
                }
                case EJECUTAR: {
                    /*  Ejecutamos el analizador lexico y sintactico
                     1    sobre un archivo de pruebas. 
                     */
                    String[] archivoPrueba = {"test.txt"};
                    AnalizadorSintactico.main(archivoPrueba);
                    System.out.println("Ejecutado!");
                    break;
                }
                case SALIR: {
                    System.out.println("Adios!");
                    break;
                }
                default: {
                    System.out.println("Opcion no valida!");
                    break;
                }
            }
           
        
        } while (valor != 3);

    }

    public static boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            System.out.println("\n*** Moviendo " + arch + " \n***");
            Path currentRelativePath = Paths.get("");
            String nuevoDir = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "ejerciociocup" + File.separator + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            if (arch.renameTo(new File(nuevoDir))) {
                System.out.println("\n*** Generado " + archNombre + "***\n");
                efectuado = true;
            } else {
                System.out.println("\n*** No movido " + archNombre + " ***\n");
            }

        } else {
            System.out.println("\n*** Codigo no existente ***\n");
        }
        return efectuado;
    }
}
