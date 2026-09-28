package ProyectoFinalPro;

public class Empleado {
    int id;
    String nombre;
    String departamento;

    public Empleado(int id, String nombre, String departamento) { 
        this.id = id; 
        this.nombre = nombre; 
        this.departamento = departamento; 
    }

    // ------------------ Getters ------------------
    public int getId() { 
        return id; 
    } 
    
    public String getNombre() { 
        return nombre; 
    }

    public String getDepartamento() { 
        return departamento; 
    }

    // ------------------ Setters ------------------
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    } 
    
    public void setDepartamento(String departamento) { 
        this.departamento = departamento; 
    }

    // ------------------ Overrides ------------------
    @Override 
    public String toString() { 
        return "\n =================== Empleado ==================="
        + "\n ID: " + id 
        + " | Nombre: " + nombre 
        + " | Depto: " + departamento
        + "\n ================================================\n"; 
    }
}
