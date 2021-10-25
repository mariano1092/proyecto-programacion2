package com.example.biblioteca.repositories;

import com.example.biblioteca.entities.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepositorioLibro extends JpaRepository<Libro, Long> {

    @Query(value = "SELECT * FROM libros  WHERE libros.activo = true", nativeQuery = true)
    List<Libro> findAllByActivo();

    @Query(value = "SELECT * FROM libros  WHERE libros.id = :id AND libros.activo = true", nativeQuery = true)
    Optional<Libro> findByIdAndActivo(@Param("id") long id);

    @Query(value = "SELECT * FROM libros WHERE libros.titulo LIKE %:q% AND libros.activo =true", nativeQuery = true)
    List<Libro> findByTitle(@Param("q")String q);

}
