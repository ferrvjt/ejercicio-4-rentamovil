package model;

public abstract class Vehiculo {
    private String placa;
    private String marca;
    private String modelo;
    private double tarifaDiaria;
    private boolean disponibilidad;

    public Vehiculo(String placa, String marca, String modelo, double tarifaDiaria) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("La placa no puede estar vacía.");
        }
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede estar vacía.");
        }
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("El modelo no puede estar vacío.");
        }
        if (tarifaDiaria <= 0) {
            throw new IllegalArgumentException("La tarifa diaria debe ser mayor a cero.");
        }
        this.placa = placa.trim().toUpperCase();
        this.marca = marca.trim();
        this.modelo = modelo.trim();
        this.tarifaDiaria = tarifaDiaria;
        this.disponibilidad = true;
    }

    public boolean validarDisponibilidad() {
        return disponibilidad;
    }

    public abstract double calcularCosto(int dias);

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

    public abstract String obtenerCategoria();

    /**
     * Devuelve los datos comunes del vehículo. Las subclases amplían
     * este resultado agregando sus propias características.
     */
    public String obtenerDetalles() {
        return String.format(
                "Placa: %s | Categoría: %s | Marca: %s | Modelo: %s | Tarifa diaria: Q%.2f | Disponible: %s",
                placa, obtenerCategoria(), marca, modelo, tarifaDiaria, disponibilidad ? "Sí" : "No");
    }

    protected void validarDias(int dias) {
        if (dias <= 0) {
            throw new IllegalArgumentException("Los días de alquiler deben ser un número entero positivo.");
        }
    }
}
