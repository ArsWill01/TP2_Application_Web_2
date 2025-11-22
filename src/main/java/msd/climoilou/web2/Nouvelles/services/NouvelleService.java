package msd.climoilou.web2.Nouvelles.services;

import msd.climoilou.web2.Criteres.model.Critere;
import msd.climoilou.web2.Nouvelles.model.Nouvelle;
import msd.climoilou.web2.Nouvelles.repository.NouvelleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class NouvelleService {
    final
    NouvelleRepository nouvelleRepository;

    public NouvelleService(NouvelleRepository nouvelleRepository) {
        this.nouvelleRepository = nouvelleRepository;
    }

    public List<Nouvelle> getAll(){
        return nouvelleRepository.findAll();
    }

    public void deleteById(long id){
        nouvelleRepository.deleteById(id);
    }

    public boolean existsById(long id){
        return nouvelleRepository.existsById(id);
    }

    public Collection<Nouvelle> findNouvelleById(long id){
        return nouvelleRepository.findNouvelleById(id);
    }

    public Nouvelle save(Nouvelle nouvelle){
        return nouvelleRepository.save(nouvelle);
    }

    public Collection<Nouvelle> getAll(Critere critere) {
        String texteRecherche = critere.getTextRecherche();
        String dateRecherche = critere.getDateRecherche();
        LocalDateTime localDateTime = null;
        if (!Objects.isNull(dateRecherche) && !dateRecherche.isEmpty()) {
            String pattern = "yyyy-MM-dd HH:mm:ss";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            localDateTime = LocalDateTime.parse(dateRecherche, formatter);
        }

        if (!Objects.isNull(texteRecherche) && !Objects.isNull(dateRecherche)) {
            return nouvelleRepository.findByTexteContainingAndDateGreaterThanEqual(
                    texteRecherche,
                    localDateTime
            );
        }

        if (!Objects.isNull(texteRecherche)) {
            return nouvelleRepository.findByTexteContaining(texteRecherche);
        }

        if (!Objects.isNull(dateRecherche)) {
            return nouvelleRepository.findByDateGreaterThanEqual(localDateTime);
        }

        return nouvelleRepository.findAll();
    }
}
