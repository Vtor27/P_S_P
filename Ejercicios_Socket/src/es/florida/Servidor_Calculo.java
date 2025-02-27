package es.florida;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor_Calculo {

    public static int extraerNumero(String linea) {
        int numero;
        try {
            numero = Integer.parseInt(linea);
        } catch (NumberFormatException e) {
            numero = 0;
        }
        return numero;
    }

    public static int calcular(String op, String n1, String n2) {
        int resultado = 0;
        char simbolo = op.charAt(0);
        int num1 = extraerNumero(n1);
        int num2 = extraerNumero(n2);

        switch (simbolo) {
            case '+':
                resultado = num1 + num2;
                break;
            case '-':
                resultado = num1 - num2;
                break;
            case '*':
                resultado = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    System.err.println("Error en el hilo " + Thread.currentThread().getName() + ": División por cero");
                }
                break;
            default:
                System.err.println("Error en el hilo " + Thread.currentThread().getName() + ": Operación no válida");
        }
        return resultado;
    }

    public static void main(String[] args) throws IOException {
        System.err.println("SERVIDOR >>> Arranca el servidor, espera peticiones");
        @SuppressWarnings("resource")
		ServerSocket socketEscucha = new ServerSocket(1236); // Puerto 1235

        while (true) {
            // Aceptar conexiones entrantes
            Socket conexion = socketEscucha.accept();
            System.err.println("SERVIDOR >>> Nueva conexión aceptada!");

            // Crear un hilo para manejar la conexión
            Thread hiloCliente = new Thread(() -> {
                try {
                    System.err.println("HILO " + Thread.currentThread().getName() + " >>> Iniciando comunicación con cliente");

                    InputStream is = conexion.getInputStream();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(is));

                    String operador = bf.readLine();
                    String num1 = bf.readLine();
                    String num2 = bf.readLine();

                    int resultado = calcular(operador, num1, num2);

                    PrintWriter pw = new PrintWriter(conexion.getOutputStream());
                    pw.println("HILO " + Thread.currentThread().getName() + " >>> Resultado: " + resultado);
                    pw.flush();

                    System.err.println("HILO " + Thread.currentThread().getName() + " >>> Resultado enviado: " + resultado);

                    // Cerrar la conexión
                    conexion.close();
                    System.err.println("HILO " + Thread.currentThread().getName() + " >>> Conexión cerrada");

                } catch (IOException e) {
                    System.err.println("HILO " + Thread.currentThread().getName() + " >>> Error: " + e.getMessage());
                }
            });

            // Asignar un nombre al hilo y ejecutarlo
            hiloCliente.setName("Cliente-" + conexion.getPort());
            hiloCliente.start();
        }
    }
}