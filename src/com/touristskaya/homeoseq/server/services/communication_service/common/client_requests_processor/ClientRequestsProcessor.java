package com.touristskaya.homeoseq.server.services.communication_service.common.client_requests_processor;

import com.touristskaya.homeoseq.common.actions.actions_dispatcher.ActionsDispatcher;
import com.touristskaya.homeoseq.common.camera.Camera;
import com.touristskaya.homeoseq.common.promise.Promise;
import com.touristskaya.homeoseq.common.system_events_handler.SystemEventsHandler;
import com.touristskaya.homeoseq.server.errors.Errors;
import com.touristskaya.homeoseq.server.services.Services;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_request.ClientRequest;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_request.ClientRequestTypes;
import com.touristskaya.homeoseq.server.services.communication_service.common.client_requests_payload_extractor.ClientRequestsPayloadExtractor;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_manager.CommunicationManager;
import com.touristskaya.homeoseq.server.services.communication_service.common.communication_messages.CommunicationMessages;
import com.touristskaya.homeoseq.server.services.communication_service.common.data_serializer.DataSerializer;

import java.awt.image.BufferedImage;
import java.util.List;

public class ClientRequestsProcessor {
    private CommunicationManager mCommunicationManager;
    private ActionsDispatcher mActionsDispatcher;
    private DataSerializer mDataSerializer;
    private ClientRequestsPayloadExtractor mPayloadExtractor;

    public ClientRequestsProcessor(CommunicationManager communicationManager, ActionsDispatcher actionsDispatcher) {
        mCommunicationManager = communicationManager;
        mActionsDispatcher = actionsDispatcher;
        mDataSerializer = new DataSerializer();
        mPayloadExtractor = new ClientRequestsPayloadExtractor();
    }

    public void process(ClientRequest request) {
        SystemEventsHandler.onInfo("");
        SystemEventsHandler.onInfo("REQUEST_PROCESSED: " + request.getType());

        mCommunicationManager.sendResponseMessage(
                CommunicationMessages.confirmReceiveRequestMessage(request.getUuid())
        );

        switch (request.getType()) {
            case (ClientRequestTypes.IS_ALIVE): {
                mCommunicationManager.sendResponseMessage(
                        CommunicationMessages.requestResultMessage(request.getUuid(), String.valueOf(true))
                );

                break;
            }

            case (ClientRequestTypes.GET_ALL_CAMERAS): {
                Promise<List<Camera>> allCamerasResultPromise = new Promise<>();
                allCamerasResultPromise.then(cameras -> {

                    String serializedCameras = mDataSerializer.serialize(cameras);

                    mCommunicationManager.sendResponseMessage(
                            CommunicationMessages.requestResultMessage(
                                    request.getUuid(),
                                    serializedCameras
                            )
                    );
                });

                mActionsDispatcher.dispatch(
                        Services.camerasService.actionCreators.getAllCamerasAction(allCamerasResultPromise)
                );

                break;
            }

            case (ClientRequestTypes.GET_CAMERA_IMAGE): {
                String cameraId = mPayloadExtractor.getCameraImageRequest(request);
                if (cameraId == null) {
                    mCommunicationManager.sendResponseMessage(
                            CommunicationMessages.errorMessage(
                                    request.getUuid(),
                                    Errors.getError(Errors.BAD_REQUEST_DATA)
                            )
                    );
                }

                Promise<BufferedImage> imagePromise = new Promise<>();
                imagePromise.then(image -> {
                    String serializedImageData = mDataSerializer.serialize(cameraId, image);

                    mCommunicationManager.sendResponseMessage(
                            CommunicationMessages.requestResultMessage(request.getUuid(), serializedImageData)
                    );
                });

                mActionsDispatcher.dispatch(
                        Services.camerasService.actionCreators.getCameraImageAction(cameraId, imagePromise)
                );

                break;
            }

            default: {
                SystemEventsHandler.onInfo("ClientRequestsProcessor->process(): SENDING_ERROR_RESPONSE");

                mCommunicationManager.sendResponseMessage(
                        CommunicationMessages.errorMessage(request.getUuid(), Errors.getError(Errors.UNKNOWN_REQUEST))
                );
            }
        }

        SystemEventsHandler.onInfo("");
    }
}
