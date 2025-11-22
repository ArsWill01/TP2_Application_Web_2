package msd.climoilou.web2.Nouvelles.repository;

import msd.climoilou.web2.Nouvelles.model.Nouvelle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;

public interface NouvelleRepository extends JpaRepository<Nouvelle, Long> {
    Collection<Nouvelle> findNouvelleById(Long id);

    Collection<Nouvelle> findByTexteContainingAndDateGreaterThanEqual(String titre, LocalDateTime date);

    Collection<Nouvelle> findByTexteContaining(String texte);

    Collection<Nouvelle> findByDateGreaterThanEqual(LocalDateTime date);
}
