package dev.danielk.basicjava.regex;

import java.util.Arrays;

/**
 * String.split()에서 자주 사용하는 정규식 패턴 예제
 *
 * 기본 문자 클래스:
 *   \d  숫자 [0-9]
 *   \D  숫자가 아닌 것
 *   \w  알파벳 대소문자, 숫자, 언더바
 *   \W  \w가 아닌 것
 *   \s  공백 문자 (스페이스, 탭, 줄바꿈)
 *   \S  공백이 아닌 문자
 *
 * 수량자:
 *   *     0번 이상
 *   +     1번 이상
 *   ?     0 또는 1번
 *   {n}   정확히 n번
 *   {n,}  n번 이상
 *   {n,m} n번 이상 m번 이하
 *
 * 경계/위치:
 *   ^  문자열 시작
 *   $  문자열 끝
 *   \b 단어 경계
 *   |  OR (A 또는 B)
 */
public class SplitRegexExamples {

    public static void main(String[] args) {
        System.out.println("====== split 기본 패턴 ======");
        splitByWhitespace();
        splitByMultipleWhitespace();
        extractNumbers();
        extractLetters();
        splitByDelimiter();
        splitByMultipleDelimiters();

        System.out.println("\n====== 실전 활용 패턴 ======");
        validateOnlyNumbers();
        validateEmail();
        validatePhoneNumber();
        validatePassword();
        normalizeWhitespace();
    }

    // ────────────────────────────────────────────
    // split 기본 패턴
    // ────────────────────────────────────────────

    // 단순 공백으로 구분
    static void splitByWhitespace() {
        String input = "사과 바나나 포도 딸기";
        String[] result = input.split(" ");
        System.out.println("=== 공백 구분 ===");
        System.out.println(Arrays.toString(result));
        // [사과, 바나나, 포도, 딸기]
    }

    // 연속 공백(탭 포함) 구분 — \s+
    static void splitByMultipleWhitespace() {
        String input = "사과  바나나\t포도   딸기";
        String[] result = input.split("\\s+");
        System.out.println("=== 연속 공백/탭 구분 (\\ s+) ===");
        System.out.println(Arrays.toString(result));
        // [사과, 바나나, 포도, 딸기]
    }

    // 숫자가 아닌 문자로 분리해 숫자만 추출 — \D+
    static void extractNumbers() {
        String input = "abc123def456ghi789";
        String[] result = input.split("\\D+");
        System.out.println("=== 숫자 추출 (\\D+) ===");
        Arrays.stream(result)
              .filter(s -> !s.isEmpty())
              .forEach(System.out::println);
        // 123, 456, 789
    }

    // 숫자로 분리해 영문자만 추출 — \d+
    static void extractLetters() {
        String input = "abc123DEF456ghi789XYZ";
        String[] result = input.split("\\d+");
        System.out.println("=== 영문자 추출 (\\d+) ===");
        Arrays.stream(result)
              .filter(s -> !s.isEmpty())
              .forEach(System.out::println);
        // abc, DEF, ghi, XYZ
    }

    // 단일 구분기호 (쉼표)
    static void splitByDelimiter() {
        String csv = "이름,나이,직업,도시";
        String[] result = csv.split(",");
        System.out.println("=== 쉼표 구분 ===");
        System.out.println(Arrays.toString(result));
        // [이름, 나이, 직업, 도시]
    }

    // 복합 구분기호 — 쉼표, 세미콜론, 파이프, 슬래시 중 하나
    static void splitByMultipleDelimiters() {
        String input = "서울,부산;대구|광주/인천";
        String[] result = input.split("[,;|/]");
        System.out.println("=== 복합 구분기호 ([,;|/]) ===");
        System.out.println(Arrays.toString(result));
        // [서울, 부산, 대구, 광주, 인천]
    }

    // ────────────────────────────────────────────
    // 실전 활용 패턴 (matches / replaceAll)
    // ────────────────────────────────────────────

    // ① 숫자만 포함되어 있는지 확인 — ^\\d+$
    static void validateOnlyNumbers() {
        String[] inputs = {"12345", "123abc", "00001"};
        System.out.println("=== 숫자만 포함 여부 (^\\d+$) ===");
        for (String s : inputs) {
            System.out.println(s + " → " + s.matches("^\\d+$"));
        }
    }

    // ② 이메일 형식 검증
    static void validateEmail() {
        String pattern = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";
        String[] inputs = {"user@example.com", "invalid@", "hello.world@mail.co.kr"};
        System.out.println("=== 이메일 검증 ===");
        for (String s : inputs) {
            System.out.println(s + " → " + s.matches(pattern));
        }
    }

    // ③ 한국 휴대폰 번호 — 010-1234-5678 형식
    static void validatePhoneNumber() {
        // 010, 011, 016~019 허용 / 중간 자리 3~4자리
        String pattern = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        String[] inputs = {"010-1234-5678", "011-234-5678", "010-12345-6789", "02-1234-5678"};
        System.out.println("=== 휴대폰 번호 검증 ===");
        for (String s : inputs) {
            System.out.println(s + " → " + s.matches(pattern));
        }
    }

    // ④ 비밀번호 규칙 — 영문·숫자 포함 8~16자 (전방 탐색 활용)
    static void validatePassword() {
        // (?=.*[A-Za-z]) : 영문 최소 1개 포함 강제
        // (?=.*\\d)       : 숫자 최소 1개 포함 강제
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
        String[] inputs = {"abcdefg1", "12345678", "abc123", "Password1"};
        System.out.println("=== 비밀번호 검증 (영문+숫자 8~16자) ===");
        for (String s : inputs) {
            System.out.println(s + " → " + s.matches(pattern));
        }
    }

    // ⑤ 연속 공백을 한 칸으로 정규화 — \s+
    static void normalizeWhitespace() {
        String input = "안녕   하세요.  오늘도\t좋은  하루  되세요.";
        String result = input.replaceAll("\\s+", " ").trim();
        System.out.println("=== 공백 정규화 (\\s+) ===");
        System.out.println("입력: " + input);
        System.out.println("결과: " + result);
    }
}
