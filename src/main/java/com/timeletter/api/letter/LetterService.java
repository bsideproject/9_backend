package com.timeletter.api.letter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LetterService {

    private final LetterRepository letterRepository;

    public List<Letter> findAll() {
        return letterRepository.findAll();
    }

    public String create(final Letter entity){
        validate(entity);

        Letter save = letterRepository.save(entity);

        log.info("Entity id : {} is saved",save.getId());

        return save.getId();
    }

    private void validate(Letter entity) {
        if(entity == null){
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null");
        }
    }

    public Letter retrieve(final String id) {
        return letterRepository.findById(id).get();
    }

    public String update(final Letter entity) {
        validate(entity);

        final Optional<Letter> original = letterRepository.findById(entity.getId());

        original.ifPresent(letter -> {
            letter.setTitle(entity.getTitle());
            letter.setContent(entity.getContent());
            letter.setLetterStatus(entity.getLetterStatus());
            letter.setReceivedDate(entity.getReceivedDate());
            letter.setReceivedPhoneNumber(entity.getReceivedPhoneNumber());
            letterRepository.save(letter);
        });

        return entity.getId();
    }

    public void delete(final Letter entity) {
        validate(entity);

        try {
            letterRepository.delete(entity);
        }catch (Exception e){
            log.error("Error deleting entity ", entity.getId(),e);

            throw new RuntimeException("error deleting entity " + entity.getId());
        }
    }

    public List<Letter> findAllByUserId(String userId) {
        return letterRepository.findAllByUserID(userId);
    }

    public Letter findByLetterId(String letterId) {
        return letterRepository.findById(letterId).get();
    }
}
