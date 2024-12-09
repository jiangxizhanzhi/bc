package com.cyberpeace.oj.course.controller.req;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassroomMonitorReqTest {

    private ClassroomMonitorReq classroomMonitorReq;

    @BeforeEach
    void setUp() {
        classroomMonitorReq = new ClassroomMonitorReq();
    }

    @Test
    void testSetClassroom_id_ShouldSetClassroomIdCorrectly() {
        // Arrange
        String classroomId = "testClassroomId";

        // Act
        classroomMonitorReq.setClassroom_id(classroomId);

        // Assert
        assertEquals(classroomId, classroomMonitorReq.getClassroomId());
    }

    @Test
    void testSetPattern_ShouldSetPatternCorrectly() {
        // Arrange
        String pattern = "testPattern";

        // Act
        classroomMonitorReq.setPattern(pattern);

        // Assert
        assertEquals(pattern, classroomMonitorReq.getPattern());
    }

    @Test
    void testGetClassroomId_ShouldReturnCorrectClassroomId() {
        // Arrange
        String classroomId = "testClassroomId";
        classroomMonitorReq.setClassroom_id(classroomId);

        // Act
        String actualClassroomId = classroomMonitorReq.getClassroomId();

        // Assert
        assertEquals(classroomId, actualClassroomId);
    }

    @Test
    void testGetPattern_ShouldReturnCorrectPattern() {
        // Arrange
        String pattern = "testPattern";
        classroomMonitorReq.setPattern(pattern);

        // Act
        String actualPattern = classroomMonitorReq.getPattern();

        // Assert
        assertEquals(pattern, actualPattern);
    }
}
