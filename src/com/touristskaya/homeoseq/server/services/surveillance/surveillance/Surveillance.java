package com.touristskaya.homeoseq.server.services.surveillance.surveillance;

import com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_pipeline.SurveillancePipeline;
import com.touristskaya.homeoseq.server.services.surveillance.surveillance.surveillance_status.SurveillanceStatus;

public class Surveillance {
    private SurveillancePipeline mPipeline;
    private Thread mPipelineThread;

    public Surveillance() {
        mPipeline = new SurveillancePipeline();
    }

    public boolean start() {
        if (mPipelineThread != null) {
            return false;
        }

        mPipeline.startPipeline();

        mPipelineThread = new Thread(mPipeline);
        mPipelineThread.start();

        return true;
    }

    public boolean stop() {
        mPipeline.stopPipeline();
        if (mPipelineThread == null) {
            return true;
        }

        try {
            mPipelineThread.join();
            mPipelineThread = null;

            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    public SurveillanceStatus getStatus() {
        return mPipeline.getStatus();
    }
}
