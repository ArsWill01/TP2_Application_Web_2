package msd.climoilou.web2.Criteres.services;

import msd.climoilou.web2.Criteres.model.Critere;
import msd.climoilou.web2.Criteres.repository.CritereRepository;
import msd.climoilou.web2.Nouvelles.model.Nouvelle;
import msd.climoilou.web2.Nouvelles.repository.NouvelleRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CritereService {
    final
    CritereRepository critereRepository;

    public CritereService(CritereRepository critereRepository) {
        this.critereRepository = critereRepository;
    }

    public List<Critere> getAll(){
        return critereRepository.findAll();
    }

    public void deleteById(long id){
        critereRepository.deleteById(id);
    }

    public boolean existsById(long id){
        return critereRepository.existsById(id);
    }

    public Collection<Critere> getCritereById(long id){
        return critereRepository.findCritereById(id);
    }

    public Critere save(Critere critere){
        return critereRepository.save(critere);
    }
}
