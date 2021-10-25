package com.example.biblioteca.services;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.repositories.RepositorioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioLibro implements ServicioBase<Libro>{

    @Autowired
    private RepositorioLibro repositorio;

    @Override
    @Transactional
    public List<Libro> findAll() throws Exception {
        try {
            List<Libro> entities = this.repositorio.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro findById(long id) throws Exception {
        try {
            Optional<Libro> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro saveOne(Libro entity) throws Exception {
        try {
            Libro libro = this.repositorio.save(entity);
            return libro;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Libro updateOne(Libro entity, long id) throws Exception {
        try {
            Optional<Libro> opt = this.repositorio.findById(id);
            Libro libro = opt.get();
            libro = this.repositorio.save(entity);
            return libro;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Libro> opt = this.repositorio.findById(id);
            if (!opt.isEmpty()) {
                Libro libro = opt.get();
                libro.setActivo(!libro.isActivo());
                this.repositorio.save(libro);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /*   Metodos nuevos   */

    @Transactional
    public List<Libro> findAllByActivo() throws Exception{
        try {
            List<Libro> entities = this.repositorio.findAllByActivo();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Libro findByIdAndActivo(long id) throws Exception {
        try {
            Optional<Libro> opt = this.repositorio.findByIdAndActivo(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Libro> findByTitle(String q) throws Exception{
        try{
            List<Libro> entities = this.repositorio.findByTitle(q);
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
