package zerobase.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.weather.domain.VO.WeatherRecord;

import java.time.LocalDate;

@Repository
public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {
    WeatherRecord findByDate(LocalDate recordDate);
}
