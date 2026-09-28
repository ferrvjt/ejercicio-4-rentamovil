package model;

public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje) {
        super(placa, marca, modelo, tarifaDiaria);
        if (cilindraje <= 0) {
            throw new IllegalArgumentException("La cilindraje debe ser mayor a cero.");
        }
        this.cilindraje = cilindraje;
    }

    public int getCilindrada() {
        return cilindraje;
    }

    @Override
    public double calcularCosto(int dias) {
        validarDias(dias);
        double tarifa = getTarifaDiaria() * dias;
        if (cilindraje > 250){
            tarifa += 75;
        }
        return tarifa;
    }

    @Override
    public String obtenerCategoria() {
        return "Motocicleta";
    }

    @Override
    public String obtenerDetalles() {
        return super.obtenerDetalles() + String.format(" | Cilindraje: %d cc", cilindraje);
    }    
}
