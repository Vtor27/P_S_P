package es.florida;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Cliente_Calculo {

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Uso: java ClienteCalculo <operador> <numero1> <numero2> <nombreCliente>");
            return;
        }

        String operador = args[0]; // Tipo de operación (+, -, *, /)
        String num1 = args[1];     // Primer número
        String num2 = args[2];     // Segundo número
        String nombreCliente = args[3]; // Nombre del cliente

        System.out.println("CLIENTE >>> Arranca cliente: " + nombreCliente);

        try {
            InetSocketAddress direccion = new InetSocketAddress("localhost", 1236);
            Socket socket = new Socket();
            socket.connect(direccion);

            System.out.println("CLIENTE >>> Conectado al servidor como: " + nombreCliente);

            // Enviar datos al servidor
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(operador);
            pw.println(num1);
            pw.println(num2);
            pw.println(nombreCliente); // Enviar el nombre del cliente (opcional para identificación en el servidor)
            pw.flush();

            // Leer el resultado del servidor
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String resultado = br.readLine();

            System.out.println("CLIENTE >>> Resultado recibido del servidor: " + resultado);

            // Cerrar la conexión
            socket.close();
        } catch (IOException e) {
            System.err.println("CLIENTE >>> Error: " + e.getMessage());
        }
    }
}