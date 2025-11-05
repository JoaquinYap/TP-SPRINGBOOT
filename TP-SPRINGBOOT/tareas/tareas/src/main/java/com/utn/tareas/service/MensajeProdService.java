package com.utn.tareas.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")

public class MensajeProdService implements MensajeService{
    @Override
    public String mostrarBienvenida(){
        return "Bienvenido al servicio de Tareas. Recuerda que tienes un limite de 1000 tareas";
    }

    @Override
    public String mostrarDespedida(){
        return "Desconexion exitosa del servicio. Vuelva pronto!";
    }

}
