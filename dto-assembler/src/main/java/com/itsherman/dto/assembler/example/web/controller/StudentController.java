package com.itsherman.dto.assembler.example.web.controller;

import com.itsherman.dto.assembler.DtoTransFormer;
import com.itsherman.dto.assembler.example.entity.Student;
import com.itsherman.dto.assembler.example.entity.Teacher;
import com.itsherman.dto.assembler.example.service.StudentService;
import com.itsherman.dto.assembler.example.web.command.StudentCreateCommand;
import com.itsherman.dto.assembler.example.web.command.TeacherCreateCommand;
import com.itsherman.dto.assembler.example.web.dto.StudentDto;
import com.itsherman.web.common.request.ApiPageRequest;
import com.itsherman.web.common.request.ApiRequest;
import com.itsherman.web.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Api("懒加载测试")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation("创建学生")
    @PostMapping("/create")
    public ApiResponse<Void> create(@RequestBody ApiRequest<StudentCreateCommand> request) {
        StudentCreateCommand studentCreateCommand = request.getCommand();
        Set<TeacherCreateCommand> teacherCreateCommands = studentCreateCommand.getTeachers();
        Student student = new Student();
        BeanUtils.copyProperties(request.getCommand(), student, "teachers");

        Set<Teacher> teachers = new HashSet<>();
        for (TeacherCreateCommand teacherCreateCommand : teacherCreateCommands) {
            Teacher teacher = new Teacher();
            BeanUtils.copyProperties(teacherCreateCommand, teacher);
            teachers.add(teacher);
        }
        student.setTeachers(teachers);
        studentService.create(student);
        return ApiResponse.createSuccess();
    }

    @ApiOperation("获取学生详情")
    @GetMapping("/{id}")
    public ApiResponse<StudentDto> detail(@PathVariable Long id) {
        Student student = studentService.getById(id);
        return ApiResponse.createSuccess(DtoTransFormer.to(StudentDto.class).apply(student));
    }

    @ApiOperation("获取学生列表")
    @GetMapping("/list")
    public ApiResponse<StudentDto> list(@PathVariable ApiPageRequest<Void> request) {
        return ApiResponse.createSuccess(DtoTransFormer.to(StudentDto.class).apply(studentService.findAllByPage(request.getPageable())));
    }
}
