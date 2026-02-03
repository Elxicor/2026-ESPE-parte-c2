LLM utilizado: Gemini

Conversacion mantenida con el LLM

Esta conversación se hizo a continuación del ultimo mensaje del chat mantenido en conv_programación.md

## Mensaje inicial (Usuario)

Ahora dame todos los test necesarios para el ejercicio realizado

## Respuesta 1 (LLM)

Te proporciono los tests completos para `OrderTest.java` basándome en las especificaciones de tu ejercicio: [baeldung](https://www.baeldung.com/junit-assert-exception)

## OrderTest.java

```java
package es.upm.grise.profundizacion.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        order = new Order();
        product1 = new Product("Product1");
        product2 = new Product("Product2");
    }

    // Test 1: Constructor - la lista debe estar vacía pero no nula
    @Test
    public void testConstructor_ItemsListIsEmptyButNotNull() {
        assertNotNull(order.getItems(), "La lista de items no debe ser nula");
        assertTrue(order.getItems().isEmpty(), "La lista de items debe estar vacía");
    }

    // Test 2: addItem con price negativo - debe lanzar IncorrectItemException
    @Test
    public void testAddItem_NegativePrice_ThrowsException() {
        Item item = new Item(product1, -10.0, 5);
        
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        }, "Debe lanzar IncorrectItemException cuando el price es negativo");
    }

    // Test 3: addItem con price cero - debe funcionar (>= 0)
    @Test
    public void testAddItem_ZeroPrice_NoException() throws IncorrectItemException {
        Item item = new Item(product1, 0.0, 5);
        
        assertDoesNotThrow(() -> {
            order.addItem(item);
        }, "No debe lanzar excepción cuando el price es cero");
        
        assertEquals(1, order.getItems().size(), "Debe haber 1 item en la lista");
    }

    // Test 4: addItem con quantity cero - debe lanzar IncorrectItemException
    @Test
    public void testAddItem_ZeroQuantity_ThrowsException() {
        Item item = new Item(product1, 10.0, 0);
        
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        }, "Debe lanzar IncorrectItemException cuando quantity es cero");
    }

    // Test 5: addItem con quantity negativa - debe lanzar IncorrectItemException
    @Test
    public void testAddItem_NegativeQuantity_ThrowsException() {
        Item item = new Item(product1, 10.0, -5);
        
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        }, "Debe lanzar IncorrectItemException cuando quantity es negativa");
    }

    // Test 6: addItem válido - debe agregarse correctamente
    @Test
    public void testAddItem_ValidItem_AddsSuccessfully() throws IncorrectItemException {
        Item item = new Item(product1, 10.0, 3);
        
        order.addItem(item);
        
        assertEquals(1, order.getItems().size(), "Debe haber 1 item en la lista");
        assertTrue(order.getItems().contains(item), "El item debe estar en la lista");
    }

    // Test 7: addItem mismo producto y mismo precio - debe incrementar quantity
    @Test
    public void testAddItem_SameProductSamePrice_IncrementsQuantity() throws IncorrectItemException {
        Item item1 = new Item(product1, 10.0, 3);
        Item item2 = new Item(product1, 10.0, 5);
        
        order.addItem(item1);
        order.addItem(item2);
        
        assertEquals(1, order.getItems().size(), "Debe haber solo 1 item en la lista");
        
        // Verificar que la cantidad se incrementó
        Item itemInOrder = order.getItems().iterator().next();
        assertEquals(8, itemInOrder.getQuantity(), "La quantity debe ser 3 + 5 = 8");
    }

    // Test 8: addItem mismo producto pero diferente precio - debe agregar nuevo item
    @Test
    public void testAddItem_SameProductDifferentPrice_AddsNewItem() throws IncorrectItemException {
        Item item1 = new Item(product1, 10.0, 3);
        Item item2 = new Item(product1, 15.0, 5);
        
        order.addItem(item1);
        order.addItem(item2);
        
        assertEquals(2, order.getItems().size(), "Debe haber 2 items en la lista (mismo producto, distinto precio)");
    }

    // Test 9: addItem productos diferentes - debe agregar ambos
    @Test
    public void testAddItem_DifferentProducts_AddsBoth() throws IncorrectItemException {
        Item item1 = new Item(product1, 10.0, 3);
        Item item2 = new Item(product2, 20.0, 2);
        
        order.addItem(item1);
        order.addItem(item2);
        
        assertEquals(2, order.getItems().size(), "Debe haber 2 items en la lista");
    }

    // Test 10: addItem múltiples items con lógica mixta
    @Test
    public void testAddItem_MixedScenario() throws IncorrectItemException {
        Item item1 = new Item(product1, 10.0, 3);   // Producto 1, precio 10
        Item item2 = new Item(product2, 20.0, 2);   // Producto 2, precio 20
        Item item3 = new Item(product1, 10.0, 5);   // Producto 1, precio 10 (merge)
        Item item4 = new Item(product1, 15.0, 1);   // Producto 1, precio 15 (nuevo)
        
        order.addItem(item1);
        order.addItem(item2);
        order.addItem(item3);
        order.addItem(item4);
        
        assertEquals(3, order.getItems().size(), "Debe haber 3 items diferentes en la lista");
    }
}
```

## Dependencias necesarias en pom.xml

Asegúrate de tener estas dependencias en tu `pom.xml`: [geeksforgeeks](https://www.geeksforgeeks.org/software-testing/junit-5-maven-dependency/)

```xml
<dependencies>
    <!-- JUnit 5 (Jupiter) -->
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-api</artifactId>
        <version>5.9.3</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.9.3</version>
        <scope>test</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.22.2</version>
        </plugin>
    </plugins>
</build>
```

## Notas importantes

**Asumiendo que `Item` y `Product` tienen estos constructores:**
- `Item(Product product, double price, int quantity)`
- `Product(String name)`

**Si tus clases tienen constructores diferentes**, avísame y ajusto los tests. [stackoverflow](https://stackoverflow.com/questions/71018642/unit-testing-a-custom-exception-in-java)

**Cobertura de los tests:**
- ✅ Constructor (lista vacía no nula)
- ✅ Validación de price (negativo, cero, positivo)
- ✅ Validación de quantity (negativa, cero, positiva)
- ✅ Agregar items válidos
- ✅ Merge de mismo producto/mismo precio
- ✅ Separación de mismo producto/diferente precio
- ✅ Escenarios mixtos complejos

Ejecuta con `mvn test` para verificar que todos pasen correctamente.


## Seguimiento (Usuario)

da un error

The constructor Product(String) is undefinedJava(134217858)
es.upm.grise.profundizacion.order.Product

package es.upm.grise.profundizacion.order;


public class Product {
    
    // Please notice the difference between the class diagram and this implementation
    // The reason is to facilitate unit testing
    
    long id;
    
    void setId(long id) {
        
        this.id = id;
        
    }
    
    long getId() {
        
        return id;
        
    }


}


## Respuesta 2 (LLM)

Entiendo el problema. `Product` solo tiene `id` (sin constructor con String) y `Item` es una **interfaz** (no una clase). [baeldung](https://www.baeldung.com/junit-assert-exception)

Necesitas crear una **implementación concreta de Item** para tus tests. Te proporciono la solución completa:

## 1. Crear ItemImpl.java (implementación de Item para testing)

**Ruta:** `src/test/java/es/upm/grise/profundizacion/order/ItemImpl.java`

```java
package es.upm.grise.profundizacion.order;

public class ItemImpl implements Item {
    
    private Product product;
    private double price;
    private int quantity;
    
    public ItemImpl(Product product, double price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }
    
    @Override
    public Product getProduct() {
        return this.product;
    }
    
    @Override
    public double getPrice() {
        return this.price;
    }
    
    @Override
    public int getQuantity() {
        return this.quantity;
    }
    
    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
```

## 2. OrderTest.java actualizado

```java
package es.upm.grise.profundizacion.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setUp() {
        order = new Order();
        
        // Crear productos con IDs diferentes
        product1 = new Product();
        product1.setId(1L);
        
        product2 = new Product();
        product2.setId(2L);
    }

    // Test 1: Constructor - la lista debe estar vacía pero no nula
    @Test
    public void testConstructor_ItemsListIsEmptyButNotNull() {
        assertNotNull(order.getItems(), "La lista de items no debe ser nula");
        assertTrue(order.getItems().isEmpty(), "La lista de items debe estar vacía");
    }

    // Test 2: addItem con price negativo - debe lanzar IncorrectItemException
    @Test
    public void testAddItem_NegativePrice_ThrowsException() {
        Item item = new ItemImpl(product1, -10.0, 5);
        
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        }, "Debe lanzar IncorrectItemException cuando el price es negativo");
    }

    // Test 3: addItem con price cero - debe funcionar (>= 0)
    @Test
    public void testAddItem_ZeroPrice_NoException() throws IncorrectItemException {
        Item item = new ItemImpl(product1, 0.0, 5);
        
        assertDoesNotThrow(() -> {
            order.addItem(item);
        }, "No debe lanzar excepción cuando el price es cero");
        
        assertEquals(1, order.getItems().size(), "Debe haber 1 item en la lista");
    }

    // Test 4: addItem con quantity cero - debe lanzar IncorrectItemException
    @Test
    public void testAddItem_ZeroQuantity_ThrowsException() {
        Item item = new ItemImpl(product1, 10.0, 0);
        
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        }, "Debe lanzar IncorrectItemException cuando quantity es cero");
    }

    // Test 5: addItem con quantity negativa - debe lanzar IncorrectItemException
    @Test
    public void testAddItem_NegativeQuantity_ThrowsException() {
        Item item = new ItemImpl(product1, 10.0, -5);
        
        assertThrows(IncorrectItemException.class, () -> {
            order.addItem(item);
        }, "Debe lanzar IncorrectItemException cuando quantity es negativa");
    }

    // Test 6: addItem válido - debe agregarse correctamente
    @Test
    public void testAddItem_ValidItem_AddsSuccessfully() throws IncorrectItemException {
        Item item = new ItemImpl(product1, 10.0, 3);
        
        order.addItem(item);
        
        assertEquals(1, order.getItems().size(), "Debe haber 1 item en la lista");
        assertTrue(order.getItems().contains(item), "El item debe estar en la lista");
    }

    // Test 7: addItem mismo producto y mismo precio - debe incrementar quantity
    @Test
    public void testAddItem_SameProductSamePrice_IncrementsQuantity() throws IncorrectItemException {
        Item item1 = new ItemImpl(product1, 10.0, 3);
        Item item2 = new ItemImpl(product1, 10.0, 5);
        
        order.addItem(item1);
        order.addItem(item2);
        
        assertEquals(1, order.getItems().size(), "Debe haber solo 1 item en la lista");
        
        // Verificar que la cantidad se incrementó
        Item itemInOrder = order.getItems().iterator().next();
        assertEquals(8, itemInOrder.getQuantity(), "La quantity debe ser 3 + 5 = 8");
    }

    // Test 8: addItem mismo producto pero diferente precio - debe agregar nuevo item
    @Test
    public void testAddItem_SameProductDifferentPrice_AddsNewItem() throws IncorrectItemException {
        Item item1 = new ItemImpl(product1, 10.0, 3);
        Item item2 = new ItemImpl(product1, 15.0, 5);
        
        order.addItem(item1);
        order.addItem(item2);
        
        assertEquals(2, order.getItems().size(), "Debe haber 2 items en la lista (mismo producto, distinto precio)");
    }

    // Test 9: addItem productos diferentes - debe agregar ambos
    @Test
    public void testAddItem_DifferentProducts_AddsBoth() throws IncorrectItemException {
        Item item1 = new ItemImpl(product1, 10.0, 3);
        Item item2 = new ItemImpl(product2, 20.0, 2);
        
        order.addItem(item1);
        order.addItem(item2);
        
        assertEquals(2, order.getItems().size(), "Debe haber 2 items en la lista");
    }

    // Test 10: addItem múltiples items con lógica mixta
    @Test
    public void testAddItem_MixedScenario() throws IncorrectItemException {
        Item item1 = new ItemImpl(product1, 10.0, 3);   // Producto 1, precio 10
        Item item2 = new ItemImpl(product2, 20.0, 2);   // Producto 2, precio 20
        Item item3 = new ItemImpl(product1, 10.0, 5);   // Producto 1, precio 10 (merge)
        Item item4 = new ItemImpl(product1, 15.0, 1);   // Producto 1, precio 15 (nuevo)
        
        order.addItem(item1);
        order.addItem(item2);
        order.addItem(item3);
        order.addItem(item4);
        
        assertEquals(3, order.getItems().size(), "Debe haber 3 items diferentes en la lista");
    }
}
```

## 3. IMPORTANTE: Ajusta Order.java para comparar productos por ID

Tu implementación actual de `Order.java` probablemente usa `.equals()` para comparar productos, pero `Product` no tiene ese método sobrescrito. Necesitas comparar por **ID**: [baeldung](https://www.baeldung.com/junit-assert-exception)

```java
// En Order.java, cambia esta línea:
if (existingItem.getProduct().equals(item.getProduct())) {

// Por esta:
if (existingItem.getProduct().getId() == item.getProduct().getId()) {
```

## Resumen de cambios

1. **Crear** `ItemImpl.java` en `src/test/java/.../order/` (implementación concreta de la interfaz)
2. **Actualizar** `OrderTest.java` con los tests ajustados
3. **Modificar** `Order.java` para comparar productos por `.getId()` en lugar de `.equals()`

Ejecuta `mvn test` y todos los tests deberían pasar correctamente.