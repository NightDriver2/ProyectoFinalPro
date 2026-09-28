package ProyectoFinalPro;

import java.util.*;

public class GrafoDependencias {
        private Map<Integer, List<Integer>> grafo;

        public GrafoDependencias() {
            this.grafo = new HashMap<>();
        }

        public void registrarTarea(int idTarea) {
            grafo.putIfAbsent(idTarea, new ArrayList<>());
        }

        public void agregarDependecia(int idTarea, int idTareaPrevia) {
            registrarTarea(idTarea);
            registrarTarea(idTareaPrevia);

            if (!grafo.get(idTarea).contains(idTareaPrevia)) {
                grafo.get(idTarea).add(idTareaPrevia);
                System.out.println("Dependencia agregada: Tarea " + idTarea + " depende de Tarea " + idTareaPrevia);
            }
        }

        public void sincronizarTarea(Tarea tarea){
            registrarTarea(tarea.getId());
            for (int idPrevia : tarea.getDependencias()) {
                agregarDependecia(tarea.getId(), idPrevia);
            }
        }

        public boolean depenciasSatisfechas(int idTarea, Set<Integer> tareasCompletadas) {
            List<Integer> previas = grafo.get(idTarea);
           if (previas == null || previas.isEmpty()) { 
            return true; 
        }

        for (int idPrevia : previas) {
            if (!tareasCompletadas.contains(idPrevia)) {
                return false;
            }
        }
        return true;
    }

    public boolean esAsignable(Tarea tarea, Empleado empleado, Set<Integer> tareasCompletadas) {
        if (!tarea.getDepartamento().equalsIgnoreCase(empleado.getDepartamento())) {
            System.out.println("El empleado " + empleado.getNombre() + " no pertenece al departamento de la tarea " + tarea.getNombre());
            return false;
        }

        if (!dependenciasSatisfechas(tarea.getId(), tareasCompletadas)) {
            System.out.println("La tarea " + tarea.getNombre() + " no puede ser asignada a " + empleado.getNombre() + " porque no se han completado todas las tareas previas.");
            return false;
        }
        System.out.println("La tarea " + tarea.getNombre() + " puede ser asignada a " + empleado.getNombre());
        return true;
    }

    public boolean tineCiclo() {
        Set<Integer> visitados = new HashSet<>();
        Set<Integer> enRecursion = new HashSet<>();

        for (Integer nodo : grafo.keySet()) {
            if (dfsCiclo(nodo, visitados, enRecursion)) {
                return true;
            }
        }
        return false;
    }

    private boolean dfsCiclo(Integer actual, Set<Integer> visitados, Set<Integer> enRecursion) {
        if (enRecursion.contains(actual)) return true;
        if (visitados.contains(actual)) return false;
        
        visitados.add(actual);
        enRecursion.add(actual);

        for (Integer vecino : grafo.getOrDefault(actual, new ArrayList<>())) {
            if (dfsCiclo(vecino, visitados, enRecursion)) {
                return true;
            }
        }

        enRecursion.remove(actual);
        return false;
    }

    public List<Integer> obtenerOrdenEjecucion() {
        if (tieneCiclo()) {
            System.out.println("No se puede obtener un orden de ejecución debido a un ciclo en las dependencias.");
            return new ArrayList<>();
        }

        Stack<Integer> pilaAccion = new Stack<>();
        Set<Integer> visitados = new HashSet<>();

        for (Integer nodo : grafo.keySet()) {
            if (!visitados.contains(nodo)) {
                topologicalSort(nodo, visitados, pilaAccion);
            }
        }

        List<Integer> orden = new ArrayList<>();
        while (!pilaAccion.isEmpty()) {
            orden.add(pilaAccion.pop());
        }
        return orden;

    }

    private void topologicalSort(Integer v, Set<Integer> visitados, Stack<Integer> pila) {
        visitados.add(v);

        for (Integer vecino : grafo.getOrDefault(v, new ArrayList<>())) {
            if (!visitados.contains(vecino)) {
                topologicalSort(vecino, visitados, pila);
            }
        }
        pila.push(v);
    }


}