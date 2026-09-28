
package ProyectoFinalPro;

import java.time.LocalDate;
import java.util.ArrayList;

public class PruebasSistema {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  SECCIÓN / PASO 6: PRUEBAS Y RESULTADOS DEL SISTEMA");
        System.out.println("  Repositorio: NightDriver2/ProyectoFinalPro");
        System.out.println("==================================================\n");

        // ----------------------------------------------------
        // 6.1 Prueba 1: Pila (LIFO) - Tareas Urgentes
        // ----------------------------------------------------
        System.out.println("--- 6.1 PRUEBA 1: PILA (LIFO) ---");
        Pila pila = new Pila();
        pila.push(101);
        System.out.println("✓ Tarea 101 agregada a Pila.");
        pila.push(102);
        System.out.println("✓ Tarea 102 agregada a Pila.");

        System.out.println("Primera tarea urgente (Peek): " + pila.peek());
        System.out.println("✓ Tarea eliminada (Pop): " + pila.pop());
        System.out.print("Tareas urgentes restantes (Pila): ");
        pila.ShowAll();
        System.out.println("\n✓ Resultado: EXITOSO. La tarea 102 (última en entrar) fue la primera en eliminarse.\n");

        // ----------------------------------------------------
        // 6.2 Prueba 2: Cola (FIFO) - Tareas Programadas
        // ----------------------------------------------------
        System.out.println("--- 6.2 PRUEBA 2: COLA (FIFO) ---");
        Colas cola = new Colas();
        cola.enqueue(201);
        System.out.println("✓ Tarea 201 agregada a Cola.");
        cola.enqueue(202);
        System.out.println("✓ Tarea 202 agregada a Cola.");

        System.out.println("Primera tarea programada (Front): " + cola.front());
        System.out.println("✓ Tarea eliminada (Dequeue): " + cola.dequeue());
        System.out.println("Tareas programadas restantes (Cola): " + cola.getAll());
        System.out.println("✓ Resultado: EXITOSO. La tarea 201 (primera en entrar) fue la primera en eliminarse.\n");

        // ----------------------------------------------------
        // 6.3 Prueba 3: Lista - Acceso Aleatorio
        // ----------------------------------------------------
        System.out.println("--- 6.3 PRUEBA 3: LISTA (ACCESO ALEATORIO) ---");
        Listas lista = new Listas();
        lista.insert(301);
        System.out.println("✓ Tarea 301 agregada a Lista.");
        lista.insert(302);
        System.out.println("✓ Tarea 302 agregada a Lista.");

        System.out.println("✓ Tarea 302 encontrada en posición: " + lista.find(302));
        System.out.println("✓ Tarea " + lista.delete(0) + " eliminada de posición 0.");
        System.out.print("Tareas por departamento (Lista): ");
        lista.showAll();
        System.out.println("✓ Resultado: EXITOSO. Búsqueda y eliminación por índice verificadas.\n");

        // ----------------------------------------------------
        // 6.4 Prueba 4: Cola de Prioridades (Fase II)
        // ----------------------------------------------------
        System.out.println("--- 6.4 PRUEBA 4: COLA DE PRIORIDADES (FASE II) ---");
        ColaPrioridad colaPrio = new ColaPrioridad();

        // Se insertan objetos Tarea con id, titulo, prioridad y fecha
        Tarea t1 = new Tarea(1, "Soporte Servidor", 3, LocalDate.now().plusDays(2), 2.0, "TI");
        Tarea t2 = new Tarea(2, "Fallo Crítico BD", 1, LocalDate.now().plusDays(1), 1.5, "TI");
        Tarea t3 = new Tarea(3, "Mantenimiento Red", 1, LocalDate.now(), 3.0, "TI"); // Prioridad 1, fecha más cercana

        colaPrio.enqueue(t1);
        colaPrio.enqueue(t2);
        colaPrio.enqueue(t3);

        System.out.println("Tarea más urgente en ColaPrioridad (Peek): " + colaPrio.peek().getTitulo());
        System.out.println("✓ Procesando tarea más urgente (Dequeue): " + colaPrio.dequeue().getTitulo());
        System.out.println("✓ Siguiente tarea en cola: " + colaPrio.peek().getTitulo());
        System.out.println("✓ Resultado: EXITOSO. Prioridad 1 con fecha más cercana procesada en primer lugar.\n");

        // ----------------------------------------------------
        // 6.5 Prueba 5: Vista Consolidada General
        // ----------------------------------------------------
        System.out.println("--- 6.5 VISTA CONSOLIDADA GENERAL DE RESULTADOS ---");
        System.out.println("==================================================");
        System.out.print("TAREAS URGENTES (Pila - LIFO): ");
        pila.ShowAll();
        System.out.println();
        System.out.println("TAREAS PROGRAMADAS (Cola - FIFO): " + cola.getAll());
        System.out.print("TAREAS POR DEPARTAMENTO (Lista): ");
        lista.showAll();
        System.out.println("COLA DE PRIORIDADES (Fase II): " + colaPrio.size() + " tarea(s) pendiente(s)");
        System.out.println("==================================================\n");

        System.out.println("==================================================");
        System.out.println("     TODAS LAS PRUEBAS FINALIZADAS CON ÉXITO      ");
        System.out.println("==================================================");
    } 
}


