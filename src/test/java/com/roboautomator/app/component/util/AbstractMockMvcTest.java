package com.roboautomator.app.component.util;

import javax.persistence.EntityManager;

import com.roboautomator.app.component.collection.CollectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractMockMvcTest {

    @MockBean
    protected CollectionRepository collectionRepository;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    protected MockMvc mockMvc;

}
