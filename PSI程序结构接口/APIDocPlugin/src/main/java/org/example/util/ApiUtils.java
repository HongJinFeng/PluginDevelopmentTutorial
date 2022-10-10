package org.example.util;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import org.example.constant.Constants;
import org.example.dto.ApiBodyDTO;
import org.example.dto.ApiRequestParamDTO;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class ApiUtils {

    public static boolean isRestfulApiMethod(PsiMethod psiMethod) {
        for (PsiAnnotation annotation : psiMethod.getAnnotations()) {
            String packagePath = annotation.getQualifiedName();
            if (Constants.RequestMapping.equals(packagePath) ||
                    Constants.GetMapping.equals(packagePath) ||
                    Constants.PostMapping.equals(packagePath) ||
                    Constants.PutMapping.equals(packagePath) ||
                    Constants.DeleteMapping.equals(packagePath) ||
                    Constants.PatchMapping.equals(packagePath)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMappingAnnotation(PsiAnnotation psiAnnotation) {
        return Constants.RequestMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.GetMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.PostMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.PutMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.DeleteMapping.equals(psiAnnotation.getQualifiedName()) ||
                Constants.PatchMapping.equals(psiAnnotation.getQualifiedName());
    }

    public static String getPath(PsiMethod psiMethod) {
        StringBuilder result = new StringBuilder();
        PsiClass psiClass = PsiTreeUtil.getParentOfType(psiMethod, PsiClass.class);
        if (psiClass == null) {
            return "";
        }
        PsiAnnotation classAnnotation = psiClass.getAnnotation(Constants.RequestMapping);
        // 拼接 类上面 RequestMapping 注解的路径
        if (classAnnotation != null) {
            result.append(getAnnotationAttributeValue(classAnnotation, "value"));
        }
        for (PsiAnnotation annotation : psiMethod.getAnnotations()) {
            if (isMappingAnnotation(annotation)) {
                result.append(getAnnotationAttributeValue(annotation, "value"));
                break;
            }
        }
        return result.toString();
    }

    public static String getDesc(PsiMethod psiMethod) {
        if (psiMethod.getDocComment() == null) {
            return "";
        }
        return psiMethod.getDocComment().getText();
    }

    public static String getAnnotationAttributeValue(PsiAnnotation annotation, String attrName) {
        PsiNameValuePair[] attrs = annotation.getParameterList().getAttributes();
        for (PsiNameValuePair attr : attrs) {
            if (attr.getAttributeName().equals(attrName)) {
                return attr.getLiteralValue();
            }
        }
        return "";
    }

    public static String getHTTPMethod(PsiMethod psiMethod) {
        // 通过 psiMethod的 getAnnotation 方法，判断注解上是否有 GetMapping
        // 如果有，表示 HTTP 请求方法为 Get 请求，以此类推
        if (psiMethod.getAnnotation(Constants.GetMapping) != null) {
            return "GET";
        } else if (psiMethod.getAnnotation(Constants.PostMapping) != null) {
            return "POST";
        } else if (psiMethod.getAnnotation(Constants.DeleteMapping) != null) {
            return "DELETE";
        } else if (psiMethod.getAnnotation(Constants.PutMapping) != null) {
            return "PUT";
        } else if (psiMethod.getAnnotation(Constants.PatchMapping) != null) {
            return "PATCH";
        }
        return "";
    }

    public static List<ApiRequestParamDTO> getParams(PsiMethod psiMethod, Project project) {
        // 通过 getParameterList().getParameters() 获取到方法上所有的参数
        // 遍历参数列表
        for (PsiParameter parameter : psiMethod.getParameterList().getParameters()) {
            // 当若前的参数能获取到 RequestParam 注解，则解析当前 PsiParameter 对象获取参数
            PsiAnnotation annotation = parameter.getAnnotation(Constants.RequestParam);
            if (annotation != null) {
                // 解析 PsiParameter 参数
                return doGetParams(psiMethod, parameter, project);
            }
        }
        return null;
    }

    private static List<ApiRequestParamDTO> doGetParams(PsiMethod psiMethod, PsiParameter parameter, Project project) {
        // 获取参数的类路径，getType 方法返回 PsiType 对象
        // getPresentableText 用于获取类型的名称
        String type = parameter.getType().getPresentableText();
        List<ApiRequestParamDTO> result = new LinkedList<>();
        // 判断类型是否是 Java 内建类型，例如 String、List、Map 等
        if (Constants.basicType. contains(type)) {
            result.add(getBasicTypeParamDTO( psiMethod, parameter, type));
            return result;
        }
        PsiClass psiClass = getPsiClassByType( parameter.getType(), project);
        if (psiClass == null) {
            return null;
        }
        // 如果入参是对象，遍历对象中的每一个字段，包装为一个 List 返回
        for (PsiField item : psiClass.getAllFields()) {
            ApiRequestParamDTO paramDTO = new ApiRequestParamDTO();
            paramDTO.setName(item.getName());
            paramDTO.setType(item.getType().getPresentableText());
            PsiDocComment docComment = item.getDocComment();
            if (docComment != null) {
                paramDTO.setDesc(docComment.getText());
            }
            result.add(paramDTO);
        }
        return result;
    }

    private static ApiRequestParamDTO getBasicTypeParamDTO(PsiMethod psiMethod, PsiParameter parameter, String type) {
        // 对于基本类型，没有嵌套对象多层级关系，直接返回
        ApiRequestParamDTO result = new ApiRequestParamDTO();
        // PsiParameter.getName 用于获取字段名字
        result.setName(parameter.getName());
        result.setType(type);
        // 由于方法上可能会对入参进行注释，此处获取方法上的对于字段的注释
        result.setDesc(getParamDesc(psiMethod, result.getName()));
        return result;
    }

    public static String getParamDesc(PsiMethod psiMethod, String paramName) {
        if (psiMethod == null) {
            return "";
        }
        // 获取方法上注释
        PsiDocComment docComment = psiMethod.getDocComment();
        if (docComment == null) {
            return "";
        }
        // 获取注释中的标签，例如 @param 标签就包含在其中
        PsiDocTag[] psiDocTags = docComment.getTags();
        // 获取到 @param 标签，过滤出我们所需要的入参注释，将空格、* 等字符去除
        // 即可得到入参的注释
        for (PsiDocTag psiDocTag : psiDocTags) {
            if (psiDocTag.getText().toLowerCase(Locale.ROOT).contains("@param") && (!psiDocTag.getText().contains("[")) && psiDocTag.getText().contains(paramName)) {
                return psiDocTag.getText().replace("@param", "").
                        replace("@Param", "").
                        replace(paramName, "").replace(":", "").
                        replace("*", "").
                        replace("\n", " ");
            }
        }
        return "";
    }

    public static ApiBodyDTO getRespBody(PsiMethod psiMethod, Project project) {
        PsiType returnType = psiMethod.getReturnType();
        if (returnType == null) {
            return null;
        }
        // 判断是否为 Java 内建基本类型，如果是基本类型，则不存在对象嵌套，可以简单的返回
        if (Constants.basicType.contains(returnType.getPresentableText())) {
            ApiBodyDTO result = new ApiBodyDTO();
            result.setName(returnType.getPresentableText());
            result.setType(returnType.getPresentableText());
            return result;
        }
        ApiBodyDTO result = new ApiBodyDTO();
        // 查看下面 getPsiClassByType 方法实现
        PsiClass psiClass = getPsiClassByType(returnType, project);
        // 获取不到类定义，处理方式和 java 内建类型一致
        if (psiClass == null) {
            result = new ApiBodyDTO();
            result.setName(returnType.getPresentableText());
            result.setType(returnType.getPresentableText());
            return result;
        }
        List<ApiBodyDTO> resultItem = new LinkedList<>();
        // 遍历类的每一个字段，解析后添加到 resultItem 中
        for (PsiField field : psiClass.getFields()) {
            // getObjBody 用于解析返回 ApiBodyDTO 定义
            resultItem.add(getObjBody(field, project));
        }
        result.setBodyDto(resultItem);
        return result;
    }

    // 递归解析字段构建 ApiBodyDTO 对象
    private static ApiBodyDTO getObjBody(PsiField psiField, Project project) {
        PsiType returnType = psiField.getType();
        // 如果字段类型是 Java 内建类型，直接返回
        if (Constants.basicType.contains(returnType.getPresentableText())) {
            return getBodyDTOByField(psiField);
        }
        PsiClass psiClass = getPsiClassByType(psiField.getType(), project);
        if (psiClass == null) {
            return getBodyDTOByField(psiField);
        }
        ApiBodyDTO result = new ApiBodyDTO();
        List<ApiBodyDTO> resultItem = new LinkedList<>();
        // 如果是对象类型，递归解析对象的每一个字段
        for (PsiField field : psiClass.getFields()) {
            resultItem.add(getObjBody(field, project));
        }
        result.setBodyDto(resultItem);
        return result;
    }

    private static ApiBodyDTO getBodyDTOByField(PsiField psiField) {
        ApiBodyDTO item = new ApiBodyDTO();
        item.setName(psiField.getName());
        item.setType(psiField.getType().getPresentableText());
        PsiDocComment docComment = psiField.getDocComment();
        if (docComment != null) {
            item.setDesc(docComment.getText());
        }
        return item;
    }

    private static PsiClass getPsiClassByType(PsiType returnType, Project project) {
        // JavaPsiFacade.findClass() 方法在 PSI 中非常重要
        // 可通过类路径，获取到类的 PsiClass 对象
        return JavaPsiFacade.getInstance(project).findClass(returnType.getCanonicalText(), GlobalSearchScope.allScope(project));
    }

    public static ApiBodyDTO getReqBody(PsiMethod psiMethod, Project project) {
        for (PsiParameter parameter : psiMethod.getParameterList().getParameters()) {
            PsiAnnotation annotation = parameter.getAnnotation(Constants.RequestBody);
            if (annotation != null) {
                return doGetRequestBody(parameter, project);
            }
        }
        return null;
    }

    public static ApiBodyDTO doGetRequestBody(PsiParameter parameter, Project project) {
        PsiClass psiClass = getPsiClassByType(parameter.getType(), project);
        if (psiClass == null) {
            return null;
        }
        ApiBodyDTO result = new ApiBodyDTO();
        List<ApiBodyDTO> resultItem = new LinkedList<>();
        for (PsiField filed : psiClass.getAllFields()) {
            if (Constants.basicType.contains(filed.getType().getPresentableText())) {
                ApiBodyDTO item = new ApiBodyDTO();
                item.setName(filed.getName());
                item.setType(filed.getType().getPresentableText());
                PsiDocComment docComment = filed.getDocComment();
                if (docComment != null) {
                    item.setDesc(docComment.getText());
                }
                resultItem.add(item);
            } else {
                resultItem.add(getObjBody(filed, project));
            }
        }
        result.setBodyDto(resultItem);
        return result;
    }

}
