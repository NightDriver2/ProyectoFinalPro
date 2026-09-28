package ProyectoFinalPro;

import java.util.Scanner;
import java.util.HashSet;
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

        while(opcion !=9) {
            System.out.println("   SISTEMA DE GESTION DE TAREAS - PROYECTO FINAL   ");
            System.out.println(" --- CATALOGO DE EMPLEADOS ( ABB Y TABLA HASH ) ---");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Buscar empleado por ID");
            System.out.println("3. Mostrar empleados (In-Order por ID)");
            System.out.println();
            System.out.println(" --- CATALOGO DE TAREAS Y DEPENDENCIAS ( COLA Y GRAFO ) ---");
            System.out.println("4. Registrar  nueva tarea");
            System.out.println("5. Registrar dependencia entre tareas");
            System.out.println("6. Validar y asignar tarea a empleado");
            System.out.println("7. Marcar tarea como completada");
            System.out.println();
            System.out.println("--- PERSISTENCIA DE DATOS Y SALIDA ---");
            System.out.println("8. Cargar o Guardar datos en archivos");
            System.out.println("9. Salir");
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
                        gestorHash.insertar(emp);
                        System.out.println("Empleado registrado exitosamente.");
                    }
                    case 2 -> {
                        System.out.print("Ingresa el ID a buscar: ");
                        int id = Integer.parseInt(sc.nextLine());
                        Empleado emp = arbolEmpleados.buscar(id);
                        if (emp != null) {
                            System.out.println("Empleado encontrado: " + emp);
                        } else {
                            System.out.println("No existe el empleado con el ID" + id);
                        }
                    }
                    case 3 -> arbolEmpleados.mostrarInOrden();
                    case 4 -> {
                        System.out.print("ID de tarea: ");
                        int id = Integer.parseInt(sc.nextLine());
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


                        Tarea t = new Tarea(id, titulo, prioridad, fecha, horas, departamento);
                        colaPrioridad.insertar(t);
                        grafoDependencias.sincronizarTarea(t);
                        System.out.println("Tarea registrada en la cola de prioridad y en el grafo.");
                    }
                    case 5 -> {
                        System.out.print("ID de Tarea dependiente: ");
                        int idDependiente = Integer.parseInt(sc.nextLine());
                        System.out.print("ID de Tarea previa requerida: ");
                        int idPrevia = Integer.parseInt(sc.nextLine());

                        grafoDependencias.agregarDependencia(idDependiente, idPrevia);
                    }
                    case 6 -> {
                        System.out.print("ID de tarea a asignar: ");
                        int idTarea = Integer.parseInt(sc.nextLine());
                        System.out.print("ID de empleado asignado: ");
                        int idEmpleado = Integer.parseInt(sc.nextLine());


                        Empleado emp = arbolEmpleados.buscar(idEmpleado);
                        Tarea t = colaPrioridad.obtenerFrente();

                        if (emp == null) {
                            System.out.println("Error: Empleado no encontrado.");
                        } else if (t == null) {
                            System.out.println("Error: No hay tareas registradas en la cola.");
                        } else {
                            grafoDependencias.esAsignable(t, emp, tareasCompletadas);
                        }
                    }

                    case 7 -> {
                        System.out.print("ID de Tarea completada: ");
                        int idTarea = Integer.parseInt(sc.nextLine());
                        tareasCompletadas.add(idTarea);
                        System.out.println("Tarea #" + idTarea + " marcada como completada.");
                    }
                    case 8 -> {
                        System.out.println("\n--- MENU DE ARCHIVOS ---");
                        System.out.println("1. GUardar empleado en disco");
                        System.out.println("2. Cargar empleados desde disco");
                        System.out.print("Selecciona una subopcion: ");
                        int subOpcion = Integer.parseInt(sc.nextLine());

                        if (subOpcion == 1) {
                            gestorArchivos.guardarEmpleados("empleados.txt", gestorHash.obtenerTodos());
                        } else if (subOpcion == 2) {
                            gestorHash.cargarEmpleados("empleados.txt", arbolEmpleados);
                        } else {
                            System.out.println("Subopción no válida.");
                        }

                    }
                    case 9 -> System.out.println("Cerrando el sistema.");
                    default -> System.out.println("Opción no válida. Intente nuevamente.");

                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
            } catch (Exception e) {
                System.out.println("Error en ejecución: " + e.getMessage());
            }
        }
    }
}
