package dev.danielk.basicjava.regex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 정규식(Regex) 활용 — split / matches / replaceAll
 *
 * 기본 문자 클래스:
 *   \d  숫자 [0-9]           \D  숫자가 아닌 것
 *   \w  알파벳·숫자·언더바    \W  \w가 아닌 것
 *   \s  공백(스페이스·탭·줄바꿈)  \S  공백이 아닌 것
 *
 * 수량자:
 *   *  0번 이상   +  1번 이상   ?  0 또는 1번
 *   {n}  정확히 n번   {n,}  n번 이상   {n,m}  n~m번
 *
 * 경계/위치:
 *   ^  문자열 시작   $  문자열 끝   \b  단어 경계   |  OR
 *
 * 자바 주의: 정규식 \d → 자바 문자열 "\\d"
 */
@DisplayName("정규식(Regex) 활용")
class RegexSplitTest {

    // ── 공백 구분 ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("단순 공백으로 구분")
    void splitByWhitespace() {
        String input = "사과 바나나 포도 딸기";
        String[] result = input.split(" ");

        assertThat(result).containsExactly("사과", "바나나", "포도", "딸기");
    }

    @Test
    @DisplayName("\\s+ : 연속 공백·탭을 하나로 묶어 구분")
    void splitByMultipleWhitespace() {
        String input = "사과  바나나\t포도   딸기";
        String[] result = input.split("\\s+");

        assertThat(result).containsExactly("사과", "바나나", "포도", "딸기");
    }

    // ── 숫자 / 영문자 추출 ───────────────────────────────────────────────────

    @Test
    @DisplayName("\\D+ : 비숫자 문자로 분리해 숫자만 추출")
    void extractNumbers() {
        String input = "abc123def456ghi789";
        String[] result = input.split("\\D+");

        // 앞에 비숫자가 없으므로 빈 문자열 없이 바로 숫자 시작
        assertThat(result).containsExactly("123", "456", "789");
    }

    @Test
    @DisplayName("\\d+ : 숫자로 분리해 영문자만 추출")
    void extractLetters() {
        String input = "abc123DEF456ghi789XYZ";
        String[] result = input.split("\\d+");

        assertThat(result).containsExactly("abc", "DEF", "ghi", "XYZ");
    }

    // ── 구분기호 분리 ────────────────────────────────────────────────────────

    @Test
    @DisplayName("단일 구분기호(쉼표)로 분리")
    void splitByComma() {
        String csv = "이름,나이,직업,도시";
        String[] result = csv.split(",");

        assertThat(result).containsExactly("이름", "나이", "직업", "도시");
    }

    @Test
    @DisplayName("[,;|/] : 복합 구분기호 중 하나로 분리")
    void splitByMultipleDelimiters() {
        String input = "서울,부산;대구|광주/인천";
        String[] result = input.split("[,;|/]");

        assertThat(result).containsExactly("서울", "부산", "대구", "광주", "인천");
    }

    // ── 실전 패턴: matches ───────────────────────────────────────────────────

    @Test
    @DisplayName("^\\d+$ : 숫자만 포함 여부 확인")
    void validateOnlyNumbers() {
        assertThat("12345".matches("^\\d+$")).isTrue();
        assertThat("123abc".matches("^\\d+$")).isFalse();
        assertThat("00001".matches("^\\d+$")).isTrue();
    }

    @Test
    @DisplayName("이메일 형식 검증")
    void validateEmail() {
        String pattern = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";

        assertThat("user@example.com".matches(pattern)).isTrue();
        assertThat("hello.world@mail.co.kr".matches(pattern)).isTrue();
        assertThat("invalid@".matches(pattern)).isFalse();
        assertThat("noatsign.com".matches(pattern)).isFalse();
    }

    @Test
    @DisplayName("한국 휴대폰 번호 검증 — 010-1234-5678 형식")
    void validatePhoneNumber() {
        // 010, 011, 016~019 허용 / 중간 자리 3~4자리
        String pattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";

        assertThat("010-1234-5678".matches(pattern)).isTrue();
        assertThat("011-234-5678".matches(pattern)).isTrue();
        assertThat("010-12345-6789".matches(pattern)).isFalse();  // 중간 자리 초과
        assertThat("02-1234-5678".matches(pattern)).isFalse();    // 지역번호 형식
    }

    @Test
    @DisplayName("비밀번호 규칙 — 영문·숫자 포함 8~16자 (전방 탐색 활용)")
    void validatePassword() {
        // (?=.*[A-Za-z]) 영문 최소 1개, (?=.*\\d) 숫자 최소 1개 강제
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";

        assertThat("abcdefg1".matches(pattern)).isTrue();   // 영문 + 숫자 8자
        assertThat("Password1".matches(pattern)).isTrue();
        assertThat("12345678".matches(pattern)).isFalse();  // 숫자만
        assertThat("abcdefgh".matches(pattern)).isFalse();  // 영문만
        assertThat("abc123".matches(pattern)).isFalse();    // 6자 (미달)
    }

    // ── 실전 패턴: replaceAll ────────────────────────────────────────────────

    @Test
    @DisplayName("\\s+ : 연속 공백을 한 칸으로 정규화")
    void normalizeWhitespace() {
        String input = "안녕   하세요.  오늘도\t좋은  하루  되세요.";
        String result = input.replaceAll("\\s+", " ").trim();

        assertThat(result).isEqualTo("안녕 하세요. 오늘도 좋은 하루 되세요.");
    }
}
