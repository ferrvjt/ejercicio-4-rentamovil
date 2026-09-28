# Ejercicio 4 - Herencia
## RentaMovil
### Participantes
Alvaro Elias Flores Pardo - 261868
Fernando Javier Tomás Velásquez - 261329

## Análisis

### Requisitos funcionales:
- Permite registrar nuevos vehículos tomando en cuenta la placa, marca, modelo, tarifa y disponibilidad.
- Permite registrar entre tres tipos de vehículos: Sedán, motocicleta y camioneta de carga.
- No permite registrar placas duplicadas.
- Permite consultar vehículos y verificar: Asientos, transmisión, cilindraje (motos), capacidad de carga máxima (camionetas de carga).
- Realizar cobros, tomando en cuenta diferentes tarifas.
- Permite hacer cotizaciones incluso si un vehículo no está disponible.
- Al realizar el alquiler. Primero revisa disponibilidad, segundo muestra el total, y espera confirmación.
- Al aceptar el alquiler. El vehículo no debe tener dos alquileres al mismo tiempo.
- Al cancelar antes de confirmar, no se modifican la disponibilidad ni los ingresos..
- Hasta que se devuelva el vehículo no puede alquilarse nuevamente.
- Permite consultar cuántos vehículos están registrados, cuántos disponibles, cuantos alquilados y mostrar por categoría. 
- Permite consultar la ganancia total por alquileres confirmados.
- El costo base del alquiler se obtiene multiplicando la tarifa diaria por la cantidad de días.
- Los sedanes automáticos tienen un recargo de Q50 por día. Los manuales no tienen este recargo.
- Las motocicletas con cilindraje mayor de 250 cc tienen un recargo único de Q75 por alquiler. Las de 250 cc o menos no tienen recargo.
- Las camionetas de carga tienen un recargo de Q100 por cada tonelada de capacidad máxima por cada día de alquiler. La capacidad puede incluir decimales y el cobro no depende de la carga que transporte el cliente.
- Las cotizaciones deben mostrar las características del vehículo, su disponibilidad y el costo total, sin modificar los ingresos ni la disponibilidad.
- El monto completo se cobra únicamente al confirmar el alquiler.
- La devolución deja disponible el vehículo y no genera otro cobro ni elimina el ingreso registrado.
- Las tarifas, cantidades de pasajeros, cilindrajes y capacidades de carga deben ser mayores que cero. Los días deben ser números enteros positivos.
- No se aceptan placas vacías ni repetidas.
- Las placas inexistentes, los intentos de alquilar vehículos ocupados y las devoluciones de vehículos disponibles deben generar un mensaje claro sin modificar la información.
- Las entradas con un formato incorrecto no deben provocar que el programa termine inesperadamente.
- El programa debe iniciar con al menos dos vehículos por categoría, con placas diferentes, todos disponibles y sin ingresos registrados.
- El sistema funcionará mediante un menú de consola que permanecerá disponible hasta seleccionar la opción de salir.
- Los montos se mostrarán con dos decimales.
- La información se conservará únicamente durante la ejecución. No se manejan fechas, multas, reembolsos ni reservaciones.


Las clases principales que utilizaremos serán:
## Clase Vehículo
 Clase padre abstracta que contiene los métodos y atributos comunes entre los vehículos que maneja la empresa.
### Atributos:
- placa: String, privado. Identifica al vehículo.
- marca: String, privado. Guarda la marca del vehículo.
- modelo: String, privado. Guarda el modelo del vehículo.
- tarifaDiaria: double, privado. Almacena el precio base por día.
- disponibilidad: boolean, privado. Indica si puede alquilarse. Su valor inicial será TRUE.
### Métodos:
- Vehiculo(String placa, String marca, String modelo, double tarifaDiaria), protegido: Constructor que inicializa los atributos comunes y establece la disponibilidad en TRUE. Rechaza placas vacías y tarifas que no sean mayores que cero.
- validarDisponibilidad(): boolean, público. Devuelve TRUE cuando el vehículo está disponible.
- calcularCosto(int dias): double, público y abstracto. Cada subclase implementa este método con su regla de cobro. Solo acepta días mayores que cero.
- marcarAlquilado(): void.  Cambia la disponibilidad a TRUE. Rechaza el cambio si el vehículo ya está alquilado.
- marcarDisponible(): void. Cambia la disponibilidad a TRUE. Rechaza el cambio si ya estaba disponible.
- getPlaca(): String, público. Devuelve la placa.
- getMarca(): String, público. Devuelve la marca.
- getModelo(): String, público. Devuelve el modelo.
- getTarifaDiaria(): double, público. Devuelve la tarifa diaria.
- obtenerCategoria(): String, público y abstracto. Devuelve el nombre de la categoría.
- obtenerDetalles(): String, público. Devuelve los datos comunes del vehículo. Las subclases amplían este resultado con sus características.

## Clase Sedán
 Hereda de Vehículo y agrega la cantidad de pasajeros y el tipo de transmisión.
### Atributos:
- cantidadPasajeros: int, privado. Indica cuántas personas puede transportar. Debe ser mayor que cero.
- transmisionAutomatica: boolean, privado. Su valor es TRUE para transmisión automática y FALSE para manual.
### Métodos:
- Sedan(String placa, String marca, String modelo, double tarifaDiaria, int cantidadPasajeros, boolean transmisionAutomatica), público: Constructor que inicializa los datos comunes mediante el constructor de Vehículo y asigna los atributos propios.
- calcularCosto(int dias): double, público. Si es automático, devuelve (tarifaDiaria + 50) × dias. Si es manual, devuelve tarifaDiaria × dias. Consulta la tarifa mediante getTarifaDiaria().
- obtenerCategoria(): String, público. Devuelve "Sedán".
- obtenerDetalles(): String, público. Agrega la cantidad de pasajeros y el tipo de transmisión a los datos comunes.

