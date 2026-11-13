package simuladormt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class SimuladorMT {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;

        System.out.println("--- SIMULADOR DE MAQUINA DE TURING ---");

        // Ciclo principal: Permite cargar nuevas máquinas
        while (!salir) {
            MaquinaTuring maquina = new MaquinaTuring();
            System.out.print("\nIngrese el nombre del archivo de la maquina (ejemplo: maquina1.txt): ");
            String rutaArchivo = teclado.nextLine();
            
            try {
                BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
                String linea;
                
                // PROCESAMIENTO DEL ARCHIVO
                while ((linea = lector.readLine()) != null) {
                    linea = linea.trim();
                    if (linea.isEmpty()) continue;

                    if (linea.startsWith("Estados(")) {
                        String contenido = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")"));
                        String[] partes = contenido.split(",");
                        for (String p : partes) {
                            maquina.estados.add(p.trim());
                        }
                    } 
                    else if (linea.startsWith("Inicial(")) {
                        maquina.estadoInicial = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")")).trim();
                    } 
                    else if (linea.startsWith("Ha(")) {
                        maquina.estadoAceptacion = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")")).trim();
                    } 
                    else if (linea.startsWith("He(")) {
                        maquina.estadoRechazo = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")")).trim();
                    } 
                    else if (linea.startsWith("Alfabeto(")) {
                        String contenido = linea.substring(linea.indexOf("(") + 1, linea.indexOf(")"));
                        String[] partes = contenido.split(",");
                        for (String p : partes) {
                            maquina.alfabeto.add(p.trim().charAt(0));
                        }
                    } 
                    else if (linea.startsWith("MT(")) {
                        String[] lados = linea.split("=");
                        String izq = lados[0].substring(lados[0].indexOf("(") + 1, lados[0].indexOf(")"));
                        String der = lados[1].substring(lados[1].indexOf("(") + 1, lados[1].indexOf(")"));
                        
                        String[] izqPartes = izq.split(",");
                        String[] derPartes = der.split(",");
                        
                        String estAct = izqPartes[0].trim();
                        char simLeido = izqPartes[1].trim().charAt(0);
                        
                        String estSig = derPartes[0].trim();
                        char simEscrito = derPartes[1].trim().charAt(0);
                        char mov = derPartes[2].trim().charAt(0);
                        
                        Transicion t = new Transicion(estAct, simLeido, estSig, simEscrito, mov);
                        maquina.transiciones.add(t);
                    }
                }
                lector.close();
                System.out.println("¡Archivo cargado con exito!");
                
                //MODO INTERACTIVO (INGRESO DE CINTA)
                boolean cambiarMaquina = false;
                while (!cambiarMaquina && !salir) {
                    System.out.print("\nIngrese los caracteres para la cinta y presione ENTER: ");
                    String cadenaCinta = teclado.nextLine();
                    
                    //SIMULACION
                    maquina.simular(cadenaCinta);
                    
                    // Preguntamos qué hacer después
                    System.out.println("¿Que desea hacer ahora?");
                    System.out.println("1. Ingresar una nueva cinta (misma maquina)");
                    System.out.println("2. Cargar una nueva definicion de maquina");
                    System.out.println("3. Salir");
                    System.out.print("Elija una opcion: ");
                    String opcion = teclado.nextLine();
                    
                    if (opcion.equals("2")) {
                        cambiarMaquina = true; // Rompe el ciclo interno, vuelve a pedir archivo
                    } else if (opcion.equals("3")) {
                        salir = true; // Rompe todos los ciclos, termina el programa
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Error al procesar el archivo. Verifique que exista y este bien escrito.");
            }
        }
        System.out.println("\nGracias por usar el Simulador de Maquina de Turing.");
    }
}