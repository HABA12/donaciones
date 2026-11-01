package com.remington.bancoalimentos;

import com.remington.bancoalimentos.model.entity.*;
import com.remington.bancoalimentos.model.repository.*;
import com.remington.bancoalimentos.service.BusinessRuleException;
import com.remington.bancoalimentos.service.DonacionService;
import com.remington.bancoalimentos.service.EntregaService;
import com.remington.bancoalimentos.service.InventarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BancoAlimentosApplicationTests {

    @Autowired
    private DonacionService donacionService;

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private DonanteRepository donanteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private BeneficiarioRepository beneficiarioRepository;

    @Test
    @DisplayName("Test 01: Registrar donación válida incrementa stock en inventario (RN-07)")
    void testRegistrarDonacionIncrementaStock() {
        Donante donante = donanteRepository.findAll().get(0);
        Usuario usuario = usuarioRepository.findAll().get(0);
        Producto producto = productoRepository.findAll().get(0);

        BigDecimal stockInicial = inventarioService.consultarStock(producto.getIdProducto());

        Donacion d = donacionService.registrarDonacionConDetalle(
                donante.getIdDonante(),
                usuario.getIdUsuario(),
                LocalDate.now(),
                producto.getIdProducto(),
                new BigDecimal("50.00"),
                "APTO",
                "Donación de prueba"
        );

        assertNotNull(d.getIdDonacion());
        BigDecimal stockFinal = inventarioService.consultarStock(producto.getIdProducto());
        assertEquals(stockInicial.add(new BigDecimal("50.00")), stockFinal);
    }

    @Test
    @DisplayName("Test 02: Rechazar donación si el alimento viene averiado o en mal estado (RN-14)")
    void testRechazarDonacionAlimentoNoApto() {
        Donante donante = donanteRepository.findAll().get(0);
        Usuario usuario = usuarioRepository.findAll().get(0);
        Producto producto = productoRepository.findAll().get(0);

        assertThrows(BusinessRuleException.class, () -> {
            donacionService.registrarDonacionConDetalle(
                    donante.getIdDonante(),
                    usuario.getIdUsuario(),
                    LocalDate.now(),
                    producto.getIdProducto(),
                    new BigDecimal("10.00"),
                    "RECHAZADO",
                    "Empaque roto"
            );
        });
    }

    @Test
    @DisplayName("Test 03: Rechazar entrega si el beneficiario no pertenece a estratos 1, 2 o 3 (RN-12)")
    void testRechazarEntregaBeneficiarioEstratoInvalido() {
        Beneficiario bInvalido = new Beneficiario();
        bInvalido.setNombreBeneficiario("Fundación Estrato Alto");
        bInvalido.setTipoBeneficiario("Fundacion");
        bInvalido.setEstratoSocioeconomico(5); // Estrato 5 prohibido por RN-12
        bInvalido.setUbicacionComuna("Comuna 14");
        bInvalido.setTelefonoContacto("3000000000");
        bInvalido.setTipoAyudaSolicitada("Mercado");
        bInvalido = beneficiarioRepository.save(bInvalido);

        Usuario usuario = usuarioRepository.findAll().get(0);
        Producto producto = productoRepository.findAll().get(0);

        final Long bId = bInvalido.getIdBeneficiario();
        assertThrows(BusinessRuleException.class, () -> {
            entregaService.registrarEntregaConDetalle(
                    bId,
                    usuario.getIdUsuario(),
                    LocalDate.now(),
                    "Ruta Norte",
                    producto.getIdProducto(),
                    new BigDecimal("10.00"),
                    "Prueba rechazo"
            );
        });
    }

    @Test
    @DisplayName("Test 04: Rechazar entrega si la cantidad supera el saldo en inventario (RN-16)")
    void testRechazarEntregaSuperaSaldos() {
        Beneficiario b = beneficiarioRepository.findAll().get(0);
        Usuario u = usuarioRepository.findAll().get(0);
        Producto p = productoRepository.findAll().get(0);

        BigDecimal stockActual = inventarioService.consultarStock(p.getIdProducto());
        BigDecimal cantidadExcesiva = stockActual.add(new BigDecimal("9999.00"));

        assertThrows(BusinessRuleException.class, () -> {
            entregaService.registrarEntregaConDetalle(
                    b.getIdBeneficiario(),
                    u.getIdUsuario(),
                    LocalDate.now(),
                    "Ruta Sur",
                    p.getIdProducto(),
                    cantidadExcesiva,
                    "Sobregiro"
            );
        });
    }
}
