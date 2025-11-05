package com.utn.tareas.model;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Tarea {
    public long id;
    public String descripcion;
    public boolean completada;
    public Prioridad prioridad;

    public Tarea(String descripcion) {
        this.descripcion = descripcion;
        this.completada = false;
        this.prioridad = Prioridad.MEDIA;
        this.id = 0L;
    }


}
