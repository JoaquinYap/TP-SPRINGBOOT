package com.utn.tareas.service;
import com.utn.tareas.model.Prioridad;
import com.utn.tareas.model.Tarea;
import com.utn.tareas.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaService {

    @Value("${app.nombre}")
    private String appNombre;

    @Value("${app.max-tareas}")
    private int maxTareas;

    @Value("${app.mostrar-estadisticas}")
    private boolean mostrarEstadisticas;


    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository){
        this.tareaRepository = tareaRepository;
    }

    public Tarea agregarTarea(String descripcion, Prioridad prioridad) {

        if (tareaRepository.obtenerTodas().size() >= maxTareas) {
            throw new IllegalStateException(
                    String.format("Error: Se ha alcanzado el límite máximo de %d tareas para la aplicación '%s'.",
                            maxTareas, appNombre)
            );
        }


        Tarea nuevaTarea = new Tarea(descripcion);
        nuevaTarea.setPrioridad(prioridad);
        // El repositorio se encarga de asignar el ID
        return tareaRepository.guardar(nuevaTarea);
    }


    public List<Tarea> listarTodas() {
        return tareaRepository.obtenerTodas();
    }


    public List<Tarea> listarPendientes() {
        return tareaRepository.obtenerTodas().stream()
                .filter(tarea -> !tarea.isCompletada())
                .collect(Collectors.toList());
    }


    public List<Tarea> listarCompletadas() {
        return tareaRepository.obtenerTodas().stream()
                .filter(Tarea::isCompletada)
                .collect(Collectors.toList());
    }


    public boolean marcarComoCompletada(long id) {
        return tareaRepository.buscarPorId(id).map(tarea -> {
            tarea.setCompletada(true);
            tareaRepository.guardar(tarea); // Guarda el cambio
            return true;
        }).orElse(false);
    }


    public String obtenerEstadisticas() {
        List<Tarea> todas = tareaRepository.obtenerTodas();
        long total = todas.size();
        long completadas = todas.stream().filter(Tarea::isCompletada).count();
        long pendientes = total - completadas; // O usar: todas.stream().filter(t -> !t.isCompletada()).count();

        // Formatea el resultado
        return String.format(
                "Estadísticas del Repositorio:\n" +
                        "- Tareas Totales: %d\n" +
                        "- Tareas Completadas: %d\n" +
                        "- Tareas Pendientes: %d",
                total, completadas, pendientes
        );
    }
    public String imprimirConfiguracion() {
        return String.format(
                "--- [Configuración Actual de la Aplicación] ---\n" +
                        "Nombre de la App: %s\n" +
                        "Límite Máximo de Tareas: %d\n" +
                        "Mostrar Estadísticas: %b",
                appNombre, maxTareas, mostrarEstadisticas
        );
    }

}
