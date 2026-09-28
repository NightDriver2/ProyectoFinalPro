package ProyectoFinalPro; 

import java.util.Comparator;
import java.util.PriorityQueue;

public class ColaPrioridad {
    private PriorityQueue<Empleado> cola;

    public ColaPrioridad() {
        // Comparator to order employees by their ID
        Comparator<Empleado> comparator = new Comparator<Empleado>() {
            @Override
            public int compare(Empleado e1, Empleado e2) {
                return Integer.compare(e1.getId(), e2.getId());
            }
        };
        cola = new PriorityQueue<>(comparator);
    }

    public void agregarEmpleado(Empleado empleado) {
        cola.add(empleado);
    }

    public Empleado eliminarEmpleado() {
        return cola.poll();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int obtenerTamaño() {
        return cola.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cola de Prioridad de Empleados:\n");
        for (Empleado empleado : cola) {
            sb.append(empleado.toString()).append("\n");
        }
        return sb.toString();
    }
}