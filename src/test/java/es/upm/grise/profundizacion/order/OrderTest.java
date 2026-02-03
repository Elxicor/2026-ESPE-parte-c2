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
