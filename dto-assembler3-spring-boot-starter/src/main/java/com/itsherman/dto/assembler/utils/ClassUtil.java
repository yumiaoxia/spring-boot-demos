package com.itsherman.dto.assembler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {
    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

    private static final String CLASS_SUFFIX = ".class";
    private static final String FILE_PROTOCAL = "file";
    private static final String JAR_PROTOCAL = "jar";

    /**
     * 获取同一路径下所有子类或接口实现类
     *
     * @param
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<Class<? extends T>> getAllAssignedClass(Class<T> cls) throws IOException,
            ClassNotFoundException {
        List<Class<? extends T>> classes = new ArrayList<>();
        for (Class<?> c : getClasses(cls)) {
            if (cls.isAssignableFrom(c) && !cls.equals(c)) {
                Class<? extends T> clazz = (Class<? extends T>) c;
                classes.add(clazz);
            }
        }
        return classes;
    }

    /**
     * 取得当前类路径下的所有类
     *
     * @param cls
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> getClasses(Class<?> cls) throws IOException, ClassNotFoundException {
        String pk = cls.getPackage().getName();
        String path = pk.replace('.', '/');
        log.info("获得类路径：{}", path);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URL url = classloader.getResource(path);
        List<Class<?>> resultClasses = new ArrayList<>();
        if (url != null) {
            String protocol = url.getProtocol();
            if (protocol.equals(FILE_PROTOCAL)) {
                resultClasses = getClassesFromDir(url.getPath(), pk, true, CLASS_SUFFIX);
            } else if (protocol.equals(JAR_PROTOCAL)) {
                JarFile jarFile = null;
                try {
                    jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jarFile != null) {
                    resultClasses = getClassesFromJar(jarFile.entries(), pk, true);
                }
            }
        }
        return resultClasses;
    }

    private static List<Class<?>> getClassesFromJar(Enumeration<JarEntry> jarEntries, String basePackage, boolean isRecursion) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        while (jarEntries.hasMoreElements()) {
            JarEntry entry = jarEntries.nextElement();
            if (!entry.isDirectory()) {
                String entryName = entry.getName().replace("/", ".");
                if (entryName.endsWith(CLASS_SUFFIX) && !entryName.contains("$") && entryName.startsWith(basePackage)) {
                    entryName = entryName.replace(".class", "");
                    if (isRecursion) {
                        classes.add(Class.forName(entryName));
                    } else if (entryName.replace(basePackage + ".", "").contains(".")) {
                        classes.add(Class.forName(entryName));
                    }
                }
            }
        }
        return classes;
    }

    private static List<Class<?>> getClassesFromDir(String filePath, String basePackage, boolean isRecursion, String suffixstr) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            filePath = URLDecoder.decode(new String(filePath.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
            File file = new File(filePath);
            File[] files = file.listFiles();
            for (File childFile : files) {
                if (childFile.isDirectory()) {
                    if (isRecursion) {
                        classes.addAll(getClassesFromDir(childFile.getPath(), basePackage + "." + childFile.getName(), isRecursion, suffixstr));
                    }
                } else {
                    String fileName = childFile.getName();
                    if (fileName.endsWith(suffixstr) && !fileName.contains("$")) {
                        fileName = fileName.replace(suffixstr, "");
                        classes.add(Class.forName(basePackage + "." + fileName));
                    }
                }
            }
        } catch (UnsupportedEncodingException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 迭代查找类
     *
     * @param dir
     * @param pk
     * @return
     * @throws ClassNotFoundException
     */
    private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {
        log.info("类父路径" + dir.getPath());
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!dir.exists()) {
            log.info("文件夹不存在");
            return classes;
        }
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                classes.addAll(getClasses(f, pk + "." + f.getName()));
            }
            String name = f.getName();
            if (name.endsWith(".class")) {
                classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));
            }
        }
        log.info("所有类：{}", classes);
        return classes;
    }
}