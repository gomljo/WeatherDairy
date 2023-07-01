package zerobase.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.entity.Diary;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    Diary findDiaryByDate(LocalDate date);
    List<Diary> findDiaryByDateBetween(LocalDate start, LocalDate end);
}
