package com.unu.web.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.unu.web.service.ContratoService;

@Component
class StartupRunner implements CommandLineRunner {

    @Autowired
    private ContratoService contratoService;

    @Override
    public void run(String...args) throws Exception {
        contratoService.ActualizarEstadosContratos();
    }
}
