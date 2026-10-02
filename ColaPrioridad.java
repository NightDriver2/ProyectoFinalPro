package ProyectoFinalPro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Estructura de Cola de Prioridades para la Fase II.
 * Gestiona objetos 'Tarea' priorizando menor valor numérico de prioridad (1 :
 * y desempate por la fecha de entrega más cercana.
 */
public class ColaPrioridad {

    private PriorityQueue<Tarea> cola;

    public ColaPrioridad() {
        // 1. Prioridad: menor valor int = mayor urgencia (1 es la más urgente)
        // 2. Fecha de entrega: la fecha más antigua/próxima va primero
        Comparator<Tarea> comparadorTareas = (t1, t2) -> {
        int compPrioridad = Integer.compare(t2.getPrioridad(), t1.getPrioridad());            
        if (compPrioridad != 0) {
                return compPrioridad; // Si las prioridades son distintas, define el orden
            }
            // Desempate por fecha de entrega
            if (t1.getFechaEntrega() != null && t2.getFechaEntrega() != null) {
                return t1.getFechaEntrega().compareTo(t2.getFechaEntrega());
            }
            return 0;
        };

        this.cola = new PriorityQueue<>(comparadorTareas);
    }

    // Insertar una tarea en la cola de prioridades
    public void enqueue(Tarea tarea) {
        if (tarea != null) {
            this.cola.add(tarea);
        }
    }

    // Extraer y retornar la tarea con mayor urgencia
    public Tarea dequeue() {
        return this.cola.poll();
    }

    // Consultar la tarea más urgente sin eliminarla
    public Tarea peek() {
        return this.cola.peek();
    }

    // Verificar si la cola está vacía
    public boolean isEmpty() {
        return this.cola.isEmpty();
    }

    // Cantidad de tareas en la cola
    public int size() {
        return this.cola.size();
    }

    // Obtener una copia de todas las tareas en cola (útil para la Vista Consola)
    public List<Tarea> getAll() {
        return new ArrayList<>(this.cola);
    }

    // Limpiar la cola de prioridades
    public void clear() {
        this.cola.clear();
    }
}





