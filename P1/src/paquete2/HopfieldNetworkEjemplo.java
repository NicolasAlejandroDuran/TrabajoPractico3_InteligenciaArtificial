package paquete2;
public class HopfieldNetworkEjemplo {
    private int[][] weights; // Matriz de pesos para la red
    private int[] state;     // Estado actual de las neuronas

    // Constructor que inicializa la matriz de pesos y el estado de las neuronas
    public HopfieldNetworkEjemplo(int size) {
        weights = new int[size][size]; // Inicializaci�n de la matriz de pesos a cero
        state = new int[size];         // Inicializaci�n del estado de las neuronas
    }

    // M�todo para entrenar la red con patrones dados
    public void train(int[][] patterns) {
        // Recorrido de cada patr�n
        for (int[] pattern : patterns) {
            // Actualizaci�n de los pesos seg�n la regla de Hebb
            for (int i = 0; i < pattern.length; i++) {
                for (int j = 0; j < pattern.length; j++) {
                    if (i != j) { // No se actualizan los pesos de las neuronas consigo mismas
                        weights[i][j] += pattern[i] * pattern[j];
                    }
                }
            }
        }
    }

    // M�todo para establecer el estado inicial de las neuronas
    public void setState(int[] initialState) {
        state = initialState.clone(); // Clonaci�n del estado inicial para evitar efectos colaterales
    }

    // M�todo para ejecutar la red y actualizar el estado de las neuronas
    public void run() {
        boolean stable; // Variable para verificar si la red ha alcanzado un estado estable
        do {
            stable = true; // Asumimos que la red es estable hasta que se demuestre lo contrario
            for (int i = 0; i < state.length; i++) {
                int sum = 0; // Variable para almacenar la suma ponderada de entradas
                for (int j = 0; j < state.length; j++) {
                    sum += weights[i][j] * state[j]; // C�lculo de la suma ponderada
                }
                int newState = sum >= 0 ? 1 : -1; // Determinaci�n del nuevo estado de la neurona
                if (newState != state[i]) { // Si el estado cambia, la red no es estable
                    state[i] = newState; // Actualizaci�n del estado de la neurona
                    stable = false; // Marcar la red como inestable
                }
            }
        } while (!stable); // Repetir hasta que la red sea estable
    }

    // M�todo para obtener el estado actual de las neuronas
    public int[] getState() {
        return state;
    }

    // M�todo principal para ejecutar el ejemplo del prototipo
    public static void main(String[] args) {
        // Patrones de entrenamiento (pueden agregarse m�s patrones)
        int[][] patterns = {
            {1, -1, 1, -1, 1, -1, 1, -1, 1, -1},
        };

        // Creaci�n de la red de Hopfield con 10 neuronas
        HopfieldNetworkEjemplo network = new HopfieldNetworkEjemplo(10);
        // Entrenamiento de la red con los patrones dados
        network.train(patterns);

        // Estado inicial que puede contener ruido
        int[] initialState = {1, -1, 1, -1, 1, -1, 1, -1, 1, -1};
        // Establecer el estado inicial de la red
        network.setState(initialState);
        // Ejecutar la red para alcanzar un estado estable
        network.run();

        // Obtener el estado final de la red
        int[] finalState = network.getState();
        // Imprimir el estado final
        for (int s : finalState) {
            System.out.print(s + " ");
        }
    }
}
