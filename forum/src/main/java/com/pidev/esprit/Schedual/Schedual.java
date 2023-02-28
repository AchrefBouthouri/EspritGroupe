package com.pidev.esprit.Schedual;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
public class Schedual {

   /* @Autowired
    EtudiantRepository etudiantRepository ;
    @Autowired
    ContratRepository contratRepository;
    @Autowired
    EquipeRepository equipeRepository ;

    @Scheduled(cron="* 1 * * * *")
    public void selectAll()
    {
        List< Etudiant > list = (List<Etudiant>) etudiantRepository.findAll();
        for (Etudiant e : list)
            log.info(e.toString());
    }
/*
    @Scheduled(cron="0 0 13 * * *")
    public void retrieveAndUpdateStatusContrat()
    {
        List<Contrat> contrats = contratRepository.findAll() ;
        for (Contrat c : contrats)
        {
            if(c.getDateFinC().compareTo(DateUtils.addDays(c.getDateFinC(), 15)) <= 0)
                System.out.println( "Info Contrat :\n Date de Fin : "+c.getDateFinC()+
                        "Specialite : "+c.getTypeContrat()+
                        "Etudiant : "+c.getEtudiant());

            if(c.getDateFinC().compareTo(DateUtils.addDays(c.getDateFinC(), 15)) >= 0)
            {
                c.setArchive(true);
                contratRepository.save(c) ;
            }
        }
    }

    @Scheduled(cron="* * * * * *")
    public void faireEvoluerEquipes()
    {
        List<Equipe> equipes = equipeRepository.findAll() ;
        for(Equipe equipe : equipes)
        {
            int i = 0 ;
            for(Etudiant etudiant : equipe.getEtudiants())
            {
                for (Contrat contrat : etudiant.getContrats())
                {
                    if (DateUtils.addDays(contrat.getDateDebut(), 365).before(contrat.getDateFinC()))
                        i++ ;
                    if(i != 0)
                        break;
                }
            }
            if (i>=3)
            {
                switch (equipe.getNiveau())
                {
                    case JUNIOR:
                        equipe.setNiveau(Niveau.SENIOR);
                        equipeRepository.save(equipe) ;
                        break;
                    case SENIOR:
                        equipe.setNiveau(Niveau.EXPERT);
                        equipeRepository.save(equipe) ;
                        break;
                }
            }
        }
    }

*/

    // *     *    *   *          *        *
    //s     min   h   j(0/31)    mois    jour de la semaine (lundi -> dim)*/
}