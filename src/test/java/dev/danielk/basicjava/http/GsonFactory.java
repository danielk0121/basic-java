package dev.danielk.basicjava.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 테스트 공용 Gson 팩토리.
 * Gson은 java.time.LocalDateTime을 기본 직렬화하지 못하므로
 * ISO_LOCAL_DATE_TIME 형식의 문자열로 변환하는 어댑터를 등록한다.
 */
public final class GsonFactory {

    private GsonFactory() {}

    public static Gson create() {
        JsonSerializer<LocalDateTime> ser = (src, type, ctx) ->
                ctx.serialize(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        JsonDeserializer<LocalDateTime> de = (json, type, ctx) ->
                LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, ser)
                .registerTypeAdapter(LocalDateTime.class, de)
                .create();
    }
}
