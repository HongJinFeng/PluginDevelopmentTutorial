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
        for (PsiParameter parameter : psiMethod.getParameterList().getParameters()) {
            PsiAnnotation annotation = parameter.getAnnotation(Constants.RequestParam);
            if (annotation != null) {
                return doGetParams(psiMethod, parameter, project);
            }
        }
        return null;
    }

    private static List<ApiRequestParamDTO> doGetParams(PsiMethod psiMethod, PsiParameter parameter, Project project) {
        String type = parameter.getType().getPresentableText();
        List<ApiRequestParamDTO> result = new LinkedList<>();
        if (Constants.basicType.contains(type)) {
            result.add(getBasicTypeParamDTO(psiMethod, parameter, type));
            return result;
        }
        PsiClass psiClass = getPsiClassByType(parameter.getType(), project);
        if (psiClass == null) {
            return null;
        }
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
        ApiRequestParamDTO result = new ApiRequestParamDTO();
        result.setName(parameter.getName());
        result.setType(type);
        result.setDesc(getParamDesc(psiMethod, result.getName()));
        return result;
    }

    public static String getParamDesc(PsiMethod psiMethod, String paramName) {
        if (psiMethod == null) {
            return "";
        }
        PsiDocComment docComment = psiMethod.getDocComment();
        if (docComment == null) {
            return "";
        }
        PsiDocTag[] psiDocTags = docComment.getTags();
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
        if (Constants.basicType.contains(returnType.getPresentableText())) {
            ApiBodyDTO result = new ApiBodyDTO();
            result.setName(returnType.getPresentableText());
            result.setType(returnType.getPresentableText());
            return result;
        }
        ApiBodyDTO result = new ApiBodyDTO();
        PsiClass psiClass = getPsiClassByType(returnType, project);
        if (psiClass == null) {
            result = new ApiBodyDTO();
            result.setName(returnType.getPresentableText());
            result.setType(returnType.getPresentableText());
            return result;
        }
        List<ApiBodyDTO> resultItem = new LinkedList<>();
        for (PsiField field : psiClass.getFields()) {
            resultItem.add(getObjBody(field, project));
        }
        result.setBodyDto(resultItem);
        return result;
    }

    private static ApiBodyDTO getObjBody(PsiField psiField, Project project) {
        PsiType returnType = psiField.getType();
        if (Constants.basicType.contains(returnType.getPresentableText())) {
            return getBodyDTOByField(psiField);
        }
        PsiClass psiClass = getPsiClassByType(psiField.getType(), project);
        if (psiClass == null) {
            return getBodyDTOByField(psiField);
        }
        ApiBodyDTO result = new ApiBodyDTO();
        List<ApiBodyDTO> resultItem = new LinkedList<>();
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
