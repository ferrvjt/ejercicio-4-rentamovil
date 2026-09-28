package model;

public class TransporteCarga extends Vehiculo {
    private double capacidadCarga;

    public TransporteCarga(String placa, String marca, String modelo, double tarifaDiaria, double capacidadCarga) {
        super(placa, marca, modelo, tarifaDiaria);
        if (capacidadCarga <= 0) {
            throw new IllegalArgumentException("La capacidad de carga debe ser mayor a cero.");
        }
        this.capacidadCarga = capacidadCarga;
    }

    public double getCapacidadCarga() {
        return capacidadCarga;
    }

    @Override
    public double calcularCosto(int dias) {
        validarDias(dias);
        double tarifa = (getTarifaDiaria() + (100*capacidadCarga)) * dias;
        return tarifa;
    }

    @Override
    public String obtenerCategoria() {
        return "Transporte de Carga";
    }

    @Override
    public String obtenerDetalles() {
        return super.obtenerDetalles() + String.format(" | Capacidad de Carga: %.2f kg", capacidadCarga);
    }
    
}
