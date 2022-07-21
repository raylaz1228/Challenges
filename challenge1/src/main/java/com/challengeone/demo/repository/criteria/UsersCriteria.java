package com.challengeone.demo.repository.criteria;

import java.util.Map;
import lombok.Builder;
import lombok.Data;


@Data
public class UsersCriteria {
    private Map<String, String> filter;
    private String sortKey;
    private Integer limit;
    private Integer start;

    @Builder
    public UsersCriteria(Integer start, Integer limit, Map<String, String> filter, String sortKey) {
        this.start = start;
        this.limit = limit;
        this.filter = filter;
        this.sortKey = sortKey;
    }
}
