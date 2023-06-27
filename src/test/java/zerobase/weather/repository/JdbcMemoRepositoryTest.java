package zerobase.weather.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zerobase.weather.domain.Memo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional // 데이터베이스 테스트 시 많이 사용한다, 결과가 데이터베이스에 반영되지 않는다!
class JdbcMemoRepositoryTest {

    @Autowired
    JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemoTest() {
        // given
        Memo newMemo = new Memo(1, "InsertMemoTest");
        // when
        jdbcMemoRepository.save(newMemo);
        // then
        Optional<Memo> memo = jdbcMemoRepository.findById(1);
        assertEquals(memo.get().getText(), "InsertMemoTest");
    }

}