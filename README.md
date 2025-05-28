# Trabajo Práctico Integrador  
## Backend de Aplicaciones 2025

---

### Enunciado

En este trabajo los estudiantes asumirán el rol de desarrolladores de un **backend** para una **agencia de venta de vehículos usados** que les da la posibilidad a sus clientes de realizar **pruebas de manejo** de sus productos.

Cada vez que un interesado quiere probar un auto, esta prueba se registra, asociando los **datos del cliente**, del **vehículo a probar** y del **empleado** de la agencia que acompañará al cliente.

Los vehículos que tiene para la venta la agencia cuentan con un **sistema de geolocalización** que transmite regularmente su ubicación al backend, de manera que se puede conocer la ubicación de cada vehículo en un determinado momento.

Debido al elevado costo de los vehículos, se instruyó a los empleados a que **no permitan que el auto se aleje más de un cierto radio** desde la agencia y a evitar que el vehículo **ingrese en zonas peligrosas**, definidas como cuadrantes marcados por dos puntos (coordenadas **noroeste** y **sureste**).

Si alguna de estas condiciones se incumple:

- Se debe enviar una **notificación** al teléfono del empleado indicando que haga regresar el vehículo inmediatamente.
- Se debe agregar al **cliente en una lista de restringidos** (no podrá realizar más pruebas).

> ⚠️ **IMPORTANTE**: Para cálculos sobre coordenadas se trabajará en un **plano euclidiano**. Usar un sistema de coordenadas esféricas es opcional.

---

### Estructura de la base de datos

Se provee una **estructura de referencia** para la base de datos, la cual puede ser modificada por los alumnos.  
**No** se incluye la tabla para almacenar notificaciones: debe ser **creada por los alumnos** según los requerimientos del trabajo.

---

### ✅ Consignas (checklist)

Los requerimientos para el trabajo integrador son:

- [ ] **Crear endpoints para:**
  - [ ] Crear una nueva prueba:
    - [ ] Validar que el cliente no tenga la licencia vencida.
    - [ ] Verificar que el cliente no esté restringido.
    - [ ] Asegurar que el vehículo no esté en otra prueba.
  - [ ] Listar todas las pruebas en curso.
  - [ ] Finalizar una prueba con comentarios del empleado.
  - [ ] Recibir posición actual de un vehículo y:
    - [ ] Verificar si está en una prueba.
    - [ ] Validar si se excedió el radio o se ingresó a zona peligrosa.
    - [ ] Guardar notificación (y opcionalmente enviarla).
    - [ ] Restringir al cliente si se infringió una regla.
  - [ ] Enviar notificaciones de promoción a uno o más teléfonos.
  - [ ] Generar reportes de:
    - [ ] Incidentes (excesos de límites).
    - [ ] Detalle de incidentes por empleado.
    - [ ] Kilómetros recorridos por vehículo en un período.
    - [ ] Detalle de pruebas realizadas por vehículo.

- [ ] **Consumir servicio externo con configuración**:
  - [ ] Latitud/Longitud de la agencia.
  - [ ] Radio máximo permitido.
  - [ ] Listado de zonas peligrosas.

- [ ] **Implementar seguridad de acceso**:
  - [ ] Solo empleados pueden crear pruebas y mandar notificaciones.
  - [ ] Solo usuarios asociados a vehículos pueden enviar posiciones.
  - [ ] Solo administradores pueden acceder a los reportes.

---

### Restricciones obligatorias

El backend debe diseñarse con una **arquitectura de microservicios**, cumpliendo con:

1. Al menos **2 microservicios** que organicen los endpoints.
2. Implementación de un **API Gateway** como punto de entrada.
3. Interacción con los **servicios provistos por la cátedra**.
4. Un **mecanismo de seguridad** que garantice lo especificado en las consignas.

---
