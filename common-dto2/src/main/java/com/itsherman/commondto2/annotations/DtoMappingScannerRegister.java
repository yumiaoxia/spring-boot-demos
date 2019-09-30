package com.itsherman.commondto2.annotations;

import com.itsherman.commondto2.core.DtoDefinitionHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p> </p>
 *
 * @author 俞淼霞
 * @since 2019-09-26
 */
public class DtoMappingScannerRegister implements ImportBeanDefinitionRegistrar {

    private static final Logger LOGGER = LoggerFactory.getLogger(DtoMappingScannerRegister.class);

    private static final String CLASS_SUFFIX = ".class";
    private static final String FILE_PROTOCAL = "file";
    private static final String JAR_PROTOCAL = "jar";


    public DtoMappingScannerRegister() {
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes dtoMappingAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableDtoMapping.class.getName()));
        if (dtoMappingAttrs != null) {
            Object mappings = dtoMappingAttrs.get("mappings");
            AnnotationAttributes[] dtoMappingAttributes = (AnnotationAttributes[]) mappings;
            if (dtoMappingAttributes != null && dtoMappingAttributes.length > 0) {
                for (AnnotationAttributes dtoMappingAttribute : dtoMappingAttributes) {
                    String basePackage = dtoMappingAttribute.getString("basePackage");
                    boolean isRecursion = dtoMappingAttribute.getBoolean("isRecursion");
                    registerDtoDefinitions(basePackage, isRecursion);
                }
            }
        }
    }

    private void registerDtoDefinitions(String basePackage, boolean isRecursion) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Set<String> classNames = new HashSet<>();
        String packagePath = basePackage.replace(".", "/");
        URL url = contextClassLoader.getResource(packagePath);
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals(FILE_PROTOCAL)) {
                Set<String> dirClassNames = getClassNameFromDir(url.getPath(), basePackage, isRecursion, CLASS_SUFFIX);
                classNames.addAll(dirClassNames);
            } else if (protocol.equals(JAR_PROTOCAL)) {
                JarFile jarFile = null;
                try {
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jarFile != null) {
                    getClassNameFromJar(jarFile.entries(), basePackage, true);
                }
            }
            Set<Class> dtoClasses = filterByAnnotation(classNames);
            DtoDefinitionHolder dtoDefinitionHolder = new DtoDefinitionHolder(dtoClasses);
        } else {
            LOGGER.warn("basepackage must be specified!");
        }
    }


    private Set<Class> filterByAnnotation(Collection<String> classNames) {
        Assert.notNull(classNames, "[Assertion Failed] - classNames must be not null!");
        Set<Class> dtoClasses = new HashSet<>();
        try {
            for (String className : classNames) {
                Class clazz = Class.forName(className);
                DtoModel dtoModel = (DtoModel) clazz.getAnnotation(DtoModel.class);
                if (dtoModel != null) {
                    dtoClasses.add(clazz);
                }
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return dtoClasses;
    }

    private Set<String> getClassNameFromJar(Enumeration<JarEntry> jarEntries, String basePackage, boolean isRecursion) {
        Set<String> classNames = new HashSet<>();
        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();
            if (!entry.isDirectory()) {
                String entryName = entry.getName().replace("/", ".");
                if (entryName.endsWith(CLASS_SUFFIX) && !entryName.contains("$") && entryName.startsWith(basePackage)) {
                    entryName = entryName.replace(".class", "");
                    if (isRecursion) {
                        classNames.add(entryName);
                    } else if (entryName.replace(basePackage + ".", "").contains(".")) {
                        classNames.add(entryName);
                    }
                }
            }
        }
        return classNames;
    }

    private Set<String> getClassNameFromDir(String filePath, String basePackage, boolean isRecursion, String suffixstr) {
        Set<String> classNames = new HashSet<>();
        try {
            filePath = URLDecoder.decode(new String(filePath.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
            File file = new File(filePath);
            File[] files = file.listFiles();
            for (File childFile : files) {
                if (childFile.isDirectory()) {
                    if (isRecursion) {
                        classNames.addAll(getClassNameFromDir(childFile.getPath(), basePackage + "." + childFile.getName(), isRecursion, suffixstr));
                    }
                } else {
                    String fileName = childFile.getName();
                    if (fileName.endsWith(suffixstr) && !fileName.contains("$")) {
                        fileName = fileName.replace(suffixstr, "");
                        classNames.add(basePackage + "." + fileName);
                    }
                }
            }
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return classNames;
    }

}
