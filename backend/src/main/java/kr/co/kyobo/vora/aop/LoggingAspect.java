package kr.co.kyobo.vora.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kyobo.vora.common.ParamConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Aspect
@Service
public class LoggingAspect {
	/**
	 * 서비스에서 메소드가 실행되기 전에 파라메터를 납치하여 확인한다
	 *
	 * @param joinPoint
	 * @throws IOException
	 */
	@Before("execution(* kr.co.kyobo.vora.service..*.*(..))")
	public void logBefore(JoinPoint joinPoint) throws IOException {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		String uri = request.getRequestURI().toString();

		log.info(ParamConstants.BEFORE_LINE);
		log.info(ParamConstants.REQUEST_URI + uri);
		log.info(ParamConstants.LINE);

		log.info(ParamConstants.HIJECKED + LoggingAspect.class.getName() + ParamConstants.COMMA + joinPoint.getSignature().getName() + ParamConstants.METHODS);
		Object[] args = joinPoint.getArgs();

		if (args.length > 0) {
			log.info(ParamConstants.PARAMS_START);
			for (int i = 0; i < args.length; i++) {
				if(args[i].toString() != null){
					log.info(ParamConstants.AGRS_START + i + ParamConstants.AGRS_END + args[i].toString());
				}else{
					log.info(ParamConstants.AGRS_START + i + ParamConstants.AGRS_END + "null");
				}

			}
			log.info(ParamConstants.PARAMS_END);
		} else {
			log.info(ParamConstants.AGRS_UNDEFINED);
		}
	}

	/**
	 * method 실행 시간 확인, @Around : 핵심업무 전후에 자동호출, ProceedingJoinPoint
	 *
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* kr.co.kyobo.vora.service..*.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		// 핵심업무 실행 전
		long start = System.currentTimeMillis();
		// 핵심업무 실행
		Object result = pjp.proceed();
		// 핵심업무 실행 후
		long end = System.currentTimeMillis();
		// 핵심업무 실행시간 연산
		log.info(ParamConstants.EXECUTE_START + (end - start) + ParamConstants.EXECUTE_END);
		log.info(ParamConstants.AROUND_LINE);
		return result;
	}

	/**
	 * 서비스의 메소드가 실행된 후 결과를 뿌려준다
	 *
	 * @param joinPoint
	 * @param result
	 * @throws JsonProcessingException
	 */
	@AfterReturning(
			pointcut = "execution(* kr.co.kyobo.vora.service..*.*(..))", returning = ParamConstants.RESULT)
	public void logAfterReturning(JoinPoint joinPoint, Object result) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		log.info(ParamConstants.METHOD_RESULT);
		log.info(ParamConstants.AFTER_RETURNING);
		log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
		log.info(ParamConstants.LINE);
	}

}
