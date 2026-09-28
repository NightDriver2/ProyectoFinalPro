package ProyectoFinalPro;

import java.io.File;
import java.io.FileWriter;              
import java.io.IOException;
import java.util.Scanner;

public class GestorArchivos {
    private String nombreArchivo;

    public GestorArchivos(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void escribirArchivo(String contenido) {
        try (FileWriter writer = new FileWriter(nombreArchivo, true)) {
            writer.write(contenido + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    public void leerArchivo() {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe.");
            return;
        }

        try (Scanner scanner = new Scanner(archivo)) {
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}