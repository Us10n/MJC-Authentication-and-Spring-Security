package com.epam.esm.service.util.validator;

import com.epam.esm.domain.dto.GiftCertificateDto;
import com.epam.esm.service.exception.ExceptionHolder;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static com.epam.esm.service.exception.ExceptionMessageKey.*;

/**
 * The type Gift certificate validator.
 */
@UtilityClass
public class GiftCertificateValidator {
    private static final Integer MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 128;
    private static final Double MIN_PRICE = 0.1;
    private static final Double MAX_PRICE = 10000.0;
    private static final Integer MIN_DURATION = 1;
    private static final Integer MAX_DURATION = 28;

    /**
     * Is name valid boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean isNameValid(String name) {
        return name != null && name.length() >= MIN_LENGTH && name.length()<=MAX_LENGTH;
    }

    /**
     * Is description valid boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public boolean isDescriptionValid(String description) {
        return description != null && description.length() >= MIN_LENGTH;
    }

    /**
     * Is price valid boolean.
     *
     * @param price the price
     * @return the boolean
     */
    public boolean isPriceValid(Double price) {
        return price != null && price >= MIN_PRICE && price <= MAX_PRICE;
    }

    /**
     * Is duration valid boolean.
     *
     * @param duration the duration
     * @return the boolean
     */
    public boolean isDurationValid(Integer duration) {
        return duration != null && duration >= MIN_DURATION && duration <= MAX_DURATION;
    }

    /**
     * Is date valid boolean.
     *
     * @param date the date
     * @return the boolean
     */
    public boolean isDateValid(String date) {
        try {
            LocalDateTime.parse(date);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Is gift certificate dto valid.
     *
     * @param giftCertificateDto the gift certificate dto
     * @param holder             the holder
     */
    public void isGiftCertificateDtoValid(GiftCertificateDto giftCertificateDto, ExceptionHolder holder) {
        if (giftCertificateDto == null) {
            holder.addException(NULL_PASSED, GiftCertificateDto.class);
            return;
        }
        if (!isNameValid(giftCertificateDto.getName())) {
            holder.addException(BAD_GIFT_CERTIFICATE_NAME, giftCertificateDto.getName());
        }
        if (!isDescriptionValid(giftCertificateDto.getDescription())) {
            holder.addException(BAD_GIFT_CERTIFICATE_DESCRIPTION, giftCertificateDto.getDescription());
        }
        if (!isPriceValid(giftCertificateDto.getPrice())) {
            holder.addException(BAD_GIFT_CERTIFICATE_PRICE, giftCertificateDto.getPrice());
        }
        if (!isDurationValid(giftCertificateDto.getDuration())) {
            holder.addException(BAD_GIFT_CERTIFICATE_DURATION, giftCertificateDto.getDuration());
        }
        if (!isDateValid(giftCertificateDto.getCreateDate().toString())) {
            holder.addException(BAD_GIFT_CERTIFICATE_CREATE_DATE, giftCertificateDto.getCreateDate());
        }
        if (!isDateValid(giftCertificateDto.getLastUpdateDate().toString())) {
            holder.addException(BAD_GIFT_CERTIFICATE_UPDATE_DATE, giftCertificateDto.getLastUpdateDate());
        }
        if(giftCertificateDto.getTags().isEmpty()){
            holder.addException(GIFT_CERTIFICATE_MUST_CONTAIN_TAGS);
        }
        giftCertificateDto.getTags().forEach(tagDto -> TagValidator.isTagDtoValid(tagDto, holder));
    }
}
