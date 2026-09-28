package ProyectoFinalPro;


public class ArbolEmpleados { 
    private NodoEmpleado raiz;

    public ArbolEmpleados() { 
        this.raiz = null; 
    }


    public void insertar(Empleado emp) { 
        raiz = insertarRec(raiz, emp); 
    }

    private NodoEmpleado insertarRec(NodoEmpleado nodo, Empleado emp) { 
        if (nodo == null) { 
            return new NodoEmpleado(emp); 
        } 

        if (emp.getId() < nodo.empleado.getId()) { 
            nodo.izquierdo = insertarRec(nodo.izquierdo, emp); 
        } else if (emp.getId() > nodo.empleado.getId()) { 
            nodo.derecho = insertarRec(nodo.derecho, emp); 
        } else{
            System.out.println("Ya existe un empleado registrado con el ID: " + emp.getId());
        }

        return nodo; 
    }

    public Empleado buscar(int id) { 
        NodoEmpleado resultado = buscarRec(raiz, id);
        return (resultado != null) ? resultado.empleado : null;
    }

    private NodoEmpleado buscarRec(NodoEmpleado nodo, int id) { 
        if (nodo == null || nodo.empleado.getId() == id) { 
            return nodo; 
        } 

        if (id < nodo.empleado.getId()) { 
            return buscarRec(nodo.izquierdo, id); 
        } 
        return buscarRec(nodo.derecho, id); 
    }


    public void mostrarInOrden() { 
        if (raiz == null) { 
            System.out.println("El árbol está vacío."); 
            return;

        } 
        System.out.println(" \n --- Catalogo de empleados (In-Order por ID) ---");
        inOrderRec(raiz);
        System.out.println("--------------------------------------------------\n\n");
    }

    private void inOrderRec(NodoEmpleado nodo) { 
        if (nodo != null) { 
            inOrderRec(nodo.izquierdo); 
            System.out.println(nodo.empleado);
            inOrderRec(nodo.derecho); 
        } 
    }

    private void eliminar(int id) { 
        raiz = eliminarRec(raiz, id); 
    }

    private NodoEmpleado eliminarRec(NodoEmpleado nodo, int id) {
        if ( nodo == null) {
            System.out.println("No se encontró un empleado con el ID: " + id);
            return null;
        }
        if (id < nodo.empleado.getId()) {
            nodo.izquierdo = eliminarRec(nodo.izquierdo, id);
        } else if (id > nodo.empleado.getId()) {
            nodo.derecho = eliminarRec(nodo.derecho, id);
        } else {
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null) return nodo.izquierdo;

            nodo.empleado = obtenerMinimo(nodo.derecho).empleado;
            nodo.derecho = eliminarRec(nodo.derecho, nodo.empleado.getId());
        }
        return nodo;
    }

    private NodoEmpleado obtenerMinimo(NodoEmpleado nodo) { 
        while (nodo.izquierdo != null) { 
            nodo = nodo.izquierdo; 
        } 
        return nodo; 
    }

}