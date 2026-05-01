package dev.danielk.basicjava.http;

/**
 * 4xx / 5xx 응답 본문 DTO.
 */
public record ApiError(int status, String message) {
}
