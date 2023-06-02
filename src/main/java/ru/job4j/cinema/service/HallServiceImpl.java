package ru.job4j.cinema.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepositoryImpl;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepositoryImpl hallRepository;

    @Override
    public Optional<Hall> findById(int id) {
        return hallRepository.findById(id);
    }
}
