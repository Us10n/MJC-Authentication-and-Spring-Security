package com.epam.esm.service.util.validator;

import com.epam.esm.domain.dto.OrderInputDto;
import com.epam.esm.service.exception.ExceptionHolder;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    public boolean isUserIdValid(Long userId) {
        return userId != null && userId > 0;
    }

    /**
     * Is gift certificate id valid boolean.
     *
     * @param certId the cert id
     * @return the boolean
     */
    public boolean isGiftCertificateIdValid(Long certId) {
        return certId != null && certId > 0;
    }

    private void validateGiftCertificateIds(List<Long> certIds, ExceptionHolder holder) {

    }

    /**
     * Is order valid.
     *
     * @param order  the order
     * @param holder the holder
     */
    public void isOrderInputDtoValid(OrderInputDto order, ExceptionHolder holder) {
        if (order == null) {
            holder.addException(NULL_PASSED, OrderInputDto.class);
            return;
        }
        if (CollectionUtils.isEmpty(order.getGiftCertificateIds())) {
            holder.addException(NO_GIFT_CERTIFICATE_ID_VALUES);
            return;
        }
        if (!isUserIdValid(order.getUserId())) {
            holder.addException(BAD_ORDER_USER_ID, order.getUserId());
        }

        order.getGiftCertificateIds().forEach(giftCertificateId -> {
            if (!isGiftCertificateIdValid(giftCertificateId)) {
                holder.addException(BAD_ORDER_GIFT_CERTIFICATE_ID, giftCertificateId);
            }
        });
    }
}
