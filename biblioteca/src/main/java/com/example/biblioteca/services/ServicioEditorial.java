package com.example.biblioteca.services;

import com.example.biblioteca.entities.Editorial;
import com.example.biblioteca.repositories.RepositorioEditorial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioEditorial implements ServicioBase<Editorial>{

    @Autowired
    private RepositorioEditorial repositorio;

    @Override
    @Transactional
    public List<Editorial> findAll() throws Exception {
        try {
            List<Editorial> editoriales = this.repositorio.findAll();
            return editoriales;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Editorial findById(long id) throws Exception {
        try {
            Optional<Editorial> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Editorial saveOne(Editorial entity) throws Exception {
        try {
            Editorial editorial = this.repositorio.save(entity);
            return editorial;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Editorial updateOne(Editorial entity, long id) throws Exception {
        try {
            Optional<Editorial> opt = this.repositorio.findById(id);
            Editorial editorial = opt.get();
            editorial = this.repositorio.save(entity);
            return editorial;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(long id) throws Exception {
        try {
            Optional<Editorial> opt = this.repositorio.findById(id);
            if (!opt.isEmpty()) {
                Editorial editorial = opt.get();
                editorial.setActivo(!editorial.isActivo());
                this.repositorio.save(editorial);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
