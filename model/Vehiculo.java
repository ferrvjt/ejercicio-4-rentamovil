package model;

public class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponibilidad;

    public Vehiculo(String placa, String marca, String modelo, double tarifaDiaria) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.disponibilidad = true;
    }

    public boolean validarDisponibilidad() {
        return disponibilidad;
    }

    public double calcularCosto(int dias) {
        return dias * tarifaDiaria;
    }

    public void marcarAlquilado() {
        if (!disponibilidad) {
            throw new IllegalStateException(
                    "El vehículo con placa " + placa + " ya se encuentra alquilado.");
        }
        disponibilidad = false;
    }
 
    public void marcarDisponible() {
        if (disponibilidad) {
            throw new IllegalStateException(
                    "El vehículo con placa " + placa + " ya se encuentra disponible.");
        }
        disponibilidad = true;
    }
 
    public String getPlaca() {
        return placa;
    }
 
    public String getMarca() {
        return marca;
    }
 
    public String getModelo() {
        return modelo;
    }
 
    public double getTarifaDiaria() {
        return tarifaDiaria;
    }
 
    public String obtenerCategoria() {
        return "General";
    }
 
    /**
     * Devuelve los datos comunes del vehículo. Las subclases amplían
     * este resultado agregando sus propias características.
     */
    public String obtenerDetalles() {
        return String.format(
                "Placa: %s | Marca: %s | Modelo: %s | Tarifa diaria: Q%.2f | Disponible: %s",
                placa, marca, modelo, tarifaDiaria, disponibilidad ? "Sí" : "No");
    }
 
    protected void validarDias(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días deben ser un número entero positivo.");
        }
    }
}
