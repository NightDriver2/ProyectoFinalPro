package ProyectoFinalPro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

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
                    LocalDate fechaEntrega = LocalDate.parse(datos[3].trim());
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
    public static void guardarEmpleados(ArrayList<Empleado> listaEmpleados, String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Empleado emp : listaEmpleados) {
                String linea = String.format("%d,%s,%s",
                        emp.getId(),
                        emp.getNombre(),
                        emp.getDepartamento());
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("✓ Empleados guardados exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("X Error al guardar el archivo de empleados: " + e.getMessage());
        }
    }

    /**
     * Lee un archivo de texto y reconstruye la lista de objetos Empleado.
     */
    public static ArrayList<Empleado> cargarEmpleados(String rutaArchivo) {
        ArrayList<Empleado> empleadosCargados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(",");
                if (datos.length >= 3) {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    String departamento = datos[2].trim();

                    Empleado empleado = new Empleado(id, nombre, departamento);
                    empleadosCargados.add(empleado);
                }
            }
            System.out.println("✓ Empleados cargados desde archivo: " + empleadosCargados.size());
        } catch (IOException e) {
            System.out.println("i No se encontró el archivo de empleados (" + rutaArchivo + ")");
        } catch (Exception e) {
            System.out.println("X Error al procesar el formato del archivo de empleados");
        }

        return empleadosCargados;
    }
}