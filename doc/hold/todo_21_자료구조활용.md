# todo 21: 자료구조 활용

> 알고리즘 — JDK 라이브러리 사용, 직접 구현 안함

## 작업 목록
- [ ] 스택 — 보일러플레이트 / 연습예제 작성
- [ ] 큐 — 보일러플레이트 / 연습예제 작성
- [ ] 덱 — 보일러플레이트 / 연습예제 작성
- [ ] 우선순위 큐 — 보일러플레이트 / 연습예제 작성
- [ ] Thread-safe 스택·큐 — 보일러플레이트 작성

## 구현 계획

### 스택 (Stack)
- 구현체: `ArrayDeque` (`Deque` 인터페이스, `Stack` 클래스 대신 사용)
  - `Stack`은 레거시 클래스 (Vector 기반, synchronized → 느림) — 신규 코드에서는 사용 금지
- 주요 메서드
  - `push(e)` — 맨 위에 삽입
  - `pop()` — 맨 위 제거 후 반환 (비어있으면 NoSuchElementException)
  - `peek()` — 맨 위 조회 (제거 안 함)
  - `isEmpty()` — 비어있는지 확인
- 대표 활용: 괄호 유효성 검사, 후위 표기식 계산, 되돌리기(undo)

### 큐 (Queue)
- 구현체: `ArrayDeque` (일반 용도), `LinkedList` (null 삽입 필요 시)
  - `ArrayDeque`가 `LinkedList`보다 메모리·성능 모두 우수 — 기본 선택
- 주요 메서드
  - `offer(e)` — 꼬리에 삽입 (실패 시 false 반환)
  - `poll()` — 머리 제거 후 반환 (비어있으면 null)
  - `peek()` — 머리 조회 (제거 안 함)
  - `add(e)` / `remove()` — offer/poll의 예외 던지는 버전
- 대표 활용: BFS, 작업 대기열, 이벤트 처리

### 덱 (Deque — Double-Ended Queue)
- 구현체: `ArrayDeque`
- 주요 메서드
  - `addFirst(e)` / `addLast(e)` — 앞/뒤 삽입
  - `removeFirst()` / `removeLast()` — 앞/뒤 제거
  - `peekFirst()` / `peekLast()` — 앞/뒤 조회
- 스택과 큐를 모두 대체할 수 있는 범용 자료구조
- 대표 활용: 슬라이딩 윈도우, 양방향 BFS, 팰린드롬 검사

### 우선순위 큐 (PriorityQueue — 힙)
- 구현체: `PriorityQueue` (기본: 최솟값 우선 — min-heap)
  - max-heap: `PriorityQueue<>(Comparator.reverseOrder())`
  - 커스텀 정렬: `PriorityQueue<>(Comparator.comparingInt(...))`
- 주요 메서드
  - `offer(e)` / `poll()` / `peek()` — 큐와 동일
  - `size()` — 원소 개수
- 시간 복잡도: offer/poll O(log n), peek O(1)
- 대표 활용: 다익스트라, K번째 최솟값, 작업 스케줄링

### Thread-safe 스택·큐
- `ConcurrentLinkedQueue<E>` — lock-free 비동기 큐 (단방향, 논블로킹)
  - offer/poll/peek — 위의 Queue와 동일 API
- `LinkedBlockingQueue<E>` — 블로킹 큐 (생산자-소비자 패턴)
  - `put(e)` — 꽉 차면 블로킹
  - `take()` — 비어있으면 블로킹
  - `offer(e, timeout, unit)` / `poll(timeout, unit)` — 타임아웃 버전
- `ConcurrentLinkedDeque<E>` — lock-free 비동기 덱 (양방향, 논블로킹)
- `LinkedBlockingDeque<E>` — 블로킹 덱 (양방향 생산자-소비자 패턴)
  - `putFirst(e)` / `putLast(e)` — 앞/뒤 삽입, 꽉 차면 블로킹
  - `takeFirst()` / `takeLast()` — 앞/뒤 제거, 비어있으면 블로킹
  - 선택 크기 제한 가능 (`new LinkedBlockingDeque<>(capacity)`)
- `ArrayBlockingQueue<E>` — 고정 크기 블로킹 큐 (bounded buffer)
- `CopyOnWriteArrayList` — 읽기 다수·쓰기 소수 환경에서 스택 대용으로 사용 가능
- 선택 기준
  - 단순 멀티스레드 큐 → `ConcurrentLinkedQueue`
  - 생산자-소비자 패턴 → `LinkedBlockingQueue`
  - 크기 제한 필요 → `ArrayBlockingQueue`
  - 양방향 논블로킹 → `ConcurrentLinkedDeque`
  - 양방향 블로킹 (앞뒤 모두 생산·소비) → `LinkedBlockingDeque`

## 구현 파일
- `src/test/java/dev/danielk/basicjava/datastructure/`
  - `StackTest.java` — ArrayDeque as Stack, 괄호 유효성 검사
  - `QueueTest.java` — ArrayDeque / LinkedList as Queue, BFS
  - `DequeTest.java` — ArrayDeque 양방향, 팰린드롬, 슬라이딩 윈도우 최댓값
  - `PriorityQueueTest.java` — min/max-heap, 커스텀 정렬, K번째 최솟값
  - `ConcurrentLinkedQueueTest.java` — lock-free 큐, 멀티스레드 offer/poll
  - `LinkedBlockingQueueTest.java` — 블로킹 큐, 생산자-소비자 패턴
  - `ArrayBlockingQueueTest.java` — 고정 크기 블로킹 큐, 스로틀링
  - `ConcurrentLinkedDequeTest.java` — lock-free 덱, 작업 훔치기 패턴
  - `LinkedBlockingDequeTest.java` — 블로킹 덱, 양방향 생산자-소비자
  - `exercise/` — 연습예제 (문제/답안 쌍, 미작성)
