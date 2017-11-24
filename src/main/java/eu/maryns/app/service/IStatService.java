package eu.maryns.app.service;


import eu.maryns.app.domain.Stat;

import java.util.List;

public interface IStatService {


    List<Stat> loadAll();

    List<Stat> recalculateAll();
}
