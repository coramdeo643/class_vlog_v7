package com.tenco.blog._core.interceptor;

import com.tenco.blog._core.utils.GetClientIP;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

/**
 * IP 기반 접근 제어를 위한 Spring Web MVC 인터셉터.
 * 특정 IP 주소에서 들어오는 요청만 허용하도록 설정합니다.
 */
@Component // 1. IoC(Inversion of Control) 컨테이너에 빈(Bean)으로 등록합니다. 스프링이 이 클래스의 인스턴스를 생성하고 관리합니다.
public class IPAccessInterceptor implements HandlerInterceptor { // 2. HandlerInterceptor 인터페이스를 구현하여 HTTP 요청을 가로채는 인터셉터 클래스를 정의합니다.

	private static final Logger log = LoggerFactory.getLogger(IPAccessInterceptor.class);
	// 3. 허용할 IP 주소 목록을 정의합니다. "127.0.0.1"은 IPv4 로컬호스트, "::1"은 IPv6 로컬호스트를 의미합니다.
	private final List<String> allowedIPs = Arrays.asList("0:0:0:0:0:0:0:1", "192.168.0.126");

	@Override // 4. 부모 인터페이스의 메서드를 재정의합니다.
	// 5. preHandle 메서드는 컨트롤러의 메서드(핸들러)가 실행되기 전에 호출됩니다.
	//    true를 반환하면 요청 처리가 계속되고, false를 반환하면 중단됩니다.
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 6. 유틸리티 클래스를 사용하여 요청한 클라이언트의 IP 주소를 가져옵니다.
		String clientIP = GetClientIP.getClientIP(request);
		log.info("IP : {}", clientIP);
		// 7. 클라이언트의 IP가 허용된 IP 목록에 포함되어 있지 않은지 확인합니다.
		if(!allowedIPs.contains(clientIP)) {
			// 8. 접근이 허용되지 않은 경우, HTTP 상태 코드 403(Forbidden)과 함께 "Access Denied" 메시지를 응답으로 보냅니다.
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
			return false; // 9. false를 반환하여 요청 처리를 중단하고, 컨트롤러로 요청이 전달되지 않도록 합니다.
		}
		return true; // 10. 클라이언트의 IP가 허용된 목록에 있으면 true를 반환하여 요청 처리를 계속 진행합니다.
	}
}