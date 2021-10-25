package com.example.biblioteca.services;

import com.example.biblioteca.entities.Autor;
import com.example.biblioteca.repositories.RepositorioAutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioAutor implements ServicioBase<Autor> {

    @Autowired
    private RepositorioAutor repositorio;

    @Override
    @Transactional
    public List<Autor> findAll() throws Exception {
        try {
            List<Autor> autores = this.repositorio.findAll();
            return autores;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Autor findById(long id) throws Exception {
        try {
            Optional<Autor> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Autor saveOne(Autor entity) throws Exception {
        try {
            Autor autor = this.repositorio.save(entity);
            return autor;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Autor updateOne(Autor entity, long id) throws Exception {
        try {
            Optional<Autor> opt = this.repositorio.findById(id);
            Autor autor = opt.get();
            autor = this.repositorio.save(entity);
            return autor;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Autor> opt = this.repositorio.findById(id);

            if (!opt.isEmpty()) {
                Autor autor = opt.get();
                autor.setActivo(!autor.isActivo());
                this.repositorio.save(autor);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
