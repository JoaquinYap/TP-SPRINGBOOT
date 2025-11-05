package com.utn.tareas;
import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.service.MensajeService;
import com.utn.tareas.service.TareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
@SpringBootApplication
public class TareasApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(TareasApplication.class);
    private final TareaService tareaService;
    private final MensajeService mensajeService;

    public TareasApplication(TareaService tareaService, MensajeService mensajeService){
        this.tareaService = tareaService;
        this.mensajeService = mensajeService;
    }
	public static void main(String[] args) {
		SpringApplication.run(TareasApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception{

        log.info(mensajeService.mostrarBienvenida());

        log.info(tareaService.imprimirConfiguracion());

        log.info("TAREAS INICIALES EN EL SISTEMA: ");
        imprimirLista(tareaService.listarTodas());

        log.info("AGREGANDO NUEVA TAREA...");
        try {
            Tarea nuevaTarea = tareaService.agregarTarea("Diseñar el TareaController REST",Prioridad.MEDIA);
            log.info("Tarea agregada con éxito: {}", nuevaTarea);
        } catch (IllegalStateException e) {
            log.error("ERROR al agregar tarea: {}", e.getMessage());
        }

        log.info("TAREAS PENDIENTES: ");
        imprimirLista(tareaService.listarPendientes());

        List<Tarea> pendientes = tareaService.listarPendientes();
        if (!pendientes.isEmpty()) {
            long idAMarcar = pendientes.get(0).getId();
            log.info("6. MARCANDO TAREA CON ID {} COMO COMPLETADA...", idAMarcar);
            boolean marcada = tareaService.marcarComoCompletada(idAMarcar);
            if (marcada) {
                log.info("Tarea ID {} marcada como completada.", idAMarcar);
            } else {
                log.warn("No se pudo marcar la tarea ID {} (no encontrada).", idAMarcar);
            }
        } else {
            log.warn("No hay tareas pendientes para marcar como completadas.");
        }

        log.info("MOSTRANDO ESTADÍSTICAS: ");
        log.info("\n{}", tareaService.obtenerEstadisticas());

        log.info("TAREAS COMPLETADAS: ");
        imprimirLista(tareaService.listarCompletadas());

        log.info(mensajeService.mostrarDespedida());


    }

    private void imprimirLista(List<Tarea> tareas) {
        if (tareas.isEmpty()) {
            log.info("    -> (Lista vacía)");
            return;
        }
        tareas.forEach(tarea -> log.info("    -> {}", tarea.toString()));
    }



}
