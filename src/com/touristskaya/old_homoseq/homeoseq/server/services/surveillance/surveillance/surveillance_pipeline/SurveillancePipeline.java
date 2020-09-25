package com.touristskaya.old_homoseq.homeoseq.server.services.surveillance.surveillance.surveillance_pipeline;

import com.touristskaya.old_homoseq.homeoseq.common.pipeline_operation.PipelineOperation;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.old_homoseq.homeoseq.server.services.surveillance.surveillance.surveillance_pipeline.operations.ModifyTestStringValueOperation;
import com.touristskaya.old_homoseq.homeoseq.server.services.surveillance.surveillance.surveillance_pipeline.operations.WaitOperation;
import com.touristskaya.old_homoseq.homeoseq.server.services.surveillance.surveillance.surveillance_status.SurveillanceStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SurveillancePipeline implements Runnable {
    private volatile boolean mPipelineRunning;
    private SurveillanceStatus mSurveillanceStatus;
    private List<PipelineOperation> mOperations;

    public SurveillancePipeline() {
        mPipelineRunning = true;

        mSurveillanceStatus = new SurveillanceStatus();

        mOperations = new ArrayList<>(
                Arrays.asList(
                      new ModifyTestStringValueOperation(),
                      new WaitOperation()
                )
        );
    }

    public synchronized void startPipeline() {
        mPipelineRunning = true;
    }

    public synchronized void stopPipeline() {
        mPipelineRunning = false;
    }

    public synchronized SurveillanceStatus getStatus() {
        return mSurveillanceStatus;
    }

    @Override
    public void run() {
        while (mPipelineRunning) {
            SystemEventsHandler.onInfo("SurveillancePipeline->RUNNING");

            SurveillanceStatus intermediateStatus = new SurveillanceStatus(mSurveillanceStatus);
            intermediateStatus.setIsRunning(true);
            for (PipelineOperation operation : mOperations) {
                operation.setInput(intermediateStatus);
                operation.run();
                intermediateStatus = (SurveillanceStatus) operation.getResult();
            }
            intermediateStatus.setTimestamp(System.currentTimeMillis());
            updateSurveillanceStatus(intermediateStatus);
        }

        SurveillanceStatus finalStatus = new SurveillanceStatus(mSurveillanceStatus);
        finalStatus.setIsRunning(false);
        finalStatus.setTimestamp(System.currentTimeMillis());

        updateSurveillanceStatus(finalStatus);
    }

    private synchronized void updateSurveillanceStatus(SurveillanceStatus updatedStatus) {
        mSurveillanceStatus = new SurveillanceStatus(updatedStatus);
    }
}
