package com.example.biblioteca.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "El título no puede estar vacío")
    private String titulo;

    @NotEmpty(message = "La descripción no puede estar vacío")
    @Size(min=20, max=250, message = "La descripcón debe tener entre 20 y 250 caracteres.")
    private String descripcion;

    private int cantPaginas;

    private short stock;
    @Min(value= 5)
    @Max(value = 2000)
    private float precio;
    private String imagen;

    @NotNull(message = "La fecha no puede ser nula")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @PastOrPresent(message = "Igual o menor a la fecha de hoy")
    private Date fechaDeLanzamiento;
    private boolean activo = true;

    @NotNull(message = "El Autor es REQUERIDO")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="fk_autor", nullable = false)
    private Autor autor;

    @NotNull(message = "La Editorial es REQUERIDA")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="fk_editorial", nullable = false)
    private Editorial editorial;
}
