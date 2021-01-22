package ru.korolkovrs.springsecurity.lesson11.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.korolkovrs.springsecurity.lesson11.entities.Score;
import ru.korolkovrs.springsecurity.lesson11.entities.User;
import ru.korolkovrs.springsecurity.lesson11.repositoryes.ScoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreRepository scoreRepository;

    public Score getScoreByUserId(Long id) {
        Score score =  scoreRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Error in the '%s' data '%s' not found", id)));
        return score;
    }

    public Score incUserScore(Score score, User user) {
        return scoreRepository.save(new Score(user.getId(), score.getScore() + 1, user));
    }

    public Score decUserScore(Score score, User user) {
        return scoreRepository.save(new Score(user.getId(), score.getScore() - 1, user));
    }
}
