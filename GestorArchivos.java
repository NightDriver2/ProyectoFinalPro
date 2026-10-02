package ProyectoFinalPro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GestorArchivos {

    private static final String CARPETA_DATOS = "data";

    // =========================================================================
    // 1. GESTIÓN DE TAREAS
    // =========================================================================

    public void guardarTareas(String nombreArchivo, List<Tarea> listaTareas) {
        File carpeta = new File(CARPETA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File archivo = new File(carpeta, nombreArchivo);

        if (listaTareas.isEmpty()) {
            System.out.println("No hay tareas para guardar.");
            return;
        }

        // Usamos punto y coma (;) como separador para evitar conflictos si el título tiene comas
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Tarea t : listaTareas) {
                String linea = String.format("%d;%s;%d;%s;%.2f;%s",
                        t.getId(),
                        t.getTitulo(),
                        t.getPrioridad(),
                        t.getFechaEntrega() != null ? t.getFechaEntrega().toString() : "",
                        t.getTiempoEstimado(),
                        t.getDepartamento());
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("✓ Tareas guardadas exitosamente en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("X Error al guardar el archivo de tareas: " + e.getMessage());
        }
    }

    public List<Tarea> cargarTareas(String nombreArchivo) {
        List<Tarea> tareasCargadas = new ArrayList<>();
        File archivo = new File(CARPETA_DATOS, nombreArchivo);

        if (!archivo.exists()) {
            System.out.println("i No se encontró el archivo de tareas (" + archivo.getAbsolutePath() + ")");
            return tareasCargadas;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // Cambiado a punto y coma (;) para coincidir con el guardado
                String[] datos = linea.split(";");
                if (datos.length >= 6) {
                    try {
                        int id = Integer.parseInt(datos[0].trim());
                        String titulo = datos[1].trim();
                        int prioridad = Integer.parseInt(datos[2].trim());
                        String fechaEntrega = datos[3].trim();
                        double tiempoEstimado = Double.parseDouble(datos[4].trim());
                        String departamento = datos[5].trim();

                        Tarea tarea = new Tarea(id, titulo, prioridad, fechaEntrega, tiempoEstimado, departamento);
                        tareasCargadas.add(tarea);
                    } catch (NumberFormatException ex) {
                        System.out.println("Línea de tarea ignorada (error numérico): " + linea);
                    }
                }
            }
            System.out.println("✓ Tareas cargadas desde archivo: " + tareasCargadas.size());
        } catch (IOException e) {
            System.out.println("X Error al leer el archivo de tareas: " + e.getMessage());
        }

        return tareasCargadas;
    }

    // =========================================================================
    // 2. GESTIÓN DE EMPLEADOS
    // =========================================================================

    public void guardarEmpleados(String nombreArchivo, List<Empleado> empleados) {
        File carpeta = new File(CARPETA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); 
        }

        File archivo = new File(carpeta, nombreArchivo);

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados para guardar.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Empleado e : empleados) {
                pw.println(e.getId() + ";" + e.getNombre() + ";" + e.getDepartamento());
            }
            System.out.println(empleados.size() + " empleado(s) guardado(s) en " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    public List<Empleado> cargarEmpleados(String nombreArchivo) {
        List<Empleado> lista = new ArrayList<>();
        File archivo = new File(CARPETA_DATOS, nombreArchivo);

        if (!archivo.exists()) {
            System.out.println("El archivo " + archivo.getAbsolutePath() + " no existe todavía.");
            return lista;
        }    

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;

                String[] partes = linea.split(";");
                if (partes.length != 3) {
                    System.out.println("Línea ignorada (formato inválido): " + linea);
                    continue;
                }

                try {
                    int id = Integer.parseInt(partes[0].trim());
                    lista.add(new Empleado(id, partes[1].trim(), partes[2].trim()));
                } catch (NumberFormatException ex) {
                    System.out.println("Línea ignorada (ID inválido): " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar empleados: " + e.getMessage());
        }
        return lista;
    }
}