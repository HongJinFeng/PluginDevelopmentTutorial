package org.example.action;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import org.example.dto.ApiBodyDTO;
import org.example.dto.ApiDTO;
import org.example.dto.ApiRequestParamDTO;
import org.example.util.ApiUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GenAPIDocAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        // 插入符号处如果不是 PsiMethod ，不处理。
        if (!(psiElement instanceof PsiMethod)) {
            return;
        }
        // 如果是，转换为 PsiMethod
        PsiMethod psiMethod = (PsiMethod) psiElement;
        // 如果不是 controller API 方法，不处理
        if (!ApiUtils.isRestfulApiMethod(psiMethod)) {
            return;
        }
        // 如果是 API 方法，创建 ApiDTO 对象，用于表示 API 文档信息
        ApiDTO apiDTO = new ApiDTO();
        String path = ApiUtils.getPath(psiMethod);
        String desc = ApiUtils.getDesc(psiMethod);
        String method = ApiUtils.getHTTPMethod(psiMethod);
        List<ApiRequestParamDTO> params = ApiUtils.getParams(psiMethod, e.getProject());
        ApiBodyDTO respBody = ApiUtils.getRespBody(psiMethod, e.getProject());
        ApiBodyDTO reqBody = ApiUtils.getReqBody(psiMethod, e.getProject());
        apiDTO.setPath(path);
        apiDTO.setDesc(desc);
        apiDTO.setMethod(method);
        apiDTO.setParams(params);
        apiDTO.setRequestBody(reqBody);
        apiDTO.setResponseBody(respBody);
        Notifications.Bus.notify(new Notification("Print", "接口文档", apiDTO.toString(), NotificationType.INFORMATION), e.getProject());
    }
}
