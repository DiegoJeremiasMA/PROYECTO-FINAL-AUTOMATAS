package simuladormt;
public class Transicion {
    String estadoActual;
    char simboloLeido;
    String estadoSiguiente;
    char simboloEscrito;
    char movimiento; // Puede ser 'D' (Derecha), 'I' (Izquierda) o 'N' (Ninguno)

    // Este es el constructor para guardar los datos fácilmente
    public Transicion(String estadoActual, char simboloLeido, String estadoSiguiente, char simboloEscrito, char movimiento) {
        this.estadoActual = estadoActual;
        this.simboloLeido = simboloLeido;
        this.estadoSiguiente = estadoSiguiente;
        this.simboloEscrito = simboloEscrito;
        this.movimiento = movimiento;
    }
}