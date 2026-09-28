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

    // =========================================================================
    // 1. GESTIÓN DE TAREAS (Persistencia en archivo .txt / .csv)
    // =========================================================================

    /**
     * Guarda la lista de tareas en un archivo de texto en formato CSV:
     * id,titulo,prioridad,fechaEntrega,tiempoEstimado,departamento
     */
    public static void guardarTareas(ArrayList<Tarea> listaTareas, String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Tarea t : listaTareas) {
                String linea = String.format("%d,%s,%d,%s,%.2f,%s",
                        t.getId(),
                        t.getTitulo(),
                        t.getPrioridad(),
                        t.getFechaEntrega().toString(),
                        t.getTiempoEstimado(),
                        t.getDepartamento());
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("✓ Tareas guardadas exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("X Error al guardar el archivo de tareas: " + e.getMessage());
        }
    }

    /**
     * Lee un archivo de texto y reconstruye la lista de objetos Tarea.
     */
    public static ArrayList<Tarea> cargarTareas(String rutaArchivo) {
        ArrayList<Tarea> tareasCargadas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(",");
                if (datos.length >= 6) {
                    int id = Integer.parseInt(datos[0].trim());
                    String titulo = datos[1].trim();
                    int prioridad = Integer.parseInt(datos[2].trim());
                    String fechaEntrega = datos[3].trim();
                    double tiempoEstimado = Double.parseDouble(datos[4].trim());
                    String departamento = datos[5].trim();

                    Tarea tarea = new Tarea(id, titulo, prioridad, fechaEntrega, tiempoEstimado, departamento);
                    tareasCargadas.add(tarea);
                }
            }
            System.out.println("✓ Tareas cargadas desde archivo: " + tareasCargadas.size());
        } catch (IOException e) {
            System.out.println("i No se encontró el archivo de tareas (" + rutaArchivo + ")");
        } catch (Exception e) {
            System.out.println("X Error al procesar el formato del archivo de tareas");
        }

        return tareasCargadas;
    }

    // =========================================================================
    // 2. GESTIÓN DE EMPLEADOS (Persistencia en archivo .txt / .csv)
    // =========================================================================

    /**
     * Guarda la lista de empleados en un archivo de texto en formato CSV:
     * id,nombre,departamento
     */
    private static final String CARPETA_DATOS = "data";

    public void guardarEmpleados(String nombreArchivo, List<Empleado> empleados) {
        File carpeta = new File(CARPETA_DATOS);
        if (!carpeta.exists()) {
            carpeta.mkdirs(); // crea la carpeta si no existe
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
            System.out.println("Error al guardar: " + e.getMessage());
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
            System.out.println("Error al cargar: " + e.getMessage());
        }
        return lista;
    }
}