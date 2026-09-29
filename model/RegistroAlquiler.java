package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistroAlquiler {
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Alquiler> alquileres;

    public RegistroAlquiler() {
        vehiculos = new ArrayList<>();
        alquileres = new ArrayList<>();
    }

    public boolean validarPlacaUnica(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            return false;
        }
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                return false;
            }
        }
        return true;
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (vehiculo == null) {
            throw new IllegalArgumentException("El vehículo no puede ser nulo.");
        }
        if (!validarPlacaUnica(vehiculo.getPlaca())) {
            throw new IllegalArgumentException("Ya existe un vehículo registrado con la placa " + vehiculo.getPlaca() + ".");
        }
        vehiculos.add(vehiculo);
    }

    public Vehiculo buscarVehiculo(String placa) {
        if (placa == null) {
            return null;
        }
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa.trim())) {
                return vehiculo;
            }
        }
        return null;
    }

    public double cotizar(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehículo registrado con la placa '" + placa + "'.");
        }
        return vehiculo.calcularCosto(dias);
    }

    public Alquiler confirmarAlquiler(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehículo registrado con la placa '" + placa + "'.");
        }
        if (!vehiculo.validarDisponibilidad()) {
            throw new IllegalStateException("El vehículo con placa '" + vehiculo.getPlaca() + "' no está disponible (ya se encuentra alquilado).");
        }
        
        double montoTotal = vehiculo.calcularCosto(dias);
        vehiculo.marcarAlquilado();
        Alquiler alquiler = new Alquiler(vehiculo, dias, montoTotal);
        alquileres.add(alquiler);
        return alquiler;
    }

    public void registrarDevolucion(String placa) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo == null) {
            throw new IllegalArgumentException("No existe un vehículo registrado con la placa '" + placa + "'.");
        }
        if (vehiculo.validarDisponibilidad()) {
            throw new IllegalStateException("El vehículo con placa '" + vehiculo.getPlaca() + "' ya se encuentra disponible (no está alquilado).");
        }
        
        // Se marca el vehículo como disponible nuevamente sin alterar el historial ni los ingresos
        vehiculo.marcarDisponible();
    }

    public List<Vehiculo> consultarVehiculos() {
        return Collections.unmodifiableList(vehiculos);
    }

    public List<Alquiler> consultarAlquileres() {
        return Collections.unmodifiableList(alquileres);
    }

    public double calcularTotalIngresos() {
        double totalIngresos = 0;
        for (Alquiler alquiler : alquileres) {
            totalIngresos += alquiler.getMontoTotal();
        }
        return totalIngresos;
    }

    public String generarReporte() {
        int totalVehiculos = vehiculos.size();
        int totalDisponibles = 0;
        int totalAlquilados = 0;

        int sedanesTotal = 0, sedanesDisp = 0, sedanesAlq = 0;
        int motosTotal = 0, motosDisp = 0, motosAlq = 0;
        int cargasTotal = 0, cargasDisp = 0, cargasAlq = 0;

        for (Vehiculo vehiculo : vehiculos) {
            boolean disp = vehiculo.validarDisponibilidad();
            if (disp) {
                totalDisponibles++;
            } else {
                totalAlquilados++;
            }

            if (vehiculo instanceof Sedan) {
                sedanesTotal++;
                if (disp) sedanesDisp++; else sedanesAlq++;
            } else if (vehiculo instanceof Motocicleta) {
                motosTotal++;
                if (disp) motosDisp++; else motosAlq++;
            } else if (vehiculo instanceof TransporteCarga) {
                cargasTotal++;
                if (disp) cargasDisp++; else cargasAlq++;
            }
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("====================================================\n");
        reporte.append("              REPORTE GENERAL DE FLOTA              \n");
        reporte.append("====================================================\n");
        reporte.append(String.format("Total de Vehículos Registrados: %d\n", totalVehiculos));
        reporte.append(String.format("Total de Vehículos Disponibles: %d\n", totalDisponibles));
        reporte.append(String.format("Total de Vehículos Alquilados  : %d\n", totalAlquilados));
        reporte.append("----------------------------------------------------\n");
        reporte.append("DESGLOSE POR CATEGORÍA:\n");
        reporte.append(String.format(" - Sedanes           : Total = %d | Disponibles = %d | Alquilados = %d\n",
                sedanesTotal, sedanesDisp, sedanesAlq));
        reporte.append(String.format(" - Motocicletas      : Total = %d | Disponibles = %d | Alquilados = %d\n",
                motosTotal, motosDisp, motosAlq));
        reporte.append(String.format(" - Transporte Carga  : Total = %d | Disponibles = %d | Alquilados = %d\n",
                cargasTotal, cargasDisp, cargasAlq));
        reporte.append("----------------------------------------------------\n");
        reporte.append(String.format("Ingresos Acumulados Confirmados: Q%.2f\n", calcularTotalIngresos()));
        reporte.append("====================================================\n");
        return reporte.toString();
    }
}
