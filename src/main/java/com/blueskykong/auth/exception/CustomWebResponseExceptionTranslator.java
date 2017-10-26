package com.blueskykong.auth.exception;

/**
 * Created by keets on 2016/12/5.
 */
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

public class CustomWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebResponseExceptionTranslator.class);

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception;
        HttpHeaders headers = new HttpHeaders();
        int httpStatus;

        if (e instanceof HystrixRuntimeException) {
            Throwable actual = ((HystrixRuntimeException) e).getFallbackException().getCause().getCause();

            oAuth2Exception = new OAuth2Exception(actual.getMessage());

            String code;
            String message;

            if (actual instanceof AbstractException) {
                httpStatus = ((AbstractException) actual).getStatus().value();
                code = ((AbstractException) actual).getCode().getCode()+"";
                message = ((AbstractException) actual).getCode().getMessage();
                LOGGER.error("AbstractException", actual);
            } else {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
                code = GenericErrorCodes.GENERIC_API_ERROR_CODE.getCode() + "";
                message = GenericErrorCodes.GENERIC_API_ERROR_CODE.getMessage();
                LOGGER.error("HystrixRuntimeException", actual);
            }

            oAuth2Exception.addAdditionalInformation("code", code);
            oAuth2Exception.addAdditionalInformation("message", message);
        } else {
            ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
            oAuth2Exception = responseEntity.getBody();
            httpStatus = HttpStatus.UNAUTHORIZED.value();

            String errorCode = oAuth2Exception.getMessage();

            oAuth2Exception.addAdditionalInformation("code", StringUtils.isNotEmpty(errorCode) ? errorCode : GenericErrorCodes.GENERIC_API_ERROR_CODE.getCode() + "");
            oAuth2Exception.addAdditionalInformation("message", "账号认证失败");
            LOGGER.error("OAuth2Exception", oAuth2Exception);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(oAuth2Exception, headers, HttpStatus.valueOf(httpStatus));
    }

}
