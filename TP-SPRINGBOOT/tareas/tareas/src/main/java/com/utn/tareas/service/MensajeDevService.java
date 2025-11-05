package com.utn.tareas.service;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class MensajeDevService implements MensajeService{
    @Override
        public String mostrarBienvenida(){
            return "Bienvenido! Has iniciado con el perfil de Desarrollador." + "Disfruta del debug y el limite de 10 tareas.";
        }

    @Override
        public String mostrarDespedida(){
        return "Sesion finalizada. No olvides 'commit' para guardar los cambios";
    }


}
