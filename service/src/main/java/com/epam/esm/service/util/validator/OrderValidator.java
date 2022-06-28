package com.epam.esm.service.util.validator;

import com.epam.esm.domain.dto.TagDto;
import com.epam.esm.domain.dto.Order;
import com.epam.esm.service.exception.ExceptionHolder;
import lombok.experimental.UtilityClass;

import static com.epam.esm.service.exception.ExceptionMessageKey.*;

/**
 * The type Order validator.
 */
@UtilityClass
public class OrderValidator {
    /**
     * Is user id valid boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean isUserIdValid(long userId) {
        return userId > 0;
    }

    /**
     * Is gift certificate id valid boolean.
     *
     * @param certId the cert id
     * @return the boolean
     */
    public boolean isGiftCertificateIdValid(long certId) {
        return certId > 0;
    }

    /**
     * Is order valid.
     *
     * @param order  the order
     * @param holder the holder
     */
    public void isOrderValid(Order order, ExceptionHolder holder){
        if (order == null) {
            holder.addException(NULL_PASSED, TagDto.class);
            return;
        }
        if (!isUserIdValid(order.getUserId())) {
            holder.addException(BAD_ORDER_USER_ID, order.getUserId());
        }
        if (!isGiftCertificateIdValid(order.getGiftCertificateId())) {
            holder.addException(BAD_ORDER_GIFT_CERTIFICATE_ID, order.getGiftCertificateId());
        }
    }
}
