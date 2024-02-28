package com.example.routes.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PageService {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_LIMIT = 5;

    public PageRequest createPageRequest(Integer page, Integer elementsCount) {
        int resolvedPage = (page != null && page >= 0) ? page-1 : DEFAULT_PAGE;
        int resolvedElementsCount = (elementsCount != null && elementsCount > 0) ? elementsCount : DEFAULT_LIMIT;

        return PageRequest.of(resolvedPage, resolvedElementsCount);
    }
}

