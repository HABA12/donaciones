package com.remington.bancoalimentos.service;

import java.util.Map;

public interface AnalisisIAService {
    /**
     * Genera análisis predictivo y estratégico sobre el inventario y donaciones
     * utilizando el modelo de IA en Groq.
     */
    String generarAnalisisPredictivo();

    /**
     * Analiza una consulta específica del usuario o planificador de bodega.
     */
    String consultarIA(String pregunta);
}
