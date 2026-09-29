import java.util.List;
import java.util.Scanner;
import model.Alquiler;
import model.Motocicleta;
import model.RegistroAlquiler;
import model.Sedan;
import model.TransporteCarga;
import model.Vehiculo;

public class Main {
    private RegistroAlquiler registro;
    private Scanner scanner;

    public Main() {
        registro = new RegistroAlquiler();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.cargarDatosIniciales();
        app.ejecutarMenu();
    }

    private void cargarDatosIniciales() {
        try {
            // Se registran al menos 2 vehículos por categoría
            // 1. Sedanes (uno manual y uno automático)
            registro.agregarVehiculo(new Sedan("P101AAA", "Toyota", "Yaris", 200.0, 5, false));
            registro.agregarVehiculo(new Sedan("P102BBB", "Honda", "Civic", 250.0, 5, true));

            // 2. Motocicletas (una <= 250cc y una > 250cc)
            registro.agregarVehiculo(new Motocicleta("M201CCC", "Suzuki", "GN125", 100.0, 125));
            registro.agregarVehiculo(new Motocicleta("M202DDD", "Yamaha", "MT-07", 180.0, 689));

            // 3. Camionetas de Carga (con distintas capacidades, incluyendo decimales)
            registro.agregarVehiculo(new TransporteCarga("C301EEE", "Hino", "300", 200.0, 1.5));
            registro.agregarVehiculo(new TransporteCarga("C302FFF", "Isuzu", "NPR", 350.0, 4.0));
        } catch (Exception e) {
            System.err.println("Error cargando datos iniciales: " + e.getMessage());
        }
    }

    public void ejecutarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n====================================================");
            System.out.println("              SISTEMA RENTAMOVIL - MENÚ             ");
            System.out.println("====================================================");
            System.out.println("1. Registrar un nuevo vehículo");
            System.out.println("2. Consultar la flota de vehículos");
            System.out.println("3. Cotizar el alquiler de un vehículo");
            System.out.println("4. Confirmar un alquiler");
            System.out.println("5. Registrar devolución de un vehículo");
            System.out.println("6. Generar reporte general e ingresos");
            System.out.println("7. Salir");
            System.out.println("====================================================");
            System.out.print("Seleccione una opción: ");

