package zhijianhu.libraryserver.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import zhijianhu.entity.Activity;
import zhijianhu.entity.UserContext;
import zhijianhu.libraryserver.annotation.LogActivity;
import zhijianhu.libraryserver.mapper.ActivityMapper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;

@Aspect
@Component
public class ActivityLogAspect {

    @Autowired
    private ActivityMapper activityMapper;
    
    private final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 定义切点：所有带有@LogActivity注解的方法
     */
    @Pointcut("@annotation(zhijianhu.libraryserver.annotation.LogActivity)")
    public void activityLogPointcut() {
    }

    /**
     * 方法返回后记录活动
     */
    @AfterReturning(pointcut = "activityLogPointcut()", returning = "result")
    public void logActivity(JoinPoint joinPoint, Object result) {
        try {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            
            // 获取注解
            LogActivity logActivity = method.getAnnotation(LogActivity.class);
            if (logActivity == null) {
                return;
            }
            // 获取方法参数名和参数值
            String[] paramNames = signature.getParameterNames();
            Object[] args = joinPoint.getArgs();
            
            // 创建SpEL表达式上下文
            EvaluationContext context = new StandardEvaluationContext();
//            只有code==1时记录
            String condition = logActivity.condition();
            if (!condition.isEmpty()) {
                Expression conditionExp = parser.parseExpression(condition);
                Boolean shouldLog = conditionExp.getValue(context, Boolean.class);
                if (shouldLog != null && !shouldLog) {
                    return; // 不满足条件，不记录
                }
            }
             // 处理批量操作
            if (logActivity.batch() && !logActivity.batchItems().isEmpty()) {
                Expression batchItemsExp = parser.parseExpression(logActivity.batchItems());
                Object items = batchItemsExp.getValue(context);

                if (items instanceof Collection) {
                    Collection<?> collection = (Collection<?>) items;
                    for (Object item : collection) {
                        context.setVariable("item", item);

                        String batchDesc = logActivity.batchDescription();
                        Expression batchDescExp = parser.parseExpression(batchDesc, new TemplateParserContext());
                        String itemDescription = batchDescExp.getValue(context, String.class);

                        // 创建并保存每个批量项的活动记录
                        Activity activity = new Activity();
                        activity.setActivityType(logActivity.type().getCode());
                        activity.setContent(itemDescription);
                        activity.setTime(new Date());
                        activity.setUserId(UserContext.getUserId());

                        activityMapper.insert(activity);
                    }
                    return; // 已处理批量操作，不再执行后续的单条记录
                }
            }
            for (int i = 0; i < paramNames.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            
            // 如果有返回值，也加入上下文
            if (result != null) {
                context.setVariable("result", result);
            }
            
            // 解析活动描述
            String description = logActivity.description();
            if (!description.isEmpty()) {
                Expression expression = parser.parseExpression(description, new TemplateParserContext());
                description = expression.getValue(context, String.class);
            }
            
            // 获取当前登录用户ID
            Integer userId = UserContext.getUserId();
            
            // 创建活动记录
            Activity activity = new Activity();
            activity.setActivityType(logActivity.type().getCode());
            activity.setContent(description);
            activity.setTime(new Date());
            activity.setUserId(userId);
            
            // 从上下文中提取bookId和adminId（如果有）
            if (context.lookupVariable("bookId") != null) {
                activity.setBookId((Long) context.lookupVariable("bookId"));
            }
            
            if (context.lookupVariable("adminId") != null) {
                activity.setAdminId((Long) context.lookupVariable("adminId"));
            }
            
            // 保存活动记录
            activityMapper.insert(activity);
            
        } catch (Exception e) {
            // 记录日志但不影响业务流程
            e.printStackTrace();
        }
    }
    /**
     * 模板解析上下文，用于支持SpEL表达式中的模板语法
     */
    private static class TemplateParserContext implements org.springframework.expression.ParserContext {
        @Override
        public boolean isTemplate() {
            return true;
        }

        @Override
        public String getExpressionPrefix() {
            return "{";
        }

        @Override
        public String getExpressionSuffix() {
            return "}";
        }
    }
}