package model;

public class Sedan extends Vehiculo {
    private int cantidadPasajeros;
    private boolean transmisionAutomatica;

    public Sedan(String placa, String marca, String modelo, double tarifaDiaria, int cantidadPasajeros, boolean transmisionAutomatica) {
        super(placa, marca, modelo, tarifaDiaria);
        if (cantidadPasajeros <= 0) {
            throw new IllegalArgumentException("La cantidad de pasajeros debe ser mayor a cero.");
        }
        this.cantidadPasajeros = cantidadPasajeros;
        this.transmisionAutomatica = transmisionAutomatica;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public boolean isTransmisionAutomatica() {
        return transmisionAutomatica;
    }

    @Override
    public double calcularCosto(int dias) {
        validarDias(dias);
        double tarifa = getTarifaDiaria();
        if (transmisionAutomatica) {
            return (tarifa + 50) * dias;
        }
        return tarifa * dias;
    }

    @Override
    public String obtenerCategoria() {
        return "Sedán";
    }

    @Override
    public String obtenerDetalles() {
        return super.obtenerDetalles() + String.format(
                " | Pasajeros: %d | Transmisión: %s",
                cantidadPasajeros, transmisionAutomatica ? "Automática" : "Manual");
    }
}
