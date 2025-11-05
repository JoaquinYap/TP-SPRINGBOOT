package com.utn.tareas.repository;

import com.utn.tareas.model.Tarea;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TareaRepository {
    private final List<Tarea> tareas;
    private AtomicLong nextId = new AtomicLong(0);

    public TareaRepository() {
        this.tareas = new ArrayList<>();

        guardar(new Tarea("Terminar TP de Spring Boot"));
        guardar(new Tarea("Rendir parciales"));
        guardar(new Tarea("Entrenar"));
        guardar(new Tarea("Comprar para cenar"));
    }

    public Tarea guardar(Tarea tarea) {
        // Genera ID automático
        if (tarea.getId() == 0) {
            tarea.setId(nextId.incrementAndGet());
        }
        if (tarea.getId() > 0 &&
                tareas.stream().anyMatch(t -> t.getId() == tarea.getId())) {

            eliminar(tarea.getId()); // Elimina la versión antigua
        }

        this.tareas.add(tarea);
        return tarea;
    }

    public List<Tarea> obtenerTodas() {
        // Retorna una copia de la lista para evitar modificaciones externas directas
        return new ArrayList<>(this.tareas);
    }

    public Optional<Tarea> buscarPorId(long id) {
        return this.tareas.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    public boolean eliminarPorId(long id) {
        return this.tareas.removeIf(t -> t.getId() == id);
    }

    private void eliminar(long id) {
        this.tareas.removeIf(t -> t.getId() == id);
    }




}


