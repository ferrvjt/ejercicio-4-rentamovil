package model;

public class Alquiler {
    private Vehiculo vehiculo;
    private int dias;
    private double montoTotal;

    public Alquiler(Vehiculo vehiculo, int dias, double montoTotal) {
        if (dias <= 0) {
            throw new IllegalArgumentException("La cantidad de días debe ser mayor a cero.");
        }
        this.vehiculo = vehiculo;
        this.dias = dias;
        this.montoTotal = montoTotal;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public int getDias() {
        return dias;
    }

    public double calcularCostoTotal() {
        return vehiculo.calcularCosto(dias);
    }

    public double getMontoTotal() {
        return montoTotal;
    }
}
