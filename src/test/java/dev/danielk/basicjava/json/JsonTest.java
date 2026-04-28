package dev.danielk.basicjava.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * JSON 처리 보일러플레이트
 *
 * Gson
 *   Gson gson = new Gson()
 *   jsonString = gson.toJson(obj)
 *   obj        = gson.fromJson(json, Class)
 *   obj        = gson.fromJson(json, new TypeToken<T>(){})                  // 제네릭 타입 명시
 *   list       = gson.fromJson(json, new TypeToken<List<T>>(){}.getType())
 *
 * Jackson ObjectMapper
 *   ObjectMapper om = new ObjectMapper()
 *   jsonString = om.writeValueAsString(obj)
 *   obj        = om.readValue(json, Class)
 *   obj        = om.readValue(json, new TypeReference<T>(){})               // 제네릭 타입 명시
 *   list       = om.readValue(json, new TypeReference<List<T>>(){})
 *
 * 주의: ObjectMapper 직렬화 시 getter 필요, 역직렬화 시 기본 생성자 + setter 필요
 */
@DisplayName("JSON 처리")
class JsonTest {

    // ── 샘플 POJO ─────────────────────────────────────────────────────────────

    static class Product {
        private int id;
        private String name;
        private double price;

        public Product() {}

        public Product(int id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public double getPrice() { return price; }

        public void setId(int id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setPrice(double price) { this.price = price; }

        @Override
        public String toString() {
            return "Product{id=" + id + ", name='" + name + "', price=" + price + "}";
        }
    }

    private static final String PRODUCT_JSON =
            "{\"id\":1,\"name\":\"노트북\",\"price\":999.99}";
    private static final String PRODUCT_LIST_JSON =
            "[{\"id\":1,\"name\":\"노트북\",\"price\":999.99},{\"id\":2,\"name\":\"마우스\",\"price\":29.99}]";

    // ── Gson ──────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Gson: 객체 직렬화 - toJson()")
    void gson_toJson_object() {
        Gson gson = new Gson();
        Product product = new Product(1, "노트북", 999.99);

        String json = gson.toJson(product);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"name\":\"노트북\"");
        assertThat(json).contains("\"price\":999.99");
    }

    @Test
    @DisplayName("Gson: 객체 역직렬화 - fromJson(Class)")
    void gson_fromJson_object_byClass() {
        Gson gson = new Gson();

        Product product = gson.fromJson(PRODUCT_JSON, Product.class);

        assertThat(product.getId()).isEqualTo(1);
        assertThat(product.getName()).isEqualTo("노트북");
        assertThat(product.getPrice()).isEqualTo(999.99);
    }

    @Test
    @DisplayName("Gson: 객체 역직렬화 - fromJson(TypeToken)")
    void gson_fromJson_object_byTypeToken() {
        // TypeToken<T>: 제네릭 타입 정보를 런타임에 보존
        // new TypeToken<T>(){} — 단독으로 TypeToken 인스턴스로 전달 (Gson 2.10+)
        // new TypeToken<T>(){}.getType() — getType()으로 java.lang.reflect.Type으로 변환 후 전달
        Gson gson = new Gson();

        Product product1 = gson.fromJson(PRODUCT_JSON, new TypeToken<Product>() {});
        Product product2 = gson.fromJson(PRODUCT_JSON, new TypeToken<Product>() {}.getType());

        assertThat(product1.getName()).isEqualTo("노트북");
        assertThat(product2.getName()).isEqualTo("노트북");
    }

    @Test
    @DisplayName("Gson: 리스트 직렬화 - toJson()")
    void gson_toJson_list() {
        Gson gson = new Gson();
        List<Product> products = List.of(
                new Product(1, "노트북", 999.99),
                new Product(2, "마우스", 29.99)
        );

        String json = gson.toJson(products);

        assertThat(json).startsWith("[");
        assertThat(json).endsWith("]");
        assertThat(json).contains("\"노트북\"", "\"마우스\"");
    }

    @Test
    @DisplayName("Gson: 리스트 역직렬화 - fromJson(TypeToken)")
    void gson_fromJson_list_byTypeToken() {
        // 배열 역직렬화: TypeToken<List<T>>(){}.getType() 사용
        // new TypeToken<List<T>>(){} 단독 전달도 가능 (Gson 2.10+)
        Gson gson = new Gson();

        List<Product> products = gson.fromJson(PRODUCT_LIST_JSON,
                new TypeToken<List<Product>>() {}.getType());

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("노트북");
        assertThat(products.get(1).getName()).isEqualTo("마우스");
    }

    // ── Jackson ObjectMapper ──────────────────────────────────────────────────

    @Test
    @DisplayName("ObjectMapper: 객체 직렬화 - writeValueAsString()")
    void objectMapper_writeValueAsString_object() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Product product = new Product(1, "노트북", 999.99);

        String json = om.writeValueAsString(product);

        assertThat(json).contains("\"id\":1");
        assertThat(json).contains("\"name\":\"노트북\"");
        assertThat(json).contains("\"price\":999.99");
    }

    @Test
    @DisplayName("ObjectMapper: 객체 역직렬화 - readValue(Class)")
    void objectMapper_readValue_object_byClass() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();

        Product product = om.readValue(PRODUCT_JSON, Product.class);

        assertThat(product.getId()).isEqualTo(1);
        assertThat(product.getName()).isEqualTo("노트북");
        assertThat(product.getPrice()).isEqualTo(999.99);
    }

    @Test
    @DisplayName("ObjectMapper: 객체 역직렬화 - readValue(TypeReference)")
    void objectMapper_readValue_object_byTypeReference() throws JsonProcessingException {
        // TypeReference<T>: 제네릭 타입 정보를 런타임에 보존
        // new TypeReference<T>(){} 또는 new TypeReference<>(){} (자바 11+, 타입 생략)
        ObjectMapper om = new ObjectMapper();

        Product product1 = om.readValue(PRODUCT_JSON, new TypeReference<Product>() {});
        Product product2 = om.readValue(PRODUCT_JSON, new TypeReference<>() {});

        assertThat(product1.getName()).isEqualTo("노트북");
        assertThat(product2.getName()).isEqualTo("노트북");
    }

    @Test
    @DisplayName("ObjectMapper: 리스트 직렬화 - writeValueAsString()")
    void objectMapper_writeValueAsString_list() throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        List<Product> products = List.of(
                new Product(1, "노트북", 999.99),
                new Product(2, "마우스", 29.99)
        );

        String json = om.writeValueAsString(products);

        assertThat(json).startsWith("[");
        assertThat(json).endsWith("]");
        assertThat(json).contains("\"노트북\"", "\"마우스\"");
    }

    @Test
    @DisplayName("ObjectMapper: 리스트 역직렬화 - readValue(TypeReference)")
    void objectMapper_readValue_list_byTypeReference() throws JsonProcessingException {
        // 배열 역직렬화: TypeReference<List<T>> 사용
        // 제네릭 타입을 전부 명시하거나 (List<Product>), 자바 11+에서 생략 (<>) 가능
        ObjectMapper om = new ObjectMapper();

        List<Product> products = om.readValue(PRODUCT_LIST_JSON,
                new TypeReference<List<Product>>() {});

        assertThat(products).hasSize(2);
        assertThat(products.get(0).getName()).isEqualTo("노트북");
        assertThat(products.get(1).getName()).isEqualTo("마우스");
    }
}
