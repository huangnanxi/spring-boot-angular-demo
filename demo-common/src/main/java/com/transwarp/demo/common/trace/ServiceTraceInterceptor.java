package com.transwarp.demo.common.trace;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.transwarp.demo.common.entity.AppException;
import com.transwarp.demo.common.entity.Result;
import com.transwarp.demo.common.response.ErrorCode;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Created by huangnx on 2018/8/26.
 */
@Aspect
@Component
public class ServiceTraceInterceptor {
    /** logger */
    private static final Logger logger     = LoggerFactory.getLogger(ServiceTraceInterceptor.class);

    /** 调用时间阈值 */
    private long                threshold  = 1000L;

    /** 需要过滤的参数 */
    private String[]            filterArgs;

    /** 实现一个FastJSON过滤器 */
    private ValueFilter         nameFilter = new ValueFilter() {
        @Override
        public Object process(Object source, String name, Object value) {
            Object result = value;

            if (filterArgs == null || value == null) {
                return result;
            }

            try {
                for (String filterArg : filterArgs) {
                    if (name.equals(filterArg) && ClassUtils
                            .isAssignableValue(String.class, value)) {
                        result = ((String) value).length() + "L";

                        break;
                    }
                }
            } catch (Throwable t) {
                logger.error("FastJSON过滤异常", t);
            }

            return result;
        }
    };

    @Pointcut("@within(com.transwarp.demo.common.trace.ServiceTrace)")
    public void traceLog(){}

    /**
     * {@inheritDoc}
     */
    @Around("traceLog()")
    public Object invoke(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;

        Signature s = pjp.getSignature();
        MethodSignature ms = (MethodSignature)s;
        Method method = ms.getMethod();

        String methodName = pjp.getClass().getSimpleName() + "." + method.getName();
        TraceLog traceLog = new TraceLog(logger, methodName, this.threshold);

        if (logger.isDebugEnabled()) {
            logger.debug("ME:{}|Begin|ARGS:{}", methodName,
                    JSON.toJSONString(pjp.getArgs(), this.nameFilter));
        }

        try {
            traceLog.begin();
            result = pjp.proceed();

            // 返回结果异常判断
            if (ClassUtils.isAssignable(Result.class, method.getReturnType())) {
                Result newResult = (Result) result;
                if (!newResult.getSuccess()) {
                    traceLog.setExType("A");
                    traceLog.setErrorCode(newResult.getErrorCode());
                }
            }
        } catch (AppException appEx) {
            traceLog.setExType("A");
            traceLog.setErrorCode(appEx.getErrorCode());
            result = this.handleException(method, appEx, appEx.getErrorCode(), appEx.getMessage(), appEx.getArgs());
        } catch (Throwable t) {
            traceLog.setExType("T");
            logger.error("服务拦截器调用" + methodName + "异常！", t);
            result = this.handleException(method, t, ErrorCode.ERROR_SERVICE_INTERCEPTOR_INVOKE,
                    ErrorCode.ERROR_SERVICE_INTERCEPTOR_INVOKE_MSG, null);
        } finally {
            traceLog.end();

            if (logger.isDebugEnabled()) {
                logger.debug("ME:{}|End|Result:{}", methodName, JSON.toJSONString(result, this.nameFilter));
            }

            traceLog.log();
        }

        return result;
    }

    /**
     * 处理异常
     * @param method 方法
     * @param t 异常
     * @param errorCode 错误码
     * @param message 错误信息
     * @return 调用结果
     */
    private Object handleException(Method method, Throwable t, String errorCode, String message, String[] args) {
        Object result = null;

        if (ClassUtils.isAssignable(Result.class, method.getReturnType())) {
            result = BeanUtils.instantiateClass(method.getReturnType());
            ((Result) result).fail(errorCode, message);

            return result;
        } else {
            throw new AppException(errorCode, message);
        }
    }

    /**
     * 设置调用时间阈值
     * @param threshold 调用时间阈值
     */
    public void setThreshold(long threshold) {
        this.threshold = threshold;
    }

    /**
     * @param filterArgs the filterArgs to set
     */
    public void setFilterArgs(String[] filterArgs) {
        this.filterArgs = filterArgs;
    }
}
