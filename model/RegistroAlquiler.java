package model;

import java.util.ArrayList;

public class RegistroAlquiler {
    private ArrayList<Vehiculo> vehiculos;
    private ArrayList<Alquiler> alquileres;

    public RegistroAlquiler() {
        vehiculos = new ArrayList<>();
        alquileres = new ArrayList<>();
    }

    public boolean validarPlacaUnica(String placa) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                return false; // La placa ya existe
            }
        }
        return true; // La placa es única

    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        if (validarPlacaUnica(vehiculo.getPlaca())) {
            vehiculos.add(vehiculo);
        } else {
            throw new IllegalArgumentException("La placa del vehículo ya existe en el registro.");
        }
    }

    public Vehiculo buscarVehiculo(String placa) {
        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                return vehiculo;
            }
        }
        return null; // No se encontró el vehículo
    }

    public double cotizar(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo != null) {
            return vehiculo.calcularCosto(dias);
        } else {
            throw new IllegalArgumentException("No se encontró un vehículo con la placa proporcionada.");
        }
    }

    public Alquiler confirmarAlquiler(String placa, int dias) {
        Vehiculo vehiculo = buscarVehiculo(placa);
        if (vehiculo != null) {
            double montoTotal = vehiculo.calcularCosto(dias);
            Alquiler alquiler = new Alquiler(vehiculo, dias, montoTotal);
            alquileres.add(alquiler);
            return alquiler;
        } else {
            throw new IllegalArgumentException("No se encontró un vehículo con la placa proporcionada.");
        }
    }

    public void registrarDevolucion(String placa) {
        //Devolver el carro despues de uso sin eliminarlo del registro de vehiculos
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getVehiculo().getPlaca().equalsIgnoreCase(placa)) {
                alquileres.remove(alquiler);
                return; // Salir del método después de eliminar el alquiler
            }
        }
        throw new IllegalArgumentException("No se encontró un alquiler con la placa proporcionada.");
    }   

    public ArrayList<Vehiculo> consultarVehiculos() {
        return vehiculos;
    }

    public ArrayList<Alquiler> consultarAlquileres() {
        return alquileres;
    }

    public double calcularTotalIngresos() {
        double totalIngresos = 0;
        for (Alquiler alquiler : alquileres) {
            totalIngresos += alquiler.getMontoTotal();
        }
        return totalIngresos;
    }

    public String generarResporte(){
        //. Devuelve las cantidades de vehículos registrados, disponibles y alquilados, tanto generales como por categoría, junto con los ingresos acumulados.
        int totalVehiculos = vehiculos.size();
        int totalAlquilados = alquileres.size();
        int totalDisponibles = totalVehiculos - totalAlquilados;

        int totalMotocicletas = 0;
        int totalSedanes = 0;
        int totalTransportesCarga = 0;

        for (Vehiculo vehiculo : vehiculos) {
            if (vehiculo instanceof Motocicleta) {
                totalMotocicletas++;
            } else if (vehiculo instanceof Sedan) {
                totalSedanes++;
            } else if (vehiculo instanceof TransporteCarga) {
                totalTransportesCarga++;
            }
        }

        StringBuilder reporte = new StringBuilder();

        reporte.append("----- Reporte de Alquileres -----\n");
        reporte.append("Total de Vehículos Registrados: ").append(totalVehiculos).append("\n");
        reporte.append("Total de Vehículos Disponibles: ").append(totalDisponibles).append("\n");
        reporte.append("Total de Vehículos Alquilados: ").append(totalAlquilados).append("\n");
        reporte.append("Total de Motocicletas: ").append(totalMotocicletas).append("\n");
        reporte.append("Total de Sedanes: ").append(totalSedanes).append("\n");
        reporte.append("Total de Transportes de Carga: ").append(totalTransportesCarga).append("\n");
        reporte.append("Ingresos Acumulados: $").append(calcularTotalIngresos()).append("\n");
        return reporte.toString();
    }    
}
