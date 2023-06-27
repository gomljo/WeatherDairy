# 실전 날씨일기 프로젝트

---
OpenWeatherMap API을 활용한 날씨일기 구현 프로젝트입니다.


## 실전 날씨 일기 프로젝트 목표

   - 날씨, 일기를 작성/조회/수정/삭제 하는 백엔드를 구현
   - 테스트 코드 작성
   - 외부 API의 데이터를 활용
     - Open Weather Map API 사용(https://openweathermap.org/)
   - JPA 방식으로 MySQL 사용

## API 리스트 명세

✅ POST / create / diary
- date parameter로 받기 (date 형식 : yyyy-MM-dd)
- text parameter로 일기 글을 받기
- 외부 API 에서 받아온 날씨 데이터와 함께 DB에 저장

✅ GET / read / diary
- date parameter로 조회할 날짜를 받기
- 해당 날짜의 일기를 List 형태로 반환

✅ GET / read / diaries
- startDate, ednDate parameter 로 조회할 날짜 기간의 시작일/종료일을 받기
- 해당 기간의 일기를 List 형태로 반환

✅ PUT / update / diary
- date parameter로 수정할 날짜 받기
- text parameter로 수정할 새 일기 글을 받기
- 해당 날짜의 첫번째 일기 글을 새로 받아온 일기글로 수정

✅ DELETE / delete / diary
- date parameter로 삭제할 날짜 받기
- 해당 날짜의 모든 일기를 삭제

## 프로젝트 완성도 높이기

✅ DB와 관련된 함수들을 트랜잭션 처리 [ ]

✅ 매일 새벽 1시에 날씨 데이터를 외부 API 에서 받아다 DB에 저장해두는 로직을 구현 [ ]

✅ logback 을 이용하여 프로젝트에 로그를 남기기 [ ]

✅ ExceptionHandler 을 이용한 예외처리 [ ]
✅ swagger 을 이용하여 API documentation 작성 [ ]