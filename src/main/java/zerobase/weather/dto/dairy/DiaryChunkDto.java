package zerobase.weather.dto.dairy;

import lombok.*;
import zerobase.weather.domain.entity.Diary;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryChunkDto {

    List<DiaryDto> diaryChunk;

    public static DiaryChunkDto fromEntity(List<Diary> diaryChunk){
        List<DiaryDto> diaryChunkDto = diaryChunk.stream().map(DiaryDto::fromEntity).collect(Collectors.toList());

        return DiaryChunkDto.builder()
                .diaryChunk(diaryChunkDto)
                .build();
            }

}