## Clase Motocicleta 
Hereda de Vehículo y tiene el cilindraje como característica distintiva.
### Atributos:
- cilindraje: int, privado. Almacena el cilindraje en centímetros cúbicos. Debe ser mayor que cero.
### Métodos:
- Motocicleta(String placa, String marca, String modelo, double tarifaDiaria, int cilindraje), público: Constructor que inicializa los datos heredados y el cilindraje.
- calcularCosto(int dias): double, público. Devuelve tarifaDiaria × dias y suma Q75 si el cilindraje es mayor de 250 cc. El recargo se aplica una sola vez por alquiler.
- obtenerCategoria(): String, público. Devuelve "Motocicleta".
- obtenerDetalles(): String, público. Agrega el cilindraje a los datos comunes.

## Clase TransporteCarga
Hereda de Vehículo y agrega la capacidad máxima de carga.
### Atributos:
- capacidadCargaMaxima: double, privado. Representa la capacidad máxima en toneladas. Permite decimales y debe ser mayor que cero.
### Métodos:
-TransporteCarga(String placa, String marca, String modelo, double tarifaDiaria, double capacidadCargaMaxima), público: Constructor que inicializa los datos heredados y la capacidad de carga.
-calcularCosto(int dias): double, público. Devuelve (tarifaDiaria + 100 × capacidadCargaMaxima) × dias.
-obtenerCategoria(): String, público. Devuelve "Transporte de carga".
-obtenerDetalles(): String, público. Agrega la capacidad máxima en toneladas a los datos comunes.

## Clase Alquiler 
Conserva los datos de cada alquiler confirmado para consultar el historial y calcular los ingresos.
### Atributos:
- vehiculo: Vehiculo, privado. Referencia al vehículo que se alquiló.
- dias: int, privado. Guarda la cantidad de días contratados.
- montoTotal: double, privado. Conserva el monto cobrado al confirmar la operación.
### Métodos:
- Alquiler(Vehiculo vehiculo, int dias, double montoTotal): Constructor utilizado por RegistroAlquiler para guardar los datos de una operación confirmada.
- getVehiculo(): Vehiculo, público. Devuelve el vehículo asociado.
- getDias(): int, público. Devuelve la duración contratada.
- getMontoTotal(): double, público. Devuelve el importe cobrado.
Esta clase conserva el registro histórico. La disponibilidad actual se mantiene en Vehículo para evitar almacenar el mismo estado en dos lugares.

## Clase RegistroAlquiler
Administra la flota, coordina los alquileres y devoluciones, conserva el historial y obtiene los reportes.
### Atributos:
- vehiculos: ArrayList<Vehiculo>, privado. Almacena los vehículos registrados.
- alquileres: ArrayList<Alquiler>, privado. Conserva los alquileres confirmados.
### Métodos:
- RegistroAlquiler(), público: Constructor que inicializa ambas listas vacías.
- validarPlacaUnica(String placa): boolean, privado. Devuelve TRUE si la placa no pertenece a otro vehículo registrado.
- registrarVehiculo(Vehiculo vehiculo): void, público. Verifica que la placa no se repita y agrega el vehículo.
- buscarVehiculo(String placa): Vehiculo, público. Devuelve el vehículo correspondiente. Si no existe, genera un error que la clase Principal convierte en un mensaje.
- cotizar(String placa, int dias): double, público. Busca el vehículo y solicita su cálculo de costo. No exige disponibilidad ni modifica los registros.
- confirmarAlquiler(String placa, int dias): Alquiler, público. Verifica la disponibilidad y los días, calcula el costo, registra el alquiler y marca el vehículo como ocupado. Devuelve el alquiler creado.
- registrarDevolucion(String placa): void, público. Verifica que el vehículo exista y esté alquilado; luego lo marca como disponible. No modifica el historial ni los ingresos.
- consultarVehiculos(): ArrayList  $<Vehiculo>$, público. Devuelve una copia de la lista para consultar la flota sin permitir cambios directos en la colección interna.
- consultarHistorial(): ArrayList $<Alquiler>$, público. Devuelve una copia de los alquileres confirmados.
- calcularGananciaTotal(): double, público. Suma los montos de los alquileres confirmados. El resultado representa los ingresos acumulados.
- generarReporte(): String, público. Devuelve las cantidades de vehículos registrados, disponibles y alquilados, tanto generales como por categoría, junto con los ingresos acumulados.

## Clase Principal
Contiene el método main y se encarga de la interacción con el usuario mediante la consola.
### Atributos:
- registro: RegistroAlquiler, privado. Referencia al objeto que administra las operaciones.
- entrada: Scanner, privado. Permite leer los datos ingresados en la consola.
### Métodos:
- Principal(), público: Constructor que crea RegistroAlquiler y el lector de consola.
- main(String[] args): void, público y estático. Crea Principal, carga los datos iniciales e inicia el menú.
- cargarDatosIniciales(): void, privado. Registra al menos dos vehículos de cada categoría, todos disponibles. Incluye un sedán manual y uno automático, una motocicleta de hasta 250 cc y otra de más de 250 cc, y camionetas con distintas capacidades, incluyendo una decimal.
- ejecutarMenu(): void, público. Muestra las opciones, recibe las entradas y llama a las operaciones correspondientes hasta que el usuario decida salir. Controla errores de formato y operaciones inválidas para mostrar mensajes claros.