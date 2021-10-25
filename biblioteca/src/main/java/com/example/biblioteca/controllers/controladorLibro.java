package com.example.biblioteca.controllers;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.services.ServicioAutor;
import com.example.biblioteca.services.ServicioEditorial;
import com.example.biblioteca.services.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

@Controller
public class controladorLibro {

    @Autowired
    private ServicioLibro svcLibro;

    @Autowired
    private ServicioAutor svcAutor;

    @Autowired
    private ServicioEditorial svcEditorial;

    @GetMapping("/inicio")
    public String inicio(Model model){
        try{
            List<Libro> libros = this.svcLibro.findAllByActivo();
            model.addAttribute("libros", libros);
            return "views/inicio";

        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalleLibro(Model model, @PathVariable("id") long id){
        try {
            Libro libro = this.svcLibro.findByIdAndActivo(id);
            model.addAttribute("libro", libro);
            return "views/detalle";

        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }

    }

    @GetMapping(value ="/busqueda")
    public String busquedaLibro(Model model, @RequestParam(value = "query", required = false) String q){
        try {
            List<Libro> libros = this.svcLibro.findByTitle(q);
            model.addAttribute("libros", libros);
            return "views/busqueda";

        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/crud")
    public String crudLibro(Model model){
        try {
            List<Libro> libros = this.svcLibro.findAllByActivo();
            model.addAttribute("libros", libros);
            return "views/crud";

        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/formulario/libro/{id}")
    public String formularioLibro(Model model, @PathVariable("id") long id){
        try {
            model.addAttribute("autores", this.svcAutor.findAll());
            model.addAttribute("editoriales" , this.svcEditorial.findAll());

            if(id == 0){
                model.addAttribute("libro", new Libro());
            } else{
                model.addAttribute("libro", this.svcLibro.findById(id));
            }
            return "views/formulario/libro";

        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping(value = "/formulario/libro/{id}")
    public String guardarLibro(
            @RequestParam("archivo")MultipartFile archivo,
            @Valid @ModelAttribute("libro") Libro libro,
                               BindingResult result, Model model,
                                @PathVariable("id") long id){
        try {
            model.addAttribute("autores", this.svcAutor.findAll());
            model.addAttribute("editoriales", this.svcEditorial.findAll());
            if(result.hasErrors()){
                return "views/formulario/libro";
            }
            String ruta = "src\\main\\resources\\static\\images";
            int index = archivo.getOriginalFilename().indexOf(".");
            String extension = "";
            extension = "." + archivo.getOriginalFilename().substring(index+1);
            String nombreFoto = Calendar.getInstance().getTimeInMillis()+extension;

            Path rutaAbsoluta = id != 0 ? Paths.get(ruta+"//"+libro.getImagen()) :
                    Paths.get(ruta+"//"+nombreFoto);

            if(id == 0){
                if(archivo.isEmpty()){
                    model.addAttribute("imageErrorMsg", "La imagen es requerida");
                    return "views/formulario/libro";
                }
                if(!validarExtension(archivo)){
                    model.addAttribute("imageErrorMsg", "La extensión no es válida");
                    return "views/formulario/libro";
                }
                if(archivo.getSize() >= 5000000){
                    model.addAttribute("imageErrorMsg", "El peso excede los 5 mb");
                    return "views/formulario/libro";
                }
                Files.write(rutaAbsoluta, archivo.getBytes());
                libro.setImagen(nombreFoto);

                this.svcLibro.saveOne(libro);
            } else{
                if(!validarExtension(archivo)){
                    model.addAttribute("imageErrorMsg", "La extensión no es válida");
                    return "views/formulario/libro";
                }
                if(archivo.getSize() >= 5000000){
                    model.addAttribute("imageErrorMsg", "El peso excede los 5 mb");
                    return "views/formulario/libro";
                }
                if(!archivo.isEmpty()){
                    Files.write(rutaAbsoluta, archivo.getBytes());
                }
                this.svcLibro.updateOne(libro, id);
            }

            return "redirect:/crud";

        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/eliminar/libro/{id}")
    public String eliminarLibro(Model model, @PathVariable("id") long id){
        try {
            model.addAttribute("libro", this.svcLibro.findById(id));
            return "views/formulario/eliminar";

        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping(value = "/eliminar/libro/{id}")
    public String desactivarLibro(Model model, @PathVariable("id") long id) {
        try {
            this.svcLibro.deleteById(id);
            return "redirect:/crud";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    public boolean validarExtension(MultipartFile archivo){
        try{
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
