package ProyectoFinalPro;

import java.util.ArrayList;
import java.util.List;

public class CalculadorEstadisticas {
    // ----------------------- Calcular tiempo chatttt -----------------------
    public double calcularTiempo(List <Tarea> tareas) {
        if(tareas == null || tareas.isEmpty()) {
            return 0.0;
        }
        return sumarTiempoRecursivo(tareas, 0);
    }

    public double sumarTiempoRecursivo(List<Tarea> tareas, int index) {
        if(index >= tareas.size()) {
            return 0.0;
        }

        return  tareas.get(index).getTiempoEstimado() + sumarTiempoRecursivo(tareas, index + 1);
    }

    public double calcularTiempoXDepartamento(List<Tarea> tareas, String dpto) {
        return sumarTiempoDptoRecursivo(tareas, dpto, 0);
    }

    public double sumarTiempoDptoRecursivo(List<Tarea> tareas, String dpto, int index) {
        if(index >= tareas.size()) {
            return 0.0;
        }
        Tarea acTarea = tareas.get(index);
        double tiempoActual = acTarea.getDepartamento()
            .equalsIgnoreCase(dpto) 
            ? acTarea.getTiempoEstimado() : 0.0;
        return tiempoActual + sumarTiempoRecursivo(tareas, index + 1);
    }

    // ----------------------- DISQUE DIVIDE Y VENCERAS COMO DND XD -----------------------
    public static class AsignacionCarga {
        public List<Tarea> grupoTareas;
        public double tiempoTotal;

        public AsignacionCarga(List<Tarea> grupoTareas, double tiempoTotal) {
            this.grupoTareas = grupoTareas;
            this.tiempoTotal = tiempoTotal;
        }
    }

    public AsignacionCarga[] optimizarDistribucion(List<Tarea> tareas) {
        if(tareas == null || tareas.isEmpty()) {
            return new AsignacionCarga[] {new AsignacionCarga(new ArrayList<>(), 0), new AsignacionCarga(new ArrayList<>(), 0)};
        }

        return dividirYBalancear(tareas, 0, tareas.size() - 1);
    }

    public AsignacionCarga[] dividirYBalancear(List<Tarea> tareas, int inicio, int fin) {
        if(inicio == fin) {
            List<Tarea> lista = new ArrayList<>();
            lista.add(tareas.get(inicio));
            AsignacionCarga grupo = new AsignacionCarga(lista, tareas.get(inicio).getTiempoEstimado());
            
            return new AsignacionCarga[] {grupo, new AsignacionCarga(new ArrayList<>(), 0)}; 
        }

        int medio = inicio + (fin - inicio) / 2;
        AsignacionCarga[] resultadoIzq = dividirYBalancear(tareas, inicio, medio);
        AsignacionCarga[] resultadoDer = dividirYBalancear(tareas, medio + 1, fin);

        return combinarCargas(resultadoIzq, resultadoDer);
    }

    public AsignacionCarga[] combinarCargas(AsignacionCarga[] izq, AsignacionCarga[] der) {
        List<Tarea> grupo1 = new ArrayList<>(izq[0].grupoTareas);
        grupo1.addAll(der[0].grupoTareas);

        List<Tarea> grupo2 = new ArrayList<>(izq[1].grupoTareas);
        grupo2.addAll(der[1].grupoTareas);

        double t1 = calcularTiempo(grupo1);
        double t2 = calcularTiempo(grupo2);

        return new AsignacionCarga[]{
            new AsignacionCarga(grupo1, t1),
            new AsignacionCarga(grupo2, t2)
        };
    }

    // ----------------------- REPORTE ALN -----------------------
    public void mostrarReporteDistribucion(List<Tarea> tareas) {
        System.out.println("\n------- DISTRIBUCIÓN DE TAREAS -------");
        AsignacionCarga[] distribucion = optimizarDistribucion(tareas);

        System.out.println(" ===> Grupo A:"); 
        System.out.println(" - Carga Total: " + distribucion[0].tiempoTotal + " hrs"); 
        System.out.println(" - Tareas: " + distribucion[0].grupoTareas);
        System.out.println("\n");
        
        System.out.println(" ===> Grupo B:"); 
        System.out.println(" - Carga Total: " + distribucion[1].tiempoTotal + " hrs"); 
        System.out.println(" - Tareas: " + distribucion[1].grupoTareas);
        System.out.println("\n");
    }
}
