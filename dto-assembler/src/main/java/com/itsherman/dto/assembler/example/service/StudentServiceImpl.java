package com.itsherman.dto.assembler.example.service;

import com.itsherman.dto.assembler.example.dao.StudentRepository;
import com.itsherman.dto.assembler.example.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void create(Student student) {
        studentRepository.save(student);
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.getOne(id);
    }

    @Override
    public Page<Student> findAllByPage(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }


}
