package zerobase.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.entity.Diary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByDate(LocalDate date);
    List<Diary> findByDateBetween(LocalDate start, LocalDate end);
    Diary findFirstByDate(LocalDate date);
    void deleteAllByDate(LocalDate date);
}
