package com.raccoon.learnapp.student.impl.service;

import com.raccoon.learnapp.student.api.Student;
import com.raccoon.learnapp.student.impl.dao.entity.StudentEntity;
import com.raccoon.learnapp.student.impl.dao.StudentRepository;
import com.raccoon.learnapp.student.impl.model.StudentRegistrationData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentConvertor studentConvertor;

    @Test
    void shouldReturnStudents() {
        // given
        StudentEntity studentDef = new StudentEntity();
        Student student = new Student();

        when(studentRepository.findAll()).thenReturn(List.of(studentDef));
        when(studentConvertor.convert(studentDef)).thenReturn(student);

        // when
        List<Student> result = studentService.getStudents();

        // then
        assertThat(result).asList().containsExactly(student);
        verify(studentRepository).findAll();
        verify(studentConvertor).convert(studentDef);
    }

    @Test
    void shouldSignUpStudent() {
        // given
        StudentRegistrationData registrationData = new StudentRegistrationData();
        StudentEntity studentDef = new StudentEntity();

        when(studentConvertor.convert(registrationData)).thenReturn(studentDef);

        // when
        studentService.signUpStudent(registrationData);

        // then
        verify(studentConvertor).convert(registrationData);
        verify(studentRepository).save(studentDef);
    }
}