package ProyectoFinalPro;

import java.util.ArrayList;
import java.util.List;

public class Tarea implements Comparable<Tarea>{
    int id;
    String titulo;
    int prioridad;
    String fechaEntrega;
    double tiempoEstimado;
    String departamento;
    List<Integer> dependencias;

    public Tarea(int id, String titulo, int prioridad, String fechaEntrega, double tiempoEstimado, String departamento) { 
        this.id = id; 
        this.titulo = titulo; 
        this.prioridad = prioridad; 
        this.fechaEntrega = fechaEntrega; 
        this.tiempoEstimado = tiempoEstimado; 
        this.departamento = departamento; 
        this.dependencias = new ArrayList<>(); 
    }

    // ------------------Para ver lo de las dependencias ------------------
    public void agregarDependencia(int idTareaPrevia) { 
        this.dependencias.add(idTareaPrevia); 
    } 
    
    public List<Integer> getDependencias() {
        return dependencias;
    }

    // ------------------ Getters ------------------
    public int getId() { 
        return id; 
    } 
    
    public String getTitulo() { 
        return titulo; 
    } 
    
    public int getPrioridad() { 
        return prioridad; 
    } 
    
    public String getFechaEntrega() { 
        return fechaEntrega; 
    } 
    
    public double getTiempoEstimado() { 
        return tiempoEstimado; 
    } 
    
    public String getDepartamento() { 
        return departamento; 
    }


    // ------------------ Overrides ------------------
    @Override 
    public int compareTo(Tarea otra) {
        int compPrioridad = Integer.compare(this.prioridad, otra.prioridad);

        if(compPrioridad != 0) {
            return compPrioridad;
        }
        return this.fechaEntrega.compareTo(otra.fechaEntrega);
    }
    
    @Override 
    public String toString() { 
        return "\n ------ Tarea ------" +
            "[" + id + "] " 
            + titulo 
            + " | Prioridad: " + prioridad 
            + " | Entrega: " + fechaEntrega 
            + " | Horas: " + tiempoEstimado 
            + " | Depto: " + departamento
            + "\n ---------------------"; 
    }
}
