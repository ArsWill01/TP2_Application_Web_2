package msd.climoilou.web2.Nouvelles.services;

import msd.climoilou.web2.Nouvelles.model.Nouvelle;
import msd.climoilou.web2.Nouvelles.repository.NouvelleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
}
