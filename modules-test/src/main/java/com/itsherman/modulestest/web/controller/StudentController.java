package com.itsherman.modulestest.web.controller;

import com.itsherman.modulestest.entity.Student;
import com.itsherman.modulestest.entity.Teacher;
import com.itsherman.modulestest.service.StudentService;
import com.itsherman.modulestest.web.dto.StudentDto;
import com.itsherman.web.common.request.ApiPageRequest;
import com.itsherman.web.common.request.ApiRequest;
import com.itsherman.web.common.response.ApiPageResponse;
import com.itsherman.web.common.response.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
    public ApiResponse<Void> create(@RequestBody ApiRequest<com.itsherman.modulestest.web.command.StudentCreateCommand> request) {
        com.itsherman.modulestest.web.command.StudentCreateCommand studentCreateCommand = request.getCommand();
        Set<com.itsherman.modulestest.web.command.TeacherCreateCommand> teacherCreateCommands = studentCreateCommand.getTeachers();
        Student student = new Student();
        BeanUtils.copyProperties(request.getCommand(), student, "teachers");

        Set<Teacher> teachers = new HashSet<>();
        for (com.itsherman.modulestest.web.command.TeacherCreateCommand teacherCreateCommand : teacherCreateCommands) {
            Teacher teacher = new Teacher();
            BeanUtils.copyProperties(teacherCreateCommand, teacher);
            teacher.setStudents(Collections.singleton(student));
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
    @PostMapping("/list")
    public ApiPageResponse<List<StudentDto>> list(@RequestBody @Validated ApiPageRequest<Void> request) {
        Page<Student> studentPage = studentService.findAllByPage(request.getPageable());
        Page<StudentDto> result = DtoTransFormer.toPage(StudentDto.class).apply(studentPage);
        return ApiPageResponse.createPageSuccess(result);
    }
}
