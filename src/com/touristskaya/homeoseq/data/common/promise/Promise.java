package com.touristskaya.homeoseq.data.common.promise;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class Promise<T> {
    private Consumer<T> mResultAcceptor;
    private Consumer<String> mErrorAcceptor;
    private T mResult;
    private String mError;
    private LinkedBlockingQueue<T> mResultQueue;

    public Promise() {
        mResultQueue = new LinkedBlockingQueue<>();
        mResultAcceptor = null;
        mErrorAcceptor = null;
        mResult = null;
        mError = null;
    }

    public void setResult(T result) {
        mResult = result;

        try {
            mResultQueue.put(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resolve(T result) {
        mResult = result;

        if (mResultAcceptor != null) {
            mResultAcceptor.accept(mResult);
        }

        try {
            mResultQueue.put(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resolveWithCurrentResult() {
        if (mResultAcceptor != null) {
            mResultAcceptor.accept(mResult);
        }
    }

    public void reject(String error) {
        mError = error;

        if (mErrorAcceptor != null) {
            mErrorAcceptor.accept(mError);
        }
    }

    public T get() {
        T result = null;
        try {
            result = mResultQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void then(Consumer<T> resultAcceptor) {
        mResultAcceptor = resultAcceptor;

        if (mResult != null) {
            mResultAcceptor.accept(mResult);
        }
    }

    public void error(Consumer<String> errorAcceptor) {
        mErrorAcceptor = errorAcceptor;

        if (mError != null) {
            mErrorAcceptor.accept(mError);
        }
    }
}
