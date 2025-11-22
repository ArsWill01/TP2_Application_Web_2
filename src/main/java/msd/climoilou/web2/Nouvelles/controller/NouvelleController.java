package msd.climoilou.web2.Nouvelles.controller;

import msd.climoilou.web2.Nouvelles.model.Nouvelle;
import msd.climoilou.web2.Nouvelles.repository.NouvelleRepository;
import msd.climoilou.web2.Nouvelles.services.NouvelleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@CrossOrigin(origins = {"http://localhost:5173","http://localhost:5174","http://localhost:5175"})
@RestController
@RequestMapping("/nouvelles")
public class NouvelleController {

    private final NouvelleService nouvelleService;

    private Logger logger = LoggerFactory.getLogger(NouvelleController.class);

    public NouvelleController(NouvelleService nouvelleService) {
        this.nouvelleService = nouvelleService;
    }

    @GetMapping
    public Collection<Nouvelle> getAllNouvelles() throws InterruptedException {
        logger.info("NouvelleController getAllNouvelles");

        return nouvelleService.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNouvelle(@PathVariable Long id) throws InterruptedException {
        logger.info("NouvelleController deleteNouvelle pour l'ID: {}", id);

        try {
            nouvelleService.deleteById(id);

            return ResponseEntity.noContent().build();

        } catch (EmptyResultDataAccessException e) {
            if (nouvelleService.existsById(id)) {
                return ResponseEntity.internalServerError().build();
            }

            logger.warn("Tentative de suppression d'une Nouvelle inexistante avec l'ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nouvelle> updateNouvelle(@PathVariable Long id, @RequestBody Nouvelle detailsNouvelle) throws InterruptedException {
        logger.info("NouvelleController updateNouvelle pour l'ID: {}", id);

        try {
            nouvelleService.findNouvelleById(id);
            Nouvelle nouvelle;
            nouvelle = detailsNouvelle;
            nouvelleService.save(nouvelle);

            return ResponseEntity.noContent().build();

        } catch (EmptyResultDataAccessException e) {
            if (nouvelleService.existsById(id)) {
                return ResponseEntity.internalServerError().build();
            }

            logger.warn("Tentative d'update d'une Nouvelle inexistante avec l'ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Nouvelle> createNouvelle(@RequestBody Nouvelle nouvelle) {
        logger.info("NouvelleController createNouvelle");

        try {
            nouvelle.setId(null);

            Nouvelle nouvelleSauvegardee = nouvelleService.save(nouvelle);

            return ResponseEntity
                    .created(URI.create("/nouvelles/" + nouvelleSauvegardee.getId()))
                    .body(nouvelleSauvegardee);

        } catch (Exception e) {
            logger.error("Erreur lors de la création de la Nouvelle: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