            String entrada = scanner.nextLine().trim();
            int opcion;
            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor ingrese un número del 1 al 7.");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarNuevoVehiculo();
                    break;
                case 2:
                    consultarFlota();
                    break;
                case 3:
                    cotizarAlquiler();
                    break;
                case 4:
                    confirmarAlquiler();
                    break;
                case 5:
                    registrarDevolucion();
                    break;
                case 6:
                    generarReporte();
                    break;
                case 7:
                    salir = true;
                    System.out.println("\n¡Gracias por utilizar el sistema RentaMovil!");
                    break;
                default:
                    System.out.println("Opción no válida. Elija un número entre 1 y 7.");
            }
        }
    }

    private void registrarNuevoVehiculo() {
        System.out.println("\n--- REGISTRO DE NUEVO VEHÍCULO ---");
        System.out.println("Seleccione el tipo de vehículo:");
        System.out.println("1. Sedán");
        System.out.println("2. Motocicleta");
        System.out.println("3. Transporte de Carga");
        System.out.print("Opción: ");

        String tipoStr = scanner.nextLine().trim();
        int tipo;
        try {
            tipo = Integer.parseInt(tipoStr);
            if (tipo < 1 || tipo > 3) {
                System.out.println("Tipo de vehículo no válido.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un número entero (1, 2 o 3).");
            return;
        }

        System.out.print("Ingrese la placa: ");
        String placa = scanner.nextLine().trim();

        System.out.print("Ingrese la marca: ");
        String marca = scanner.nextLine().trim();

        System.out.print("Ingrese el modelo: ");
        String modelo = scanner.nextLine().trim();

        System.out.print("Ingrese la tarifa diaria (Q): ");
        double tarifaDiaria;
        try {
            tarifaDiaria = Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Formato de tarifa inválido.");
            return;
        }

        try {
            Vehiculo nuevoVehiculo = null;
            if (tipo == 1) {
                System.out.print("Ingrese la cantidad de pasajeros: ");
                int pasajeros = Integer.parseInt(scanner.nextLine().trim());
                System.out.print("¿Es de transmisión automática? (s/n): ");
                String autoStr = scanner.nextLine().trim().toLowerCase();
                boolean esAutomatica = autoStr.startsWith("s");

                nuevoVehiculo = new Sedan(placa, marca, modelo, tarifaDiaria, pasajeros, esAutomatica);
            } else if (tipo == 2) {
                System.out.print("Ingrese el cilindraje en cc: ");
                int cilindraje = Integer.parseInt(scanner.nextLine().trim());

                nuevoVehiculo = new Motocicleta(placa, marca, modelo, tarifaDiaria, cilindraje);
            } else if (tipo == 3) {
                System.out.print("Ingrese la capacidad máxima de carga en toneladas: ");
                double capacidad = Double.parseDouble(scanner.nextLine().trim());

                nuevoVehiculo = new TransporteCarga(placa, marca, modelo, tarifaDiaria, capacidad);
            }

            registro.agregarVehiculo(nuevoVehiculo);
            System.out.println("✅ Vehículo registrado exitosamente.");
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato numérico de los campos específicos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar vehículo: " + e.getMessage());
        }
    }

    private void consultarFlota() {
        System.out.println("\n--- CONSULTA DE FLOTA DE VEHÍCULOS ---");
        List<Vehiculo> vehiculos = registro.consultarVehiculos();
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos registrados en el sistema.");
            return;
        }
        for (int i = 0; i < vehiculos.size(); i++) {
            System.out.println((i + 1) + ". " + vehiculos.get(i).obtenerDetalles());
        }
    }

    private void cotizarAlquiler() {
        System.out.println("\n--- COTIZAR ALQUILER ---");
        System.out.print("Ingrese la placa del vehículo a cotizar: ");
        String placa = scanner.nextLine().trim();

        System.out.print("Ingrese la cantidad de días de alquiler: ");
        int dias;
        try {
            dias = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Formato de días inválido. Debe ser un número entero.");
            return;
        }

        try {
            Vehiculo vehiculo = registro.buscarVehiculo(placa);
            if (vehiculo == null) {
                System.out.println("No se encontró un vehículo registrado con la placa '" + placa + "'.");
                return;
            }
            double total = registro.cotizar(placa, dias);
            System.out.println("\n--- DETALLES DE LA COTIZACIÓN ---");
            System.out.println(vehiculo.obtenerDetalles());
            System.out.println(String.format("Días solicitados: %d", dias));
            System.out.println(String.format("Costo total cotizado: Q%.2f", total));
            if (!vehiculo.validarDisponibilidad()) {
                System.out.println("Este vehículo está OCUPADO actualmente. Puede cotizarse, pero no podrá alquilarse hasta su devolución.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la cotización: " + e.getMessage());
        }
    }

    private void confirmarAlquiler() {
        System.out.println("\n--- CONFIRMAR ALQUILER ---");
        System.out.print("Ingrese la placa del vehículo a alquilar: ");
        String placa = scanner.nextLine().trim();

        System.out.print("Ingrese la cantidad de días: ");
        int dias;
        try {
            dias = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Formato de días inválido. Debe ser un número entero.");
            return;
        }

        try {
            Vehiculo vehiculo = registro.buscarVehiculo(placa);
            if (vehiculo == null) {
                System.out.println("No se encontró un vehículo con la placa '" + placa + "'.");
                return;
            }
            if (!vehiculo.validarDisponibilidad()) {
                System.out.println("No se puede confirmar el alquiler: El vehículo con placa '" + placa + "' ya se encuentra alquilado.");
                return;
            }

            double total = registro.cotizar(placa, dias);
            System.out.println("\n--- RESUMEN DE ALQUILER ---");
            System.out.println(vehiculo.obtenerDetalles());
            System.out.println(String.format("Duración: %d días", dias));
            System.out.println(String.format("Monto a cobrar: Q%.2f", total));
            System.out.print("¿Desea confirmar el alquiler y efectuar el cobro? (s/n): ");

            String confirmacion = scanner.nextLine().trim().toLowerCase();
            if (confirmacion.startsWith("s")) {
                Alquiler alq = registro.confirmarAlquiler(placa, dias);
                System.out.println("Alquiler confirmado exitosamente por un total de Q" + String.format("%.2f", alq.getMontoTotal()) + ".");
            } else {
                System.out.println("ℹOperación cancelada por el usuario. La disponibilidad y los ingresos permanecen sin cambios.");
            }
        } catch (Exception e) {
            System.out.println("Error al confirmar alquiler: " + e.getMessage());
        }
    }

    private void registrarDevolucion() {
        System.out.println("\n--- REGISTRAR DEVOLUCIÓN ---");
        System.out.print("Ingrese la placa del vehículo a devolver: ");
        String placa = scanner.nextLine().trim();

        try {
            registro.registrarDevolucion(placa);
            System.out.println("Devolución registrada correctamente. El vehículo con placa '" + placa.toUpperCase() + "' ahora está disponible para alquiler.");
        } catch (Exception e) {
            System.out.println("Error al registrar devolución: " + e.getMessage());
        }
    }

    private void generarReporte() {
        System.out.println();
        System.out.print(registro.generarReporte());
    }
}
