package com.cn.momo.system.freeMarker.util;

import com.cn.momo.config.ErrorConfig;
import com.cn.momo.exception.BusinessException;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * dongwenmo 2021-06-07
 */
public class FreeMarker {

    /**
     * @param templatePath: 模板所在目录
     * @param templateName: 模板名称
     * @param map:          数据模板
     * @param aimFilePath:  目标文件路径
     *                      dongwenmo 2021-06-07
     */
    public static void generateJavaFile(String templatePath, String templateName, Map<String, Object> map, String aimFilePath) throws BusinessException {
        // 1.创建FreeMarker的配置类
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        // 2.指定模板加载器，将模板存入缓存中
        // 文件路径加载器
        FileTemplateLoader ftl = null;
        try {
            ftl = new FileTemplateLoader(new File(templatePath));
        } catch (IOException e) {
            throw new BusinessException(ErrorConfig.ERR_13001, "创建模版加载器失败：" + e.getMessage());
        }
        cfg.setTemplateLoader(ftl);

        // 3.获取模板
        Template template = null;
        try {
            template = cfg.getTemplate(templateName, "UTF-8");
        } catch (IOException e) {
            throw new BusinessException(ErrorConfig.ERR_13002, "获取模版失败：" + e.getMessage());
        }

        // 4.构造数据模型
        // 5.文件输出
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(aimFilePath), StandardCharsets.UTF_8));
            template.process(map, out);
        } catch (TemplateException | IOException e) {
            throw new BusinessException(ErrorConfig.ERR_13003, "文件输出失败：" + e.getMessage());
        }

    }

    public static String generateText(String templateContent, Map<String, Object> map) throws BusinessException {
        // 1.创建FreeMarker的配置类
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        // 2.指定模板加载器，将模板存入缓存中
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("myTemplate", templateContent);
        cfg.setTemplateLoader(stringLoader);

        // 3.获取模板
        Template template = null;
        try {
            template = cfg.getTemplate("myTemplate", "utf-8");
        } catch (IOException e) {
            throw new BusinessException(ErrorConfig.ERR_13002, "获取模版失败：" + e.getMessage());
        }

        // 4.构造数据模型
        // 5.输入生成内容
        StringWriter stringWriter = new StringWriter();
        try {
            template.process(map, new BufferedWriter(stringWriter));
        } catch (TemplateException | IOException e) {
            throw new BusinessException(ErrorConfig.ERR_13004, "文本输出失败：" + e.getMessage());
        }

        return stringWriter.toString();
    }
}
