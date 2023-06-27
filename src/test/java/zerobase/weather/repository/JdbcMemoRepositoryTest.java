package zerobase.weather.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.weather.domain.Memo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    private Memo createMemo(int id, String text){
      return Memo.builder()
              .id(id)
              .text(text)
              .build();
    }
    @Test
    void findAllMemoTest() {
        // given
        Memo memoFirst = createMemo(1, "first memo");
        Memo memoSecond = createMemo(2, "second memo");
        Memo memoThird = createMemo(3, "third memo");

        List<Memo> expectMemoList = new ArrayList<>();
        expectMemoList.add(memoFirst);
        expectMemoList.add(memoSecond);
        expectMemoList.add(memoThird);

        given(jdbcMemoRepository.findAll())
                .willReturn(expectMemoList);
        // when
        List<Memo> actualMemoList = jdbcMemoRepository.findAll();
        System.out.println(actualMemoList);

        // then
        assertEquals(3, actualMemoList.size());
        assertEquals(1, actualMemoList.get(0).getId());
        assertEquals("first memo", actualMemoList.get(0).getText());
        assertEquals(2, actualMemoList.get(1).getId());
        assertEquals("second memo", actualMemoList.get(1).getText());
        assertEquals(3, actualMemoList.get(2).getId());
        assertEquals("third memo", actualMemoList.get(2).getText());

    }
}