package com.ssg.todoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    /** 페이지는 1부터 시작 */
    @Builder.Default
    @Min(1)
    private int page = 1;

    /** 페이지당 건수(범위 10~100) */
    @Builder.Default
    @Min(10)
    @Max(100)
    @Positive
    private int size = 10;

    /** 리스트 페이지 유지용 링크 쿼리 */
    private String link;

    /** 검색 타입들: "t"(title), "w"(writer) */
    private List<String> types;

    /** 검색어 */
    private String keyword;

    /** 완료 여부 필터 */
    private boolean finished;

    /** 기간 검색 */
    private LocalDate from;
    private LocalDate to;

    /** LIMIT offset */
    public int getSkip() {
        // page가 1 미만이거나 size가 1 미만인 경우를 방어
        int safePage = Math.max(1, page);
        int safeSize = Math.max(1, size);
        return (safePage - 1) * safeSize;
    }

    /** 키워드 존재 여부 */
    public boolean hasKeyword() {
        return keyword != null && !keyword.isBlank();
    }

    /** 타입 존재 여부 */
    public boolean hasTypes() {
        return types != null && !types.isEmpty();
    }

    /** 특정 타입 포함 여부 */
    public boolean checkType(String type) {
        return hasTypes() && types.contains(type);
    }

    /** 컨트롤러/서비스 진입 초기에 한 번 호출해서 값 정리하면 안전 */
    public void normalize() {
        if (page < 1) page = 1;
        if (size < 1) size = 10;
        if (size > 100) size = 100;

        // types 정리: null 방지 + 공백/널 원소 제거
        if (types == null) {
            types = Collections.emptyList();
        } else {
            types.removeIf(t -> t == null || t.isBlank());
        }

        // 기간(from <= to) 보정은 필요 시 이곳에서 처리
        // if (from != null && to != null && from.isAfter(to)) { LocalDate tmp = from; from = to; to = tmp; }
    }

    /** 현재 검색 파라미터를 쿼리스트링으로 */
    public String getLink() {
        StringBuilder builder = new StringBuilder();

        builder.append("page=").append(Math.max(1, page));
        builder.append("&size=").append(Math.max(1, size));

        if (finished) {
            builder.append("&finished=on");
        }

        if (hasTypes()) {
            for (String t : types) {
                builder.append("&types=").append(URLEncoder.encode(t, StandardCharsets.UTF_8));
            }
        }

        if (hasKeyword()) {
            builder.append("&keyword=").append(URLEncoder.encode(keyword, StandardCharsets.UTF_8));
        }

        if (from != null) {
            builder.append("&from=").append(from);
        }

        if (to != null) {
            builder.append("&to=").append(to);
        }

        return builder.toString();
    }
}
