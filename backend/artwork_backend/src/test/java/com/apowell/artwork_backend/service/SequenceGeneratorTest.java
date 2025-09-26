package com.apowell.artwork_backend.service;

import com.apowell.artwork_backend.model.Counter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SequenceGeneratorTest {

    @Mock
    private MongoOperations mongoOperations;

    @InjectMocks
    private SequenceGeneratorService sequenceGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getNextSequence_existingCounter_returnsUpdatedSeq() {
        // Arrange
        Counter counter = new Counter();
        counter.setSeq(5);

        when(mongoOperations.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(Counter.class)
        )).thenReturn(counter);

        // Act
        int next = sequenceGenerator.getNextSequence("artwork");

        // Assert
        assertEquals(5, next); // returned seq matches mock
        verify(mongoOperations).findAndModify(any(Query.class), any(Update.class), any(FindAndModifyOptions.class), eq(Counter.class));
    }

    @Test
    void getNextSequence_noCounter_returnsOne() {
        // Arrange
        when(mongoOperations.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(Counter.class)
        )).thenReturn(null);

        // Act
        int next = sequenceGenerator.getNextSequence("artwork");

        // Assert
        assertEquals(1, next); // fallback to 1 if counter doesn't exist
    }
}
