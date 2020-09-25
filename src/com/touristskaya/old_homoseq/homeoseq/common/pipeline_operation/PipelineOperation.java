package com.touristskaya.old_homoseq.homeoseq.common.pipeline_operation;

public interface PipelineOperation<T, U> {
    void setInput(T value);
    U getResult();
    void run();
}
