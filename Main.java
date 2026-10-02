package ProyectoFinalPro;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArbolEmpleados arbolEmpleados = new ArbolEmpleados();
        GestorHash gestorHash = new GestorHash();
        ColaPrioridad colaPrioridad = new ColaPrioridad();
        GestorArchivos gestorArchivos = new GestorArchivos();
        GrafoDependencias grafoDependencias = new GrafoDependencias();
        Set<Integer> tareasCompletadas = new HashSet<>();

        int opcion = 0;

        while(opcion != 11) { // Ajustado a 11 por el aumento de opciones
            System.out.println();
            System.out.println("   SISTEMA DE GESTION DE TAREAS - PROYECTO FINAL   ");
            System.out.println(" --- CATALOGO DE EMPLEADOS ( ABB Y TABLA HASH ) ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Buscar empleado por ID");
            System.out.println("3. Mostrar empleados (In-Order por ID)");
            System.out.println("4. Ver tareas asignadas a un empleado"); // <-- Nueva opción añadida antes de tareas
            System.out.println();
            System.out.println(" --- CATALOGO DE TAREAS Y DEPENDENCIAS ( COLA Y GRAFO ) ---");
            System.out.println("5. Registrar nueva tarea");
            System.out.println("6. Registrar dependencia entre tareas");
            System.out.println("7. Validar y asignar tarea de mayor prioridad a empleado"); // <-- Modificada (automática)
            System.out.println("8. Eliminar tarea");
            System.out.println("9. Ver todas las tareas registradas");
            System.out.println();
            System.out.println("--- PERSISTENCIA DE DATOS Y SALIDA ---");
            System.out.println("10. Cargar o Guardar datos en archivos");
            System.out.println("11. Salir");
            System.out.print("\nSeleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());

                switch (opcion) {
                    case 1 -> {
                        System.out.print("ID de empleado: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre de empleado: ");
                        String nombre = sc.nextLine();
                        System.out.print("Departamento de empleado: ");
                        String departamento = sc.nextLine();

                        Empleado emp = new Empleado(id, nombre, departamento);
                        arbolEmpleados.insertar(emp);
                        gestorHash.guardarEmpleado(emp);
                        System.out.println("\nEmpleado registrado exitosamente.");
                    }
                    case 2 -> {
                        System.out.print("Ingresa el ID a buscar: ");
                        int id = Integer.parseInt(sc.nextLine());
                        Empleado emp = arbolEmpleados.buscar(id);
                        if (emp != null) {
                            System.out.println("\nEmpleado encontrado: " + emp);
                        } else {
                            System.out.println("\nNo existe el empleado con el ID " + id);
                        }
                    }
                    case 3 -> {
                        System.out.println();
                        arbolEmpleados.mostrarInOrden();
                    }
                    case 4 -> {
                        System.out.print("ID del empleado a consultar: ");
                        int idEmp = Integer.parseInt(sc.nextLine());
                        Empleado emp = gestorHash.buscarIdEmpleado(idEmp);
                        
                        if (emp == null) {
                            System.out.println("\nError: No existe un empleado con el ID " + idEmp);
                        } else {
                            System.out.println("\n--- TAREAS ASIGNADAS A: " + emp.getNombre() + " ---");
                            
                            // Buscamos en el gestorHash las tareas que pertenezcan a este empleado
                            boolean encontradas = false;
                            for (Tarea t : gestorHash.getMapaTareas().values()) {
                                // Nota: Asegúrate de que tu clase Tarea tenga un método como getEmpleadoAsignado() o similar, 
                                // o ajusta esta condición según cómo guardes la asignación en tu proyecto.
                                if (t.getEmpleadoAsignado() != null && t.getEmpleadoAsignado().getId() == idEmp) {
                                    System.out.println(t);
                                    encontradas = true;
                                }
                            }
                            
                            if (!encontradas) {
                                System.out.println("Este empleado no tiene tareas asignadas actualmente.");
                            }
                        }
                    }
                    case 5 -> {
                        // ID automático basado en el número de tareas existentes + 1
                        int idAuto = gestorHash.getMapaTareas().size() + 1;
                        // O si prefieres asegurarte de que no se repita si borraste alguna, puedes usar:
                        // int idAuto = (int)(Math.random() * 1000) + 1; o un contador estático.
                        // El más seguro y limpio con el tamaño actual es:
                        
                        // Asegurando un ID único consecutivo incluso si hay eliminaciones:
                        int idGenerado = 1;
                        while(gestorHash.existeTarea(idGenerado)) {
                            idGenerado++;
                        }

                        System.out.println("ID de tarea asignado automáticamente: " + idGenerado);
                        System.out.print("Título de tarea: ");
                        String titulo = sc.nextLine();
                        System.out.print("Prioridad (1 Urgente - 5 baja): ");
                        int prioridad = Integer.parseInt(sc.nextLine());
                        System.out.print("Fecha de entrega (AAAA-MM-DD): ");
                        String fecha = sc.nextLine();
                        System.out.print("Horas estimadas: ");
                        double horas = Double.parseDouble(sc.nextLine());
                        System.out.print("Departamento: ");
                        String departamento = sc.nextLine();

                        Tarea t = new Tarea(idGenerado, titulo, prioridad, fecha, horas, departamento);
                        colaPrioridad.enqueue(t);
                        grafoDependencias.sincronizarTarea(t);
                        gestorHash.guardarTarea(t);
                        System.out.println("\nTarea registrada con ID " + idGenerado + " en la cola de prioridad, grafo y hash.");
                    }
                    case 6 -> {
                        System.out.print("ID de Tarea dependiente: ");
                        int idDependiente = Integer.parseInt(sc.nextLine());
                        System.out.print("ID de Tarea previa requerida: ");
                        int idPrevia = Integer.parseInt(sc.nextLine());

                        System.out.println();
                        grafoDependencias.agregarDependecia(idDependiente, idPrevia);
                    }
                    case 7 -> {
                        System.out.print("ID de empleado asignado: ");
                        int idEmpleado = Integer.parseInt(sc.nextLine());

                        Empleado emp = arbolEmpleados.buscar(idEmpleado);

                        System.out.println();
                        if (emp == null) {
                            System.out.println("Error: Empleado no encontrado.");
                        } else if (colaPrioridad.isEmpty()) {
                            System.out.println("Error: No hay tareas registradas en la cola.");
                        } else {
                            Tarea tEncontrada = null;
                            List<Tarea> tareasTemporales = new ArrayList<>();

                            // Recorremos la cola de prioridad en orden estricto de mayor a menor urgencia
                            while (!colaPrioridad.isEmpty()) {
                                Tarea t = colaPrioridad.dequeue(); // Saca la tarea más urgente disponible

                                // Validamos si coincide el departamento, no está asignada y cumple con el grafo de dependencias
                                if (t.getDepartamento().equalsIgnoreCase(emp.getDepartamento()) && t.getEmpleadoAsignado() == null) {
                                    if (grafoDependencias.esAsignable(t, emp, tareasCompletadas)) {
                                        tEncontrada = t;
                                        break; // Encontramos la de mayor prioridad válida para este empleado, rompemos el ciclo
                                    }
                                }
                                
                                // Si no cumple o es de otro departamento, la guardamos temporalmente para regresarla a la cola
                                tareasTemporales.add(t);
                            }

                            // Devolvemos a la cola de prioridad todas las tareas que no fueron asignadas
                            for (Tarea t : tareasTemporales) {
                                colaPrioridad.enqueue(t);
                            }

                            if (tEncontrada == null) {
                                System.out.println("Error: No hay tareas disponibles o asignables en el departamento '" + emp.getDepartamento() + "' para este empleado.");
                            } else {
                                System.out.println("Asignando tarea compatible con mayor prioridad: " + tEncontrada.getTitulo() + " (Depto: " + tEncontrada.getDepartamento() + ")");
                                
                                // Asignamos el empleado a la tarea
                                tEncontrada.setEmpleadoAsignado(emp);
                                
                                System.out.println("¡Tarea asignada exitosamente a " + emp.getNombre() + "!");
                            }
                        }
                    }
                    case 8 -> {
                        System.out.print("ID de Tarea a eliminar: ");
                        int idTarea = Integer.parseInt(sc.nextLine());
                        gestorHash.eliminarTarea(idTarea);
                    }
                    case 9 -> {
                        gestorHash.mostrarTodasTareas();
                    }
                    case 10 -> {
                        System.out.println("\n--- MENU DE ARCHIVOS ---");
                        System.out.println("1. Guardar empleados en disco");
                        System.out.println("2. Cargar empleados desde disco");
                        System.out.println("3. Guardar tareas en disco");      // <-- Nueva opción
                        System.out.println("4. Cargar tareas desde disco");      // <-- Nueva opción
                        System.out.print("Selecciona una subopcion: ");
                        int subOpcion = Integer.parseInt(sc.nextLine());

                        System.out.println();
                        if (subOpcion == 1) {
                            gestorArchivos.guardarEmpleados("empleados.txt", gestorHash.obtenerTodos());
                        } 
                        else if (subOpcion == 2) {
                            int cargados = 0;
                            for (Empleado emp : gestorArchivos.cargarEmpleados("empleados.txt")) {
                                if (gestorHash.existeEmpleado(emp.getId())) {
                                    continue; 
                                }
                                arbolEmpleados.insertar(emp);
                                gestorHash.guardarEmpleado(emp);
                                cargados++;
                            }
                            System.out.println(cargados + " empleado(s) cargado(s).");
                        } 
                        else if (subOpcion == 3) {
                            // Guardar la colección de tareas del HashMap convirtiéndola a Lista
                            List<Tarea> listaTareas = new ArrayList<>(gestorHash.getMapaTareas().values());
                            gestorArchivos.guardarTareas("tareas.txt", listaTareas);
                        } 
                        else if (subOpcion == 4) {
                            int cargadas = 0;
                            for (Tarea t : gestorArchivos.cargarTareas("tareas.txt")) {
                                if (gestorHash.existeTarea(t.getId())) {
                                    continue; // Evitar duplicados si ya existen
                                }
                                // Las agregamos al GestorHash, Cola de Prioridad y Grafo de Dependencias
                                gestorHash.guardarTarea(t);
                                colaPrioridad.enqueue(t);
                                grafoDependencias.sincronizarTarea(t);
                                cargadas++;
                            }
                            System.out.println(cargadas + " tarea(s) cargada(s) exitosamente.");
                        } 
                        else {
                            System.out.println("Subopción no válida.");
                        }
                    }
                    case 11 -> System.out.println("\nCerrando el sistema.");
                    default -> System.out.println("\nOpción no válida. Intente nuevamente.");

                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada no válida. Por favor, ingrese un número.");
            } catch (Exception e) {
                System.out.println("\nError en ejecución: " + e.getMessage());
            }
        }
        sc.close();
    }
}