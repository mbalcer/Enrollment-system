package pl.mbalcer.enrollmentsystem.service;

import java.util.List;
import java.util.Optional;

public interface ServiceInitiator<D> {

    List<D> findAll();

    Optional<D> findOne(Long id);

    D create(D dto);

    D update(D dto, Long id);

    void delete(Long id);

}
