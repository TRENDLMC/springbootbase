package hello.hellospring.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint)throws Throwable{
        long Start=System.currentTimeMillis();
        System.out.println("START="+joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish=System.currentTimeMillis();
            long timeMs=finish-Start;
            System.out.println("END:"+joinPoint.toString()+""+timeMs+"ms");
        }
    }
}
