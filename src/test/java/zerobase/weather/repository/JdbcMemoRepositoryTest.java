package zerobase.weather.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zerobase.weather.domain.Memo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
//@Transactional // 데이터베이스 테스트 시 많이 사용한다, 결과가 데이터베이스에 반영되지 않는다!
class JdbcMemoRepositoryTest {

    @Mock
    JdbcMemoRepository jdbcMemoRepository;

    @Test
    void insertMemoTest() {
        // given
        Memo newMemo = new Memo(1, "InsertMemoTest");
        given(jdbcMemoRepository.save(any()))
                .willReturn(Memo.builder()
                        .id(1)
                        .text("InsertMemoTest")
                        .build()
                );
        ArgumentCaptor<Memo> memoArgumentCaptor = ArgumentCaptor.forClass(Memo.class);
        // when
        jdbcMemoRepository.save(newMemo);
        // then
        verify(jdbcMemoRepository, times(1)).save(memoArgumentCaptor.capture());
        assertEquals(1, memoArgumentCaptor.getValue().getId());
        assertEquals("InsertMemoTest", memoArgumentCaptor.getValue().getText());
    }

}