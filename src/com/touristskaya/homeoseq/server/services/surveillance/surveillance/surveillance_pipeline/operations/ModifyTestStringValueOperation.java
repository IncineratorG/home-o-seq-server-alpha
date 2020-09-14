package com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_pipeline.operations;

import com.touristskaya.homeoseq.common.pipeline_operation.PipelineOperation;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_status.SurveillanceStatus;

public class ModifyTestStringValueOperation implements PipelineOperation<SurveillanceStatus, SurveillanceStatus> {
    private SurveillanceStatus mSurveillanceStatus;

    public ModifyTestStringValueOperation() {

    }

    @Override
    public void setInput(SurveillanceStatus value) {
        mSurveillanceStatus = value;
    }

    @Override
    public SurveillanceStatus getResult() {
        return mSurveillanceStatus;
    }

    @Override
    public void run() {
//        SystemEventsHandler.onInfo("ModifyTestStringValueOperation");

        mSurveillanceStatus.setTestStringValue("TEST_STRING: " + System.currentTimeMillis());
    }
}
