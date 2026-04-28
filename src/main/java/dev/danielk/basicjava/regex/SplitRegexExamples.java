package dev.danielk.basicjava.regex;

import java.util.Arrays;

public class SplitRegexExamples {

    public static void main(String[] args) {
        splitByWhitespace();
        splitByMultipleWhitespace();
        extractNumbers();
        extractLetters();
        splitByDelimiter();
        splitByMultipleDelimiters();
    }

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
        System.out.println("=== 연속 공백/탭 구분 ===");
        System.out.println(Arrays.toString(result));
        // [사과, 바나나, 포도, 딸기]
    }

    // 숫자가 아닌 문자로 분리해 숫자만 추출 — \D+
    static void extractNumbers() {
        String input = "abc123def456ghi789";
        String[] result = input.split("\\D+");
        System.out.println("=== 숫자 추출 ===");
        // 맨 앞 빈 문자열 제거를 위해 filter 사용
        Arrays.stream(result)
              .filter(s -> !s.isEmpty())
              .forEach(System.out::println);
        // 123, 456, 789
    }

    // 숫자로 분리해 영문자만 추출 — \d+
    static void extractLetters() {
        String input = "abc123DEF456ghi789XYZ";
        String[] result = input.split("\\d+");
        System.out.println("=== 영문자 추출 ===");
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
        System.out.println("=== 복합 구분기호 분리 ===");
        System.out.println(Arrays.toString(result));
        // [서울, 부산, 대구, 광주, 인천]
    }
}
