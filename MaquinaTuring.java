package simuladormt;

import java.util.ArrayList;

public class MaquinaTuring {
    ArrayList<String> estados = new ArrayList<>();
    String estadoInicial = "";
    String estadoAceptacion = ""; 
    String estadoRechazo = "";    
    ArrayList<Character> alfabeto = new ArrayList<>();
    ArrayList<Transicion> transiciones = new ArrayList<>();

    public MaquinaTuring() {
    }
    
    public void mostrarDatos() {
        System.out.println("Estados: " + estados);
        System.out.println("Inicial: " + estadoInicial);
        System.out.println("Aceptacion (Ha): " + estadoAceptacion);
        System.out.println("Rechazo (He): " + estadoRechazo);
        System.out.println("Alfabeto: " + alfabeto);
        System.out.println("Transiciones cargadas: " + transiciones.size());
    }

    //SIMULA LA MAQUINA
    public void simular(String cadenaEntrada) {
        // 1. Validar que la cadena solo tenga caracteres del alfabeto
        for (int i = 0; i < cadenaEntrada.length(); i++) {
            if (!alfabeto.contains(cadenaEntrada.charAt(i))) {
                System.out.println("\nERROR: El caracter '" + cadenaEntrada.charAt(i) + "' no pertenece al alfabeto.");
                return;
            }
        }

        // 2. Crear la cinta infinita
        ArrayList<Character> cinta = new ArrayList<>();
        if (cadenaEntrada.isEmpty()) {
            cinta.add('-'); // Si no ingresan nada, ponemos un espacio en blanco
        } else {
            for (char c : cadenaEntrada.toCharArray()) {
                cinta.add(c);
            }
        }

        // 3. Preparar variables de movimiento
        String estadoActual = estadoInicial;
        int cabezal = 0; // Posición de la cabeza lectora

        System.out.println("\n--- INICIANDO SIMULACION ---");

        // 4. Bucle que se repite hasta que lleguemos al estado de Aceptación o Rechazo
        while (!estadoActual.equals(estadoAceptacion) && !estadoActual.equals(estadoRechazo)) {
            
            // Si el cabezal se sale por la derecha, agregamos un espacio en blanco a la cinta
            if (cabezal >= cinta.size()) {
                cinta.add('-');
            }
            // Si el cabezal se sale por la izquierda, agregamos un espacio al inicio
            if (cabezal < 0) {
                cinta.add(0, '-');
                cabezal = 0;
            }

            char simboloLeido = cinta.get(cabezal);
            
            // Mostrar en pantalla paso a paso
            imprimirCinta(cinta, estadoActual, cabezal);

            // Buscar la regla que coincida con el estado actual y el símbolo leído
            Transicion transicionAplicar = null;
            for (Transicion t : transiciones) {
                if (t.estadoActual.equals(estadoActual) && t.simboloLeido == simboloLeido) {
                    transicionAplicar = t;
                    break;
                }
            }

            // Si no encuentra una regla para moverse, se traba (Rechazo)
            if (transicionAplicar == null) {
                System.out.println("-> No hay regla para el estado " + estadoActual + " leyendo '" + simboloLeido + "'");
                estadoActual = estadoRechazo;
                break;
            }

            // Aplicar la regla: Cambiar estado, escribir símbolo y mover cabezal
            estadoActual = transicionAplicar.estadoSiguiente;
            cinta.set(cabezal, transicionAplicar.simboloEscrito);

            if (transicionAplicar.movimiento == 'D') {
                cabezal++;
            } else if (transicionAplicar.movimiento == 'I') {
                cabezal--;
            }
        }

        // Mostrar el estado de la cinta en el último paso
        imprimirCinta(cinta, estadoActual, cabezal);

        // 5. Imprimir el veredicto final
        System.out.println("\n*********************************");
        if (estadoActual.equals(estadoAceptacion)) {
            System.out.println("RESULTADO FINAL: ACEPTACION");
        } else {
            System.out.println("RESULTADO FINAL: NO ACEPTACION");
        }
        System.out.println("*********************************\n");
    }

    // Método de apoyo para dibujar la cinta bonito en la consola
    private void imprimirCinta(ArrayList<Character> cinta, String estado, int posicionCabezal) {
        System.out.print("Cinta: [ ");
        for (int i = 0; i < cinta.size(); i++) {
            if (i == posicionCabezal) {
                System.out.print(">" + cinta.get(i) + "< "); // Marca dónde está el cabezal
            } else {
                System.out.print(cinta.get(i) + " ");
            }
        }
        System.out.println("] | Estado: " + estado);
    }
}