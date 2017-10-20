package com.blueskykong.auth.client.fallback;

import com.blueskykong.auth.client.feign.UtilClient;
import com.blueskykong.auth.exception.ErrorCodes;
import com.blueskykong.exception.RemoteException;

/**
 * @author keets
 * @date 2017/9/26
 */
public class UtilFallbackClient implements UtilClient {

    @Override
    public long generateId() {
        throw new RemoteException(ErrorCodes.ID_SERVICE_CALL_FAILURE);
    }

}
