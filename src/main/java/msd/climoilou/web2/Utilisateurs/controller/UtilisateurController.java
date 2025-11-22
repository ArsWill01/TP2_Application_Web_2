package msd.climoilou.web2.Utilisateurs.controller;

import msd.climoilou.web2.Utilisateurs.model.Utilisateur;
import msd.climoilou.web2.Utilisateurs.services.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin(origins = {"http://localhost:5173","http://localhost:5174","http://localhost:5175"})
@RestController
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    private final Logger logger = LoggerFactory.getLogger(UtilisateurController.class);

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/utilisateurs")
    public Collection<Utilisateur> getAllUtilisateurs() throws InterruptedException {
        logger.info("UtilisateurController getAllUtilisateurs");

        return utilisateurService.getAll();
    }
}
