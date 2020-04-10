package todolist.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	// Para sincronizar salidas por consola con Logger y no con
	// System.out.println
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// En el packete controller, se aplica a todas las clases, todos los metodos, no
	// importa lo que devuelvan, no importa que o cuantos argumentos tengan
	@Pointcut("execution(* todolist.controller.*.*(..))")
	private void forControllerPackage() {
	}

	@Pointcut("execution(* todolist.repository.*.*(..))")
	private void forRepositoryPackage() {
	}

	@Pointcut("execution(* todolist.service.*.*(..))")
	private void forServicePackage() {
	}

	@Pointcut("forControllerPackage() || forRepositoryPackage() || forServicePackage()")
	private void forAppFlow() {
	}

	@Before("forAppFlow()")
	private void before(JoinPoint theJoinPoint) {

		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("==============> @Before ==============>" + theMethod);

		Object[] args = theJoinPoint.getArgs();
		for (Object tempArgs : args) {
			myLogger.info("==============> Argument= " + tempArgs);
		}

	}

	@AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
	private void afterReturning(JoinPoint theJoinPoint, Object theResult) {

		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("==============> @AfterReturning ==============>" + theMethod);

		myLogger.info("==============> Result= " + theResult);

	}

}
