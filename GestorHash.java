package ProyectoFinalPro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class GestorHash {
    HashMap<Integer, Tarea> mapaTareas;
    HashMap<Integer, Empleado> mapaEmpleados;
    
    public GestorHash() {
        this.mapaTareas = new HashMap<>();
        this.mapaEmpleados = new HashMap<>();
    }

    // ------------------ HashMap para acomodar las tareas lol ------------------
    public void guardarTarea(Tarea tarea) {
        if(tarea == null) return;

        mapaTareas.put(tarea.getId(), tarea);
        System.out.println("La tarea se guardo en la linea");
    }

    public Tarea buscarIdTarea(int id) {
        return mapaTareas.get(id);
    }

    public boolean existeTarea(int id) {
        return mapaTareas.containsKey(id);
    }

    public boolean eliminarTarea(int id) {
        if(mapaTareas.containsKey(id)) {
            mapaTareas.remove(id);
            System.out.println("Tarea #" + id + " fue eliminada exitosamente");
            return true;
        }

        System.out.println("La tarea #" + id + " no existe brou :(.");
        return false;
    }

    public void mostrarTodasTareas() {
        if(mapaTareas.isEmpty()) {
            System.out.println("No hay ninguna tarea en la tabla de tareas ;)");
            return;
        }

        System.out.println("\\n---- TABLA HASH DE TAREAS (Total: " + mapaTareas.size() + ") ----");
        for(Entry<Integer, Tarea> entry : mapaTareas.entrySet()) {
            System.out.println("[ID: " + entry.getKey() + "] " + entry.getValue());
        }
    }

    // ------------------ HashMap para acomodar las empleados lol ------------------
    public void guardarEmpleado(Empleado empleado) { 
        if (empleado == null) return; 

        mapaEmpleados.put(empleado.getId(), empleado); 
        System.out.println("Empleado #" + empleado.getId() + " se guardo exitosamente."); 
    }

    public Empleado buscarIdEmpleado(int id) { 
        return mapaEmpleados.get(id); 
    }

    public boolean existeEmpleado(int id) { 
        return mapaEmpleados.containsKey(id); 
    }

    public boolean eliminarEmpleado(int id) {
        if(mapaEmpleados.containsKey(id)) {
            mapaEmpleados.remove(id);
            System.out.println("Empleado #" + id + " fue eliminada exitosamente");
            return true;
        }

        System.out.println("El empleado #" + id + " no existe brou :(.");
        return false;
    }

    public void mostrarTodosEmpleados() {
        if(mapaEmpleados.isEmpty()) {
            System.out.println("No hay ningun empleado en la tabla de empleados ;)");
            return;
        }

        System.out.println("\\n---- TABLA HASH DE EMPLEADOS (Total: " + mapaEmpleados.size() + ") ----");
        for(Entry<Integer, Empleado> entry : mapaEmpleados.entrySet()) {
            System.out.println("[ID: " + entry.getKey() + "] " + entry.getValue());
        }
    }

    // ------------------ POR SI SI O POR SI NO ------------------
    public HashMap<Integer, Tarea> getMapaTareas() {
        return mapaTareas;
    }

    public HashMap<Integer, Empleado> getMapaEmpleado() {
        return mapaEmpleados;
    }

    public List<Empleado> obtenerTodos() {
        return new ArrayList<>(mapaEmpleados.values());
    }
}
