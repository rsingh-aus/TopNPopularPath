package com.zuora.exercise.factory;

import com.zuora.exercise.service.TopNPopularPathService;
import com.zuora.exercise.service.TopNPopularPathServiceImpl;
import java.util.function.Supplier;

public final class TopNPopularPathServiceFactory {

    private static final Supplier<TopNPopularPathService> service = TopNPopularPathServiceImpl::new;

    public static TopNPopularPathService getTopNPopularPathService() {
        return service.get();
    }
}
