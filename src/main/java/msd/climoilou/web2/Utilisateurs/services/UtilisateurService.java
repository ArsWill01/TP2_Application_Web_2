package msd.climoilou.web2.Utilisateurs.services;

import msd.climoilou.web2.Nouvelles.model.Nouvelle;
import msd.climoilou.web2.Utilisateurs.model.Utilisateur;
import msd.climoilou.web2.Utilisateurs.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Utilisateur> getAll(){
        return utilisateurRepository.findAll();
    }
}
