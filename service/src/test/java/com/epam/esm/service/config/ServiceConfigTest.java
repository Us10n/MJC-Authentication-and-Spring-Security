package com.epam.esm.service.config;

import com.epam.esm.repository.dao.GiftCertificateDao;
import com.epam.esm.repository.dao.OrderDao;
import com.epam.esm.repository.dao.TagDao;
import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.repository.dao.impl.OrderDaoImpl;
import com.epam.esm.repository.dao.impl.TagDaoImpl;
import com.epam.esm.repository.dao.impl.UserDaoImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"com.epam.esm"})
public class ServiceConfigTest {

    @Bean
    @Primary
    public GiftCertificateDao giftCertificateDao() {
        return Mockito.mock(GiftCertificateDaoImpl.class);
    }

    @Bean
    @Primary
    public TagDao tagDao() {
        return Mockito.mock(TagDaoImpl.class);
    }

    @Bean
    @Primary
    public OrderDao orderDao() {
        return Mockito.mock(OrderDaoImpl.class);
    }

    @Bean
    @Primary
    public UserDao userDao() {
        return Mockito.mock(UserDaoImpl.class);
    }
}